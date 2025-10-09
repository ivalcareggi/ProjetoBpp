# Relatório de Qualidade - Projeto de Rastreamento de Hábitos (FGH)

Este documento resume a aplicação de boas práticas de código, os "code smells" identificados e as refatorações realizadas para melhorar a manutenibilidade, legibilidade e qualidade geral do código-fonte do projeto.

## 1. Aplicação de Código Limpo

Aderimos aos princípios de Código Limpo para garantir que a base de código seja compreensível e fácil de manter.

### Nomenclatura
-   **Exemplos de bons nomes utilizados**:
    -   Classes de Domínio: `Habito`, `DiasHabito`, `FrequenciaService`. Nomes refletem claramente seu propósito no domínio do problema.
    -   Métodos: `calcularDiasEsperados`, `buscarMaiorStreak`, `analisarCorrelacoes`. Nomes verbosos que descrevem a ação e o que é retornado.
    -   Exceções: `ResourceNotFoundException`. Nome explícito para uma situação de erro específica.
    -   DTOs: `TaxaSucessoDTO`, `MelhorDesempenhoDTO`. Sufixo `DTO` e nomes que indicam claramente os dados que carregam.
-   **Convenções adotadas**:
    -   Seguimos as convenções padrão do Java: `PascalCase` para classes e `camelCase` para métodos e variáveis.
    -   Evitamos abreviações e "números mágicos", preferindo constantes nomeadas ou variáveis descritivas.

### Estrutura de Funções
-   **Tamanho médio das funções**: A maioria das funções nos controllers e serviços, especialmente após as refatorações, é mantida curta e focada (geralmente < 15 linhas), com exceções notáveis em lógicas complexas que são candidatas a futuras refatorações (`analisarCorrelacoes`).
-   **Exemplos de funções bem estruturadas**:
    -   **Responsabilidade Única**: O método refatorado `listarTodos()` nos controllers (`HabitoController`, `DiasHabitoController`) agora apenas delega a chamada, exemplificando perfeitamente a responsabilidade única.
    -   **Coesão**: Os métodos privados extraídos em `FrequenciaService` (e.g., `calcularDiariamente`, `calcularDiasEspecificos`) são altamente coesos, cada um lidando com uma única forma de cálculo.

### Formatação
-   **Padrões de indentação**: O projeto utiliza indentação consistente com 4 espaços, seguindo o padrão da comunidade Java, garantido pela formatação automática da IDE.
-   **Organização visual do código**:
    -   A estrutura de pacotes (`controllers`, `services`, `models`, `repository`) organiza o código de forma clara por camada, facilitando a navegação.
    -   Linhas em branco são utilizadas para separar blocos lógicos dentro dos métodos, melhorando a legibilidade.

## 2. Code Smells Identificados

A tabela abaixo resume os principais "code smells" identificados durante a análise do projeto.

| Code Smell                                 | Localização                                                                                             | Severidade | Status                 |
| ------------------------------------------ | ------------------------------------------------------------------------------------------------------- | ---------- | ---------------------- |
| Long Method (Método Longo)                 | `FrequenciaService.calcularDiasEsperados()`                                                             | Média      | **Corrigido (Sprint 4)** |
| Duplicate Code (Código Duplicado)          | `HabitoController.listarTodos()`, `DiasHabitoController.listarTodos()`                                    | Baixa      | **Corrigido (Sprint 4)** |
| Inappropriate Intimacy / Feature Envy      | `HabitoDTO.getHabito()`, `DiasHabitoDTO.getDiasHabito()`                                                 | Média      | Pendente               |
| Long Method (Método Longo)                 | `AnalyticsService.analisarCorrelacoes()`                                                                | Alta       | Pendente               |

## 3. Refatorações Realizadas

As seguintes refatorações foram aplicadas para corrigir os code smells identificados e melhorar a qualidade geral do código.

### Refatoração 1: Extract Method em FrequenciaService

**Antes:** O método `calcularDiasEsperados` continha toda a lógica de cálculo em um `switch` extenso.
```java
// Em FrequenciaService.java
public int calcularDiasEsperados(Habito habito, LocalDate inicio, LocalDate fim) {
    TipoFrequencia tipo = habito.getTipoFrequencia();
    String config = habito.getConfigFrequencia();
    int diasEsperados = 0;
    LocalDate dataInicioCalculo = inicio.isBefore(habito.getDataCriacao()) ? habito.getDataCriacao() : inicio;
    if (dataInicioCalculo.isAfter(fim)) {
        return 0;
    }
    switch (tipo) {
        case DIARIAMENTE:
            diasEsperados = (int) ChronoUnit.DAYS.between(dataInicioCalculo, fim) + 1;
            break;
        // ... outros casos com lógica complexa ...
    }
    return diasEsperados;
}
````

**Depois:** A lógica de cada `case` foi extraída para seu próprio método privado, tornando o método principal um distribuidor de chamadas.

```java
// Em FrequenciaService.java
public int calcularDiasEsperados(Habito habito, LocalDate inicio, LocalDate fim) {
    LocalDate dataInicioCalculo = inicio.isBefore(habito.getDataCriacao()) ? habito.getDataCriacao() : inicio;
    if (dataInicioCalculo.isAfter(fim)) {
        return 0;
    }

    switch (habito.getTipoFrequencia()) {
        case DIARIAMENTE:
            return calcularDiariamente(dataInicioCalculo, fim);
        case DIAS_ESPECIFICOS:
            return calcularDiasEspecificos(habito.getConfigFrequencia(), dataInicioCalculo, fim);
        case INTERVALO_DE_DIAS:
            return calcularIntervaloDeDias(habito, dataInicioCalculo, fim);
        case VEZES_POR_SEMANA:
            return calcularVezesPorSemana(habito.getConfigFrequencia(), dataInicioCalculo, fim);
        default:
            return 0;
    }
}

private int calcularDiariamente(LocalDate inicio, LocalDate fim) {
    // ... implementação ...
}

private int calcularDiasEspecificos(String config, LocalDate inicio, LocalDate fim) {
    // ... implementação ...
}
// ... outros métodos privados ...
```

### Refatoração 2: Eliminação de Código Duplicado nos Controllers

**Antes:** Os métodos `listarTodos()` em ambos os controllers continham a mesma lógica de mapeamento.

```java
// Em HabitoController.java
@GetMapping
public ResponseEntity<List<HabitoDTO>> listarTodos() {
    List<Habito> habitos = habitoService.listarTodos();
    List<HabitoDTO> habitosDto = habitos.stream()
        .map(HabitoDTO::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok(habitosDto);
}

// Em DiasHabitoController.java
@GetMapping
public ResponseEntity<List<DiasHabitoDTO>> listarTodos() {
    List<DiasHabito> habitos = diasHabitoService.listarTodos();
    List<DiasHabitoDTO> habitosDto = habitos.stream()
        .map(DiasHabitoDTO::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok(habitosDto);
}
```

**Depois:** A lógica foi movida para novos métodos na camada de serviço, e os controllers foram simplificados.

**1. Lógica Centralizada nos Serviços:**

```java
// Adicionado em HabitoService.java
public List<HabitoDTO> listarTodosComoDTO() {
    return listarTodos().stream()
            .map(HabitoDTO::new)
            .collect(Collectors.toList());
}

// Adicionado em DiasHabitoService.java
public List<DiasHabitoDTO> listarTodosComoDTO() {
    return listarTodos().stream()
            .map(DiasHabitoDTO::new)
            .collect(Collectors.toList());
}
```

**2. Controllers Simplificados:**

```java
// Em HabitoController.java (refatorado)
@GetMapping
public ResponseEntity<List<HabitoDTO>> listarTodos() {
    List<HabitoDTO> habitosDto = habitoService.listarTodosComoDTO();
    return ResponseEntity.ok(habitosDto);
}

// Em DiasHabitoController.java (refatorado)
@GetMapping
public ResponseEntity<List<DiasHabitoDTO>> listarTodos() {
    List<DiasHabitoDTO> habitosDto = diasHabitoService.listarTodosComoDTO();
    return ResponseEntity.ok(habitosDto);
}
```

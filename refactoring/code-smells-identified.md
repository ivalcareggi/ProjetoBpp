# Code Smells Identificados

## 1. Long Method (Método Longo)
- **Arquivo**: `src/main/java/com/project/fgh/services/FrequenciaService.java`
- **Linhas**: Aprox. 55-121 (no método `calcularDiasEsperados`)
- **Descrição**: O método `calcularDiasEsperados` possui uma estrutura `switch` longa e complexa, com a lógica de cálculo para cada tipo de frequência diretamente dentro dos `case`. Isso torna o método difícil de ler e modificar.
- **Severidade**: Média
- **Status**: **Corrigido na Sprint 4**

## 2. Duplicate Code (Código Duplicado)
- **Arquivos**: `src/main/java/com/project/fgh/controllers/HabitoController.java` e `src/main/java/com/project/fgh/controllers/DiasHabitoController.java`
- **Linhas**: Aprox. 40-44 (em `HabitoController`) e 38-42 (em `DiasHabitoController`)
- **Descrição**: A lógica para listar todas as entidades, que envolve buscar no serviço, mapear para DTOs usando `stream().map()` e retornar `ResponseEntity.ok()`, é praticamente idêntica em ambos os controllers.
- **Severidade**: Baixa
- **Status**: **Corrigido na Sprint 4**

## 3. Inappropriate Intimacy / Feature Envy (Intimidade Inapropriada)
- **Arquivos**: `src/main/java/com/project/fgh/models/dto/HabitoDTO.java` e `src/main/java/com/project/fgh/models/dto/DiasHabitoDTO.java`
- **Linhas**: Aprox. 29 (em `HabitoDTO`) e 36 (em `DiasHabitoDTO`)
- **Descrição**: Os DTOs possuem métodos (`getHabito`, `getDiasHabito`) que sabem como construir uma Entidade. A responsabilidade de um DTO é carregar dados; a conversão de DTO para Entidade deveria estar em uma camada de serviço ou em uma classe Mapper dedicada, não no próprio DTO.
- **Severidade**: Média
- **Status**: Pendente

## 4. Long Method (Método Longo)
- **Arquivo**: `src/main/java/com/project/fgh/services/AnalyticsService.java`
- **Linha**: Aprox. 80-136 (no método `analisarCorrelacoes`)
- **Descrição**: O método de análise de correlações é extenso e possui múltiplas responsabilidades aninhadas: buscar dados, agrupar por data, iterar em pares de hábitos, contar ocorrências e calcular a probabilidade.
- **Severidade**: Alta
- **Status**: Pendente
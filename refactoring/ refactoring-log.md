# Log de Refatorações

## Refatoração #1: Extract Method em FrequenciaService
- **Data**: 2025-10-09
- **Code Smell**: Long Method em `FrequenciaService.calcularDiasEsperados()`
- **Técnica Aplicada**: Extract Method
- **Arquivos Afetados**: `src/main/java/com/project/fgh/services/FrequenciaService.java`
- **Justificativa**: O método `calcularDiasEsperados` continha uma estrutura `switch` com toda a lógica de cálculo para os diferentes tipos de frequência (DIARIAMENTE, DIAS_ESPECIFICOS, etc.). Isso tornava o método longo (mais de 40 linhas), com baixa coesão e difícil de ler e manter.
- **Resultado**:
  - O método principal `calcularDiasEsperados()` foi reduzido para ~10 linhas, atuando apenas como um distribuidor de chamadas.
  - A lógica foi extraída para 4 novos métodos privados: `calcularDiariamente()`, `calcularDiasEspecificos()`, `calcularIntervaloDeDias()` e `calcularVezesPorSemana()`.
- **Impacto**: A legibilidade do código foi drasticamente melhorada. Cada método agora possui uma responsabilidade única e clara, o que facilita a criação de testes unitários e a compreensão da lógica de negócio.
- **Testes**: Funcionalidade validada via testes manuais com cURL. Os cálculos no endpoint de `taxa-sucesso` permaneceram corretos, confirmando que a refatoração não introduziu regressões.

## Refatoração #2: Centralização de Lógica nos Serviços
- **Data**: 2025-10-09
- **Code Smell**: Duplicate Code nos métodos `listarTodos()` de `HabitoController` e `DiasHabitoController`.
- **Técnica Aplicada**: Move Method (a lógica de mapeamento foi movida para a camada de serviço), seguindo o princípio DRY (Don't Repeat Yourself).
- **Arquivos Afetados**:
  - `src/main/java/com/project/fgh/controllers/HabitoController.java`
  - `src/main/java/com/project/fgh/controllers/DiasHabitoController.java`
  - `src/main/java/com/project/fgh/services/HabitoService.java`
  - `src/main/java/com/project/fgh/services/DiasHabitoService.java`
- **Justificativa**: Ambos os controllers continham um bloco de código idêntico para buscar uma lista de entidades e convertê-la para uma lista de DTOs usando `stream().map().collect()`.
- **Resultado**:
  - Foram criados os métodos `listarTodosComoDTO()` em `HabitoService` e `DiasHabitoService`, centralizando a lógica de conversão.
  - Os métodos `listarTodos()` nos controllers foram simplificados para apenas uma chamada ao novo método do serviço.
- **Impacto**: Eliminação completa do código duplicado. Melhorou a separação de responsabilidades (SoC), deixando os controllers mais enxutos e focados em lidar com requisições HTTP, enquanto a camada de serviço lida com a lógica de negócio e transformação de dados.
- **Testes**: Funcionalidade validada. Endpoints `GET /habitos` e `GET /diashabito` continuam retornando as listas de DTOs formatadas corretamente.
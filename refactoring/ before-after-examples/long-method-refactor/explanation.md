## Refatoração: Extração de Método

O `code smell` identificado foi **Long Method** (Método Longo) no método `calcularDiasEsperados` da classe `FrequenciaService`.

A lógica de negócio para cada tipo de frequência estava toda contida dentro de uma única estrutura `switch`, tornando o método extenso e com múltiplas responsabilidades.

A técnica aplicada foi **Extract Method**. A lógica de cada `case` do `switch` foi extraída para seu próprio método privado.

### Benefícios:
- **Legibilidade:** O método principal agora é muito mais curto e fácil de entender, agindo como um "roteador".
- **Responsabilidade Única:** Cada novo método privado tem uma única e clara responsabilidade: calcular os dias esperados para um tipo de frequência específico.
- **Manutenibilidade:** Modificar a lógica de um tipo de frequência agora significa alterar um método pequeno e isolado, reduzindo o risco de introduzir bugs em outras lógicas.
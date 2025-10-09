## Refatoração: Eliminação de Código Duplicado

O `code smell` identificado foi **Duplicate Code** (Código Duplicado) nos métodos `listarTodos()` das classes `HabitoController` e `DiasHabitoController`.

Ambos os métodos continham a mesma lógica para converter uma lista de Entidades em uma lista de DTOs.

A técnica aplicada foi **Move Method**, movendo a responsabilidade da conversão de dados para a camada de Serviço, centralizando a lógica.

### Benefícios:
- **DRY (Don't Repeat Yourself):** A lógica de mapeamento agora existe em um único lugar para cada tipo de entidade, facilitando a manutenção.
- **Melhor Separação de Responsabilidades (SoC):** Os Controllers agora se concentram em gerenciar requisições HTTP e delegar tarefas, enquanto os Serviços cuidam da lógica de negócio e da transformação dos dados.
- **Código Mais Limpo:** Os métodos nos controllers ficaram mais enxutos e declarativos.
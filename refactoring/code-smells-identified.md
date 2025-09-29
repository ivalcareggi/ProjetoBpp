# Code Smells Identificados
## 1. Long Method
- **Arquivo**: src/services/user_service.py
- **Linha**: 45-78
- **Descrição**: Método process_user_registration com 34 linhas
- **Severidade**: Alta
- **Ferramenta**: pylint (função muito complexa)
- **Status**: Corrigido em Sprint 2
## 2. Duplicate Code
- **Arquivos**: src/controllers/auth.py e src/controllers/profile.py
- **Linhas**: 23-31 e 45-53
- **Descrição**: Validação de email duplicada
- **Severidade**: Média
- **Status**: Pendente
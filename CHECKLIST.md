# Checklists de Afazeres
## Checklist da Visão do Produto:
- [x] Define claramente o usuário-alvo
- [x] Identifica o problema específico a ser resolvido
- [x] Explicita o valor único oferecido
- [x] É inspiradora, mas realista para o escopo deste curso e tempo disponível para dedicação ao projeto
- [x] Pode ser desenvolvida em 3-4 meses individualmente ou em grupo de 2 a 4 pessoas

## Backlog
- [x] Identificar e eliminar code smells no módulo principal
- [x] Refatorar funções longas (Long Method)
- [x] Eliminar duplicação de código (Duplicate Code)
- [ ] Melhorar nomenclatura ambígua (Poor Naming)
- [ ] Aplicar princípios SOLID onde apropriado


## Code Smells Prioritários para Identificação:
### Nível Método/Função:
- [x] Long Method: Métodos com mais de 20-30 linhas
- [] Long Parameter List: Mais de 3-4 parâmetros
- [x] Duplicate Code: Código repetido em múltiplos locais
- [] Dead Code: Código não utilizado
### Nível Classe:
- [] Large Class: Classes com muitas responsabilidades
- [] Data Class: Classes apenas com dados, sem comportamento
- [] God Class: Classe que faz tudo
### Nível Estrutural:
- [] Feature Envy: Método mais interessado em outra classe
- [] Inappropriate Intimacy: Classes muito acopladas

## Definition of Done (DoD) por Sprint
### Sprint 1 (Semana 2):
- [X] Funcionalidade implementada
- [X] Código segue convenções de nomenclatura
- [X] Funções pequenas e focadas
- [X] Formatação consistente
- [X] README inicial criado
### Sprint 2 (Semana 3):
- [x] Tudo do Sprint 1 +
- [x] Code smells identificados e catalogados
- [x] Pelo menos 2 code smells corrigidos
- [x] Documentação das correções
### Sprint 3 (Semana 4):
- [x] Tudo do Sprint 2 +
- [] Princípios SOLID aplicados onde apropriado
- [] Estrutura de classes melhorada
- [] Acoplamento reduzido
### Sprint Final (Semana 5):
- [] Tudo do Sprint 3 +
- [x] Pelo menos 5 refatorações documentadas
- [x] Código final limpo e bem estruturado
- [x] Relatório de qualidade completo

## Documentos de Entrega:
- [x] Visão do Produto (PDF, 2-3 páginas)
- [] Product Backlog (PDF)
- [] Relatório de Qualidade de Código (PDF, 3-4 páginas)
- [x] Link para o repositório com o código-fonte do projeto
- [] Vídeo de apresentação (8-10 minutos)
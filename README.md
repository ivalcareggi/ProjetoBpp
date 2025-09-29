# Documentação de Produto: Projeto "FGH"

**Versão:** 1.0 (MVP + Expansão Planejada)
**Data:** 29 de Setembro de 2025

---

## 1. Declaração de Posicionamento (Elevator Pitch)

* **Para** indivíduos ambiciosos e focados em autodesenvolvimento, desde estudantes a profissionais, que buscam uma transformação real e duradoura em suas rotinas.
* **Que** enfrentam a frustração de iniciar novos hábitos com entusiasmo, mas perdem o ímpeto devido à falta de ferramentas que se adaptem às suas vidas, que ofereçam insights reais sobre seu comportamento e que os mantenham motivados após as primeiras semanas.
* **O "FGH"** (nome sugerido) é uma **plataforma inteligente de desenvolvimento de hábitos**.
* **Que** não apenas registra o progresso, mas também oferece flexibilidade total na criação de hábitos (diários, semanais, baseados em tempo ou quantidade) e revela padrões de comportamento através de análises estatísticas.
* **Diferente de** simples aplicativos de checklist ou agendas digitais que são passivos e rígidos, tratando todos os hábitos da mesma forma.
* **Nosso produto** atua como um assistente pessoal, fornecendo insights acionáveis como sua "melhor hora de produtividade" e correlações entre hábitos, transformando o aplicativo de um mero rastreador para um verdadeiro catalisador de crescimento pessoal.

---

## 2. Problema Core (Detalhado)

O problema central não é apenas "lembrar de fazer um hábito", mas sim a complexa batalha contra a inércia, a desmotivação e a falta de autoconhecimento. Usuários enfrentam múltiplos obstáculos:

1.  **Estagnação da Motivação:** A empolgação inicial inevitavelmente diminui. Sem novos gatilhos de motivação, reconhecimento de marcos e uma compreensão clara do progresso acumulado, o usuário tende a abandonar o processo.
2.  **Ciclos de Falha Desconhecidos:** O usuário muitas vezes não sabe *por que* está falhando. Ele não consegue identificar padrões como: "toda quinta-feira eu falho no meu hábito de leitura" ou "quando eu não medito, minha produtividade no trabalho cai". Sem diagnóstico, não há como ajustar a estratégia.

O "FGH" visa resolver esses problemas de forma proativa, sendo uma ferramenta que se adapta ao usuário e o ensina sobre si mesmo.

---

## 3. Funcionalidades Detalhadas

### 3.1. Funcionalidades Essenciais do MVP

#### **a. Gestão Completa de Hábitos**
* **Descrição:** O núcleo do sistema. Permite ao usuário criar, editar, pausar (ativar/desativar) e excluir hábitos. A criação é projetada para ser rápida, mas com acesso imediato às configurações avançadas de tipo e frequência.
* **Mapeamento no Código:** `HabitoService` (`salvar`, `listar`, `excluir`, `ativarDesativar`).

#### **b. Registro de Progresso Diário**
* **Descrição:** A interface principal é um dashboard focado na ação. Para cada hábito agendado para o dia, o usuário pode interagir de forma rápida e intuitiva para registrar seu progresso, seja com um simples check, iniciando um cronômetro ou inserindo uma quantidade.
* **Mapeamento no Código:** `DiasHabitoService` (`salvar`, `iniciarHabito`, `finalizarHabito`, etc).

### 3.2. Expansão Planejada: Módulos Inteligentes

#### **a. Tipos de Hábitos Avançados**
* **Descrição Ampliada:** Reconhecendo que hábitos têm naturezas distintas, o sistema suportará múltiplos tipos para um rastreamento preciso e significativo.
    * **1. Checklist (Binário):** O tipo mais simples. O hábito foi `feito` ou `não feito`. Ideal para ações rápidas como "Tomar vitaminas" ou "Arrumar a cama".
        * *Implementação:* Um simples booleano `concluido` na entidade `DiasHabito`.
    * **2. Duração (Contador de Tempo):** Para hábitos onde o tempo de dedicação é a métrica principal. Exemplos: "Meditar", "Estudar", "Praticar um instrumento". O usuário pode definir uma meta de tempo (ex: 30 min) e o app registra a duração real, permitindo comparações.
        * *Implementação:* Campos `horaInicio` e `horaFim` (`LocalDateTime`) em `DiasHabito`. Um método `finalizarHabito` calcula a diferença e armazena em `duracaoEfetivaMinutos`.
    * **3. Quantidade (Contador Numérico):** Sugestão de novo tipo para hábitos baseados em volume ou repetição. Essencial para metas como "Beber 8 copos de água", "Fazer 50 flexões" ou "Ler 20 páginas". O usuário define uma meta numérica e pode incrementar o contador ao longo do dia.
        * *Implementação:* Campos `metaQuantidade` (`Integer`) em `Habito` e `quantidadeRealizada` (`Integer`) em `DiasHabito`. A interface permitiria botões de `+` e `-` para atualizar o valor.

#### **b. Frequência Inteligente (Ritmos)**
* **Descrição Avançada:** Liberta o usuário da tirania do "diariamente". Esta funcionalidade permite que o hábito se encaixe na vida real do usuário, não o contrário. A lógica de streaks será adaptada para cada tipo de frequência, evitando penalidades injustas.
    * **1. Diariamente:** O padrão clássico.
    * **2. Dias Específicos da Semana:** O usuário seleciona os dias (ex: Seg, Qua, Sex). O hábito só aparecerá no dashboard nesses dias. O streak não quebra se o hábito não for feito em um dia não selecionado.
    * **3. X Vezes por Semana:** O usuário define uma meta semanal (ex: 4 vezes por semana). O app rastreia o cumprimento dentro da janela de Segunda a Domingo. O streak avança se a meta semanal for batida.
    * **4. Intervalo de Dias:** Para hábitos periódicos, como "A cada 3 dias". O sistema calcula dinamicamente o próximo dia de execução.
* **Implementação:** A entidade `Habito` precisará de uma estrutura flexível para armazenar a frequência, possivelmente um campo `tipoFrequencia` (ENUM) e um campo `configFrequencia` (JSON ou String) para guardar os detalhes (`["SEG", "QUA"]` ou `{"tipo": "semanal", "vezes": 4}`). A lógica de `streak` e de exibição diária dependerá dessa configuração.

#### **c. Módulo de Estatísticas e Insights**
* **Descrição Avançada:** Este é o cérebro do aplicativo, transformando dados brutos em autoconhecimento. Será uma seção dedicada a relatórios visuais e insights gerados por algoritmos.
    * **1. Taxa de Sucesso:**
        * *O que é:* Um percentual claro que mostra, para um hábito específico, quantas vezes ele foi completado versus quantas vezes era esperado (baseado em sua frequência).
        * *Benefício:* Fornece uma visão macro do desempenho, muito além do streak atual. Permite identificar hábitos que estão com baixa adesão e precisam de ajuste.
        * *Implementação:* Requer uma query que conte os `DiasHabito` concluídos em um período e divida pelo número de dias em que o hábito era esperado, de acordo com sua regra de `frequencia`.

    * **2. Melhor Dia/Horário:**
        * *O que é:* O sistema analisa os registros de `DiasHabito` (especialmente os de Duração) e identifica padrões. Ele responderá perguntas como: "Em qual dia da semana você mais completa seus hábitos?" ou "Qual o período do dia (manhã, tarde, noite) com maior taxa de sucesso?".
        * *Benefício:* Ajuda o usuário a estruturar sua rotina de forma mais inteligente, alocando os hábitos mais difíceis nos seus picos naturais de energia e disciplina.
        * *Implementação:* Análise agregada dos timestamps de conclusão em `DiasHabito`, agrupando por dia da semana e por faixas de horário (ex: 06h-12h, 12h-18h, 18h-00h).

    * **3. Análise de Correlações (Avançado):**
        * *O que é:* O recurso mais poderoso. O algoritmo busca por causalidade entre os hábitos. Exemplo: "Analisamos seus últimos 90 dias e notamos que nos dias em que você completa o hábito 'Meditar', a probabilidade de você completar o hábito 'Estudar' aumenta em 30%."
        * *Benefício:* Revela o efeito "dominó" dos hábitos (keystone habits), mostrando ao usuário quais pequenas ações geram o maior impacto em todo o seu sistema de produtividade. É um insight extremamente poderoso para otimização de rotina.
        * *Implementação:* Requer uma lógica de análise mais sofisticada. Para cada dia no histórico, o sistema verifica pares de hábitos (Hábito A, Hábito B). Ele calcula a taxa de sucesso de B nos dias em que A foi feito, e compara com a taxa de sucesso de B nos dias em que A não foi feito. Se a diferença for estatisticamente significativa, uma correlação é sugerida.

---

## 4. Requisitos de Código Limpo e Arquitetura

* **Nomenclatura Clara:** Manter a consistência (`HabitoService`, `Frequencia`, `AnalyticsService`).
* **Responsabilidade Única:** Com a adição de funcionalidades, será crucial criar novos serviços. Um `AnalyticsService` deve ser responsável por todas as consultas estatísticas, separando essa lógica complexa do `HabitoService`, que deve focar no CRUD.
* **Coesão e Responsabilidade Única em Nível de Classe:**
    * Evitaremos a criação de **"God Classes"**. À medida que o sistema cresce, novas responsabilidades serão delegadas a novas classes. Por exemplo, em vez de inflar o `HabitoService` com lógicas de análise, criaremos um `AnalyticsService` dedicado. Da mesma forma, a complexidade de calcular datas e streaks para diferentes frequências pode ser encapsulada em um `FrequenciaService` ou em uma classe de estratégia.
    * As classes devem ter um propósito claro e único. Isso evita o surgimento de **"Large Classes"** e torna o sistema mais fácil de entender e modificar.
* **Funções Pequenas e Expressivas:**
    * Manteremos os métodos curtos e focados, idealmente com menos de 20 linhas. Um método como `calcularEstatisticasHabito` será quebrado em funções menores como `calcularTaxaDeSucesso`, `identificarMelhorHorario`, etc. Isso combate o **"Long Method"**.
    * Evitaremos listas de parâmetros extensas (**"Long Parameter List"**). Se um método precisar de mais de 3 ou 4 parâmetros, consideraremos a criação de um objeto de parâmetro (Parameter Object) ou um DTO para encapsular esses dados, tornando a assinatura do método mais limpa e legível.
* **Baixo Acoplamento e Alta Coesão:**
    * As classes devem colaborar sem conhecer os detalhes íntimos umas das outras, evitando **"Inappropriate Intimacy"**. O `AnalyticsService`, por exemplo, não deve manipular o estado interno do `HabitoRepository`; ele deve solicitar os dados de que precisa através de interfaces bem definidas.
    * A lógica deve residir onde os dados estão. Evitaremos o **"Feature Envy"**, onde um método está mais interessado nos dados de outra classe do que na sua própria. Por exemplo, a lógica para determinar se um hábito deve ocorrer "hoje" com base em sua frequência pertence à própria entidade `Habito` ou a uma classe especialista em `Frequencia`, e não a um serviço genérico.
* **DTOs (Data Transfer Objects):** Usar DTOs para expor os dados estatísticos para a API, evitando expor as entidades do banco de dados diretamente e permitindo formatar os dados da maneira que o frontend precisa.
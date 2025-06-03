**📌 Contexto**

Projeto pessoal. A aplicação simula as funcionalidades essenciais de um app de streaming de audio, com foco em arquitetura moderna, código limpo e boas práticas de desenvolvimento Android.

**💡 Solução Proposta**

A proposta consiste em uma aplicação modular, escalável e funcional, capaz de listar músicas e reproduzi-las localmente. As músicas são sincronizadas a partir do Firebase e armazenadas em cache local com Room. A reprodução é feita com o Media3 (ExoPlayer) e a interface é construída com Jetpack Compose, adotando os padrões de arquitetura MVVM e MVI para um fluxo de dados reativo e unidirecional.

**✅ Funcionalidades**

- Listagem de músicas com destaque para a atual

- Reprodução com Media3 (ExoPlayer)

- Controles de mídia: Play, Pause, Próxima, Anterior

- Barra de progresso com seek interativo

- Cache local com Room e sincronização com Firebase

- Injeção de dependência com Hilt

- Arquitetura MVVM + MVI para clareza e reatividade

**🧱 Arquitetura**

A estrutura do projeto adota Clean Architecture adaptada, com foco em modularidade e separação de responsabilidades.

Padrões e Boas Práticas

- MVVM: separação clara entre UI, lógica e dados

- MVI: uso de intents, estados e ViewModel centralizado

- StateFlow: gerenciamento reativo de estados

**🛠️ Tecnologias Utilizadas**

- Kotlin + Jetpack Compose

- Media3 (ExoPlayer)

- Firebase Firestore e Storage

- Room (persistência local)

- Hilt (injeção de dependência)

- Coroutines + StateFlow

- Android ViewModel

**📈 Possíveis Melhorias Futuras**

- Modularização em :app, :core, :data, :domain, :player

- Adição de testes unitários e instrumentados

- Design System centralizado e reutilizável

- Animações com MotionLayout

- Upload com autenticação via Firebase Auth

- Suporte a múltiplos idiomas e temas

- Cache inteligente e suporte offline

- Notificações com controle de mídia

- Monitoramento de erros com Crashlytics

- Suporte a playlists, shuffle e repeat

**🚀 Como Executar**

git clone https://github.com/LeoLobo30/SpotifyAppClone.git

Abra no Android Studio (versão Flamingo ou superior)

Adicione o google-services.json em app/

Execute em um emulador ou dispositivo real

**👤 Desenvolvedor**

Leonardo Gomes Lobo📍 João Pessoa - PB👨‍💻 Android Developer (Kotlin + Jetpack Compose)🔗 LinkedIn

**📝 Considerações Finais**

Este projeto demonstra minha abordagem para construção de aplicações modernas no ecossistema Android. Com foco em escalabilidade, clareza de código e boas práticas, busquei aplicar conceitos utilizados em aplicações reais. Com mais tempo, a aplicação pode ser facilmente estendida com novas features, testes e melhorias de performance.📱


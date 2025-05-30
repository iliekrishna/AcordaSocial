# AcordaSocial: Aplicativo Mobile de Serviço Voluntário
AcordaSocial
AcordaSocial é um aplicativo Android que conecta organizações não governamentais (ONGs) com voluntários dispostos a oferecer seus serviços para causas sociais. Com uma interface simples e intuitiva, o app funciona de maneira semelhante ao LinkedIn, permitindo que ONGs encontrem e se conectem com voluntários interessados em ajudar.

## Funcionalidades
Cadastro de ONGs e Voluntários: Permite que ONGs e voluntários criem perfis personalizados.

Busca e Filtros: Voluntários podem pesquisar ONGs por tipo de serviço, localidade e outras preferências.

Match Voluntário-ONG: Algoritmo de correspondência que sugere ONGs para voluntários e vice-versa.

Painel de Controle: ONGs podem visualizar e gerenciar os voluntários interessados em seus projetos.

Notificações: Notificações em tempo real para atualizações sobre novas oportunidades de voluntariado.

Feedback: Voluntários e ONGs podem avaliar suas experiências após cada serviço.

## Tecnologias Utilizadas
Linguagem: Java ☕

Android Studio: IDE para desenvolvimento de aplicativos Android.

Banco de Dados: Firebase (para armazenamento de dados, autenticação e notificações em tempo real).

Firebase Authentication: Para realizar o login e registro de usuários.

Firebase Realtime Database: Para armazenar e recuperar informações sobre ONGs e voluntários.

Google Maps API: Para localizar ONGs e exibir em um mapa.

Push Notifications: Para enviar notificações para voluntários e ONGs.

## Como Rodar o Projeto
Pré-requisitos
Instalar o Android Studio: Certifique-se de ter o Android Studio instalado.

Configurar o Firebase: Você precisa de uma conta no Firebase. Siga a documentação oficial do Firebase para adicionar o projeto ao console do Firebase e configurar o google-services.json no seu projeto.

SDK do Android: Verifique se o SDK do Android está corretamente configurado no Android Studio.

Passos para executar o projeto
Clone o repositório para sua máquina local:

bash
Copiar
Editar
git clone https://github.com/usuario/AcordaSocial.git
Abra o projeto no Android Studio.

Sincronize o projeto com os arquivos do Gradle, caso seja solicitado.

Certifique-se de que o arquivo google-services.json foi adicionado corretamente na pasta app/.

Execute o projeto no seu dispositivo Android ou em um emulador.

O app estará disponível para testes e desenvolvimento adicionais.

Estrutura do Projeto
app/src/main/java/com/acordasocial/: Contém o código Java da aplicação.

activity: Contém as atividades principais do app, como tela de login, cadastro e painel de controle.

model: Contém as classes de modelo de dados (ex. Usuário, ONG, Voluntário).

adapter: Contém adaptadores para listas e visualizações de dados.

firebase: Contém a lógica de integração com o Firebase.

app/src/main/res/: Contém os recursos de layout, imagens, strings e estilos.

layout: Contém os arquivos XML de layout para cada tela do aplicativo.

values: Contém os arquivos de strings e estilos.

Contribuindo
Faça um fork do projeto.

Crie uma branch para a sua feature (git checkout -b feature/nova-feature).

Faça as alterações e commit (git commit -am 'Adiciona nova feature').

Push para a branch (git push origin feature/nova-feature).

Abra um Pull Request explicando suas alterações.

Licença
Este projeto é licenciado sob a MIT License - veja o arquivo LICENSE para mais detalhes.

Contato
Autor: Ilie Krishna, João Guilherme, Lucas Noguti, Ruryá Henri.

E-mail: <strong>iliekrishna86@gmail.com</strong>

GitHub: github.com/iliekrishna

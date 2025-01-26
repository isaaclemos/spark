# Spark Web Application

Este aplicativo web foi desenvolvido em Java usando o framework [Spark](https://sparkjava.com/) para avaliação durante minha graduação. Ele implementa funcionalidades básicas como autenticação de usuários, controle de sessão e gerenciamento de entidades como grupos, posições, times e jogadores.

## Funcionalidades

- **Autenticação de Usuários**: Login utilizando credenciais estáticas.
- **Gerenciamento de Sessão**: Verificação de login para proteger rotas específicas.
- **Redirecionamento de Rotas**:
  - `/` redireciona para `/grupo`.
  - `/login` redireciona para `/grupo`.
  - `/sair` encerra a sessão do usuário e redireciona para `/grupo`.
- **Controladores**:
  - `GrupoController`: Gerencia as funcionalidades relacionadas a grupos.
  - `PosicaoController`: Gerencia as funcionalidades relacionadas a posições.
  - `TimeController`: Gerencia as funcionalidades relacionadas a times.
  - `JogadorController`: Gerencia as funcionalidades relacionadas a jogadores.

## Tecnologias Utilizadas

- **Java**: Linguagem principal do projeto.
- **Spark Framework**: Framework web leve para criar aplicativos em Java.
- **Velocity Template Engine**: Para renderizar templates HTML dinâmicos.

## Configuração e Execução

### Pré-requisitos

- Java 8 ou superior instalado.
- [Maven](https://maven.apache.org/) instalado para gerenciar dependências.

### Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   cd seu-repositorio
   
2. Compile e execute o projeto usando o Maven:
   ```bash
   mvn clean install
   mvn exec:java


# Testing Microservices in Spring

O projeto tem por objetivo exercitar testes de unidade, integração, api e end-to-end.

## Application Architecture

```
 ╭┄┄┄┄┄┄┄╮      ┌──────────┐      ┌──────────┐
 ┆   ☁   ┆  ←→  │    ☕     │  ←→  │    💾     │
 ┆  Web  ┆ HTTP │  Spring  │      │ Database │
 ╰┄┄┄┄┄┄┄╯      │  Service │      └──────────┘
                └──────────┘
                     ↑ JSON/HTTP
                     ↓
                ┌──────────┐
                │    ☁     │
                │ Weather  │
                │   API    │
                └──────────┘
```

## Get started
Para testar, você precisa setar sua API_KEY como property do projeto. Ela estará disponível em : ~~darksky.net~~ [openweathermap.org](https://openweathermap.org/).

Por padrão, o Spring Boot chama o docker compose up quando a aplicação starta, não se preocupe em configurar um banco de dados.

O projeto utiliza uma docker image **postgres**

Após clonar e configurar sua API_KEY, basta executar o comando ```./mvnw spring-boot:run ``` para subir a aplicação

Para executar os testes execute ```./mvnw test```

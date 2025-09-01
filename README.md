# 📦 DASA Backend API

API backend para **gestão de estoque** e **movimentações de produtos**.  
Projeto desenvolvido com **Spring Boot**, persistência com **JPA/Hibernate**, banco de dados **MySQL** (via Docker) e mensageria com **RabbitMQ**.  
A documentação da API é disponibilizada automaticamente pelo **Swagger**.

---

## 🚀 Tecnologias

- [Java 17+](https://adoptium.net/)  
- [Spring Boot](https://spring.io/projects/spring-boot)  
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)  
- [MySQL](https://www.mysql.com/) (rodando em Docker)  
- [RabbitMQ](https://www.rabbitmq.com/) (rodando em Docker)  
- [Swagger / OpenAPI](https://swagger.io/)  
- [Maven](https://maven.apache.org/)

---

## ⚙️ Funcionalidades

- CRUD de **usuários**, **produtos** e **categorias**  
- Controle de **movimentações de estoque** (entrada e saída)  
- Integração com **RabbitMQ** para disparar eventos de atualização/exclusão  
- Persistência em banco relacional **MySQL**  
- Documentação interativa da API com **Swagger UI**

---

## 🐳 Como rodar o projeto com Docker

### 1. Subir containers necessários
```bash
docker-compose up -d
```
rodar aplicação.

Esse comando vai levantar:
MySQL (porta padrão 3306)
RabbitMQ (porta 5672 para conexão + 15672 para painel de administração)

## 📊 RabbitMQ Management

Acesse o painel do RabbitMQ em:
👉 http://localhost:15672
Usuário e senha padrão (definidos no docker-compose.yml): guest / guest

## 📂 Estrutura do projeto
```bash
 src/main/java/br/com/estoque/dasa
│
├── modules
│   ├── user        # CRUD de usuários
│   ├── product     # CRUD de produtos
│   ├── category    # CRUD de categorias
│   └── logs        # Logs e movimentações de estoque
│
├── config          # Configurações (CORS, RabbitMQ, etc.)
└── DasaApplication.java
```

## 🔌 Endpoints principais

A documentação interativa está disponível em:
👉 http://localhost:8080/swagger-ui.html

Exemplos:
- GET /products → lista todos os produtos
- POST /products → cria um novo produto
- PUT /products/{id} → atualiza um produto
- DELETE /products/{id} → remove um produto
- GET /categories → lista categorias
- POST /categories → cria categoria

## 📬 Eventos via RabbitMQ

A API publica mensagens em filas do RabbitMQ para refletir alterações de estoque.
```json
{
  "id": "2c0fa254-eeb8-4ebb-910c-0f85ea179b73",
  "quantity": "10",
  "nome":"Gabriel Gouvea"
  }
}
```
Exemplo de filas:
fila.produto.atualizacao → disparada quando um produto é atualizado
fila.produto.remocao → disparada quando um produto é removido

# NutriConecta

Projeto acadêmico em **Spring Boot MVC + Thymeleaf + JPA** para conectar doadores de alimentos, instituições receptoras e controlar doações, itens, solicitações e retiradas.

## O que já vem incluso

- Backend Spring Boot com Java 21.
- Telas Thymeleaf redesenhadas.
- CSS responsivo em `src/main/resources/static/css/app.css`.
- Banco H2 para testar localmente sem instalar MySQL.
- Profile MySQL opcional.
- Profile PostgreSQL para deploy no Render.
- `render.yaml` pronto para criar Web Service + banco PostgreSQL.

## Rodar localmente

```bash
mvn spring-boot:run
```

Acesse:

```text
http://localhost:8080
```

O projeto cria dados iniciais automaticamente:

- `doador@nutriconecta.com` / senha `123`
- `instituicao@nutriconecta.com` / senha `123`
- `admin@nutriconecta.com` / senha `123`

> Observação: este projeto ainda não tem tela de login. Esses dados são sementes para teste e cadastro.

## Rodar com MySQL local

Crie o banco:

```sql
CREATE DATABASE dbnutriconecta CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Depois rode:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

As configurações ficam em:

```text
src/main/resources/application-mysql.properties
```

## Deploy no Render

Este pacote já está pronto para Render usando `render.yaml`.

1. Suba este projeto para o GitHub.
2. Entre no Render.
3. Clique em **New +**.
4. Escolha **Blueprint** ou conecte o repositório como **Web Service**.
5. Se usar Web Service manual, configure:

Build Command:

```bash
mvn clean package -DskipTests
```

Start Command:

```bash
java -Dspring.profiles.active=render -jar target/nutriconecta-0.0.1-SNAPSHOT.jar
```

## Estrutura principal

```text
src/main/java/br/com/nutriconecta/nutriconecta
├── NutriconectaApplication.java
├── controller/NutriController.java
├── model/
└── service/NutriService.java
```

## GitHub pelo Termux

Veja o arquivo:

```text
COMANDOS-GITHUB-TERMUX.txt
```

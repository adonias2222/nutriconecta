# Deploy NutriConecta no Render

Este projeto está pronto para rodar no Render com PostgreSQL.

## Opção mais simples

1. Suba o projeto para o GitHub.
2. Acesse o Render.
3. Crie um serviço usando Blueprint, apontando para o repositório.
4. O Render vai ler o `render.yaml` e criar:
   - Web Service `nutriconecta`.
   - Banco PostgreSQL `nutriconecta-db`.

## Configuração manual

Runtime: Java

Build Command:

```bash
mvn clean package -DskipTests
```

Start Command:

```bash
java -Dspring.profiles.active=render -jar target/nutriconecta-0.0.1-SNAPSHOT.jar
```

Variáveis de ambiente necessárias se criar manualmente:

```text
DB_HOST
DB_PORT
DB_NAME
DB_USER
DB_PASSWORD
JAVA_VERSION=21
```

O profile usado no Render é:

```text
src/main/resources/application-render.properties
```

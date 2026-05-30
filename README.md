# NutriConecta

O **NutriConecta** é um projeto acadêmico desenvolvido para apoiar a gestão de doações de alimentos, conectando doadores, instituições receptoras e administradores em uma única plataforma. A proposta do sistema é facilitar o cadastro de alimentos, o controle de doações disponíveis, o acompanhamento de solicitações e o registro de retiradas ou entregas.

A aplicação foi pensada para simular um ambiente real de gerenciamento social, onde mercados, restaurantes, pessoas físicas ou outros doadores podem cadastrar alimentos disponíveis, enquanto instituições podem visualizar, solicitar e acompanhar essas doações. O sistema também permite manter um histórico organizado das operações realizadas.

## Objetivo do projeto

O principal objetivo do NutriConecta é demonstrar uma solução web para reduzir desperdícios e melhorar a distribuição de alimentos por meio da tecnologia. A plataforma centraliza informações importantes, como usuários, endereços, alimentos, doações, itens de doação, solicitações e retiradas.

Além de atender ao contexto social, o projeto também serve como aplicação prática de conceitos de desenvolvimento web, banco de dados, arquitetura MVC e persistência de dados.

## Principais funcionalidades

- Cadastro e gerenciamento de usuários do tipo doador, instituição e administrador.
- Registro de endereços vinculados aos usuários.
- Cadastro de alimentos com categoria e unidade de medida.
- Criação e listagem de doações.
- Inclusão de itens em cada doação, com quantidade e validade.
- Solicitação de doações por instituições receptoras.
- Controle de status das doações e solicitações.
- Registro de retiradas ou entregas.
- Dashboard inicial com informações resumidas do sistema.
- Interface web responsiva com páginas organizadas por módulo.

## Tecnologias utilizadas

### Backend

- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Bean Validation

### Frontend

- Thymeleaf
- HTML5
- CSS3
- Layout responsivo

### Banco de dados

- H2 Database para ambiente local e testes rápidos.
- PostgreSQL para ambiente de deploy.
- MySQL como opção de banco local.

### Deploy e infraestrutura

- Render para hospedagem da aplicação.
- PostgreSQL gerenciado no Render.
- Docker para empacotamento e execução do sistema em ambiente de produção.
- GitHub para versionamento do código-fonte.

## Arquitetura do sistema

O projeto segue uma organização baseada no padrão MVC, separando responsabilidades entre camadas de controle, serviço, modelo e visualização.

A camada de controle recebe as requisições da aplicação e direciona os fluxos das páginas. A camada de serviço concentra as regras principais de negócio. A camada de modelo representa as entidades persistidas no banco de dados. A camada de visualização utiliza páginas Thymeleaf para exibir e manipular os dados do sistema.

## Entidades principais

- **Usuário:** representa doadores, instituições e administradores.
- **Endereço:** armazena os dados de localização vinculados aos usuários.
- **Alimento:** representa os tipos de alimentos cadastrados no sistema.
- **Doação:** registra uma oferta de alimentos realizada por um doador.
- **Item de Doação:** detalha os alimentos, quantidades e validade dentro de uma doação.
- **Solicitação:** representa o pedido de uma instituição para receber uma doação.
- **Retirada:** registra a entrega ou retirada de uma doação aprovada.

## Finalidade acadêmica

Este projeto foi desenvolvido com foco educacional, demonstrando a construção de uma aplicação web completa com integração entre interface, backend e banco de dados. Ele pode ser utilizado como base para estudos de desenvolvimento de sistemas, modelagem de banco de dados, deploy de aplicações Java e organização de projetos com Spring Boot.

## Status do projeto

O NutriConecta possui funcionalidades essenciais implementadas e está estruturado para evolução. Futuras melhorias podem incluir autenticação de usuários, permissões por perfil, relatórios, notificações, filtros avançados e melhorias na experiência de uso.
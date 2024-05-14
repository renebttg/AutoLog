# Autolog

AutoLog é um sistema de gerenciamento de oficinas mecânicas com foco em automatizar serviços manuais de registro para melhor gerenciamento de tempo e recursos. Esta API fornece funcionalidades para registrar e gerenciar informações sobre usuários, carros e manutenções em uma oficina mecânica, permitindo aos usuários controlar facilmente suas operações diárias e melhorar a eficiência de seus negócios.

## Documentação

Para acessar a documentação da API, consulte [Swagger Documentation](https://app.swaggerhub.com/apis/ReneBattaglia/AutoLog-api/1.0).

Além disso, a documentação do projeto AutoLog está presente no código-fonte na branch `main-swagger-doc`. Nessa branch, o código possui anotações SpringDoc que permitem uma melhor visualização e testes de recursos no Swagger. As configurações de segurança nesta branch são mais permissivas para facilitar a visualização e teste dos endpoints. Por outro lado, na branch `main`, o código não contém as mesmas marcações da documentação da branch `main-swagger-doc`, e as configurações de segurança são mais restritivas, visando uma maior segurança e controle de acesso aos endpoints.

## Tecnologias Utilizadas

- Java 19
- Spring Boot
- Spring Security
- Hibernate/JPA
- MySQL

## Configuração

### Banco de Dados

A aplicação está configurada para utilizar um banco de dados MySQL. Certifique-se de ter o MySQL Workbench instalado e configurado corretamente. As configurações do banco de dados podem ser encontradas no arquivo `application.properties`.

### Segurança

A API utiliza JWT (JSON Web Tokens) para autenticação e autorização. As configurações de segurança estão definidas em `SecurityConfigurations.java`.

## Telas da aplicação

O projeto ainda não contém a integração completa entre o Frontend e o Backend. Abaixo estão algumas capturas de tela do projeto AutoLog, mostrando o progresso atual do desenvolvimento frontend:

### Tela de Cadastro
![Cadastro_sc](https://github.com/renebttg/AutoLog/assets/114888521/74bc666b-7ecf-4807-996d-47359f4a8519)

### Tela de Login
![Login_sc](https://github.com/renebttg/AutoLog/assets/114888521/ec62ef3a-96ca-4ce0-bcc5-81bba0f56b69)

### Dashboard Principal
![Dashboard_sc](https://github.com/renebttg/AutoLog/assets/114888521/bfc7e586-bde6-4d96-89d5-478c5cb0f333)


## Autenticação

Para acessar os endpoints protegidos da API, é necessário primeiro registrar um usuário fornecendo as seguintes informações:

```json
{
  "name": "Nome do usuário",
  "cnpj": "12345678901234",
  "email": "usuario@example.com",
  "password": "senha123",
  "phone": "1234567890",
  "nameWorkshop": "Oficina do Usuário",
  "addressWorkshop": "Rua da Oficina, 123"
}
```

Após o registro, você pode autenticar-se enviando as seguintes informações:

```json
{
  "email": "usuario@example.com",
  "password": "senha123"
}
```

O token JWT retornado após o login deve ser inserido no cabeçalho de autenticação (Bearer Token) ao acessar os endpoints protegidos da API.

## Endpoints

### Usuários

- **GET /users**: Retorna todos os usuários.
- **GET /users/{id}**: Retorna um usuário específico pelo ID.
- **PUT /users/{id}**: Atualiza um usuário existente.
- **DELETE /users/{id}**: Remove um usuário existente.

### Carros

- **GET /cars**: Retorna todos os carros.
- **GET /cars/{id}**: Retorna um carro específico pelo ID.
- **POST /cars**: Registra um novo carro.
- **PUT /cars/{id}**: Atualiza um carro existente.
- **DELETE /cars/{id}**: Remove um carro existente.

### Manutenções

- **GET /maintenances**: Retorna todas as manutenções.
- **GET /maintenances/{id}**: Retorna uma manutenção específica pelo ID.
- **POST /maintenances**: Registra uma nova manutenção.
- **PUT /maintenances/{id}**: Atualiza uma manutenção existente.
- **DELETE /maintenances/{id}**: Remove uma manutenção existente.

## Principais Contribuidores do Projeto

Abaixo estão os desenvolvedores que contribuíram para este projeto:

- **Rene Battaglia** (Back-End Developer): Responsável por todo o desenvolvimento do back-end do projeto AutoLog. Criou a lógica de negócios, integrações com o banco de dados, implementou a segurança e configurou os endpoints da API.

- **Alexandre Sampaio** (UI-UX Designer e Front-End Developer): Encarregado do design de interface do usuário (UI) e da experiência do usuário (UX) para o AutoLog. Desenvolveu o front-end completo do sistema, garantindo uma experiência intuitiva e agradável para os usuários.

- **Jean Israel** (Responsável por Diagramas do Projeto): Responsável pela elaboração e criação dos diagramas do projeto AutoLog. Esses diagramas forneceram uma representação visual clara da arquitetura, fluxos de dados e relacionamentos entre os componentes do sistema.

- **Vitor Hugo** (Responsável pela Documentação): Encarregado da documentação do projeto AutoLog. Elaborou e organizou toda a documentação técnica e de usuário, garantindo que o projeto fosse bem documentado e de fácil compreensão para os desenvolvedores e usuários.

- **Murilo Henrique** (Scrum Master): Atuou como Scrum Master no projeto AutoLog, sendo responsável por gerenciar a equipe de desenvolvimento, coordenar as atividades, facilitar as reuniões e garantir a aplicação eficaz dos princípios ágeis e práticas do Scrum.

## Contribuindo

Para contribuir, siga estas etapas:

1. Faça um fork do projeto
2. Crie uma branch para sua nova funcionalidade (`git checkout -b feature/nova-funcionalidade`)
3. Faça commit de suas alterações (`git commit -am 'Adiciona nova funcionalidade'`)
4. Faça push para a branch (`git push origin feature/nova-funcionalidade`)
5. Crie um novo Pull Request

## Licença

Este projeto está licenciado sob a [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).

# AutoLog - Branch Main-Swagger-Doc

Esta branch contém o código-fonte do projeto AutoLog com marcações do SpringDoc OpenAPI para documentação da API. Além disso, foram feitas algumas modificações, como a adição da classe OpenApiConfig para configurar a documentação e a remoção de algumas condições de segurança para permitir requisições do Swagger.

## Documentação Aprimorada com SpringDoc OpenAPI

O SpringDoc OpenAPI é uma biblioteca que simplifica a geração de documentação OpenAPI para APIs desenvolvidas com Spring. As marcações adicionadas no código-fonte permitem uma melhor visualização e interação com a documentação da API.

## Swagger Doc

Para acessar a documentação da API, consulte [Swagger Documentation](https://app.swaggerhub.com/apis/ReneBattaglia/AutoLog-api/1.0).

## OpenApiConfig

A classe `OpenApiConfig` contém as configurações necessárias para o funcionamento adequado da documentação OpenAPI no projeto AutoLog. Aqui, são definidos detalhes como o título da API, descrição, versão, entre outros.

## Remoção de Condições de Segurança

As restrições de roles em alguns endpoints foram removidas na classe `SecurityConfigurations`, no método `securityFilterChain`. Anteriormente, essas restrições permitiam apenas usuários autenticados com determinadas roles acessarem os endpoints, enquanto agora as restrições foram removidas, permitindo acesso sem autenticação para facilitar o teste e a visualização dos endpoints por meio da documentação.







# Sistema de Gestão de Oficinas AutoLog com BDD usando Gherkin

## Descrição
Este projeto utiliza BDD (Behavior-Driven Development) para criar cenários de teste para o sistema de gestão de oficinas AutoLog. O objetivo é validar o comportamento do sistema em diferentes situações, garantindo que ele atenda às necessidades de administradores e mecânicos para a organização e controle eficiente das atividades da oficina. Os testes são escritos em Gherkin e implementados usando Cucumber e Java no Eclipse.

## Tecnologias Utilizadas
- Linguagem de Programação: Java
- Framework de Teste BDD: Cucumber, Junit
- IDE: Eclipse

## Funcionalidades Testadas
* Cadastro de oficina.
* Login de mecânicos.
* Registro de tempo de serviço.
* Registro de veículos.
* Consulta de histórico de serviços.
* Consulta de carros pela placa.
* Atualização de informações da oficina.
* Atualização de informações de serviço.
* Exclusão de serviços.
* Logout de usuários.

## Cenários de Teste
~~~java
@tag
Feature: Sistema de Gestão de Oficinas AutoLog
  Para transformar a gestão de oficinas mecânicas
  Como um administrador ou mecânico
  Eu quero usar o AutoLog para organizar e controlar eficientemente as atividades da oficina

  # Cenário: Cadastro de Oficina
  @tag1
  Scenario: Cadastro de Oficina
    Given que um administrador acessa a aplicação AutoLog
    When ele preenche o formulário de cadastro da oficina com todas as informações necessárias
    And clica no botão "Cadastrar"
    Then a oficina deve ser cadastrada com sucesso
    And uma mensagem de confirmação deve ser exibida

  # Cenário: Login do Mecânico
  @tag2
  Scenario: Login do Mecânico
    Given que um mecânico possui uma conta na aplicação AutoLog
    When ele acessa a página de login
    And preenche o campo de usuário e senha corretamente
    And clica no botão "Entrar"
    Then ele deve ser redirecionado para a página principal da aplicação

  # Cenário: Registro de Tempo de Serviço
  @tag3
  Scenario: Registro de Tempo de Serviço
    Given que um mecânico está logado na aplicação AutoLog
    When ele inicia um serviço e registra o início do tempo
    And registra o término do tempo ao finalizar o serviço
    Then o tempo total gasto deve ser calculado
    And registrado no sistema

  # Cenário: Registro de Veículo
  @tag4
  Scenario: Registro de Veículo
    Given que um mecânico está logado na aplicação AutoLog
    When ele acessa a seção de registro de veículos
    And adiciona um novo veículo com todas as informações necessárias
    Then o veículo deve ser registrado com sucesso
    And o sistema deve exibir uma mensagem de confirmação

  # Cenário: Consulta de Histórico de Serviços
  @tag5
  Scenario: Consulta de Histórico de Serviços
    Given que um mecânico está logado na aplicação AutoLog
    When ele acessa a seção de histórico de serviços
    And seleciona um intervalo de datas
    Then o sistema deve exibir todas as ordens de serviço realizadas dentro do intervalo selecionado

  # Cenário: Consulta de Carro pela Placa
  @tag6
  Scenario: Consulta de Carro pela Placa
    Given que um mecânico está logado na aplicação AutoLog
    When ele acessa a seção de consulta de carros
    And insere a placa do carro desejado
    Then o sistema deve exibir as informações do carro correspondente

  # Cenário: Atualização de Informações da Oficina
  @tag7
  Scenario: Atualização de Informações da Oficina
    Given que um mecânico está logado na aplicação AutoLog
    When ele acessa a seção de configurações da oficina
    And atualiza as informações necessárias
    And clica no botão "Salvar"
    Then as informações da oficina devem ser atualizadas
    And uma mensagem de confirmação deve ser exibida

  # Cenário: Atualização de Informações do Serviço
  @tag8
  Scenario: Atualização de Informações do Serviço
    Given que um mecânico está logado na aplicação AutoLog
    When ele acessa a seção de serviços
    And seleciona um serviço específico
    And atualiza as informações necessárias do serviço
    And clica no botão "Salvar"
    Then as informações do serviço devem ser atualizadas
    And uma mensagem de confirmação deve ser exibida

  # Cenário: Deletar um Serviço
  @tag9
  Scenario: Deletar um Serviço
    Given que um mecânico está logado na aplicação AutoLog
    When ele acessa a seção de serviços
    And seleciona um serviço específico
    And clica no botão "Deletar"
    Then o serviço deve ser removido do sistema
    And uma mensagem de confirmação deve ser exibida

  # Cenário: Logout do Usuário
  @tag10
  Scenario: Logout do Usuário
    Given que um usuário está logado na aplicação AutoLog
    When ele clica no botão de logout
    Then ele deve ser desconectado da aplicação
    And redirecionado para a página de login

~~~
Após compilar os cenários, você poderá ver algo assim:
Temos 10 Cenários e 51 passos indefinidos.

![autolog_1](https://github.com/renebttg/AutoLog/assets/113401757/0a23ed39-e4d3-44cf-b72c-4466a1eadd37)

Imagem abaixo indica que ainda não foram implementados os passos para esses cenários. O Cucumber fornece orientações sobre como implementar esses métodos.

![autolog_2](https://github.com/renebttg/AutoLog/assets/113401757/0bae597b-22d8-4d18-a954-4c8f90b041bc)

Após a criação das classes e a utilização dos métodos indicados pelo Cucumber, obtivemos os seguintes resultados:

![autolog_3](https://github.com/renebttg/AutoLog/assets/113401757/09dad5c8-58ce-40ab-8e9b-b90dd050d3cd)

Utilizando a dependência Cucumber JVM com JUnit e a anotação @RunWith, conseguimos integrar e executar os testes de BDD.

![autolog_4](https://github.com/renebttg/AutoLog/assets/113401757/9c5cdf3d-55c2-455e-bb8b-f8fe965e7ae8)

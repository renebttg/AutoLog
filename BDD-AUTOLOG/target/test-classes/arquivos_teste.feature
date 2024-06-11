#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Sistema de Gestão de Oficinas AutoLog
  Para transformar a gestão de oficinas mecânicas
  Como um administrador ou mecânico
  Eu quero usar o AutoLog para organizar e controlar eficientemente as atividades da oficina
  
	@tag1
  Scenario: Cadastro de Oficina
    Given que um administrador acessa a aplicação AutoLog
    When ele preenche o formulário de cadastro da oficina com todas as informações necessárias
    And clica no botão "Cadastrar"
    Then a oficina deve ser cadastrada com sucesso
    And uma mensagem de confirmação deve ser exibida
	
	@tag2
  Scenario: Login do Mecânico
    Given que um mecânico possui uma conta na aplicação AutoLog
    When ele acessa a página de login
    And preenche o campo de usuário e senha corretamente
    And clica no botão "Entrar"
    Then ele deve ser redirecionado para a página principal da aplicação
	
	@tag3
  Scenario: Registro de Tempo de Serviço
    Given que um mecânico está logado na aplicação AutoLog
    When ele inicia um serviço e registra o início do tempo
    And registra o término do tempo ao finalizar o serviço
    Then o tempo total gasto deve ser calculado
    And registrado no sistema
	
	@tag4
  Scenario: Registro de Veículo
    Given que um mecânico está logado na aplicação AutoLog
    When ele acessa a seção de registro de veículos
    And adiciona um novo veículo com todas as informações necessárias
    Then o veículo deve ser registrado com sucesso
    And o sistema deve exibir uma mensagem de confirmação
	
	@tag5
  Scenario: Consulta de Histórico de Serviços
    Given que um mecânico está logado na aplicação AutoLog
    When ele acessa a seção de histórico de serviços
    And seleciona um intervalo de datas
    Then o sistema deve exibir todas as ordens de serviço realizadas dentro do intervalo selecionado
	
	@tag6
  Scenario: Consulta de Carro pela Placa
    Given que um mecânico está logado na aplicação AutoLog
    When ele acessa a seção de consulta de carros
    And insere a placa do carro desejado
    Then o sistema deve exibir as informações do carro correspondente
	
	@tag7
  Scenario: Atualização de Informações da Oficina
    Given que um mecânico está logado na aplicação AutoLog
    When ele acessa a seção de configurações da oficina
    And atualiza as informações necessárias
    And clica no botão "Salvar"
    Then as informações da oficina devem ser atualizadas
    And uma mensagem de confirmação deve ser exibida
	
	@tag8
  Scenario: Atualização de Informações do Serviço
    Given que um mecânico está logado na aplicação AutoLog
    When ele acessa a seção de serviços
    And seleciona um serviço específico
    And atualiza as informações necessárias do serviço
    And clica no botão "Salvar"
    Then as informações do serviço devem ser atualizadas
    And uma mensagem de confirmação deve ser exibida
	
	@tag9
  Scenario: Deletar um Serviço
    Given que um mecânico está logado na aplicação AutoLog
    When ele acessa a seção de serviços
    And seleciona um serviço específico
    And clica no botão "Deletar"
    Then o serviço deve ser removido do sistema
    And uma mensagem de confirmação deve ser exibida
	
	@tag10
  Scenario: Logout do Usuário
    Given que um usuário está logado na aplicação AutoLog
    When ele clica no botão de logout
    Then ele deve ser desconectado da aplicação
    And redirecionado para a página de login


Projeto Mini Autorizador em Java utilizando Spring Boot, Maven, e integrando com um banco de dados relacional (via Docker). Este sistema seguirá os requisitos e contratos fornecidos. Aqui está um plano inicial para implementação:

1. Estrutura do Projeto
Camadas:
Controller: Endpoints REST para lidar com as requisições.
Service: Lógica de negócios (ex.: autorização de transações).
Repository: Interação com o banco de dados.
Security: Representação da segurança da aplicação.
DTO (Data Transfer Object): Para entrada e saída de dados.
Banco de Dados: Utilizaremos o banco relacional mysql que foi definido no docker-compose.yml

2. Funcionalidades e Regras
Criação de Cartão
Endpoint POST /cartoes
Todos os cartões serão criados com um saldo inicial de R$500,00.
Regras:
Verificar se o cartão já existe (pelo número do cartão).
Retornar os códigos de resposta conforme especificado.
Obtenção de Saldo
Endpoint GET /cartoes/{numeroCartao}
Regras:
Validar se o cartão existe.
Retornar o saldo atual do cartão.
Autorização de Transação
Endpoint POST /transacoes
Regras:
Validar se o cartão existe.
Confirmar a senha do cartão.
Verificar se há saldo suficiente.
Atualizar o saldo do cartão caso a transação seja autorizada.
Regras de Conformidade
Seguir rigorosamente os contratos fornecidos para cada serviço.
Implementar tratamento adequado para autenticação (BASIC Auth).

3. Design Patterns e Boas Práticas
Factory Pattern: Para criação de entidades ou objetos pré-configurados.
Strategy Pattern: Para lidar com validações de regras de autorização.
Transactional Annotation: Para garantir integridade em transações concorrentes no banco de dados.
Testes Unitários e de Integração: Utilizando JUnit para cobertura ampla e validação do comportamento do sistema.

4. Fluxo Básico do Sistema
Criação de Cartão:
O usuário envia uma solicitação POST com os dados do cartão.
Verifica se o número do cartão já existe.
Se não existir, cria o cartão com saldo inicial.
Consulta de Saldo:
O sistema busca o saldo do cartão pelo número fornecido.
Retorna o saldo ou um erro 404 se o cartão não existir.
Autorização de Transação:
Valida a existência do cartão e a senha fornecida.
Verifica o saldo disponível.
Autoriza ou rejeita a transação com base nas regras.

5. Considerações Extras
Concorrência:
Implementar bloqueio otimista ou pessimista no banco para garantir que transações simultâneas não causem inconsistências.
Segurança:
Foi utilizado OpenSSL para criação dos arquivos .pem para criação de chaves pública e privada, confirme comando abaixo:
openssl genrsa -out private_key.pem 2048 openssl rsa -in private_key.pem -pubout -out public_key.pem
Foi utilizado BasicAuth nas requisições de API
username: username
password: password
Documentação:
Incluir um README.md detalhando:
Setup do projeto.
Estrutura do sistema.
Assumptions e decisões de design.
Como executar os testes.

6. Ferramentas e Dependências
Spring Boot: Framework principal.
Spring Data JPA: Para interações com o banco de dados Mysql.
Spring Security: Para segurança em geral. ( autenticação e criptografia)
Lombok: Para reduzir boilerplate no código.
Docker: Para execução do banco de dados.
Swagger: Para documentar a API REST.
JUnit: Para testes.
Postman: Para criação das collections.

7. Subindo a aplicação no Docker

Pelo prompt de comando vá até o diretório raiz do projeto, onde se encontra o arquivo docker-compose.yml, lá execute os comandos abaixo:

docker-compose -f docker-compose.yml up -d

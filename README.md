
# Aplicação de Criptografia

## Visão Geral

Este projeto é uma aplicação Spring Boot projetada para lidar com dados sensíveis de forma segura, utilizando criptografia e descriptografia de informações-chave. A aplicação possui endpoints REST para criar e recuperar transações, onde dados confidenciais, como documentos de usuário e tokens de cartão de crédito, são criptografados antes de serem armazenados.

## Estrutura do Projeto

- **CryptoService**: Serviço responsável pela criptografia e descriptografia de texto usando StrongTextEncryptor. Informações sensíveis são criptografadas antes de salvar e descriptografadas ao serem recuperadas.
- **TransactionService**: Serviço central para gerenciar a lógica de transações, incluindo criação de novas transações e recuperação de resultados paginados.
- **TransactionRepository**: Interface de repositório que estende JpaRepository para realizar operações CRUD em `TransactionEntity`.
- **TransactionEntity**: Entidade que representa os dados da transação, com campos criptografados para informações sensíveis.
- **TransactionController**: Controlador REST que expõe endpoints para criação e recuperação de transações.
- **DTOs**:
    - `CreateTransactionRequest`: Estrutura de dados de requisição para criação de uma transação.
    - `TransactionResponse`: Estrutura de resposta para recuperação de dados de transação.

## Primeiros Passos

### Pré-requisitos
1. **Java 17**: Certifique-se de ter o Java 17 instalado.
2. **Spring Boot**: Nenhuma configuração especial é necessária para o Spring Boot se você estiver usando uma IDE padrão como IntelliJ ou Eclipse.
3. **Variáveis de Ambiente**: Configure `APP_KEY` como uma variável de ambiente para proteger a chave de criptografia.

### Executando a Aplicação
Para iniciar a aplicação, navegue até o diretório raiz do projeto e use o seguinte comando:

```bash
./mvnw spring-boot:run
```

Alternativamente, você pode executá-lo diretamente da sua IDE rodando `CryptographyApplication`.

## Configuração de Ambiente

A aplicação depende de uma variável de ambiente para a chave de criptografia. Configure-a no seu shell antes de iniciar a aplicação:

```bash
export APP_KEY="sua_chave_secreta"
```

## Endpoints

A aplicação fornece os seguintes endpoints RESTful:

### 1. Criar Transação
- **URL**: `/transactions`
- **Método**: `POST`
- **Descrição**: Cria uma nova transação, criptografando os campos sensíveis antes de salvar.
- **Corpo da Requisição**:
  ```json
  {
    "userDocument": "string",
    "CreditCardToken": "string",
    "value": long
  }
  ```
- **Resposta**: `200 OK` em caso de sucesso, sem corpo de resposta.
- **Exemplo**:
  ```bash
  curl -X POST http://localhost:8080/transactions -H "Content-Type: application/json" -d '{
    "userDocument": "123456789",
    "CreditCardToken": "abcdef123456",
    "value": 1000
  }'
  ```

### 2. Listar Todas as Transações
- **URL**: `/transactions`
- **Método**: `GET`
- **Descrição**: Recupera uma lista paginada de todas as transações com campos sensíveis descriptografados.
- **Parâmetros de Consulta**:
    - `page`: Número da página (padrão é `0`)
    - `size`: Número de itens por página (padrão pode ser configurado nas propriedades da aplicação)
- **Resposta**: `200 OK`, retorna uma lista paginada de dados de transação.
- **Exemplo**:
  ```bash
  curl -X GET "http://localhost:8080/transactions?page=0&size=10"
  ```

## Fluxo de Criptografia e Descriptografia

1. **Criptografia**: Ao criar uma transação, `CryptoService` criptografa `userDocument` e `CreditCardToken` antes de armazená-los no banco de dados.
2. **Descriptografia**: Durante a recuperação, o DTO `TransactionResponse` descriptografa os campos, fornecendo os dados brutos ao cliente sem comprometer a segurança.

## Estrutura e Lógica do Código

- **CryptoService**:
    - Utiliza um criptografador estático inicializado com uma variável de ambiente (`APP_KEY`). Isso garante que a criptografia seja segura e vinculada a uma chave secreta em tempo de execução.
- **TransactionService**:
    - Gerencia a lógica de negócios para criação de transações, validando e transformando dados de entrada de `CreateTransactionRequest` para `TransactionEntity`, depois salvando-o com o `TransactionRepository`.
    - Usa `TransactionRepository` para recuperar transações e mapeá-las em `TransactionResponse` com valores descriptografados.
- **TransactionRepository**:
    - Extende JpaRepository, fornecendo uma interface CRUD padrão para `TransactionEntity`.
- **TransactionEntity**:
    - Anotado com `@Entity` e mapeado para a tabela `tb_transactions`, armazenando dados criptografados de forma segura.
    - Campos transitórios (`@Transient`) armazenam valores brutos (não criptografados) durante o processamento, mas não são salvos no banco de dados.
- **TransactionController**:
    - Expõe endpoints para criar transações e recuperar listas paginadas. Utiliza `TransactionService` para gerenciar a lógica de negócios subjacente.

## Testando a Aplicação

Para testar a aplicação manualmente, você pode usar ferramentas como `curl` ou Postman para interagir com os endpoints.

### Caso de Teste Exemplo para Criar Transação
1. Prepare uma requisição `POST` para `/transactions` com campos válidos `userDocument`, `CreditCardToken`, e `value`.
2. Verifique se uma resposta `200 OK` é retornada e, em seguida, confirme no banco de dados que os dados foram armazenados de forma criptografada.

### Caso de Teste Exemplo para Recuperar Transações
1. Envie uma requisição `GET` para `/transactions`.
2. Verifique se a resposta inclui campos descriptografados e se a paginação funciona conforme o esperado.

## Boas Práticas e Recomendações

- **Segurança de Ambiente**: Sempre configure `APP_KEY` em um ambiente seguro para evitar acesso não autorizado a informações confidenciais.
- **Validação de Entrada**: Certifique-se de que as entradas sejam validadas em `TransactionService` para evitar problemas como valores inválidos em `value` ou dados formatados incorretamente.
- **Manuseio de Dados Sensíveis**: Evite registrar dados sensíveis, especialmente valores brutos, para manter a conformidade com padrões de segurança.

## Conclusão

Este projeto demonstra uma abordagem segura para lidar com informações sensíveis usando criptografia em uma aplicação Spring Boot. Siga as instruções acima para rodar, testar e entender a lógica do código, e consulte a descrição de cada componente para mais informações sobre a funcionalidade do código.


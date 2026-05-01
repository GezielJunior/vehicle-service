# Vehicle Service

Microservice de gerenciamento de veiculos, parte do projeto **CarStore**. Desenvolvido com Micronaut 4.10, Groovy e PostgreSQL.

## Tech Stack

| Tecnologia | Versao |
|---|---|
| Micronaut | 4.10.13 |
| Groovy | 5.0.0 |
| Java | 25 |
| PostgreSQL | - |
| Hibernate JPA | - |
| JWT (Bearer Token) | - |

## Pre-requisitos

- JDK 25+
- PostgreSQL rodando e acessivel
- (Opcional) Docker para build nativo

## Configuracao

O servico utiliza variaveis de ambiente para configuracao. Valores padrao sao usados caso nao sejam definidas:

| Variavel | Descricao | Padrao |
|---|---|---|
| `DB_URL` | URL de conexao JDBC do PostgreSQL | `jdbc:postgresql://192.168.1.135:5432/dbvehicle` |
| `DB_USERNAME` | Usuario do banco | `postgres` |
| `DB_PASSWORD` | Senha do banco | `987654321` |
| `JWT_GENERATOR_SIGNATURE_SECRET` | Secret para assinatura dos tokens JWT | `pleaseChangeThisSecretForANewOne` |

## Como executar

```bash
# Build do projeto
./gradlew build

# Executar a aplicacao
./gradlew run

# Build de imagem Docker nativa
./gradlew dockerBuildNative
```

A aplicacao inicia na porta `8080` por padrao.

## API Endpoints

### Autenticacao

O servico utiliza JWT com Bearer Token. O fluxo de autenticacao e:

**1. Criar um usuario**

```
POST /users/
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}
```

**2. Obter token JWT**

```
POST /login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}
```

Resposta:

```json
{
  "access_token": "eyJhbGciOiJIUzI1...",
  "token_type": "Bearer"
}
```

**3. Usar o token nas requisicoes autenticadas**

```
Authorization: Bearer eyJhbGciOiJIUzI1...
```

### Veiculos

| Metodo | Endpoint | Descricao | Autenticacao |
|---|---|---|---|
| `POST` | `/vehicles/` | Cadastrar veiculo | Sim |
| `GET` | `/vehicles/{id}` | Buscar veiculo por ID | Sim |

**Exemplo - Criar veiculo:**

```
POST /vehicles/
Authorization: Bearer <token>
Content-Type: application/json

{
  "model": "Civic",
  "brand": "Honda",
  "licensePlate": "ABC-1234"
}
```

### Usuarios

| Metodo | Endpoint | Descricao | Autenticacao |
|---|---|---|---|
| `POST` | `/users/` | Criar usuario | Nao |

## Documentacao da API

O Swagger UI esta disponivel em:

```
http://localhost:8080/swagger-ui/
```

## Testes

```bash
# Executar todos os testes
./gradlew test

# Executar uma classe de teste especifica
./gradlew test --tests "br.com.carstore.VehicleServiceTest"
```

Os testes utilizam **Micronaut Test Resources**, que provisiona automaticamente um container PostgreSQL para os testes.

## Arquitetura

```
src/main/groovy/br/com/carstore/
├── Application.groovy              # Entry point
├── auth/
│   └── AuthenticationProviderUserPassword.groovy  # Autenticacao BCrypt
├── controller/
│   ├── UserController.groovy       # Endpoints de usuario
│   └── VehicleController.groovy    # Endpoints de veiculo
├── domain/
│   ├── User.groovy                 # Entidade JPA (tabela: app_user)
│   └── Vehicle.groovy              # Entidade JPA (tabela: vehicle)
├── repository/
│   ├── UserRepository.groovy       # CRUD + findByUsername
│   └── VehicleRepository.groovy    # CRUD
└── service/
    ├── UserService.groovy          # Hash de senha com BCrypt
    └── VehicleService.groovy       # Logica de negocio de veiculos
```

## Licenca

Este projeto faz parte do ecossistema CarStore.

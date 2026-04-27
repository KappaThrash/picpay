# picpay
Repo original do desafio: https://github.com/PicPay/picpay-desafio-backend

## DOCKER

Subir o container do PostgreSQL:
```bash
docker compose up -d
```

## ENDPOINTS

## Usuario

### `POST /usuario` - Criar conta Usuario

### Validação
- CPF ou CNPJ devem ser validos, serão recusados caso não sejam
- Email formatado certamente
- CPF/CNPJ e Email são unicos no sistema

#### Exemplo:
```json
{
  "nome": "João Lima",
  "tipo": "USUARIO",
  "cpf": "546.471.429-49",
  "email": "teste@exemplo.com",
  "senha": "senha123"
}
```

### `POST /lojista` - Criar conta Lojista

#### Exemplo:
```json
{
  "nome": "João Lima",
  "tipo": "LOJISTA",
  "cpnpj": "66.838.061/0001-30",
  "email": "teste@exemplo.com",
  "senha": "senha123"
}
```

## Carteira

### `POST /carteira` - Criar Carteira apartir de um Usuario existente

#### Exemplo:
```json
{
  "user_id": "550e8400-e29b-41d4-a716-446655440000"
}
```
Retorno:
```json
{
  "id": "f81d4fae-7dec-11d0-a765-00a0c91e6bf6",
  "user_id": "550e8400-e29b-41d4-a716-446655440000",
  "balance": "100"
}
```

### `GET /carteira/{UUID}` - Retorna Carteira existente

#### Exemplo:
```json
{
  "id": "f81d4fae-7dec-11d0-a765-00a0c91e6bf6",
  "user_id": "550e8400-e29b-41d4-a716-446655440000",
  "balance": "100"
}
```

## Transação

### `POST /transfer` - Efetuar transação apartir de duas carteiras existentes (Rollback em caso de falhas)

### Regras de negocio:
- Carteira payer deve pertencer a Usuario do tipo USUARIO, LOJISTAS não podem fazer transferencias, apenas receber
- Autorizador externo (Mock) tem que autorizar a transferencia
- Saldo deve ser suficiente

#### Exemplo:
```json
{
  "amount": "150",
  "payer": "f81d4fae-7dec-11d0-a765-00a0c91e6bf6",
  "payee": "7d0c10e7-972a-4774-8b51-7a0335de610a"
}
```
Retorno:
```json
{
  "id": "e02e3bf3-f748-434b-a72c-86034e7b8b7a",
  "amount": "200",
  "payer": "f81d4fae-7dec-11d0-a765-00a0c91e6bf6",
  "payee": "7d0c10e7-972a-4774-8b51-7a0335de610a"
  "created_at": "2026-04-27T23:09:38.894601324Z"
}

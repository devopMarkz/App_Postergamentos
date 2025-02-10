# 📄 Sistema de Postergação de Pagamentos

## 📌 Visão Geral

O **Sistema de Postergação de Pagamentos** é uma aplicação desenvolvida para otimizar a comunicação entre o setor financeiro e o setor de compras do Grupo Atlântica, permitindo o gerenciamento eficiente de notas fiscais postergadas. O sistema é focado na simplicidade do processo de cadastro, atualização e envio de informações, com notificações automáticas por e-mail para o setor de compras.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Security (JWT)**
- **PostgreSQL**
- **Swagger OpenAPI**
- **Spring Data JPA**
- **Spring Boot Actuator**

## ⚙️ Funcionalidades

- Cadastro de notas fiscais postergadas.
- Atualização e exclusão de notas por número único.
- Filtros avançados de busca por data, número da nota e empresa.
- Envio de notificações automáticas por e-mail para o setor de compras.
- Autenticação e autorização baseadas em roles (Financeiro, Administrador, Comprador, Usuário).

## 🔐 Perfis de Acesso

- **ROLE_FINANCEIRO:** Cadastrar, visualizar, atualizar, deletar e enviar notas postergadas.
- **ROLE_ADMINISTRADOR:** Acesso completo a todas as funcionalidades.
- **ROLE_COMPRADOR / ROLE_USUARIO:** Visualização de notas postergadas por empresa.

## 📥 Instalação

1️⃣ Clone o repositório:

```bash
git clone https://github.com/devopMarkz/App_Postergamentos.git
```

2️⃣ Configure o banco de dados PostgreSQL no `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postergacoes
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

3️⃣ Compile e execute o projeto:

```bash
./mvnw spring-boot:run
```

## 📊 Documentação da API

Acesse a documentação interativa gerada pelo Swagger OpenAPI:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 📡 Endpoints Principais

### 1️⃣ Criar Nota Postergada

```http
POST /postergamentos/financeiro
```

### 2️⃣ Buscar Notas (Financeiro)

```http
GET /postergamentos/financeiro
```

**Parâmetros de Filtro:**

- `dataMinima` (yyyy-MM-dd)
- `dataMaxima` (yyyy-MM-dd)
- `numeroUnico`
- `numeroNota`
- `codigoEmpresa`

### 3️⃣ Atualizar Nota

```http
PUT /postergamentos/financeiro
```

### 4️⃣ Deletar Nota

```http
DELETE /postergamentos/financeiro/{numeroUnico}
```

### 5️⃣ Enviar Notas Postergadas

```http
POST /postergamentos/financeiro/enviar
```

## 📧 Notificações por E-mail

O sistema envia e-mails automáticos para o setor de compras ao realizar o envio de notas postergadas, garantindo a comunicação ágil e eficiente.

## 👨‍💻 Contribuição

1️⃣ Fork este repositório.  
2️⃣ Crie uma branch com a nova feature: 

```bash
git checkout -b minha-nova-feature
```

3️⃣ Commit suas mudanças:

```bash
git commit -m 'Adiciona nova feature'
```

4️⃣ Push para a branch:

```bash
git push origin minha-nova-feature
```

5️⃣ Abra um **Pull Request** 🚀

## 📝 Licença

Este projeto está licenciado sob a **MIT License**.

---

Desenvolvido por **Marcos André** 💻

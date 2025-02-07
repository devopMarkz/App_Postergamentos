-- Tabela de Usuário
CREATE TABLE tb_usuario (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255),
    tipo_usuario VARCHAR(50) NOT NULL
);

-- Tabela de Role
CREATE TABLE tb_role (
    id SERIAL PRIMARY KEY,
    role VARCHAR(50) NOT NULL
);

-- Tabela de Empresa
CREATE TABLE tb_empresa (
    id SERIAL PRIMARY KEY,
    codigo INTEGER NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    email_corporativo VARCHAR(255) NOT NULL UNIQUE
);

-- Tabela de Nota Postergada
CREATE TABLE tb_nota_postergada (
    id SERIAL PRIMARY KEY,
    numero_unico BIGINT NOT NULL UNIQUE,
    numero_nota VARCHAR(50),
    empresa INTEGER REFERENCES tb_empresa(id),
    parceiro VARCHAR(255),
    data_vencimento DATE,
    valor_do_desdobramento DOUBLE PRECISION,
    justificativa TEXT,
    status_notificacao VARCHAR(50) NOT NULL,
    data_movimentacao DATE DEFAULT CURRENT_DATE,
    data_registro DATE DEFAULT CURRENT_DATE,
    data_alteracao DATE,
    usuario_registro VARCHAR(255) REFERENCES tb_usuario(email),
    usuario_alteracao VARCHAR(255) REFERENCES tb_usuario(email)
);

-- Tabela de relacionamento entre Usuário e Role
CREATE TABLE tb_usuario_role (
    id_usuario BIGINT REFERENCES tb_usuario(id),
    id_role BIGINT REFERENCES tb_role(id),
    PRIMARY KEY (id_usuario, id_role)
);

-- Tabela de relacionamento entre Usuário e Empresa
CREATE TABLE tb_usuario_empresa (
    id_usuario BIGINT REFERENCES tb_usuario(id),
    id_empresa INTEGER REFERENCES tb_empresa(id),
    PRIMARY KEY (id_usuario, id_empresa)
);

-- Seeding das roles na tabela tb_role
INSERT INTO tb_role (role) VALUES ('ROLE_ADMINISTRADOR');
INSERT INTO tb_role (role) VALUES ('ROLE_USUARIO');
INSERT INTO tb_role (role) VALUES ('ROLE_COMPRADOR');
INSERT INTO tb_role (role) VALUES ('ROLE_FINANCEIRO');

-- A senha encriptada é '123456'
INSERT INTO tb_usuario (email, senha, tipo_usuario) VALUES ('marcos@gmail.com', '$2a$10$ShGLoxjUkh3W/Ee4aS55WOLHgFjhQ7K5gRtYUtXxBDBZb/fwoa9z.', 'COMPRADOR');

-- Usuário do tipo ADMIN
INSERT INTO tb_usuario_role (id_usuario, id_role) VALUES (1, 1);
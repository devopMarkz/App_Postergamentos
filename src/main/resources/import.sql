INSERT INTO tb_empresa (codigo, nome, email_corporativo) VALUES (101, 'Internacional Marítima LTDA', 'marcosacs.2018@gmail.com');
INSERT INTO tb_empresa (codigo, nome, email_corporativo) VALUES (102, 'Internacional Marítima Guaratuba LTDA', 'marcosacs.2022@gmail.com');
INSERT INTO tb_empresa (codigo, nome, email_corporativo) VALUES (103, 'Interlocação de Veículos', 'marcos.andre@grupointeratlantica.com.br');

-- Seeding das roles na tabela tb_role
INSERT INTO tb_role (role) VALUES ('ROLE_ADMINISTRADOR');
INSERT INTO tb_role (role) VALUES ('ROLE_USUARIO');
INSERT INTO tb_role (role) VALUES ('ROLE_COMPRADOR');
INSERT INTO tb_role (role) VALUES ('ROLE_FINANCEIRO');

-- A senha encriptada é '123456'
INSERT INTO tb_usuario (email, senha, tipo_usuario) VALUES ('marcos@gmail.com', '$2a$10$ShGLoxjUkh3W/Ee4aS55WOLHgFjhQ7K5gRtYUtXxBDBZb/fwoa9z.', 'COMPRADOR');

-- Usuário do tipo ADMIN
INSERT INTO tb_usuario_role (id_usuario, id_role) VALUES (1, 1);

INSERT INTO tb_nota_postergada (numero_unico, numero_nota, empresa, parceiro, data_vencimento, valor_do_desdobramento, justificativa, status_notificacao, data_movimentacao, usuario_alteracao, usuario_registro) VALUES (1434576, '35901', 1, 'DCML', '2025-02-04', 1500.75, 'Ajuste financeiro solicitado pelo setor contábil', 'PENDENTE', '2025-02-04', 'marcos@gmail.com', 'marcos@gmail.com');
INSERT INTO tb_nota_postergada (numero_unico, numero_nota, empresa, parceiro, data_vencimento, valor_do_desdobramento, justificativa, status_notificacao, data_movimentacao, usuario_alteracao, usuario_registro) VALUES (1434577, '35902', 2, 'DCML', '2025-02-01', 1800.75, 'Ajuste financeiro solicitado pelo setor contábil', 'PENDENTE', '2025-02-04', 'marcos@gmail.com', 'marcos@gmail.com');
INSERT INTO tb_nota_postergada (numero_unico, numero_nota, empresa, parceiro, data_vencimento, valor_do_desdobramento, justificativa, status_notificacao, data_movimentacao, usuario_alteracao, usuario_registro) VALUES (1434578, '35903', 3, 'DCML', '2025-02-04', 2500.75, 'Ajuste financeiro solicitado pelo setor contábil', 'PENDENTE', '2025-02-04', 'marcos@gmail.com', 'marcos@gmail.com');

INSERT INTO tb_usuario_empresa (id_usuario, id_empresa) VALUES(1, 1);
INSERT INTO tb_usuario_empresa (id_usuario, id_empresa) VALUES(1, 2);
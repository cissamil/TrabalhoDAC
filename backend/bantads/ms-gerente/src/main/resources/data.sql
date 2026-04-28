TRUNCATE gerentes RESTART IDENTITY;

INSERT INTO gerentes (gerente_id, cpf, nome, email, tipo_gerente) VALUES
('a1b2c3d4-1001-4000-8000-000000000006', '98574307084', 'Geniéve', 'ger1@bantads.com.br', 'GERENTE'),
('a1b2c3d4-1002-4000-8000-000000000007', '64065268052', 'Godophredo', 'ger2@bantads.com.br', 'GERENTE'),
('a1b2c3d4-1003-4000-8000-000000000008', '23862179060', 'Gyândula', 'ger3@bantads.com.br', 'GERENTE'),
('a1b2c3d4-1004-4000-8000-000000000009', '40501740066', 'Adamântio', 'adm1@bantads.com.br', 'ADMIN');

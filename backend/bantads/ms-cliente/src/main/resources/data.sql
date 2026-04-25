TRUNCATE clientes RESTART IDENTITY;

-- Inserindo os clientes com UUIDs fixos para referência futura
INSERT INTO clientes (cliente_id, cpf, nome, email, salario, endereco) VALUES
('a1b2c3d4-1001-4000-8000-000000000001', '12912861012', 'Catharyna', 'cli1@bantads.com.br', 10000.00, 'Rua Marechal Deodoro, 100'),
('a1b2c3d4-1002-4000-8000-000000000002', '09506382000', 'Cleuddônio', 'cli2@bantads.com.br', 20000.00, 'Avenida Visconde de Guarapuava, 500'),
('a1b2c3d4-1003-4000-8000-000000000003', '85733854057', 'Catianna', 'cli3@bantads.com.br', 3000.00, 'Rua XV de Novembro, 1020'),
('a1b2c3d4-1004-4000-8000-000000000004', '58872160006', 'Cutardo', 'cli4@bantads.com.br', 500.00, 'Avenida Manoel Ribas, 350'),
('a1b2c3d4-1005-4000-8000-000000000005', '76179646090', 'Coândrya', 'cli5@bantads.com.br', 1500.00, 'Rua das Araucárias, 200');

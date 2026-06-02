TRUNCATE clientes RESTART IDENTITY CASCADE;
TRUNCATE enderecos RESTART IDENTITY CASCADE;

INSERT INTO enderecos (cep, logradouro, numero, cidade, estado) VALUES
('45321646', 'Rua Marechal Deodoro', 1, 'Curitiba', 'PR'),
('78613542', 'Avenida Visconde de Guarapuava', 2, 'Curitiba', 'PR'),
('73164532', 'Rua XV de Novembro', 3, 'Curitiba', 'PR'),
('87613516', 'Avenida Manoel Ribas', 4, 'Curitiba', 'PR'),
('42358445', 'Rua das Araucárias', 5, 'Curitiba', 'PR');

-- Inserindo os clientes com UUIDs fixos para referência futura
INSERT INTO clientes (cliente_id, cpf, nome, email, telefone, salario, endereco_id) VALUES
('a1b2c3d4-1001-4000-8000-000000000001', '12912861012', 'Catharyna', 'cli1@bantads.com.br', '45453156845' , 10000.00, 1),
('a1b2c3d4-1002-4000-8000-000000000002', '09506382000', 'Cleuddônio', 'cli2@bantads.com.br', '45453156845' , 20000.00, 2),
('a1b2c3d4-1003-4000-8000-000000000003', '85733854057', 'Catianna', 'cli3@bantads.com.br', '45453156845' , 3000.00, 3),
('a1b2c3d4-1004-4000-8000-000000000004', '58872160006', 'Cutardo', 'cli4@bantads.com.br', '45453156845' , 500.00, 4),
('a1b2c3d4-1005-4000-8000-000000000005', '76179646090', 'Coândrya', 'cli5@bantads.com.br', '45453156845' , 1500.00, 5);


--servico transacionais--

--DATABASE

CREATE DATABASE transacao

--TABELAS
CREATE TABLE movimentacoes (
    id          SERIAL PRIMARY KEY,
    data_hora   TIMESTAMP        NOT NULL,
    tipo        VARCHAR(20)      NOT NULL CHECK (tipo IN ('depósito', 'saque', 'transferência')),
    cliente_origem  VARCHAR(100) NOT NULL,
    cliente_destino VARCHAR(100),
    valor       NUMERIC(15, 2)   NOT NULL
);





--INSERTS
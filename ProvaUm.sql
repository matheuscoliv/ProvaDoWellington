CREATE TABLE produto (
                         id_produto SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         preco NUMERIC(10,2) NOT NULL,
                         estoque INTEGER NOT NULL CHECK (estoque >= 0)
);

CREATE TABLE categoria (
                           id_categoria SERIAL PRIMARY KEY,
                           nome VARCHAR(50) NOT NULL,
                           descricao TEXT
);

ALTER TABLE produto
    ADD COLUMN id_categoria INTEGER;

ALTER TABLE produto
    ADD CONSTRAINT fk_produto_categoria
        FOREIGN KEY (id_categoria)
            REFERENCES categoria(id_categoria)
            ON DELETE SET NULL;

INSERT INTO categoria (nome, descricao)
VALUES
    ('Eletronicos', 'Produtos eletronicos'),
    ('Alimentos', 'Produtos alimenticios'),
    ('Bebidas', 'Bebidas em geral');

INSERT INTO produto (nome, preco, estoque, id_categoria)
VALUES
    ('Notebook', 3500.00, 10, 1),
    ('Mouse', 80.00, 30, 1),
    ('Arroz', 25.00, 50, 2),
    ('Feijao', 10.00, 40, 2),
    ('Macarrao', 8.00, 35, 2);

SELECT
    c.nome,
    COUNT(p.id_produto) AS quantidade_produtos
FROM categoria c
         LEFT JOIN produto p
                   ON p.id_categoria = c.id_categoria
GROUP BY c.id_categoria, c.nome
ORDER BY quantidade_produtos DESC;
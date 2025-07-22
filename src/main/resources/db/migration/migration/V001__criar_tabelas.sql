CREATE TABLE pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    total DECIMAL(10,2),
    status VARCHAR(50) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE item_pedido (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     pedido_id BIGINT NOT NULL,
     sku VARCHAR(100) NOT NULL,
     quantidade INT NOT NULL,
     preco_unitario DECIMAL(10,2) NOT NULL,
     FOREIGN KEY (pedido_id) REFERENCES pedido(id) ON DELETE CASCADE
);

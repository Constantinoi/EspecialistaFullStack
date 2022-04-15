CREATE TABLE lancamento (
	codigo serial PRIMARY KEY,
	descricao VARCHAR ( 50 ) NOT NULL,
	data_vencimento DATE NOT NULL,
	data_pagamento DATE,
	valor DECIMAL(10,2) NOT NULL,
	observacao VARCHAR ( 100 ),
	tipo VARCHAR ( 20 ) NOT NULL,
	codigo_categoria BIGINT NOT NULL,
	codigo_pessoa BIGINT NOT NULL,
	FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo),
	FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
);

INSERT INTO lancamento(descricao, data_vencimento,data_pagamento,valor,	observacao,	tipo, codigo_categoria ,	codigo_pessoa ) 
VALUEs('Salario Mensal','2022-06-10',null,6500.00,'Distribuicao de lucros', 'RECEITA',1,1);

INSERT INTO lancamento(descricao, data_vencimento,data_pagamento,valor,	observacao,	tipo, codigo_categoria ,	codigo_pessoa ) 
VALUEs('Gasolina','2022-06-10',null,100.00,null, 'DESPESA',1,1);

INSERT INTO lancamento(descricao, data_vencimento,data_pagamento,valor,	observacao,	tipo, codigo_categoria ,	codigo_pessoa ) 
VALUEs('Pneus','2022-06-10',null,110.50,null, 'DESPESA',2,2);

INSERT INTO lancamento(descricao, data_vencimento,data_pagamento,valor,	observacao,	tipo, codigo_categoria ,	codigo_pessoa ) 
VALUEs('Freella','2022-06-10',null,500.00,'frella', 'RECEITA',1,1);
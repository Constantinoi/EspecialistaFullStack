CREATE TABLE pessoa (
	codigo serial PRIMARY KEY,
	nome VARCHAR ( 50 ) NOT NULL,
	ativo BOOLEAN NOT NULL,
	 logradouro VARCHAR ( 100 ) NOT NULL,
	 numero VARCHAR ( 50 ) NOT NULL,
	 complemento VARCHAR ( 100 ),
	 bairro VARCHAR ( 50 ) NOT NULL,
	 cep VARCHAR ( 50 ) NOT NULL,
	 cidade VARCHAR ( 50 ) NOT NULL,
	 estado VARCHAR ( 50 ) NOT NULL
);

INSERT INTO pessoa( nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado ) 
values ('Fabiano','1','rua fulano de tal','50','','caimbe','690000','Boa vista','Roraima');

INSERT INTO pessoa( nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado ) 
values ('Jessica','1','rua fulano de tal2','11','','caimbe','690000','Boa vista','Roraima');
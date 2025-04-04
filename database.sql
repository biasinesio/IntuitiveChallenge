Create database test;
USE test;

-- Script SQL para teste de nivelamento
-- Objetivo: Identificar as 10 operadoras com maiores despesas relacionadas a
-- "EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS DE ASSISTÊNCIA À SAÚDE MÉDICO-HOSPITALAR"
-- no último ano e no último trimestre com dados disponíveis (até outubro de 2024).

CREATE TABLE operadoras_ativas (
    registro_ans VARCHAR(20) PRIMARY KEY,
    cnpj VARCHAR(20),
    razao_social TEXT,
    nome_fantasia TEXT,
    modalidade TEXT,
    logradouro TEXT,
    numero VARCHAR(20),
    complemento TEXT,
    bairro TEXT,
    cidade TEXT,
    uf VARCHAR(2),
    cep VARCHAR(10),
    ddd VARCHAR(3),
    telefone VARCHAR(20),
    fax VARCHAR(15),
    endereco_eletronico TEXT,
    representante TEXT,
    cargo_representante TEXT,
    regiao_comercializacao VARCHAR(10),
    data_registro_ans DATE
);

CREATE TABLE despesas (
    data DATE,
    registro_ans VARCHAR(20),
    cd_conta_contabil VARCHAR(20),
    descricao TEXT,
    vl_saldo_inicial DECIMAL(18,2),
    vl_saldo_final DECIMAL(18,2)
);

-- Substitua o caminho abaixo pelo diretório local onde os arquivos estão salvos:
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1T2023_convertido2.0.csv'
INTO TABLE despesas
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

-- Substitua o caminho abaixo pelo diretório local onde os arquivos estão salvos:
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/1T2024_convertido2.0.csv'
INTO TABLE despesas
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

-- Substitua o caminho abaixo pelo diretório local onde os arquivos estão salvos:
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/2T2023_convertido2.0.csv'
INTO TABLE despesas
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

-- Substitua o caminho abaixo pelo diretório local onde os arquivos estão salvos:
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/2T2024_convertido2.0.csv'
INTO TABLE despesas
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS; 

-- Substitua o caminho abaixo pelo diretório local onde os arquivos estão salvos:
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/3T2023_convertido2.0.csv'
INTO TABLE despesas
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

-- Substitua o caminho abaixo pelo diretório local onde os arquivos estão salvos:
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/3T2024_convertido2.0.csv'
INTO TABLE despesas
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

-- Substitua o caminho abaixo pelo diretório local onde os arquivos estão salvos:
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/4T2023_convertido2.0.csv'
INTO TABLE despesas
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

-- Substitua o caminho abaixo pelo diretório local onde os arquivos estão salvos:-
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/4T2024_convertido2.0.csv'
INTO TABLE despesas
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;


SELECT * FROM operadoras_ativas;
SELECT * FROM despesas;


SELECT o.razao_social, d.registro_ans, SUM(d.vl_saldo_final) AS total_despesas
FROM despesas d
JOIN operadoras_ativas o ON d.registro_ans = o.registro_ans
WHERE d.descricao LIKE '%EVENTOS/%SINISTROS CONHECIDOS OU AVISADOS%'
    AND d.data >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)
GROUP BY d.registro_ans, o.razao_social
ORDER BY total_despesas DESC
LIMIT 10;

SELECT o.razao_social, d.registro_ans, SUM(d.vl_saldo_final) AS total_despesas
FROM despesas d
JOIN operadoras_ativas o ON d.registro_ans = o.registro_ans
WHERE d.descricao LIKE '%EVENTOS/%SINISTROS CONHECIDOS OU AVISADOS%'
  AND d.data BETWEEN '2024-08-01' AND '2024-10-01'
GROUP BY d.registro_ans, o.razao_social
ORDER BY total_despesas DESC
LIMIT 10;

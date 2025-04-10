
# Teste de Nivelamento — Ana Beatriz Sinésio

Este repositório contém a solução para o teste de nivelamento solicitado. O projeto foi originalmente dividido em 4 partes, mas a parte de Web Scraping e Transformação de Dados foi unificada para agilizar o processo e reduzir a quantidade de arquivos e módulos.

---

## 🔎 Web Scraping + Transformação de Dados | ANS - Rol de Procedimentos

Este projeto realiza o Web Scraping de arquivos PDF disponibilizados pela ANS (Agência Nacional de Saúde Suplementar), extrai informações da tabela do Anexo I, transforma os dados em CSV e realiza substituições específicas nos dados extraídos.

### ⚙️ Tecnologias Utilizadas
- Java 17+
- Jsoup – Utilizado para o Web Scraping
- Apache PDFBox – Leitura de PDFs
- Tabula – Extração de tabelas de PDFs
- Apache Commons CSV – Escrita de arquivos CSV
- Maven – Gerenciamento de dependências

### 📌 Funcionalidades
- Acessa o site da ANS e faz o download dos Anexos I e II em formato PDF.
- Armazena os arquivos na pasta `pdfs_baixados/`.
- Compacta os PDFs em `pdfs_baixados.zip`.
- Extrai a tabela de procedimentos de saúde do Anexo I.
- Salva os dados extraídos em `Tabela_Extraida.csv`.
- Substitui as siglas:
  - `OD` → Odontológica
  - `AMB` → Ambulatorial
- Compacta o CSV resultante em `Teste_Ana_Beatriz.zip`.

> ℹ️ As partes de Web Scraping e Transformação de Dados foram unificadas intencionalmente para otimizar o tempo de desenvolvimento e diminuir a complexidade da estrutura de pastas e arquivos.

### ▶️ Como Executar
**Pré-requisitos:**
- Java 17 ou superior
- Maven
- Git (opcional)

```bash
git clone https://github.com/seu-usuario/nome-do-repositorio.git
cd nome-do-repositorio/Web_Scraping_Test - Data_Transformation_Test

mvn clean install
mvn exec:java -Dexec.mainClass="org.example.Main"
```

---

## 🗃️ Database_Test — SQL + Análise de Dados

Este módulo foi desenvolvido para processar e analisar dados financeiros e cadastrais de operadoras de planos de saúde, extraídos do repositório público da ANS.

### 📚 Fontes de Dados
- Dados cadastrais:  
  https://dadosabertos.ans.gov.br/FTP/PDA/operadoras_de_plano_de_saude_ativas/
- Demonstrativos contábeis:  
  https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/

### ✍️ Tratamento de Dados
Os arquivos CSV originais passaram por tratamento **manualmente no Google Sheets** para garantir que fossem compatíveis com o banco de dados e com os comandos SQL utilizados. As alterações incluíram:

- Substituição de ponto e vírgula (`;`) por vírgula (`,`)
- Conversão de valores monetários
- Correção de datas inválidas

### 🎯 Objetivo
Identificar as 10 operadoras com maiores despesas em:
- **Eventos/Sinistros Conhecidos ou Avisados de Assistência à Saúde Médico-Hospitalar** no último trimestre
- A mesma categoria no último ano

### 📦 O que o script `database.sql` faz:
- Cria o banco de dados e tabelas
- Importa os arquivos CSV tratados
- Executa queries analíticas

### ▶️ Como Executar
**Pré-requisitos:**
- MySQL Server 8+
- Permissão para o comando `LOAD DATA INFILE`
- Caminho para CSVs configurado corretamente no script

```sql
-- Execute no MySQL Workbench ou terminal:
SOURCE caminho/para/database.sql;
```

> ⚠️ Garanta que os arquivos CSV estejam em:  
`C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/` (ou o caminho correspondente no seu sistema).

---

## 🧪 API + Frontend | Busca de Operadoras de Saúde

Este projeto implementa uma API com Flask para realizar buscas em um arquivo CSV contendo informações de operadoras de saúde, além de uma interface web com Vue.js + Vite.

### 🔧 Tecnologias
- **Back-end:** Python + Flask
- **Bibliotecas:** pandas, flask, flask_cors
- **Front-end:** Vue.js 3 + Vite
- **Testes:** Postman

### 🚀 Funcionalidades
- Rota `/buscar` (GET): retorna dados filtrados por nome fantasia da operadora
- Leitura do CSV com pandas
- Retorno em JSON
- Interface Vue.js com campo de busca, botão e resultado dinâmico

### 📮 Testes via Postman
Coleção: `App_API_test.postman_collection.json`

---

## ⚠️ Status do Projeto

Essa etapa do teste **não foi concluída dentro do prazo** devido a um erro de integração entre o back-end (Flask) e o front-end (Vue.js + Vite).

Atualmente:
- A API retorna corretamente um JSON com os dados do CSV, testado via Postman com status **200 OK**.
- Porém, ao consumir a API com Axios no Vue.js, o dado chega como **string representando um array**, e não um array real.
- Isso impede a iteração correta dos dados no Vue.

> O problema está em investigação e será resolvido para o funcionamento completo da aplicação.

---

## ▶️ Como Rodar

### 1. Servidor Flask (API)
```bash
pip install flask flask-cors pandas
python app.py
```

Acesse: `http://127.0.0.1:5000`

### 2. Frontend Vue.js + Vite
```bash
cd vue-interface
npm install
npm run dev
```

Acesse: `http://localhost:5173`

> Verifique se a URL da API no frontend está apontando corretamente para `http://127.0.0.1:5000`.

### 3. Postman
- Importe a coleção `App_API_test.postman_collection.json`
- Faça uma requisição como:
  `GET http://127.0.0.1:5000/buscar?q=Unimed`

---

## 👩‍💻 Desenvolvido por

**Ana Beatriz Sinésio**  
Design Gráfico & Sistemas para Internet  

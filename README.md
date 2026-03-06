# 📊 StatisticData – Analisador Estatístico de CSV

O **StatisticData** é uma aplicação em Java desenvolvida para realizar **análise estatísticas de arquivos CSV**.  

A ferramenta permite que o usuário carregue um arquivo CSV através de uma **interface gráfica construída com JavaFX**, escolha o **separador utilizado no arquivo** e gere uma análise estatística dos dados.

O resultado pode ser exportado nos formatos:

- **JSON**
- **XML**
- **HTML**

Além disso, o relatório em **HTML pode ser visualizado diretamente em uma página web**, facilitando a leitura e interpretação dos dados.

---

# 🚀 Funcionalidades

- Upload de arquivos **CSV**
- Seleção do **separador do arquivo** (vírgula - ponto e vírgula - pipeline)
- Geração de análise estatística das colunas do arquivo
- Exportação dos resultados em:
  - JSON
  - XML
  - HTML
- Visualização do relatório HTML em uma **página web**
- Interface gráfica simples utilizando **JavaFX**

---

# 📊 Análise Estatística

O sistema interpreta o tipo da coluna, se é numérica ou textual, e faz uma análise tendo essa relação:

- **COLUNA NUMÉRICA**
  - *Valor Mínimo*
  - *Valor Máximo*
  - *Amplitude*
  - *Desvio Padrão*
  - *Variância*
  - *Soma de todas as colunas do CSV*
  - *Valores nulos*
  - *Quartis (1, 2 e 3)*

- **COLUNA TEXTUAL**
  - *Valores únicos*
  - *Valores faltantes*
  - *Valor mais frequente*
  - *Ranking dos três valores com maior participação*

Os dados são organizados e exportados no formato selecionado pelo usuário.

---

# 🖥️ Como Utilizar

### 1. Clonar o repositório

```powershell
git clone "https://github.com/Pedro-Korb/statisticData.git"
```

### 2. Efetuar a build do projeto
```bash
mvn clean package
```

### 3. Rodar a aplicação
```bash
mvn javafx:run
```

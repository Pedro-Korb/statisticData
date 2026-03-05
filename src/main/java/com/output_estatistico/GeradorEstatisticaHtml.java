package com.output_estatistico;

import com.estrutura.ElementoHtml;
import com.estrutura.GeradorHtml;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class GeradorEstatisticaHtml {

   private Map<String, Object> oDados;
   private ElementoHtml oElementoGeral;

   public GeradorEstatisticaHtml(Map<String, Object> oDados) {
      this.oElementoGeral = new ElementoHtml()
         .setTag("div");
      this.oDados = oDados;
   }

   public GeradorEstatisticaHtml() {
      this.oElementoGeral = new ElementoHtml()
         .setTag("div");
   }

   public String getHtmlEstatistica() {
      this.setCabecalho(this.getCabecalhoDados(this.getDados()));
      this.setConteudoBody((ArrayList<Map<String, Object>>) this.getDados().get("dadosColunas"));
      return GeradorHtml.gerarHtmlPagina(GeradorHtml.gerarHtml(this.getElementoGeral()), "pt-BR", "Dados Estatísticos", true);
   }

   public CabecalhoDados getCabecalhoDados(Map<String, Object> oDados) {
      Dado oNomeTabela = new Dado()
         .setChave("nomeTabela")
         .setAlias("Nome da Tabela")
         .setInformacao(oDados.get("nomeTabela"));

      Dado oNumeroLinhas = new Dado()
         .setChave("numeroLinhas")
         .setAlias("Número de Linhas")
         .setInformacao(oDados.get("numeroLinhas"));

      Dado oNumeroColunas = new Dado()
         .setChave("numeroColunas")
         .setAlias("Número de Colunas")
         .setInformacao(oDados.get("numeroColunas"));

      CabecalhoDados oCabecalho = new CabecalhoDados()
         .addDado(oNomeTabela)
         .addDado(oNumeroLinhas)
         .addDado(oNumeroColunas);

      return oCabecalho;
   }

   private void setCabecalho(CabecalhoDados oCabecalho) {
      String sNomeArquivo = oCabecalho.getDados().get(0).getInformacao().toString();
      String sNumeroLinhas = oCabecalho.getDados().get(1).getInformacao().toString();
      String sNumeroColunas = oCabecalho.getDados().get(2).getInformacao().toString();

      ElementoHtml oLabelArquivo = new ElementoHtml()
         .setTag("span")
         .addAtributo("class", "label")
         .setConteudoTextual("Arquivo");

      ElementoHtml oTituloArquivo = new ElementoHtml()
         .setTag("h1")
         .setConteudoTextual(sNomeArquivo);

      ElementoHtml oHeaderInfo = new ElementoHtml()
         .setTag("div")
         .addAtributo("class", "header-info")
         .addConteudo(oLabelArquivo)
         .addConteudo(oTituloArquivo);

      ElementoHtml oNumeroLinhasEl = new ElementoHtml()
         .setTag("span")
         .addAtributo("class", "numero")
         .setConteudoTextual(String.valueOf(sNumeroLinhas));

      ElementoHtml oDescricaoLinhas = new ElementoHtml()
         .setTag("span")
         .addAtributo("class", "descricao")
         .setConteudoTextual("Linhas");

      ElementoHtml oMetricaLinhas = new ElementoHtml()
         .setTag("div")
         .addAtributo("class", "metrica")
         .addConteudo(oNumeroLinhasEl)
         .addConteudo(oDescricaoLinhas);

      ElementoHtml oNumeroColunasEl = new ElementoHtml()
         .setTag("span")
         .addAtributo("class", "numero")
         .setConteudoTextual(String.valueOf(sNumeroColunas));

      ElementoHtml oDescricaoColunas = new ElementoHtml()
         .setTag("span")
         .addAtributo("class", "descricao")
         .setConteudoTextual("Colunas");

      ElementoHtml metricaColunas = new ElementoHtml()
         .setTag("div")
         .addAtributo("class", "metrica")
         .addConteudo(oNumeroColunasEl)
         .addConteudo(oDescricaoColunas);

      ElementoHtml oHeaderMetricas = new ElementoHtml()
         .setTag("div")
         .addAtributo("class", "header-metricas")
         .addConteudo(oMetricaLinhas)
         .addConteudo(metricaColunas);

      ElementoHtml oHeaderResumo = new ElementoHtml()
         .setTag("div")
         .addAtributo("class", "header-resumo")
         .addConteudo(oHeaderInfo)
         .addConteudo(oHeaderMetricas);

      this.getElementoGeral().addConteudo(oHeaderResumo);
   }

   private void setConteudoBody(ArrayList<Map<String, Object>> aConteudo) {
      for (Map<String, Object> oColuna : aConteudo) {
         String sTipoColuna = (String) oColuna.get("tipoColuna");
         if (sTipoColuna == "STRING") {
            this.setDadosColunaString(oColuna);
         } else if (List.of("INTEGER", "LONG", "NUMERIC", "DOUBLE", "SHORT", "FLOAT").contains(sTipoColuna)) {
            this.setDadosColunaNumerica(oColuna, sTipoColuna == "LONG");
         }
      }
   }

   public Map<String, Object> getDados() {
      return this.oDados;
   }

   public ElementoHtml getElementoGeral() {
      return this.oElementoGeral;
   }

   private void setDadosColunaString(Map<String, Object> oColuna) {
      String sNomeColuna = oColuna.get("nomeColuna").toString();
      int iFaltantes = Integer.parseInt(oColuna.get("quantidadeValoresFaltantes").toString());
      int iUnicos = Integer.parseInt(oColuna.get("quantidadeValoresUnicos").toString());
      String sMaisFrequente = oColuna.get("valorMaisFrequente").toString();

      Map<String, Object> oMaioresParticipacoes = (Map<String, Object>) oColuna.get("maioresParticipacoes");
      ElementoHtml oCardColuna = new ElementoHtml()
         .setTag("div")
         .addAtributo("class", "card-analise-string");

      ElementoHtml oTituloColuna = new ElementoHtml()
         .setTag("h2")
         .addAtributo("class", "titulo-coluna")
         .setConteudoTextual(sNomeColuna);

      oCardColuna.addConteudo(oTituloColuna);

      ElementoHtml oResumo = new ElementoHtml()
         .setTag("div")
         .addAtributo("class", "resumo-estatistico");

      oResumo.addConteudo(new ElementoHtml()
         .setTag("p")
         .setConteudoTextual("Valores únicos: " + iUnicos));

      oResumo.addConteudo(new ElementoHtml()
         .setTag("p")
         .setConteudoTextual("Valores faltantes: " + iFaltantes));

      oResumo.addConteudo(new ElementoHtml()
         .setTag("p")
         .setConteudoTextual("Valor mais frequente: " + sMaisFrequente));

      oCardColuna.addConteudo(oResumo);

      ElementoHtml oSecaoTop = new ElementoHtml()
         .setTag("div")
         .addAtributo("class", "top-participacoes");

      ElementoHtml oTituloTop = new ElementoHtml()
         .setTag("h3")
         .setConteudoTextual("Maiores Participações");

      oSecaoTop.addConteudo(oTituloTop);

      for (int i = 1; i <= oMaioresParticipacoes.size() / 3; i++) {
         String sCategoria = (String) oMaioresParticipacoes.get("categoria_" + i);
         int iParticipacao = 0;
         if (oMaioresParticipacoes.get("participacao_" + i) != null) {
            iParticipacao = Integer.parseInt(oMaioresParticipacoes.get("participacao_" + i).toString());
         }

         double dPercentual = 0;
         if (oMaioresParticipacoes.get("percentual_" + i) != null) {
            dPercentual = Double.parseDouble(oMaioresParticipacoes.get("percentual_" + i).toString());
         }

         ElementoHtml oItem = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "item-participacao");

         ElementoHtml oLabel = new ElementoHtml()
            .setTag("span")
            .addAtributo("class", "label-categoria")
            .setConteudoTextual(sCategoria + " (" + iParticipacao + " - " + dPercentual + "%)");

         ElementoHtml oBarraContainer = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "barra-container");

         ElementoHtml oBarra = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "barra")
            .addAtributo("style", "width: " + dPercentual + "%;");

         oBarraContainer.addConteudo(oBarra);
         oItem.addConteudo(oLabel);
         oItem.addConteudo(oBarraContainer);
         oSecaoTop.addConteudo(oItem);
      }
      oCardColuna.addConteudo(oSecaoTop);
      oElementoGeral.addConteudo(oCardColuna);
   }

   private void setDadosColunaNumerica(Map<String, Object> oColuna, Boolean bLong) {
      String sNomeColuna = oColuna.get("nomeColuna").toString();
      int iFaltantes = Integer.parseInt(oColuna.get("quantidadeValoresFaltantes").toString());

      double dMedia = Double.parseDouble(oColuna.get("media").toString());
      double dMinimo = Double.parseDouble(oColuna.get("minimo").toString());
      double dMaximo = Double.parseDouble(oColuna.get("maximo").toString());
      double dSoma = Double.parseDouble(oColuna.get("soma").toString());
      double dDesvioPadrao = Double.parseDouble(oColuna.get("desvioPadrao").toString());
      double dVariancia = Double.parseDouble(oColuna.get("variancia").toString());
      double dAmplitude = Double.parseDouble(oColuna.get("amplitude").toString());
      double dQ1 = Double.parseDouble(oColuna.get("quartil_1").toString());
      double dMediana = Double.parseDouble(oColuna.get("quartil_2_mediana").toString());
      double dQ3 = Double.parseDouble(oColuna.get("quartil_3").toString());
      double dIqr = Double.parseDouble(oColuna.get("intervalo_interquartil").toString());

      ElementoHtml oCard = new ElementoHtml()
         .setTag("div")
         .addAtributo("class", bLong ? "card-analise-numerica long-numero" : "card-analise-numerica");

      ElementoHtml oHeader = new ElementoHtml()
         .setTag("div")
         .addAtributo("class", "numerica-header");

      oHeader
         .addConteudo(new ElementoHtml()
            .setTag("span")
            .addAtributo("class", "label-coluna")
            .setConteudoTextual("Análise Numérica"))
         .addConteudo(new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "nome-coluna")
            .setConteudoTextual(sNomeColuna));

      oCard.addConteudo(oHeader);
      ElementoHtml oDestaqueMedia = new ElementoHtml()
         .setTag("div")
         .addAtributo("class", bLong ? "destaque-media long-destaque" : "destaque-media");

      oDestaqueMedia
         .addConteudo(new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "titulo")
            .setConteudoTextual("Média"))
         .addConteudo(new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "valor")
            .setConteudoTextual(String.format("%.2f", dMedia)));

      oCard.addConteudo(oDestaqueMedia);
      ElementoHtml oGrid = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "grid-metricas");

      oGrid
         .addConteudo(criarMetricaBox("Mínimo", dMinimo, bLong))
         .addConteudo(criarMetricaBox("Máximo", dMaximo, bLong))
         .addConteudo(criarMetricaBox("Amplitude", dAmplitude, bLong))
         .addConteudo(criarMetricaBox("Desvio Padrão", dDesvioPadrao, bLong))
         .addConteudo(criarMetricaBox("Variância", dVariancia, bLong))
         .addConteudo(criarMetricaBox("Soma", dSoma, bLong))
         .addConteudo(criarMetricaBox("Faltantes", iFaltantes, bLong));

      oCard.addConteudo(oGrid);
      ElementoHtml oBlocoEstatistico = new ElementoHtml()
         .setTag("div")
         .addAtributo("class", "bloco-estatistico");

      oBlocoEstatistico
         .addConteudo(new ElementoHtml()
            .setTag("h3")
            .setConteudoTextual("Distribuição (Quartis)"));

      ElementoHtml gridQuartis = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "grid-metricas");

      gridQuartis
         .addConteudo(criarMetricaBox("Q1", dQ1, bLong))
         .addConteudo(criarMetricaBox("Mediana", dMediana, bLong))
         .addConteudo(criarMetricaBox("Q3", dQ3, bLong))
         .addConteudo(criarMetricaBox("IQR", dIqr, bLong));

      oBlocoEstatistico.addConteudo(gridQuartis);
      oCard.addConteudo(oBlocoEstatistico);
      this.oElementoGeral.addConteudo(oCard);
   }

   private static ElementoHtml criarMetricaBox(String sDescricao, double dValor, Boolean bLong) {
      ElementoHtml oBox = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "metrica-box");

      oBox
         .addConteudo(new ElementoHtml()
            .setTag("span")
            .addAtributo("class", bLong ? "numero long-metrica" : "numero")
            .setConteudoTextual(String.format("%.2f", dValor)))
         .addConteudo(new ElementoHtml()
            .setTag("span")
            .addAtributo("class", "descricao")
            .setConteudoTextual(sDescricao));

      return oBox;
   }
}

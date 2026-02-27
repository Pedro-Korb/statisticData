package com.output_estatistico;

import java.util.ArrayList;
import java.util.Map;

import com.estrutura.ElementoHtml;
import com.estrutura.GeradorHtml;

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
      oDados = this.getDados();
      this.setCabecalho(this.getCabecalhoDados(oDados));
      this.setConteudoBody((ArrayList<Map<String, Object>>)this.getDados().get("dadosColunas"));
      return GeradorHtml.gerarHtmlPagina(GeradorHtml.gerarHtml(this.getElementoGeral()), "pt-BR", "Dados Estatísticos",
            true);
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
      String nomeArquivo = oCabecalho.getDados().get(0).getInformacao().toString();
      String numeroLinhas = oCabecalho.getDados().get(1).getInformacao().toString();
      String numeroColunas = oCabecalho.getDados().get(2).getInformacao().toString();

      ElementoHtml labelArquivo = new ElementoHtml()
            .setTag("span")
            .addAtributo("class", "label")
            .setConteudoTextual("Arquivo");

      ElementoHtml tituloArquivo = new ElementoHtml()
            .setTag("h1")
            .setConteudoTextual(nomeArquivo);

      ElementoHtml headerInfo = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "header-info")
            .addConteudo(labelArquivo)
            .addConteudo(tituloArquivo);

      ElementoHtml numeroLinhasEl = new ElementoHtml()
            .setTag("span")
            .addAtributo("class", "numero")
            .setConteudoTextual(String.valueOf(numeroLinhas));

      ElementoHtml descricaoLinhas = new ElementoHtml()
            .setTag("span")
            .addAtributo("class", "descricao")
            .setConteudoTextual("Linhas");

      ElementoHtml metricaLinhas = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "metrica")
            .addConteudo(numeroLinhasEl)
            .addConteudo(descricaoLinhas);

      ElementoHtml numeroColunasEl = new ElementoHtml()
            .setTag("span")
            .addAtributo("class", "numero")
            .setConteudoTextual(String.valueOf(numeroColunas));

      ElementoHtml descricaoColunas = new ElementoHtml()
            .setTag("span")
            .addAtributo("class", "descricao")
            .setConteudoTextual("Colunas");

      ElementoHtml metricaColunas = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "metrica")
            .addConteudo(numeroColunasEl)
            .addConteudo(descricaoColunas);

      ElementoHtml headerMetricas = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "header-metricas")
            .addConteudo(metricaLinhas)
            .addConteudo(metricaColunas);

      ElementoHtml headerResumo = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "header-resumo")
            .addConteudo(headerInfo)
            .addConteudo(headerMetricas);

      this.getElementoGeral().addConteudo(headerResumo);
   }

   private void setConteudoBody(ArrayList<Map<String, Object>> aConteudo) {
      for (Map<String, Object> oColuna : aConteudo) {
         String sTipoColuna = (String)oColuna.get("tipoColuna");
         if (sTipoColuna == "STRING") {
            this.setDadosColunaString(oColuna);
         } else if (List.of("INTEGER", "LONG", "NUMERIC", "DOUBLE", "SHORT", "FLOAT").contains(sTipoColuna)) {
            this.setDadosColunaNumerica(oColuna);
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
      String nomeColuna = oColuna.get("nomeColuna").toString();
      int faltantes = Integer.parseInt(oColuna.get("quantidadeValoresFaltantes").toString());
      int unicos = Integer.parseInt(oColuna.get("quantidadeValoresUnicos").toString());
      String maisFrequente = oColuna.get("valorMaisFrequente").toString();

      Map<String, Object> maioresParticipacoes = (Map<String, Object>) oColuna.get("maioresParticipacoes");
      ElementoHtml cardColuna = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "card-analise-string");

      ElementoHtml tituloColuna = new ElementoHtml()
            .setTag("h2")
            .addAtributo("class", "titulo-coluna")
            .setConteudoTextual("Coluna: " + nomeColuna);

      cardColuna.addConteudo(tituloColuna);

      ElementoHtml resumo = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "resumo-estatistico");

      resumo.addConteudo(new ElementoHtml()
            .setTag("p")
            .setConteudoTextual("Valores únicos: " + unicos));

      resumo.addConteudo(new ElementoHtml()
            .setTag("p")
            .setConteudoTextual("Valores faltantes: " + faltantes));

      resumo.addConteudo(new ElementoHtml()
            .setTag("p")
            .setConteudoTextual("Valor mais frequente: " + maisFrequente));

      cardColuna.addConteudo(resumo);

      ElementoHtml secaoTop = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "top-participacoes");

      ElementoHtml tituloTop = new ElementoHtml()
            .setTag("h3")
            .setConteudoTextual("Maiores Participações");

      secaoTop.addConteudo(tituloTop);

      for (int i = 1; i <= 3; i++) {
         String categoria = (String)maioresParticipacoes.get("categoria_" + i);
         int participacao = 0;
         if(maioresParticipacoes.get("participacao_" + i) != null) {
            participacao = Integer.parseInt(maioresParticipacoes.get("participacao_" + i).toString());
         }

         double percentual = 0;
         if (maioresParticipacoes.get("percentual_" + i) != null){
            percentual = Double.parseDouble(maioresParticipacoes.get("percentual_" + i).toString());
         }

         ElementoHtml item = new ElementoHtml()
               .setTag("div")
               .addAtributo("class", "item-participacao");

         ElementoHtml label = new ElementoHtml()
               .setTag("span")
               .addAtributo("class", "label-categoria")
               .setConteudoTextual(categoria + " (" + participacao + " - " + percentual + "%)");

         ElementoHtml barraContainer = new ElementoHtml()
               .setTag("div")
               .addAtributo("class", "barra-container");

         ElementoHtml barra = new ElementoHtml()
               .setTag("div")
               .addAtributo("class", "barra")
               .addAtributo("style", "width: " + percentual + "%;");

         barraContainer.addConteudo(barra);
         item.addConteudo(label);
         item.addConteudo(barraContainer);
         secaoTop.addConteudo(item);
      }
      cardColuna.addConteudo(secaoTop);
      oElementoGeral.addConteudo(cardColuna);
   }

   private void setDadosColunaNumerica(Map<String, Object> oColuna) {
      String nomeColuna = oColuna.get("nomeColuna").toString();
      int faltantes = Integer.parseInt(oColuna.get("quantidadeValoresFaltantes").toString());

      double media = Double.parseDouble(oColuna.get("media").toString());
      double minimo = Double.parseDouble(oColuna.get("minimo").toString());
      double maximo = Double.parseDouble(oColuna.get("maximo").toString());
      double soma = Double.parseDouble(oColuna.get("soma").toString());
      double desvioPadrao = Double.parseDouble(oColuna.get("desvioPadrao").toString());
      double variancia = Double.parseDouble(oColuna.get("variancia").toString());
      double amplitude = Double.parseDouble(oColuna.get("amplitude").toString());
      double q1 = Double.parseDouble(oColuna.get("quartil_1").toString());
      double mediana = Double.parseDouble(oColuna.get("quartil_2_mediana").toString());
      double q3 = Double.parseDouble(oColuna.get("quartil_3").toString());
      double iqr = Double.parseDouble(oColuna.get("intervalo_interquartil").toString());

      ElementoHtml card = new ElementoHtml()
               .setTag("div")
               .addAtributo("class", "card-analise-numerica");

      ElementoHtml header = new ElementoHtml()
               .setTag("div")
               .addAtributo("class", "numerica-header");

      header
         .addConteudo(new ElementoHtml()
               .setTag("span")
               .addAtributo("class", "label-coluna")
               .setConteudoTextual("Análise Numérica"))
         .addConteudo(new ElementoHtml()
               .setTag("div")
               .addAtributo("class", "nome-coluna")
               .setConteudoTextual(nomeColuna));

      card.addConteudo(header);
      ElementoHtml destaqueMedia = new ElementoHtml()
               .setTag("div")
               .addAtributo("class", "destaque-media");

      destaqueMedia
         .addConteudo(new ElementoHtml()
               .setTag("div")
               .addAtributo("class", "titulo")
               .setConteudoTextual("Média"))
         .addConteudo(new ElementoHtml()
               .setTag("div")
               .addAtributo("class", "valor")
               .setConteudoTextual(String.format("%.2f", media)));

      card.addConteudo(destaqueMedia);
      ElementoHtml grid = new ElementoHtml()
               .setTag("div")
               .addAtributo("class", "grid-metricas");

      grid
         .addConteudo(criarMetricaBox("Mínimo", minimo))
         .addConteudo(criarMetricaBox("Máximo", maximo))
         .addConteudo(criarMetricaBox("Amplitude", amplitude))
         .addConteudo(criarMetricaBox("Desvio Padrão", desvioPadrao))
         .addConteudo(criarMetricaBox("Variância", variancia))
         .addConteudo(criarMetricaBox("Soma", soma))
         .addConteudo(criarMetricaBox("Faltantes", faltantes));

      card.addConteudo(grid);
      ElementoHtml blocoEstatistico = new ElementoHtml()
               .setTag("div")
               .addAtributo("class", "bloco-estatistico");

      blocoEstatistico
         .addConteudo(new ElementoHtml()
               .setTag("h3")
               .setConteudoTextual("Distribuição (Quartis)"));

      ElementoHtml gridQuartis = new ElementoHtml()
               .setTag("div")
               .addAtributo("class", "grid-metricas");

      gridQuartis
         .addConteudo(criarMetricaBox("Q1", q1))
         .addConteudo(criarMetricaBox("Mediana", mediana))
         .addConteudo(criarMetricaBox("Q3", q3))
         .addConteudo(criarMetricaBox("IQR", iqr));

      blocoEstatistico.addConteudo(gridQuartis);

      card.addConteudo(blocoEstatistico);

         this.oElementoGeral.addConteudo(card);
   }

   private static ElementoHtml criarMetricaBox(String descricao, double valor) {
      ElementoHtml box = new ElementoHtml()
               .setTag("div")
               .addAtributo("class", "metrica-box");

      box
         .addConteudo(new ElementoHtml()
               .setTag("span")
               .addAtributo("class", "numero")
               .setConteudoTextual(String.format("%.2f", valor)))
         .addConteudo(new ElementoHtml()
               .setTag("span")
               .addAtributo("class", "descricao")
               .setConteudoTextual(descricao));

      return box;
   }
}

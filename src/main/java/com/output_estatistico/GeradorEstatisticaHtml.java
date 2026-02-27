package com.output_estatistico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.html.ObjectView;

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
      // this.setConteudo(this.getConteudoDados());
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

   public Map<String, Object> getDados() {
      return this.oDados;
   }


   public ElementoHtml getElementoGeral() {
      return this.oElementoGeral;
   }
}

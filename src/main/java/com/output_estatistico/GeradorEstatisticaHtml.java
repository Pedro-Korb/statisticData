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

      for (Dado oDado : oCabecalho.getDados()) {

         ElementoHtml titulo = new ElementoHtml()
            .setTag("h1")
            .setConteudoTextual(oDado.getAlias());

         ElementoHtml valor = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "valor")
            .setConteudoTextual(oDado.getInformacao().toString());

         ElementoHtml barra = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "barra")
            .addAtributo("style", "width: 70%;");

         ElementoHtml barraContainer = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "barra-container")
            .addConteudo(barra);

         ElementoHtml box = new ElementoHtml()
            .setTag("div")
            .addAtributo("class", "box")
            .addStyle("margin", "20px")
            .addConteudo(titulo)
            .addConteudo(valor)
            .addConteudo(barraContainer);

         this.getElementoGeral().addConteudo(box);
      }
   }

   private String gerarHtmlCabecalhoTeste(List<Object> aDados) {

      for (Object sValor : aDados) {

         HashMap<String, String> oAtributosDivPrincipal = new HashMap<String, String>() {
            {
               put("id", "container");
               put("class", "box");
            }
         };

         HashMap<String, String> oStyleDivPrincipal = new HashMap<String, String>() {
            {
               put("width", "200px");
               put("height", "200px");
               put("font-size", "14px");
               put("margin", "4px");
               put("border", "5px solid black");
               put("border-radius", "10px");
               put("padding", "20px");
            }
         };

         ElementoHtml oTitulo = new ElementoHtml()
               .setTag("h1")
               .setConteudoTextual(String.valueOf(sValor));

         ElementoHtml oDiv = new ElementoHtml()
               .setTag("div")
               .setAtributos(oAtributosDivPrincipal)
               .setStyle(oStyleDivPrincipal)
               .addConteudo(oTitulo);
      }

      return "sa";
   }

   public Map<String, Object> getDados() {
      return this.oDados;
   }


   public ElementoHtml getElementoGeral() {
      return this.oElementoGeral;
   }
}

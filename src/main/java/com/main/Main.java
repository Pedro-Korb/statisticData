package com.main;

import com.estrutura.GeradorJson;
import com.estrutura.Output;
import com.leitor_csv.LeitorCsv;
import com.output_estatistico.GeradorEstatisticaHtml;

public class Main {
   public static void main(String[] args) {

      GeradorJson.gerarJson(LeitorCsv.getDadosEstatisticos(LeitorCsv.getTabela("input_csv\\relatorio_pagamentos_2024.csv", ';')), "output\\informacoes_tabela.json");

      GeradorEstatisticaHtml oTeste = new GeradorEstatisticaHtml(LeitorCsv.getDadosEstatisticos(LeitorCsv.getTabela("input_csv\\relatorio_pagamentos_2022.csv", ';')));

      Output oOutputHtml = new Output("output\\estatisticas.html");
      oOutputHtml.criaArquivoTexto(oTeste.getHtmlEstatistica());
   }
} 
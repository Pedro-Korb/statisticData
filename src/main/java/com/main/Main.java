package com.main;

import java.util.HashMap;
import java.util.ArrayList;

import com.est.GeradorJson;
import com.est.Output;
import com.leitor_csv.Csv;
import com.leitor_csv.LeitorCsv;

public class Main {
   public static void main(String[] args) {


      GeradorJson.gerarJson(LeitorCsv.getDadosEstatisticos(LeitorCsv.getTabela("input_csv\\relatorio_pagamentos_2024.csv", ';')), "output\\informacoes_tabela.json");

   }
}
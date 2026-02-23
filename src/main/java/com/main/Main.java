package com.main;

import java.util.HashMap;
import java.util.ArrayList;

import com.est.GeradorJson;
import com.leitor_csv.LeitorCsv;

public class Main {
   public static void main(String[] args) {

      ArrayList<HashMap<String, String>> aTipoCsv = new ArrayList<HashMap<String, String>>();

      aTipoCsv.add(LeitorCsv.getTipoColunas("input_csv\\Leitos_2026.csv"));

      GeradorJson.gerarJson(aTipoCsv, "output\\tipo_csv.json");
   }
}
package com.leitor_csv;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.text.Normalizer;

import tech.tablesaw.api.BooleanColumn;
import tech.tablesaw.api.NumericColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.io.csv.CsvReadOptions;

public class LeitorCsv {

   public static Table getTabela(String sPath, char sSeparador) {
      try {
         normalizarCsv(sPath);
      } catch (Exception e) {
         e.printStackTrace();
      }
      CsvReadOptions oOptions = CsvReadOptions.builder(sPath)
            .separator(sSeparador)
            .maxCharsPerColumn(100000) 
            .build();

      return Table.read().usingOptions(oOptions);
   }

   public static void normalizarCsv(String sPathArquivo) throws IOException {
      Path sPath = Paths.get(sPathArquivo);
      List<String> aLinhas = Files.readAllLines(sPath, StandardCharsets.UTF_8);
      List<String> aLinhasNormalizadas = aLinhas.stream()
         .map(l -> Normalizer.normalize(l, Normalizer.Form.NFD)
         .replaceAll("\\p{M}", ""))
         .toList();
      Files.write(sPath, aLinhasNormalizadas, StandardCharsets.UTF_8);
   }

   public static Map<String, Object> getDadosEstatisticos(Table oTable) {
      Map<String, Object> oTableMap = new LinkedHashMap<>();

      oTableMap.put("nomeTabela", oTable.name());
      oTableMap.put("numeroLinhas", oTable.rowCount());
      oTableMap.put("numeroColunas", oTable.columnCount());
      oTableMap.put("nomeColunas", oTable.columnNames());

      List<Map<String, Object>> oColunasInfo = new ArrayList<>();
      for (Column<?> oColumn : oTable.columns()) {
         oColunasInfo.add(getDadosColuna(oColumn));
      }
      oTableMap.put("dadosColunas", oColunasInfo);
      return oTableMap;
   }

   private static Map<String, Object> getDadosColuna(Column<?> oColumn) {
      Map<String, Object> oDadosColuna = new LinkedHashMap<>();

      oDadosColuna.put("nomeColuna", oColumn.name());
      oDadosColuna.put("tipoColuna", oColumn.type().name());
      oDadosColuna.put("quantidadeValoresFaltantes", oColumn.countMissing());

      if (oColumn instanceof NumericColumn) {
         NumericColumn<?> oNumCol = (NumericColumn<?>) oColumn;
         oDadosColuna.put("media", arredondar(oNumCol.mean()));
         oDadosColuna.put("minimo", arredondar(oNumCol.min()));
         oDadosColuna.put("maximo", arredondar(oNumCol.max()));
         oDadosColuna.put("soma", arredondar(oNumCol.sum()));
         oDadosColuna.put("desvioPadrao", arredondar(oNumCol.standardDeviation()));
         oDadosColuna.put("variancia", arredondar(oNumCol.variance()));
         oDadosColuna.put("amplitude", arredondar(oNumCol.max() - oNumCol.min()));
         oDadosColuna.put("quartil_1", arredondar(oNumCol.quartile1()));
         oDadosColuna.put("quartil_2_mediana", arredondar(oNumCol.median()));
         oDadosColuna.put("quartil_3", arredondar(oNumCol.quartile3()));
         oDadosColuna.put("intervalo_interquartil", arredondar(oNumCol.quartile3() - oNumCol.quartile1()));
      }
      else if (oColumn instanceof StringColumn) {
         StringColumn oStrCol = (StringColumn) oColumn;
         oDadosColuna.put("quantidadeValoresUnicos", oStrCol.countUnique());

         Map<String, Integer> freqMap = new HashMap<>();
         for (String valor : oStrCol) {
            if (valor != null) {
                  freqMap.put(valor, freqMap.getOrDefault(valor, 0) + 1);
            }
         }

         if (!freqMap.isEmpty()) {
            List<Map.Entry<String, Integer>> freqList = new ArrayList<>(freqMap.entrySet());
            freqList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

            String sValorMaisFrequente = freqList.get(0).getKey();
            oDadosColuna.put("valorMaisFrequente", sValorMaisFrequente);

            Map<String, Object> oMaioresParticipacoes = new HashMap<>();
            int limite = Math.min(3, freqList.size());
            int somaTotal = freqMap.values().stream().mapToInt(Integer::intValue).sum();

            for (int i = 0; i < limite; i++) {
                  int iQuantidade = freqList.get(i).getValue();
                  String sCategoria = freqList.get(i).getKey();

                  oMaioresParticipacoes.put("categoria_" + (i + 1), sCategoria);
                  oMaioresParticipacoes.put("participacao_" + (i + 1), iQuantidade);
                  oMaioresParticipacoes.put("percentual_" + (i + 1),
                     arredondar(((double) iQuantidade / somaTotal) * 100));
            }

            oDadosColuna.put("maioresParticipacoes", oMaioresParticipacoes);
         }
      }
      else if (oColumn instanceof BooleanColumn) {
         BooleanColumn oBoolCol = (BooleanColumn) oColumn;
         oDadosColuna.put("quantidadeTrue", oBoolCol.countTrue());
         oDadosColuna.put("quantidadeFalse", oBoolCol.countFalse());
      }
      return oDadosColuna;
   }

   private static double arredondar(double dValor) {
      return Math.round(dValor * 100.0) / 100.0;
   }
}

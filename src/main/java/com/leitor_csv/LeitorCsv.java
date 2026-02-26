package com.leitor_csv;

import tech.tablesaw.api.BooleanColumn;
import tech.tablesaw.api.NumericColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.io.csv.CsvReadOptions;

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

public class LeitorCsv {

   public static Table getTabela(String sPath, char sSeparador) {
      try {
         normalizarCsv(sPath);
      } catch (Exception e) {
         e.printStackTrace();
      }
      CsvReadOptions oOptions = CsvReadOptions.builder(sPath)
            .separator(sSeparador)
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

   public static Map<String, Object> getDadosEstatisticos(Table table) {
      Map<String, Object> tableMap = new LinkedHashMap<>();

      tableMap.put("nomeTabela", table.name());
      tableMap.put("numeroLinhas", table.rowCount());
      tableMap.put("numeroColunas", table.columnCount());
      tableMap.put("nomeColunas", table.columnNames());

      List<Map<String, Object>> colunasInfo = new ArrayList<>();
      for (Column<?> column : table.columns()) {
         colunasInfo.add(getDadosColuna(column));
      }
      tableMap.put("dadosColunas", colunasInfo);
      return tableMap;
   }

   private static Map<String, Object> getDadosColuna(Column<?> column) {
      Map<String, Object> oDadosColuna = new LinkedHashMap<>();

      oDadosColuna.put("nomeColuna", column.name());
      oDadosColuna.put("tipoColuna", column.type().name());
      oDadosColuna.put("quantidadeValoresFaltantes", column.countMissing());

      if (column instanceof NumericColumn) {
         NumericColumn<?> numCol = (NumericColumn<?>) column;
         oDadosColuna.put("media", arredondar(numCol.mean()));
         oDadosColuna.put("minimo", arredondar(numCol.min()));
         oDadosColuna.put("maximo", arredondar(numCol.max()));
         oDadosColuna.put("soma", arredondar(numCol.sum()));
         oDadosColuna.put("desvioPadrao", arredondar(numCol.standardDeviation()));
         oDadosColuna.put("variancia", arredondar(numCol.variance()));
         oDadosColuna.put("amplitude", arredondar(numCol.max() - numCol.min()));
         oDadosColuna.put("quartil_1", arredondar(numCol.quartile1()));
         oDadosColuna.put("quartil_2_mediana", arredondar(numCol.median()));
         oDadosColuna.put("quartil_3", arredondar(numCol.quartile3()));
         oDadosColuna.put("intervalo_interquartil", arredondar(numCol.quartile3() - numCol.quartile1()));
      }
      else if (column instanceof StringColumn) {
         StringColumn strCol = (StringColumn) column;
         oDadosColuna.put("quantidadeValoresUnicos", strCol.countUnique());

         Table freqTable = strCol.countByCategory()
                                 .sortDescendingOn("Count");

         if (freqTable.rowCount() > 0) {
            String valorMaisFrequente = freqTable
                                             .stringColumn(0)
                                             .get(0);

            oDadosColuna.put("valorMaisFrequente", valorMaisFrequente);
            Map<String, Object> maioresParticipacoes = new HashMap<>();
            int limite = Math.min(3, freqTable.rowCount());

            for (int i = 0; i < limite; i++) {
                  int quantidade = freqTable.intColumn("Count").get(i);
                  String categoria = freqTable.stringColumn(0).get(i);

                  maioresParticipacoes.put("categoria_" + (i + 1), categoria);
                  maioresParticipacoes.put("participacao_" + (i + 1), quantidade);
                  maioresParticipacoes.put("percentual_" + (i + 1), arredondar(((double) quantidade / freqTable.intColumn("Count").sum()) * 100));
            }
            oDadosColuna.put("maioresParticipacoes", maioresParticipacoes);
         }
      }
      else if (column instanceof BooleanColumn) {
         BooleanColumn boolCol = (BooleanColumn) column;
         oDadosColuna.put("quantidadeTrue", boolCol.countTrue());
         oDadosColuna.put("quantidadeFalse", boolCol.countFalse());
      }
      return oDadosColuna;
   }

   private static double arredondar(double valor) {
      return Math.round(valor * 100.0) / 100.0;
   }
}

package com.leitor_csv;

import tech.tablesaw.api.BooleanColumn;
import tech.tablesaw.api.NumericColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.io.csv.CsvReadOptions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LeitorCsv {

   final static private char SEPARADOR_CSV = ';';


   public static Table getTabela(String sPath) {
      CsvReadOptions oOptions = CsvReadOptions.builder(sPath)
            .separator(SEPARADOR_CSV)
            .build();

      return Table.read().usingOptions(oOptions);
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
      Map<String, Object> colunaMap = new LinkedHashMap<>();

      colunaMap.put("nomeColuna", column.name());
      colunaMap.put("tipoColuna", column.type().name());
      colunaMap.put("quantidadeValoresFaltantes", column.countMissing());

      if (column instanceof NumericColumn) {

         NumericColumn<?> numCol = (NumericColumn<?>) column;

         colunaMap.put("media", numCol.mean());
         colunaMap.put("mediana", numCol.median());
         colunaMap.put("minimo", numCol.min());
         colunaMap.put("maximo", numCol.max());
         colunaMap.put("soma", numCol.sum());
         colunaMap.put("desvioPadrao", numCol.standardDeviation());

      }
      if (column instanceof StringColumn) {
         StringColumn strCol = (StringColumn) column;
         colunaMap.put("quantidadeValoresUnicos", strCol.countUnique());

         Table freqTable = strCol.countByCategory();
         if (freqTable.rowCount() > 0) {
            String valorMaisFrequente = freqTable.sortDescendingOn("Count")
                                                .column(0)
                                                .getString(0);

            colunaMap.put("valorMaisFrequente", valorMaisFrequente);
         }
      }
      else if (column instanceof BooleanColumn) {
         BooleanColumn boolCol = (BooleanColumn) column;
         colunaMap.put("quantidadeTrue", boolCol.countTrue());
         colunaMap.put("quantidadeFalse", boolCol.countFalse());
      }

      return colunaMap;
   }
}

package com.leitor_csv;

import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvReadOptions;
import java.util.HashMap;

public class LeitorCsv {

   public static HashMap<String, String> getTipoColunas(String sPath) {
      CsvReadOptions oOptions = CsvReadOptions.builder(sPath)
         .separator(';')
         .build();

      Table oTable = Table.read().usingOptions(oOptions);
      HashMap<String, String> oTipoColunas = new HashMap<String, String>();
      
      oTable.columns().forEach(column -> {
         oTipoColunas.put(column.name(), column.type().name());
      });

      return oTipoColunas;
   }
}


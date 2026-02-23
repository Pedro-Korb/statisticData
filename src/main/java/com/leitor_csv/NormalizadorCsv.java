package com.leitor_csv;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.List;

public class NormalizadorCsv {
   
   public static void normalizarCsv(String sPathArquivo) throws IOException {
      Path sPath = Paths.get(sPathArquivo);
      List<String> aLinhas = Files.readAllLines(sPath, StandardCharsets.UTF_8);
      List<String> aLinhasNormalizadas = aLinhas.stream()
         .map(l -> Normalizer.normalize(l, Normalizer.Form.NFD)
         .replaceAll("\\p{M}", ""))
         .toList();
      Files.write(sPath, aLinhasNormalizadas, StandardCharsets.UTF_8);
   }
}
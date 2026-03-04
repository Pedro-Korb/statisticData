package com.estrutura;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class GeradorJson {

   public static void gerarJson(Object oDado, String sPathSaidaJson) {
      try {
         new Output(sPathSaidaJson).criaArquivoTexto(new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(oDado));
      } catch (JsonProcessingException oException) {
         oException.printStackTrace();
      }
   }
}

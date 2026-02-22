package com.est;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeradorJson {

   public static void gerarJson(Object oDado, String sPathSaidaJson) {
        try {
            new Output(sPathSaidaJson).write(new ObjectMapper().writeValueAsString(oDado));
        } catch (JsonProcessingException oException) {
            oException.printStackTrace();
        }
   }
   
}

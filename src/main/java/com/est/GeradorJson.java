package com.est;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import com.fasterxml.jackson.databind.SerializationFeature;

import tech.tablesaw.api.*;

import java.util.*;
import tech.tablesaw.columns.*;
import java.util.LinkedHashMap;

public class GeradorJson {

   public static void gerarJson(Object oDado, String sPathSaidaJson) {
      try {
         new Output(sPathSaidaJson).criaArquivoTexto(new ObjectMapper().writeValueAsString(oDado));
      } catch (JsonProcessingException oException) {
         oException.printStackTrace();
      }
   }
}

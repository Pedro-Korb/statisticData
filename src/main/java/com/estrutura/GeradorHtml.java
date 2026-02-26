package com.estrutura;

import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class GeradorHtml {

   static List<String> aTagsSimples = List.of("br", "hr", "img", "input", "meta", "link", "area", "base", "col", "embed", "source", "track", "wbr");

   public static String gerarHtmlPagina(String sConteudo, String sLanguage, String sTituloPagina, Boolean bUsaEstiloPadrao) {

      String sEstilo = "";

      if (bUsaEstiloPadrao) {
         sEstilo = getStyle();
      }
      return """
         <!DOCTYPE html>
         <html lang="%s">
         <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>%s</title>
            %s
         </head>
         <body>
            %s
         </body>
         </html>
         """.formatted(sLanguage, sTituloPagina, sEstilo, sConteudo);
   }


   public static String gerarHtml(ElementoHtml oElementoHtml) {
      String sHtml = "";

      sHtml += "<" 
         + oElementoHtml.getTag()
         + getHtmlAtributos(oElementoHtml.getAtributos()) 
         + geAtributoStyle(oElementoHtml.getStyle()) 
         + ">\n";

      if(oElementoHtml.getConteudo().size() == 0 && oElementoHtml.getConteudoTextual() != null){
         sHtml += oElementoHtml.getConteudoTextual();
      }

      if(!aTagsSimples.contains(oElementoHtml.getTag())) {
         sHtml += 
            gerarHtmlConteudo(oElementoHtml.getConteudo())
            +  "</"
            + oElementoHtml.getTag()
            + ">\n";
      }

      return sHtml;
   }

   public static void gerarEmArquivoHtml(String sHtml, String sPathArquivoHtml) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(sPathArquivoHtml))) {
         writer.write(sHtml);

      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private static String getHtmlAtributos(Map<String, String> oMapAtributo) {
      String sHtmlAtributos = "";
      for (Map.Entry<String, String> oAtributo : oMapAtributo.entrySet()) {
         String sChave = oAtributo.getKey();
         String sValor = oAtributo.getValue();

         sHtmlAtributos += " " + sChave + "=\"" + sValor + "\""; 
      }

      return sHtmlAtributos;
   }

   private static String geAtributoStyle(Map<String, String> oMapStyle) {
      String sHtmlStyle = "";

      StringBuilder sbStyle = new StringBuilder();
      Iterator<Map.Entry<String, String>> aStyle = oMapStyle.entrySet().iterator();
      while (aStyle.hasNext()) {
         Map.Entry<String, String> oStyle = aStyle.next();
         sbStyle.append(oStyle.getKey())
               .append(": ")
               .append(oStyle.getValue())
               .append(";");

         if (aStyle.hasNext()) {
            sbStyle.append(" ");
         }
      }

      if (sbStyle.length() > 0) {
         sHtmlStyle = " style=\"" + sbStyle.toString() + "\"";
      }

      return sHtmlStyle;
   }

   private static String gerarHtmlConteudo(List<ElementoHtml> aElementoHtml) {
      String sHtml = "";

      for (ElementoHtml oElementoHtml : aElementoHtml) {
         sHtml += gerarHtml(oElementoHtml);
      }      

      return sHtml;
   }

   private static String getStyle() {
      return """
         <style>
            body {
               font-family: 'Segoe UI', sans-serif;
               background: linear-gradient(135deg, #1e1e2f, #2b2b45);
               margin: 0;
               padding: 30px;
               color: white;
            }

            .dashboard {
               display: flex;
               flex-wrap: wrap;
               gap: 20px;
            }

            .box {
               background: #2f2f4f;
               width: 250px;
               padding: 20px;
               border-radius: 15px;
               box-shadow: 0 8px 20px rgba(0,0,0,0.4);
               transition: transform 0.2s ease, box-shadow 0.2s ease;
            }

            .box:hover {
               transform: translateY(-5px);
               box-shadow: 0 12px 25px rgba(0,0,0,0.6);
            }

            .box h1 {
               font-size: 16px;
               margin: 0 0 10px 0;
               color: #a0a0ff;
            }

            .valor {
               font-size: 32px;
               font-weight: bold;
               margin: 10px 0;
            }

            .barra-container {
               background: rgba(255,255,255,0.1);
               border-radius: 20px;
               height: 10px;
               overflow: hidden;
            }

            .barra {
               height: 100%;
               background: linear-gradient(90deg, #4facfe, #00f2fe);
               width: 70%;
            }

            .percentual {
               font-size: 12px;
               margin-top: 5px;
               text-align: right;
               opacity: 0.8;
            }
         </style>
      """;
   }

}

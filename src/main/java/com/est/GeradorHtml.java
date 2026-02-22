package com.est;

import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class GeradorHtml {

   static List<String> aTagsSimples = List.of("br", "hr", "img", "input", "meta", "link", "area", "base", "col", "embed", "source", "track", "wbr");

public static String gerarHtmlPagina(String sConteudo, String sLanguage, String sTituloPagina) {

    return """
        <!DOCTYPE html>
        <html lang="%s">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>%s</title>
        </head>
        <body>
            %s
        </body>
        </html>
        """.formatted(sLanguage, sTituloPagina, sConteudo);
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

}

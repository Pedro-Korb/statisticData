package com.est;

import java.util.List;
import java.util.Map;

public class GeradorHtml {

   static List<String> aTagsSimples = List.of("br", "hr", "img", "input", "meta", "link", "area", "base", "col", "embed", "source", "track", "wbr");


   public static String gerarHtml(ElementoHtml oElementoHtml) {
      String sHtml = "";

      if(oElementoHtml.getConteudo().size() == 0){
         sHtml += oElementoHtml.getConteudoTextual();
         return sHtml;
      }

      sHtml += "<" 
         + oElementoHtml.getTag()
         + getHtmlAtributos(oElementoHtml.getAtributos()) 
         + geAtributoStyle(oElementoHtml.getStyle()) 
         + ">";

      if(!aTagsSimples.contains(oElementoHtml.getTag())) {
         sHtml += 
            gerarHtmlConteudo(oElementoHtml.getConteudo())
            +  "</"
            + oElementoHtml.getTag()
            + ">";
      }

      return sHtml;
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
      for (Map.Entry<String, String> oStyle : oMapStyle.entrySet()) {
         String sChave = oStyle.getKey();
         String sValor = oStyle.getValue();
         sHtmlStyle += " " + sChave + ": " + sValor + ";"; 
      } 

      if (sHtmlStyle != "" && !sHtmlStyle.isEmpty()) {
         sHtmlStyle = "style=\"" + sHtmlStyle + "\"";
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

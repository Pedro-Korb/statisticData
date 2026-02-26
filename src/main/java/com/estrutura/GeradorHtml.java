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
      <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
         <style>
            .header-resumo {
               background: linear-gradient(135deg, #1f2235, #181a2a);
               padding: 35px;
               border-radius: 18px;
               margin-bottom: 40px;
               display: flex;
               justify-content: space-between;
               align-items: center;
               box-shadow: 0 15px 40px rgba(0,0,0,0.6);
               border: 1px solid rgba(255,255,255,0.05);
               font-family: 'Inter', sans-serif;
            }

            .header-info h1 {
               margin: 8px 0 0 0;
               font-size: 30px;
               font-weight: 600;
               color: #ffffff;
               letter-spacing: -0.5px;
            }

            .header-info .label {
               font-size: 13px;
               text-transform: uppercase;
               letter-spacing: 1.5px;
               color: #6c7293;
            }

            .header-metricas {
               display: flex;
               gap: 50px;
            }

            .metrica {
               text-align: center;
            }

            .metrica .numero {
               font-size: 34px;
               font-weight: 700;
               display: block;
               color: #4facfe;
            }

            .metrica .descricao {
               font-size: 13px;
               margin-top: 6px;
               color: #8f96b2;
               letter-spacing: 0.5px;
            }
         </style>
      """;
   }

}

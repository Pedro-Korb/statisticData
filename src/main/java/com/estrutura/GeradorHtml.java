package com.estrutura;

import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class GeradorHtml {

   static List<String> aTagsSimples = List.of("br", "hr", "img", "input", "meta", "link", "area", "base", "col",
         "embed", "source", "track", "wbr");

   public static String gerarHtmlPagina(String sConteudo, String sLanguage, String sTituloPagina,
         Boolean bUsaEstiloPadrao) {

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

      if (oElementoHtml.getConteudo().size() == 0 && oElementoHtml.getConteudoTextual() != null) {
         sHtml += oElementoHtml.getConteudoTextual();
      }

      if (!aTagsSimples.contains(oElementoHtml.getTag())) {
         sHtml += gerarHtmlConteudo(oElementoHtml.getConteudo())
               + "</"
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

            .label {
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
            .card-analise-string {
               background: linear-gradient(135deg, #1f2235, #181a2a);
               padding: 35px;
               border-radius: 18px;
               margin-bottom: 40px;
               box-shadow: 0 15px 40px rgba(0,0,0,0.6);
               border: 1px solid rgba(255,255,255,0.05);
               font-family: 'Inter', sans-serif;
               color: #ffffff;
            }

            /* Título da coluna */
            .titulo-coluna {
               margin: 0 0 25px 0;
               font-weight: bolder;
               font-size: 25px;
               text-transform: uppercase;
               letter-spacing: 1.5px;
               color: #5ab4d7;
            }

            /* Bloco de métricas */
            .resumo-estatistico {
               display: flex;
               gap: 60px;
               margin-bottom: 30px;
            }

            .resumo-estatistico p {
               margin: 0;
            }

            /* Cada métrica */
            .metrica-string {
               text-align: center;
            }

            .metrica-string .numero {
               font-size: 28px;
               font-weight: 700;
               display: block;
               color: #4facfe;
            }

            .metrica-string .descricao {
               font-size: 12px;
               margin-top: 6px;
               color: #8f96b2;
               letter-spacing: 0.8px;
               text-transform: uppercase;
            }

            /* Seção Top Participações */
            .top-participacoes {
               margin-top: 20px;
            }

            .top-participacoes h3 {
               font-size: 14px;
               text-transform: uppercase;
               letter-spacing: 1.5px;
               color: #6c7293;
               margin-bottom: 20px;
            }

            /* Item da participação */
            .item-participacao {
               margin-bottom: 18px;
            }

            .label-categoria {
               display: flex;
               justify-content: space-between;
               font-size: 13px;
               margin-bottom: 6px;
               color: #cfd3ff;
            }

            /* Barra */
            .barra-container {
               width: 100%;
               height: 12px;
               background: rgba(255,255,255,0.06);
               border-radius: 8px;
               overflow: hidden;
            }

            .barra {
               height: 100%;
               background: linear-gradient(90deg, #4facfe, #00f2fe);
               border-radius: 8px;
               transition: width 0.6s ease;
               box-shadow: 0 0 10px rgba(79,172,254,0.6);
            }


            .card-analise-numerica {
               background: linear-gradient(135deg, #1f2235, #181a2a);
               padding: 35px;
               border-radius: 18px;
               margin-bottom: 40px;
               box-shadow: 0 15px 40px rgba(0,0,0,0.6);
               border: 1px solid rgba(255,255,255,0.05);
               font-family: 'Inter', sans-serif;
               color: #ffffff;
            }

            .numerica-header {
               margin-bottom: 30px;
            }

            .numerica-header .label-coluna {
               font-size: 12px;
               text-transform: uppercase;
               letter-spacing: 1.5px;
               color: #6c7293;
            }

            .numerica-header .nome-coluna {
               font-size: 26px;
               font-weight: 600;
               margin-top: 6px;
               letter-spacing: -0.5px;
               color: #ffffff;
            }

            /* Destaque principal (Média) */
            .destaque-media {
               margin-top: 20px;
               padding: 20px;
               border-radius: 14px;
               background: rgba(79,172,254,0.08);
               border: 1px solid rgba(79,172,254,0.25);
            }

            .destaque-media .titulo {
               font-size: 12px;
               text-transform: uppercase;
               letter-spacing: 1.2px;
               color: #8f96b2;
            }

            .destaque-media .valor {
               font-size: 28px;
               font-weight: 700;
               margin-top: 6px;
               color: #4facfe;
            }

            /* Grid métricas principais */
            .grid-metricas {
               display: grid;
               grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
               gap: 30px;
               margin-top: 30px;
            }

            .metrica-box .numero {
               font-size: 20px;
               font-weight: 600;
               color: #ffffff;
               display: block;
            }

            .metrica-box .descricao {
               font-size: 11px;
               margin-top: 6px;
               color: #8f96b2;
               text-transform: uppercase;
               letter-spacing: 1px;
            }

            /* Bloco estatístico */
            .bloco-estatistico {
               margin-top: 35px;
               padding-top: 25px;
               border-top: 1px solid rgba(255,255,255,0.06);
            }

            .bloco-estatistico h3 {
               font-size: 12px;
               text-transform: uppercase;
               letter-spacing: 1.5px;
               color: #6c7293;
               margin-bottom: 20px;
            }
         </style>
      """;
   }

}

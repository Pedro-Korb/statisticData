package com.main;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.est.ElementoHtml;
import com.est.Input;
import com.est.Output;

public class Main {
    public static void main(String[] args) {

        Input oInput = new Input("src\\main\\java\\com\\arquivo_texto");
        Output oSaida = new Output("output\\arquivo_texto");

        HashMap<String, String> oAtributosDivPrincipal = 
        new HashMap<String, String>() {{
            put("id", "container");
            put("class", "box");
        }};

        HashMap<String, String> oStyleDivPrincipal = 
        new HashMap<String, String>() {{
            put("color", "red");
            put("font-size", "14px");
            put("margin", "4px");
            put("margin-color", "black");
        }};

        HashMap<String, String> oAtributosParagrafo = 
        new HashMap<String, String>() {{
            put("id", "p_principal");
            put("class", "paragrafo");
        }};

        HashMap<String, String> oStyleParagrafo = 
        new HashMap<String, String>() {{
            put("color", "red");
            put("font-weight", "bold");
            put("font-size", "15px");
        }};

        HashMap<String, String> oAtributosTitulo = 
        new HashMap<String, String>() {{
            put("id", "titulo_principal");
            put("class", "titulo");
        }};

        HashMap<String, String> oStyleTitulo = 
        new HashMap<String, String>() {{
            put("color", "red");
            put("font-weight", "bold");
            put("font-size", "15px");
        }};

        ElementoHtml oTitulo = 
            new ElementoHtml()
            .setTag("h1")
            .setAtributos(oAtributosTitulo)
            .setStyle(oStyleTitulo)
            .setConteudoTextual("Títul top das galáxia");

        ElementoHtml oParagrafo = 
            new ElementoHtml()
            .setTag("p")
            .setAtributos(oAtributosParagrafo)
            .setStyle(oStyleParagrafo)
            .setConteudoTextual("Conteúdo gerado para teste");


        ElementoHtml oDivPrincipal = 
            new ElementoHtml()
            .setTag("div")
            .setAtributos(oAtributosDivPrincipal)
            .setStyle(oStyleDivPrincipal);

        oDivPrincipal
            .addConteudo(oTitulo)
            .addConteudo(oParagrafo);


        ObjectMapper mapper = new ObjectMapper();

        try {
            oSaida.write(mapper.writeValueAsString(oDivPrincipal));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("teste");
    }
}
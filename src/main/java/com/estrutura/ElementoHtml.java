package com.estrutura;

import java.util.HashMap;
import java.util.ArrayList;

public class ElementoHtml {

   private String sTag;
   private HashMap<String, String> oAtributo = new HashMap<String, String>();
   private HashMap<String, String> oStyle = new HashMap<String, String>();
   private ArrayList<ElementoHtml> aConteudo = new ArrayList<ElementoHtml>();
   private String sConteudoTextual;

   public ElementoHtml(String sTag, HashMap<String, String> oAtributo, HashMap<String, String> oStyle, ArrayList<ElementoHtml> aConteudo) {
      this.sTag = sTag;
      this.oAtributo = oAtributo;
      this.oStyle = oStyle;
      this.aConteudo = aConteudo;
   }

   public ElementoHtml(String sConteudoTextual) {
   }

   public ElementoHtml() {
   }

   public ElementoHtml setTag(String sTag) {
      this.sTag = sTag;
      return this;
   }

   public ElementoHtml setAtributos(HashMap<String, String> oAtributo) {
      this.oAtributo = oAtributo;
      return this;
   }

   public ElementoHtml addAtributo(String sAtributo, String sValor) {
      this.oAtributo.put(sAtributo, sValor);
      return this;
   }

   public ElementoHtml addStyle(String sStyle,  String sValor) {
      this.oStyle.put(sStyle, sValor);
      return this;
   }

   public ElementoHtml setStyle(HashMap<String, String> oStyle) {
      this.oStyle = oStyle;
      return this;
   }

   public ElementoHtml setConteudo(ArrayList<ElementoHtml> aConteudo) {
      this.aConteudo = aConteudo;
      return this;
   }

   public ElementoHtml addConteudo(ElementoHtml oElementoHtml) {
      this.aConteudo.add(oElementoHtml);
      return this;
   }

   public ElementoHtml setConteudoTextual(String sConteudoTextual) {
      this.sConteudoTextual = sConteudoTextual;
      return this;
   }

   public String getTag() {
      return sTag;
   }

   public HashMap<String, String> getAtributos() {
      return oAtributo;
   }

   public HashMap<String, String> getStyle() {
      return oStyle;
   }

   public ArrayList<ElementoHtml> getConteudo() {
      return aConteudo;
   }

   public String getConteudoTextual() {
      return sConteudoTextual;
   }
}
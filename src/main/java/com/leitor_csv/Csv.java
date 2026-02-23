package com.leitor_csv;

import java.util.HashMap;
import java.util.ArrayList;

public class Csv {

   private String sPath;
   private HashMap<String, String> oTipoColunas;
   private HashMap<String, ArrayList<String>> oColunasTipoTexto;
   private HashMap<String, ArrayList<Double>> oColunasTipoNumerico;
   private HashMap<String, ArrayList<String>> oColunasTipoData;
   private HashMap<String, ArrayList<Boolean>> oColunasTipoBoolean;


   public String getPath() {
      return this.sPath;
   }
   
   public HashMap<String,String> getTipoColunas() {
      return this.oTipoColunas;
   }

   public HashMap<String,ArrayList<String>> getColunasTipoTexto() {
      return this.oColunasTipoTexto;
   }

   public HashMap<String,ArrayList<Double>> getColunasTipoNumerico() {
      return this.oColunasTipoNumerico;
   }


   public HashMap<String,ArrayList<String>> getColunasTipoData() {
      return this.oColunasTipoData;
   }

   public HashMap<String,ArrayList<Boolean>> getColunasTipoBoolean() {
      return this.oColunasTipoBoolean;
   }
   
   public Csv setPath(String sPath) {
      this.sPath = sPath;
      return this;
   }

   public Csv setTipoColunas(HashMap<String,String> oTipoColunas) {
      this.oTipoColunas = oTipoColunas;
      return this;
   }
   
   public Csv setColunasTipoTexto(HashMap<String,ArrayList<String>> oColunasTipoTexto) {
      this.oColunasTipoTexto = oColunasTipoTexto;
      return this;
   }
   
   public Csv setColunasTipoNumerico(HashMap<String,ArrayList<Double>> oColunasTipoNumerico) {
      this.oColunasTipoNumerico = oColunasTipoNumerico;
      return this;
   }

   public Csv setColunasTipoBoolean(HashMap<String,ArrayList<Boolean>> oColunasTipoBoolean) {
      this.oColunasTipoBoolean = oColunasTipoBoolean;
      return this;
   }

   public Csv setColunasTipoData(HashMap<String,ArrayList<String>> oColunasTipoData) {
      this.oColunasTipoData = oColunasTipoData;
      return this;
   }
}

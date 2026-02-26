package com.output_estatistico;

public class Dado {

   private String sChave;
   private String sAlias;
   private Object xInformacao;

   public Dado() {
   }

   public Dado(String sChave, String sAlias, Object xInformacao) {
      this.sChave = sChave;
      this.sAlias = sAlias;
      this.xInformacao = xInformacao;
   }

   public String getAlias() {
      return this.sAlias;
   }

   public Dado setAlias(String sAlias) {
      this.sAlias = sAlias;
      return this;
   }

   public String getChave() {
      return this.sChave;
   }

   public Dado setChave(String sChave) {
      this.sChave = sChave;
      return this;
   }

   public Object getInformacao() {
      return this.xInformacao;
   }

   public Dado setInformacao(Object xInformacao) {
      this.xInformacao = xInformacao;
      return this;
   }
   
}

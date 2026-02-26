package com.output_estatistico;

import java.util.ArrayList;

public class CabecalhoDados {
   
   private ArrayList<Dado> aDadosCabecalho;

   public CabecalhoDados(ArrayList<Dado> aDados) {
      this.aDadosCabecalho = aDados;
   }

   public CabecalhoDados() {
      this.aDadosCabecalho = new ArrayList<Dado>();
   }

   public ArrayList<Dado> getDados() {
      return this.aDadosCabecalho;
   }

   public CabecalhoDados setDados(ArrayList<Dado> aDadosCabecalho) {
      this.aDadosCabecalho = aDadosCabecalho;
      return this;
   }

   public CabecalhoDados addDado(Dado oDado) {
      this.aDadosCabecalho.add(oDado);
      return this;
   }
}
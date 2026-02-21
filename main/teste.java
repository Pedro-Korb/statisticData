package main;

import est.Input;
import est.Output;

public class teste {
    public static void main(String[] args) {

        Input oInput = new Input("input\\teste.txt");
        Output oSaida = new Output("arquivo_texto\\arquivo_texto");
        oSaida.write(oInput.getDados());
    }
}

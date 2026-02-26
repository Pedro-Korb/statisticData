package com.estrutura;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Output {

    private String sPath;

    public Output(String sPath) {
        this.sPath = sPath;
    }

    public void criaArquivoTexto(Object data) {
        try {
            FileWriter fw = new FileWriter(this.sPath);
            PrintWriter pw = new PrintWriter(fw);

            pw.println(String.valueOf(data));

            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
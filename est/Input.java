package est;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Input {

    private String sPath;
    private ArrayList<String> aDados;

    /**
     * @param sPath
     */
    public Input(String sPath) {
        this.sPath = sPath;
        this.aDados = new ArrayList<>();
        if (sPath != null) {
            this.setDados();
        }
    }

    private void setDados() {
        try {
            File oFile = new File(this.sPath);
            Scanner oScanner = new Scanner(oFile);

            while (oScanner.hasNextLine()) {
                String sData = oScanner.nextLine();
                this.aDados.add(sData);
            }

            oScanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getDados() {
        return this.aDados;
    }
}
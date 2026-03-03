package com.ui;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {

   @FXML
   private Button btnSelecionarCsv;

   @FXML
   private void onSelecionarCsv(ActionEvent event) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Selecionar arquivo CSV");

      // filtro para CSV
      fileChooser.getExtensionFilters().add(
               new FileChooser.ExtensionFilter("Arquivos CSV", "*.csv")
      );

      // pega a janela atual
      Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
               .getScene()
               .getWindow();

      File arquivoSelecionado = fileChooser.showOpenDialog(stage);

      if (arquivoSelecionado != null) {
         System.out.println("Arquivo escolhido: " + arquivoSelecionado.getAbsolutePath());

         // 🔥 aqui será o próximo passo: ler o CSV
      }
   }

}
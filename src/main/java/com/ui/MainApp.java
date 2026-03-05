package com.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

   @Override
   public void start(Stage oStage) throws Exception {
      FXMLLoader oFxmlLoader = new FXMLLoader(
         MainApp.class.getResource("/tela_principal.fxml"));

      Scene oScene = new Scene(oFxmlLoader.load());
      oStage.setTitle("Analisador CSV");
      oStage.setScene(oScene);
      oStage.show();
   }

   public static void main(String[] args) {
      launch();
   }
}
package com.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.leitor_csv.LeitorCsv;
import com.output_estatistico.GeradorEstatisticaHtml;

public class MainController {

   @FXML
   private Button btnExportar;

   @FXML
   private Button btnGerarHtml;

   @FXML
   private Button btnGerarJson;

   @FXML
   private Button btnGerarXml;

   @FXML
   private StackPane dropArea;

   @FXML
   private Label lblArquivo;

   @FXML
   private Label lblStatusPreview;

   @FXML
   private TextArea txtPreview;

   private File arquivoSelecionado;
   
   private String ultimoJsonGerado;

   @FXML
   public void initialize() {
      desabilitarBotoes();
      configurarDragAndDrop();
   }

   @FXML
   private void onSelecionarCsv() {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Selecionar arquivo CSV");

      fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Arquivos CSV", "*.csv"));

      Stage stage = (Stage) dropArea.getScene().getWindow();
      File file = fileChooser.showOpenDialog(stage);

      if (file != null) {
         processarArquivo(file);
      }
   }

   private void configurarDragAndDrop() {

      dropArea.setOnDragOver(event -> {
         if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
         }
         event.consume();
      });

      dropArea.setOnDragDropped(event -> {
         var db = event.getDragboard();

         if (db.hasFiles()) {
            File file = db.getFiles().get(0);
            processarArquivo(file);
         }

         event.setDropCompleted(true);
         event.consume();
      });
   }

   private void processarArquivo(File file) {
      this.arquivoSelecionado = file;

      this.lblArquivo.setText("✅ " + file.getName());
      habilitarBotoes();
   }

   private void desabilitarBotoes() {
      this.btnGerarJson.setDisable(true);
      this.btnGerarHtml.setDisable(true);
      this.btnGerarXml.setDisable(true);
   }

   private void habilitarBotoes() {
      this.btnGerarJson.setDisable(false);
      this.btnGerarHtml.setDisable(false);
      this.btnGerarXml.setDisable(false);
   }

   @FXML
   private void onGerarHtml() {
      if (arquivoSelecionado == null) {
         return;
      }

      try {
         GeradorEstatisticaHtml gerador = new GeradorEstatisticaHtml(
               LeitorCsv.getDadosEstatisticos(
                     LeitorCsv.getTabela(
                           arquivoSelecionado.getAbsolutePath(),
                           ';')));

         String htmlGerado = gerador.getHtmlEstatistica();

         txtPreview.setText(htmlGerado);
      } catch (Exception e) {
         e.printStackTrace();
         lblStatusPreview.setText("Erro ao gerar HTML: " + e.getMessage());
      }
   }

   @FXML
   private void onGerarJson(ActionEvent event) {
      if (arquivoSelecionado == null) {
         mostrarAlerta("Selecione um arquivo CSV primeiro.");
         return;
      }

      try {
         ObjectMapper mapper = new ObjectMapper();
         mapper.enable(SerializationFeature.INDENT_OUTPUT);

         ultimoJsonGerado = mapper.writeValueAsString(
            LeitorCsv.getDadosEstatisticos(
               LeitorCsv.getTabela(arquivoSelecionado.getAbsolutePath(), ';')
            )
         );

         txtPreview.setText(ultimoJsonGerado);

      } catch (Exception e) {
         e.printStackTrace();
         mostrarAlerta("Erro ao gerar JSON.");
      }
   }
   private void mostrarAlerta(String msg) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText(null);
      alert.setContentText(msg);
      alert.showAndWait();
   }
}
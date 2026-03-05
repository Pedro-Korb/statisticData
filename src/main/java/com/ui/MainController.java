package com.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Desktop;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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

   @FXML
   private ComboBox<String> comboOpcoes;

   @FXML
   private Button btnVisualizarHtml;

   private char cSeparador;

   private String sTipoExportacao;

   private File oArquivoSelecionado;

   private String sUltimoJsonGerado;

   private String sUltimoXmlGerado;

   @FXML
   public void initialize() {
      configurarDragAndDrop();
      this.comboOpcoes.getItems().addAll(
         "Ponto e vírgula",
         "Vírgula",
         "Pipeline");
      this.comboOpcoes.setValue("Ponto e vírgula");
      this.cSeparador = ';';
   }

   @FXML
   private void onSelecionarCsv() {
      FileChooser oFileChooser = new FileChooser();
      oFileChooser.setTitle("Selecionar arquivo CSV");

      oFileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Arquivos CSV", "*.csv"));

      Stage oStage = (Stage) dropArea.getScene().getWindow();
      File oFile = oFileChooser.showOpenDialog(oStage);

      if (oFile != null) {
         processarArquivo(oFile);
      }
   }

   @FXML
   private void onGerarXml(ActionEvent event) {
      if (this.oArquivoSelecionado == null) {
         mostrarAlerta("Selecione um arquivo CSV primeiro.");
         return;
      }

      try {
         XmlMapper oXmlMapper = new XmlMapper();
         oXmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
         
         sUltimoXmlGerado = oXmlMapper.writeValueAsString(
            LeitorCsv.getDadosEstatisticos(
               LeitorCsv.getTabela(
                  oArquivoSelecionado.getAbsolutePath(),
                  this.cSeparador)));

         txtPreview.setText(sUltimoXmlGerado);
         this.sTipoExportacao = "xml";

      } catch (Exception oException) {
         oException.printStackTrace();
         mostrarAlerta("Erro ao gerar XML.");
      }
   }

   @FXML
   private void onGerarHtml() {
      if (this.oArquivoSelecionado == null) {
         mostrarAlerta("Selecione um arquivo CSV primeiro.");
         return;
      }

      try {
         GeradorEstatisticaHtml oGerador = new GeradorEstatisticaHtml(
            LeitorCsv.getDadosEstatisticos(
               LeitorCsv.getTabela(
                  oArquivoSelecionado.getAbsolutePath(),
                  this.cSeparador)));

         txtPreview.setText(oGerador.getHtmlEstatistica());
         this.sTipoExportacao = "html";
      } catch (Exception oException) {
         oException.printStackTrace();
         lblStatusPreview.setText("Erro ao gerar HTML: " + oException.getMessage());
      }
   }

   @FXML
   private void onGerarJson(ActionEvent event) {
      if (this.oArquivoSelecionado == null) {
         mostrarAlerta("Selecione um arquivo CSV primeiro.");
         return;
      }

      try {
         ObjectMapper oMapper = new ObjectMapper();
         oMapper.enable(SerializationFeature.INDENT_OUTPUT);

         sUltimoJsonGerado = oMapper.writeValueAsString(
            LeitorCsv.getDadosEstatisticos(
               LeitorCsv.getTabela(oArquivoSelecionado.getAbsolutePath(), this.cSeparador)));

         txtPreview.setText(sUltimoJsonGerado);
         this.sTipoExportacao = "json";

      } catch (Exception oException) {
         oException.printStackTrace();
         mostrarAlerta("Erro ao gerar JSON.");
      }
   }

   @FXML
   private void onExportarArquivo(ActionEvent event) {
      if (this.txtPreview.getText() == null || this.txtPreview.getText().isEmpty()) {
         mostrarAlerta("Nenhum conteúdo gerado para exportar.");
         return;
      }

      FileChooser oFileChooser = new FileChooser();
      oFileChooser.setTitle("Salvar Arquivo");
      FileChooser.ExtensionFilter oExtFilter;

      switch (this.sTipoExportacao) {
         case "json":
            oExtFilter = new FileChooser.ExtensionFilter("Arquivo JSON (*.json)", "*.json");
            oFileChooser.getExtensionFilters().add(oExtFilter);
            oFileChooser.setInitialFileName("analise_estatistica.json");
            break;

         case "xml":
            oExtFilter = new FileChooser.ExtensionFilter("Arquivo XML (*.xml)", "*.xml");
            oFileChooser.getExtensionFilters().add(oExtFilter);
            oFileChooser.setInitialFileName("analise_estatistica.xml");
            break;

         case "html":
            oExtFilter = new FileChooser.ExtensionFilter("Arquivo HTML (*.html)", "*.html");
            oFileChooser.getExtensionFilters().add(oExtFilter);
            oFileChooser.setInitialFileName("analise_estatistica.html");
            break;

         default:
            mostrarAlerta("Tipo de exportação inválido.");
            return;
      }

      File oFile = oFileChooser.showSaveDialog(null);

      if (oFile != null) {
         try (FileWriter writer = new FileWriter(oFile)) {
            writer.write(this.txtPreview.getText());
         } catch (IOException oException) {
            oException.printStackTrace();
            mostrarAlerta("Erro ao salvar arquivo.");
         }
      }
   }

   @FXML
   private void onChangeSeparador() {
      switch (this.comboOpcoes.getValue()) {
         case "Ponto e vírgula":
            this.cSeparador = ';';
            break;
         case "Vírgula":
            this.cSeparador = ',';
            break;
         case "Pipeline":
            this.cSeparador = '|';
            break;
         default:
            this.cSeparador = ';';
      }
   }

   @FXML
   private void onVisualizarHtml() {
      if (this.sTipoExportacao == null || this.sTipoExportacao != "html") {
         mostrarAlerta("Nenhum HTML gerado para visualizar.");
         return;
      }

      try {
         File oTempFile = File.createTempFile("preview_", ".html");
         oTempFile.deleteOnExit();

         try (FileWriter writer = new FileWriter(oTempFile)) {
            writer.write(txtPreview.getText());
         }

         Desktop.getDesktop().browse(oTempFile.toURI());
      } catch (IOException oException) {
         oException.printStackTrace();
      }
   }

   private void configurarDragAndDrop() {
      this.dropArea.setOnDragOver(event -> {
         if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
         }
         event.consume();
      });

      this.dropArea.setOnDragDropped(event -> {
         var db = event.getDragboard();

         if (db.hasFiles()) {
            processarArquivo(db.getFiles().get(0));
         }

         event.setDropCompleted(true);
         event.consume();
      });
   }

   private void processarArquivo(File oFile) {
      this.oArquivoSelecionado = oFile;
      this.lblArquivo.setText("✅ " + oFile.getName());
   }

   private void mostrarAlerta(String sMsg) {
      Alert oAlert = new Alert(Alert.AlertType.WARNING);
      oAlert.setHeaderText(null);
      oAlert.setContentText(sMsg);
      oAlert.showAndWait();
   }
}
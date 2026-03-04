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

   private char separador;

   private String tipoExportacao;

   private File arquivoSelecionado;

   private String ultimoJsonGerado;

   private String ultimoXmlGerado;

   @FXML
   public void initialize() {
      configurarDragAndDrop();
      this.comboOpcoes.getItems().addAll(
            "Ponto e vírgula",
            "Vírgula",
            "Pipeline");
      this.comboOpcoes.setValue("Ponto e vírgula");
      this.separador = ';';
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

   @FXML
   private void onGerarXml(ActionEvent event) {
      if (this.arquivoSelecionado == null) {
         mostrarAlerta("Selecione um arquivo CSV primeiro.");
         return;
      }

      try {
         XmlMapper xmlMapper = new XmlMapper();
         xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

         ultimoXmlGerado = xmlMapper.writeValueAsString(
               LeitorCsv.getDadosEstatisticos(
                     LeitorCsv.getTabela(
                           arquivoSelecionado.getAbsolutePath(),
                           this.separador)));

         txtPreview.setText(ultimoXmlGerado);
         this.tipoExportacao = "xml";

      } catch (Exception e) {
         e.printStackTrace();
         mostrarAlerta("Erro ao gerar XML.");
      }
   }

   @FXML
   private void onGerarHtml() {
      if (this.arquivoSelecionado == null) {
         mostrarAlerta("Selecione um arquivo CSV primeiro.");
         return;
      }

      try {
         GeradorEstatisticaHtml gerador = new GeradorEstatisticaHtml(
               LeitorCsv.getDadosEstatisticos(
                     LeitorCsv.getTabela(
                           arquivoSelecionado.getAbsolutePath(),
                           this.separador)));

         txtPreview.setText(gerador.getHtmlEstatistica());
         this.tipoExportacao = "html";
      } catch (Exception e) {
         e.printStackTrace();
         lblStatusPreview.setText("Erro ao gerar HTML: " + e.getMessage());
      }
   }

   @FXML
   private void onGerarJson(ActionEvent event) {
      if (this.arquivoSelecionado == null) {
         mostrarAlerta("Selecione um arquivo CSV primeiro.");
         return;
      }

      try {
         ObjectMapper mapper = new ObjectMapper();
         mapper.enable(SerializationFeature.INDENT_OUTPUT);

         ultimoJsonGerado = mapper.writeValueAsString(
               LeitorCsv.getDadosEstatisticos(
                     LeitorCsv.getTabela(arquivoSelecionado.getAbsolutePath(), this.separador)));

         txtPreview.setText(ultimoJsonGerado);
         this.tipoExportacao = "json";

      } catch (Exception e) {
         e.printStackTrace();
         mostrarAlerta("Erro ao gerar JSON.");
      }
   }

   @FXML
   private void onExportarArquivo(ActionEvent event) {
      if (this.txtPreview.getText() == null || this.txtPreview.getText().isEmpty()) {
         mostrarAlerta("Nenhum conteúdo gerado para exportar.");
         return;
      }

      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Salvar Arquivo");

      FileChooser.ExtensionFilter extFilter;

      switch (this.tipoExportacao) {

         case "json":
            extFilter = new FileChooser.ExtensionFilter("Arquivo JSON (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setInitialFileName("analise_estatistica.json");
            break;

         case "xml":
            extFilter = new FileChooser.ExtensionFilter("Arquivo XML (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setInitialFileName("analise_estatistica.xml");
            break;

         case "html":
            extFilter = new FileChooser.ExtensionFilter("Arquivo HTML (*.html)", "*.html");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setInitialFileName("analise_estatistica.html");
            break;

         default:
            mostrarAlerta("Tipo de exportação inválido.");
            return;
      }

      File file = fileChooser.showSaveDialog(null);

      if (file != null) {
         try (FileWriter writer = new FileWriter(file)) {
            writer.write(this.txtPreview.getText());
         } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao salvar arquivo.");
         }
      }
   }

   @FXML
   private void onChangeSeparador() {
      switch (this.comboOpcoes.getValue()) {
         case "Ponto e vírgula":
            this.separador = ';';
            break;
         case "Vírgula":
            this.separador = ',';
            break;
         case "Pipeline":
            this.separador = '|';
            break;
         default:
            this.separador = ';';
      }
   }

   @FXML
   private void onVisualizarHtml() {
      if (this.tipoExportacao == null || this.tipoExportacao != "html") {
         mostrarAlerta("Nenhum HTML gerado para visualizar.");
         return;
      }

      try {
         File tempFile = File.createTempFile("preview_", ".html");
         tempFile.deleteOnExit();

         try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(txtPreview.getText());
         }

         Desktop.getDesktop().browse(tempFile.toURI());
      } catch (IOException e) {
         e.printStackTrace();
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
   }

   private void mostrarAlerta(String msg) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setHeaderText(null);
      alert.setContentText(msg);
      alert.showAndWait();
   }
}
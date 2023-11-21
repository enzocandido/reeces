package br.com.fatec.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController implements Initializable {

    private Button btn_cadastrar;
    private Button btn_gerenciar;
    private Button btn_barbeiros;
    private Button btn_agendar;
    private Button btn_consultar;
    @FXML
    private Button btnClientes;
    @FXML
    private Button btnRecepcionista;
    @FXML
    private Button btnBarbeiros;
    @FXML
    private Button btnAgendamentos;
    @FXML
    private Button btnConsultar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animacaoBotao(btnClientes);
        animacaoBotao(btnRecepcionista);
        animacaoBotao(btnBarbeiros);
        animacaoBotao(btnAgendamentos);
        animacaoBotao(btnConsultar);
    }

    private void animacaoBotao(Button button) {
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(100), button);
        scaleIn.setFromX(1.0);
        scaleIn.setFromY(1.0);
        scaleIn.setToX(1.1);
        scaleIn.setToY(1.1);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(100), button);
        scaleOut.setFromX(1.1);
        scaleOut.setFromY(1.1);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        button.setOnMouseEntered(event -> {
            scaleIn.play();
            alterarAparenciaCursor(button, Cursor.HAND);
        });

        button.setOnMouseExited(event -> {
            scaleOut.play();
            restaurarAparenciaCursor(button);
        });
    }

    private void animacaoTransicao(Parent root) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    private void alterarAparenciaCursor(Button button, Cursor cursor) {
        button.setCursor(cursor);
    }

    private void restaurarAparenciaCursor(Button button) {
        button.setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void btnClientes_Click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/cadastro_cliente.fxml"));
        Parent root = loader.load();
        animacaoTransicao(root);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void btnRecepcionista_Click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/cadastro_recepcionista.fxml"));
        Parent root = loader.load();
        animacaoTransicao(root);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void btnBarbeiros_Click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/cadastro_barbeiros.fxml"));
        Parent root = loader.load();
        animacaoTransicao(root);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void btnAgendamentos_Click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/agendamento.fxml"));
        Parent root = loader.load();
        animacaoTransicao(root);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void btnConsultar_Click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/consulta.fxml"));
        Parent root = loader.load();
        animacaoTransicao(root);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
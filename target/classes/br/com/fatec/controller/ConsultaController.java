package br.com.fatec.controller;

import br.com.fatec.DAO.AgendamentoDAO;
import br.com.fatec.model.Agendamento;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ConsultaController implements Initializable {

    @FXML
    private TextField txtNome;
    @FXML
    private Button btnFiltrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private DatePicker dpData;
    @FXML
    private TableView<Agendamento> tbConsulta;
    @FXML
    private TableColumn<Agendamento, Integer> tcId;
    @FXML
    private TableColumn<Agendamento, String> tcNome;
    @FXML
    private TableColumn<Agendamento, java.sql.Date> tcData;
    @FXML
    private TableColumn<Agendamento, String> tcProfissional;
    @FXML
    private TableColumn<Agendamento, String> tcUnidade;
    @FXML
    private TableColumn<Agendamento, Double> tcValor;
    @FXML
    private TableColumn<Agendamento, Integer> tcIdServico;
    
    private AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animacaoBotao(btnFiltrar);
        animacaoBotao(btnCancelar);
        configurarTabela();
    }    

    @FXML
    private void btnCancelar_Click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/menu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    private void configurarTabela() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        tcData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tcProfissional.setCellValueFactory(new PropertyValueFactory<>("profissional"));
        tcUnidade.setCellValueFactory(new PropertyValueFactory<>("unidade"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tcIdServico.setCellValueFactory(new PropertyValueFactory<>("servicoId"));
    }

    @FXML
    private void btnFiltrar_Click(ActionEvent event) {
        String nome = txtNome.getText();
        java.sql.Date data = (dpData.getValue() != null) ? java.sql.Date.valueOf(dpData.getValue()) : null;

        if (nome.isEmpty() && data == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Preencha pelo menos um dos campos (Nome ou Data) para realizar a filtragem.");
            alert.showAndWait();
            return;
        }

        try {
            ObservableList<Agendamento> agendamentosFiltrados = agendamentoDAO.filtrarPorNomeEData(nome, data);
            tbConsulta.setItems(agendamentosFiltrados);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    
}
package br.com.fatec.controller;

import br.com.fatec.DAO.ClienteDAO;

import br.com.fatec.model.Clientes;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;

import java.time.LocalDate;
import java.time.ZoneId;

import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

public class Cadastro_clienteController implements Initializable {

    @FXML
    private AnchorPane clientes_cadastro;
    @FXML
    private Button btnCadastrar;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtSexo;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtEndereco;
    @FXML
    private TextField txtId;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker dpNascimento;
    @FXML
    private Button btnPesquisar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void btnCadastrar_Click(ActionEvent event) {
        try {
            inserirCliente();
            limparCampos();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnAlterar_Click(ActionEvent event) {
    }

    @FXML
    private void btnExcluir_Click(ActionEvent event) {
        ClienteDAO clienteDAO = new ClienteDAO();
        
        try {
            int idCliente = Integer.parseInt(txtId.getText());

            Clientes cliente = new Clientes();
            cliente.setId(idCliente);

            boolean removido = clienteDAO.remove(cliente);

            Alert alert = new Alert(removido ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);

            if (removido) {
                alert.setContentText("Cliente removido com sucesso!");
                limparCampos();
            } else {
                alert.setContentText("Falha ao remover o cliente. Verifique o ID.");
            }

            alert.showAndWait();
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnCancelar_Click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/menu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    private void inserirCliente() throws SQLException, ParseException {
        ClienteDAO clienteDAO = new ClienteDAO();

        try {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String sexo = txtSexo.getText();
            String telefone = txtTelefone.getText();
            LocalDate dataNascimento = dpNascimento.getValue();

            String endereco = txtEndereco.getText();

            Clientes cliente = new Clientes();
            cliente.setNome(nome);
            cliente.setEmail(email);
            cliente.setSexo(sexo);
            cliente.setTelefone(telefone);
            cliente.setDataNascimento(Date.from(dataNascimento.atStartOfDay(ZoneId.systemDefault()).toInstant())); 
            cliente.setEndereco(endereco);

            boolean inseriu = clienteDAO.insere(cliente);
            Alert alert = new Alert(inseriu ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);

            if (inseriu) {
                alert.setContentText("Cliente cadastrado com sucesso!");
                limparCampos();
            } else {
                alert.setContentText("Falha ao cadastrar o cliente. ");
            }

            alert.showAndWait();
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        txtNome.clear();
        txtEmail.clear();
        txtSexo.clear();
        txtTelefone.clear();
        dpNascimento.setValue(null);
        txtEndereco.clear();
    }

    @FXML
    private void btnPesquisar_Click(ActionEvent event) {
    }
    
}

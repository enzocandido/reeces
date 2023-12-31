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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

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
        animacaoBotao(btnCadastrar);
        animacaoBotao(btnExcluir);
        animacaoBotao(btnAlterar);
        animacaoBotao(btnPesquisar);
        animacaoBotao(btnCancelar);
    }

    @FXML
    private void btnCadastrar_Click(ActionEvent event) {
        try {
            inserirCliente();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnAlterar_Click(ActionEvent event) {
        ClienteDAO clienteDAO = new ClienteDAO();

        try {
            int idCliente = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String sexo = txtSexo.getText();
            String telefone = txtTelefone.getText();
            LocalDate dataNascimento = dpNascimento.getValue();
            String endereco = txtEndereco.getText();

            Clientes clienteAlterado = new Clientes();
            clienteAlterado.setId(idCliente);
            clienteAlterado.setNome(nome);
            clienteAlterado.setEmail(email);
            clienteAlterado.setSexo(sexo);
            clienteAlterado.setTelefone(telefone);
            clienteAlterado
                    .setDataNascimento(Date.from(dataNascimento.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            clienteAlterado.setEndereco(endereco);

            boolean alterou = clienteDAO.altera(clienteAlterado);

            Alert alert = new Alert(alterou ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);

            if (alterou) {
                alert.setContentText("Cliente modificado com sucesso!");
                limparCampos();
            } else {
                alert.setContentText("Falha ao alterar o cliente. Verifique os dados.");
            }

            alert.showAndWait();

        } catch (NumberFormatException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Verifique os dados e tente novamente.");
            alert.showAndWait();
        }
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Informe um ID valido.");
            alert.showAndWait();
        }
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
        ClienteDAO clienteDAO = new ClienteDAO();

        try {
            int idCliente = Integer.parseInt(txtId.getText());

            Clientes clientePesquisado = clienteDAO.pesquisa("id = " + idCliente);

            if (clientePesquisado != null) {
                txtNome.setText(clientePesquisado.getNome());
                txtEmail.setText(clientePesquisado.getEmail());
                txtSexo.setText(clientePesquisado.getSexo());
                txtTelefone.setText(clientePesquisado.getTelefone());

                LocalDate dataNascimento = clientePesquisado.getDataNascimento().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();

                dpNascimento.setValue(dataNascimento);

                txtEndereco.setText(clientePesquisado.getEndereco());

                btnAlterar.setOpacity(1);
                btnAlterar.setDisable(false);
            } else {
                limparCampos();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Cliente não encontrado.");
                alert.showAndWait();
                btnAlterar.setOpacity(0.5);
                btnAlterar.setDisable(true);
            }
        } catch (NumberFormatException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Informe um ID valido.");
            alert.showAndWait();
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

    @FXML
    private void txtInput_Typed(KeyEvent event) {
        String idClienteString = txtId.getText();

        if (!idClienteString.isEmpty()) {
            try {
                int idCliente = Integer.parseInt(idClienteString);
                if (idCliente > 0) {
                    btnExcluir.setDisable(false);
                    btnExcluir.setOpacity(1);
                } else {
                    btnAlterar.setOpacity(0.5);
                    btnExcluir.setOpacity(0.5);
                }
            } catch (NumberFormatException e) {
                btnAlterar.setOpacity(0.5);
                btnExcluir.setOpacity(0.5);
            }
        } else {
            btnExcluir.setDisable(true);
            btnAlterar.setDisable(true);
            btnExcluir.setOpacity(0.5);
            btnAlterar.setOpacity(0.5);
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

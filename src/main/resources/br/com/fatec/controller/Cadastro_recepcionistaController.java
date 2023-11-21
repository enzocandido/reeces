package br.com.fatec.controller;

import br.com.fatec.DAO.RecepcionistaDAO;
import br.com.fatec.model.Recepcionista;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Cadastro_recepcionistaController implements Initializable {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtSexo;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtSalario;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtId;
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
            inserirRecepcionista();
            limparCampos();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnAlterar_Click(ActionEvent event) {
        RecepcionistaDAO recepcionistaDAO = new RecepcionistaDAO();

        try {
            int idRecepcionista = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String sexo = txtSexo.getText();
            String telefone = txtTelefone.getText();
            double salario = Double.parseDouble(txtSalario.getText());

            Recepcionista recepcionistaAlterado = new Recepcionista();
            recepcionistaAlterado.setId(idRecepcionista);
            recepcionistaAlterado.setNome(nome);
            recepcionistaAlterado.setEmail(email);
            recepcionistaAlterado.setSexo(sexo);
            recepcionistaAlterado.setTelefone(telefone);
            recepcionistaAlterado.setSalario(salario);

            boolean alterou = recepcionistaDAO.altera(recepcionistaAlterado);
            
            Alert alert = new Alert(alterou ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);

            if (alterou) {
                alert.setContentText("Recepcionista modificado com sucesso!");
                limparCampos();
            } else {
                alert.setContentText("Falha ao alterar o recepcionista. Verifique os dados.");
            }
            
            alert.showAndWait();
            
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnExcluir_Click(ActionEvent event) {
        RecepcionistaDAO recepcionistaDAO = new RecepcionistaDAO();
        
        try {
            int idRecepcionista = Integer.parseInt(txtId.getText());

            Recepcionista recepcionista = new Recepcionista();
            recepcionista.setId(idRecepcionista);

            boolean removido = recepcionistaDAO.remove(recepcionista);

            Alert alert = new Alert(removido ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);

            if (removido) {
                alert.setContentText("Recepcionista removido com sucesso!");
                limparCampos();
            } else {
                alert.setContentText("Falha ao remover o recepcionista. Verifique o ID.");
            }

            alert.showAndWait();
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void inserirRecepcionista() throws SQLException, ParseException {
        RecepcionistaDAO recepcionistaDAO = new RecepcionistaDAO();

        try {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String sexo = txtSexo.getText();
            String telefone = txtTelefone.getText();
            double salario = Double.parseDouble(txtSalario.getText());

            Recepcionista recepcionista = new Recepcionista();
            recepcionista.setNome(nome);
            recepcionista.setEmail(email);
            recepcionista.setSexo(sexo);
            recepcionista.setTelefone(telefone);
            recepcionista.setSalario(salario);

            boolean inseriu = recepcionistaDAO.insere(recepcionista);
            Alert alert = new Alert(inseriu ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);

            if (inseriu) {
                alert.setContentText("Recepcionista cadastrado com sucesso!");
                limparCampos();
            } else {
                alert.setContentText("Falha ao cadastrar o recepcionista. ");
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
        txtSalario.clear();
    }

    @FXML
    private void btnPesquisar_Click(ActionEvent event) {
        RecepcionistaDAO recepcionistaDAO = new RecepcionistaDAO();

        try {
            int idRecepcionista = Integer.parseInt(txtId.getText());

            Recepcionista recepcionistaPesquisado = recepcionistaDAO.pesquisa("id = " + idRecepcionista);

            if (recepcionistaPesquisado != null) {
                txtNome.setText(recepcionistaPesquisado.getNome());
                txtEmail.setText(recepcionistaPesquisado.getEmail());
                txtSexo.setText(recepcionistaPesquisado.getSexo());
                txtTelefone.setText(recepcionistaPesquisado.getTelefone());
                txtSalario.setText(String.valueOf(recepcionistaPesquisado.getSalario()));
                
                btnAlterar.setOpacity(1);
                btnAlterar.setDisable(false);
            } else {
                limparCampos();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Recepcionista não encontrado.");
                alert.showAndWait();
                btnAlterar.setOpacity(0.5);
            }
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

    @FXML
    private void txtId_Typed(KeyEvent event) {
        String idRecepcionistaString = txtId.getText();

        if (!idRecepcionistaString.isEmpty()) {
            try {
                int idRecepcionista = Integer.parseInt(idRecepcionistaString);
                if (idRecepcionista > 0) {
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
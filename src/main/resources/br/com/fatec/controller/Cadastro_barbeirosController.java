package br.com.fatec.controller;

import br.com.fatec.model.Barbeiro;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Cadastro_barbeirosController implements Initializable {

    @FXML
    private Button btnCadastrar;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtSexo;
    @FXML
    private TextField txtSalario;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtId;
    @FXML
    private Button btnPesquisar;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnCancelar;

    private ArrayList<Barbeiro> barbeiros;
    @FXML
    private TextField txtEspecialidade;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animacaoBotao(btnCadastrar);
        animacaoBotao(btnExcluir);
        animacaoBotao(btnAlterar);
        animacaoBotao(btnPesquisar);
        animacaoBotao(btnCancelar);

        barbeiros = new ArrayList<>();
    }

    @FXML
    private void btnCadastrar_Click(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);

        try {
            Barbeiro novoBarbeiro = new Barbeiro(
                    Integer.parseInt(txtId.getText()),
                    txtNome.getText(),
                    txtEmail.getText(),
                    txtSexo.getText(),
                    txtTelefone.getText(),
                    Double.parseDouble(txtSalario.getText()),
                    txtEspecialidade.getText());
            barbeiros.add(novoBarbeiro);
            limparCampos();
            alert.setContentText("Barbeiro cadastrado com sucesso!");
        } catch (NumberFormatException e) {
            alert.setContentText("Falha ao cadastrar barbeiro. Verifique os dados.");
        }
        alert.showAndWait();
    }

    @FXML
    private void btnAlterar_Click(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);

        try {
            int idBarbeiro = Integer.parseInt(txtId.getText());

            Barbeiro barbeiroParaAlterar = null;
            for (Barbeiro b : barbeiros) {
                if (b.getId() == idBarbeiro) {
                    barbeiroParaAlterar = b;
                    break;
                }
            }

            if (barbeiroParaAlterar != null) {
                String nome = txtNome.getText();
                String email = txtEmail.getText();
                String sexo = txtSexo.getText();
                String telefone = txtTelefone.getText();
                double salario = Double.parseDouble(txtSalario.getText());
                String especialidade = txtEspecialidade.getText();

                barbeiroParaAlterar.setNome(nome);
                barbeiroParaAlterar.setEmail(email);
                barbeiroParaAlterar.setSexo(sexo);
                barbeiroParaAlterar.setTelefone(telefone);
                barbeiroParaAlterar.setSalario(salario);
                barbeiroParaAlterar.setEspecialidade(especialidade);

                alert.setContentText("Alteração realizada com sucesso!");
                alert.setAlertType(Alert.AlertType.INFORMATION);

                limparCampos();
            } else {
                alert.setContentText("Falha ao encontrar o barbeiro. Verifique os dados.");
                alert.setAlertType(Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            alert.setContentText("Informe valores válidos para os campos.");
            alert.setAlertType(Alert.AlertType.ERROR);
        }

        alert.showAndWait();
    }

    @FXML
    private void btnExcluir_Click(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);

        try {
            int idBarbeiro = Integer.parseInt(txtId.getText());

            Barbeiro barbeiroRemover = null;
            for (Barbeiro b : barbeiros) {
                if (b.getId() == idBarbeiro) {
                    barbeiroRemover = b;
                    break;
                }
            }

            if (barbeiroRemover != null) {
                barbeiros.remove(barbeiroRemover);

                limparCampos();

                alert.setContentText("Barbeiro excluido com sucesso!");
            } else {
                alert.setContentText("Falha ao encontrar o barbeiro. Verifique os dados.");
            }
        } catch (NumberFormatException e) {
            alert.setContentText("Informe um ID valido.");
        }
        alert.showAndWait();
    }

    @FXML
    private void btnCancelar_Click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/menu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
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

    private void limparCampos() {
        txtNome.clear();
        txtEmail.clear();
        txtSexo.clear();
        txtTelefone.clear();
        txtSalario.clear();
        txtId.clear();
        txtEspecialidade.clear();
    }

    @FXML
    private void btnPesquisar_Click(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);

        try {
            int idBarbeiro = Integer.parseInt(txtId.getText());

            Barbeiro barbeiroPesquisado = null;
            for (Barbeiro b : barbeiros) {
                if (b.getId() == idBarbeiro) {
                    barbeiroPesquisado = b;
                    break;
                }
            }

            if (barbeiroPesquisado != null) {
                preencherCampos(barbeiroPesquisado);
                btnAlterar.setDisable(false);
                btnAlterar.setOpacity(1);
            } else {
                limparCampos();
                alert.setContentText("Barbeiro não encontrado.");
                btnAlterar.setDisable(true);
                btnAlterar.setOpacity(0.5);
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            alert.setContentText("Informe um ID valido.");
            btnAlterar.setDisable(true);
            btnAlterar.setOpacity(0.5);
            alert.showAndWait();
        }
    }

    private void preencherCampos(Barbeiro barbeiro) {
        txtNome.setText(barbeiro.getNome());
        txtEmail.setText(barbeiro.getEmail());
        txtSexo.setText(barbeiro.getSexo());
        txtTelefone.setText(barbeiro.getTelefone());
        txtSalario.setText(String.valueOf(barbeiro.getSalario()));
        txtEspecialidade.setText(barbeiro.getEspecialidade());
    }

    @FXML
    private void txtId_Typed(KeyEvent event) {
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
}

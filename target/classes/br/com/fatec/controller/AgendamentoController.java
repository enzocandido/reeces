package br.com.fatec.controller;

import br.com.fatec.banco.Banco;
import br.com.fatec.model.Servicos;
import java.sql.SQLException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;

public class AgendamentoController implements Initializable {
    
    private PreparedStatement pst;
    private ResultSet rs;

    @FXML
    private TextField txtCliente;
    @FXML
    private Button btnCadastrar;
    @FXML
    private TextField txtProfissional;
    @FXML
    private TextField txtUnidade;
    @FXML
    private TextField txtId;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtTotal;
    @FXML
    private ComboBox<Servicos> cmbServicos;
    @FXML
    private Button btnPesquisar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            carregarDadosDoBanco();
            configurarComboBox();
            configurarOuvinteSelecao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        animacaoBotao(btnCadastrar);
        animacaoBotao(btnExcluir);
        animacaoBotao(btnAlterar);
        animacaoBotao(btnPesquisar);
        animacaoBotao(btnCancelar);
    }

    private void carregarDadosDoBanco() throws SQLException {
        Banco.conectar();

        String sql = "SELECT * FROM servicos";

        pst = Banco.obterConexao().prepareStatement(sql);
        rs = pst.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String descricao = rs.getString("descricao");
            float valor = rs.getFloat("valor");

            Servicos servico = new Servicos(id, nome, descricao, valor);
            cmbServicos.getItems().add(servico);
        }

        Banco.desconectar();
    }

    private void configurarComboBox() {
        cmbServicos.setCellFactory(new Callback<ListView<Servicos>, ListCell<Servicos>>() {
            @Override
            public ListCell<Servicos> call(ListView<Servicos> param) {
                return new ListCell<Servicos>() {
                    @Override
                    protected void updateItem(Servicos item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getNome());
                        }
                    }
                };
            }
        });

        cmbServicos.setButtonCell(new ListCell<Servicos>() {
            @Override
            protected void updateItem(Servicos item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome());
                }
            }
        });
        
        cmbServicos.setConverter(new StringConverter<Servicos>() {
            @Override
            public String toString(Servicos servicos) {
                return servicos == null ? null : servicos.getNome();
            }

            @Override
            public Servicos fromString(String string) {
                return null;
            }
        });
    }
    
    private void configurarOuvinteSelecao() {
        cmbServicos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtTotal.setText("R$ " + String.valueOf(newValue.getValor()));
            }
        });
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
    private void btnPesquisar_Click(ActionEvent event) {
    }

    @FXML
    private void btnAlterar_Click(ActionEvent event) {
    }

    @FXML
    private void btnExcluir_Click(ActionEvent event) {
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

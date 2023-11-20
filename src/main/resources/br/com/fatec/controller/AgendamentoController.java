/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.DAO.AgendamentoDAO;
import br.com.fatec.banco.Banco;
import br.com.fatec.model.Agendamento;
import br.com.fatec.model.Servicos;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author guilh
 */
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
    private TextField txtDataHorario;
    @FXML
    private TextField txtTotal;
    @FXML
    private ComboBox<Servicos> cmbServicos;
    @FXML
    private Button btnPesquisar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            carregarDadosDoBanco();
            configurarComboBox();
            configurarOuvinteSelecao();
        } catch (SQLException e) {
            e.printStackTrace();
            // Lide com a exceção de acordo com a sua necessidade
        }
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
        // Configurar uma fábrica de células para exibir apenas o nome do serviço
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

        // Configurar o texto exibido no botão do ComboBox
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
                return null; // Não necessário para exibir o nome
            }
        });
    }
    
    private void configurarOuvinteSelecao() {
        // Adicionar um ouvinte de mudança de seleção ao ComboBox
        cmbServicos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Atualizar o txtValor com o valor do serviço selecionado
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
    
}

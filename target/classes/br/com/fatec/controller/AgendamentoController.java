package br.com.fatec.controller;

import br.com.fatec.DAO.AgendamentoDAO;
import br.com.fatec.banco.Banco;
import br.com.fatec.model.Agendamento;
import br.com.fatec.model.Servicos;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;

public class AgendamentoController implements Initializable {

    private PreparedStatement pst;
    private ResultSet rs;

    @FXML
    private TextField txtId;
    private TextField txtNomeCliente;
    @FXML
    private DatePicker dpData;
    @FXML
    private TextField txtProfissional;
    @FXML
    private TextField txtUnidade;
    private TextField txtValor;
    private TextField txtServico;
    @FXML
    private Button btnCadastrar;
    @FXML
    private TextField txtCliente;
    @FXML
    private Button btnPesquisar;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnExcluir;
    @FXML
    private TextField txtTotal;
    @FXML
    private ComboBox<Servicos> cmbServicos;
    @FXML
    private Button btnCancelar;

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

    @FXML
    private void btnCancelar_Click(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/menu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    @FXML
    private void btnCadastrar_Click(ActionEvent event) {
        try {
            inserirAgendamento();
            limparCampos();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void inserirAgendamento() throws SQLException, ParseException {
        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

        try {
            String nomeCliente = txtCliente.getText();
            LocalDate data = dpData.getValue();
            String profissional = txtProfissional.getText();
            String unidade = txtUnidade.getText();
            String valorString = txtTotal.getText().replaceAll("[^\\d.]", "");
            double valor = Double.parseDouble(valorString);

            Servicos servicoSelecionado = cmbServicos.getSelectionModel().getSelectedItem();
            
            System.out.println(nomeCliente);
            System.out.println(data);
            System.out.println(profissional);
            System.out.println(unidade);
            System.out.println(valor);
            System.out.println(servicoSelecionado);



            if (servicoSelecionado != null) {
                int servicoId = servicoSelecionado.getId();

                Agendamento agendamento = new Agendamento();
                agendamento.setNomeCliente(nomeCliente);
                agendamento.setData(Date.from(data.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                agendamento.setProfissional(profissional);
                agendamento.setUnidade(unidade);
                agendamento.setValor(valor);
                agendamento.setServicoId(servicoId);

                boolean inseriu = agendamentoDAO.insere(agendamento);
                Alert alert = new Alert(inseriu ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);

                if (inseriu) {
                    alert.setContentText("Agendamento cadastrado com sucesso!");
                    limparCampos();
                } else {
                    alert.setContentText("Falha ao cadastrar o agendamento. ");
                }

                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Selecione um serviço antes de cadastrar o agendamento.");
                alert.showAndWait();
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        txtCliente.clear();
        dpData.setValue(null);
        txtProfissional.clear();
        txtUnidade.clear();
        txtTotal.clear();
        cmbServicos.setValue(null);
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
    private void btnPesquisar_Click(ActionEvent event) throws SQLException {
        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

        int idCliente = Integer.parseInt(txtId.getText());
        Agendamento agendamentoPesquisado = agendamentoDAO.pesquisa("id = " + idCliente);
        
        if (agendamentoPesquisado != null) {
            txtCliente.setText(agendamentoPesquisado.getNomeCliente());
            txtProfissional.setText(agendamentoPesquisado.getProfissional());
            txtUnidade.setText(agendamentoPesquisado.getUnidade());
            txtTotal.setText(String.valueOf(agendamentoPesquisado.getValor()));
            
            ObservableList<Servicos> servicosList = cmbServicos.getItems();
            
            int servicoIdDesejado = agendamentoPesquisado.getServicoId(); //

            for (Servicos servico : servicosList) {
                if (servico.getId() == servicoIdDesejado) {
                    cmbServicos.getSelectionModel().select(servico);
                    break;
                }
            }
            
            LocalDate dataAgendamento = agendamentoPesquisado.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            dpData.setValue(dataAgendamento);

            txtUnidade.setText(agendamentoPesquisado.getUnidade());

            btnAlterar.setOpacity(1);
            btnAlterar.setDisable(false);
        } else {
            limparCampos();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Agendamento não encontrado.");
            alert.showAndWait();
            btnAlterar.setOpacity(0.5);
            btnAlterar.setDisable(true);
        }
    }

    @FXML
    private void btnAlterar_Click(ActionEvent event) {
        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

        try {
            int idAgendamento = Integer.parseInt(txtId.getText());
            String nomeCliente = txtCliente.getText();
            LocalDate data = dpData.getValue();
            String profissional = txtProfissional.getText();
            String unidade = txtUnidade.getText();
            String valorString = txtTotal.getText().replaceAll("[^\\d.]", "");
            double valor = Double.parseDouble(valorString);

            Servicos servicoSelecionado = cmbServicos.getSelectionModel().getSelectedItem();

            Agendamento agendamentoAlterado = new Agendamento();
            agendamentoAlterado.setId(idAgendamento);
            agendamentoAlterado.setNomeCliente(nomeCliente);
            agendamentoAlterado.setData(Date.from(data.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            agendamentoAlterado.setProfissional(profissional);
            agendamentoAlterado.setUnidade(unidade);
            agendamentoAlterado.setValor(valor);

            if (servicoSelecionado != null) {
                agendamentoAlterado.setServicoId(servicoSelecionado.getId());
            }

            boolean alterou = agendamentoDAO.altera(agendamentoAlterado);

            Alert alert = new Alert(alterou ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);

            if (alterou) {
                alert.setContentText("Agendamento alterado com sucesso!");
                limparCampos();
            } else {
                alert.setContentText("Falha ao alterar o agendamento. Verifique os dados.");
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
        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

        try {
            int idAgendamento = Integer.parseInt(txtId.getText());

            Agendamento agendamento = new Agendamento();
            agendamento.setId(idAgendamento);

            boolean removido = agendamentoDAO.remove(agendamento);

            Alert alert = new Alert(removido ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);

            if (removido) {
                alert.setContentText("Agendamento removido com sucesso!");
                limparCampos();
            } else {
                alert.setContentText("Falha ao remover o agendamento. Verifique o ID.");
            }

            alert.showAndWait();
        } catch (NumberFormatException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Informe um ID válido.");
            alert.showAndWait();
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

    private void alterarAparenciaCursor(Button button, Cursor cursor) {
        button.setCursor(cursor);
    }

    private void restaurarAparenciaCursor(Button button) {
        button.setCursor(Cursor.DEFAULT);
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
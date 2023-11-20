/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author guilh
 */
public class MenuController implements Initializable {

    @FXML
    private Button btn_cadastrar;
    @FXML
    private Button btn_gerenciar;
    @FXML
    private Button btn_barbeiros;
    @FXML
    private Button btn_agendar;
    @FXML
    private Button btn_consultar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btn_cadastrar_Clicked(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/cadastro_cliente.fxml"));
         Parent root = loader.load();
         Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
         stage.setScene(new Scene(root));
         stage.show();
    }

    @FXML
    private void btn_gerenciar_Clicked(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/gerenciamento.fxml"));
         Parent root = loader.load();
         Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
         stage.setScene(new Scene(root));
         stage.show();
    }



    @FXML
    private void btn_barbeiros_Clicked(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/cadastro_barbeiros.fxml"));
         Parent root = loader.load();
         Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
         stage.setScene(new Scene(root));
         stage.show();
    }

    @FXML
    private void btn_agendar_Clicked(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/agendamento.fxml"));
         Parent root = loader.load();
         Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
         stage.setScene(new Scene(root));
         stage.show();
    }

    @FXML
    private void btn_consultar_Clicked(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/consulta.fxml"));
         Parent root = loader.load();
         Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
         stage.setScene(new Scene(root));
         stage.show();
    }
    
}

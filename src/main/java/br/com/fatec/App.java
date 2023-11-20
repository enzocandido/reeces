package br.com.fatec;

import br.com.fatec.banco.Banco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("menu"), 1440, 900);
        this.stage = stage;
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void fechar() {
        stage.close();
    }
    
    public static void main(String[] args) {
        launch();
        try {
            Banco.conectar();
            Banco.desconectar();
        }
        catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

}
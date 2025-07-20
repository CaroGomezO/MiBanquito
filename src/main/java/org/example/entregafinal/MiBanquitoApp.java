package org.example.entregafinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.entregafinal.dao.impl.AdministradorDAOIO;

import java.io.IOException;

public class MiBanquitoApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        AdministradorDAOIO adminDAO = new AdministradorDAOIO();

        if (adminDAO.obtenerAdministrador() == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(MiBanquitoApp.class.getResource("view/admin/registrar-admin.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("Mi Banquito - Registrar Administrador");
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(MiBanquitoApp.class.getResource("view/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("Mi Banquito - Login");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
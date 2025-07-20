package org.example.entregafinal.controller.cliente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.entregafinal.Sesion;

import java.io.IOException;

public class MenuClienteController {

    @FXML
    public void irAPerfil(ActionEvent actionEvent) {
        cargarVista("/org/example/entregafinal/view/cliente/editar-perfil-cliente.fxml", "Editar Perfil Cliente", actionEvent);
    }

    @FXML
    public void irACuenta(ActionEvent actionEvent) {
        cargarVista("/org/example/entregafinal/view/cliente/ver-cuenta.fxml", "Cuenta y Movimientos", actionEvent);
    }

    @FXML
    public void irADeposito(ActionEvent actionEvent) {
        cargarVista("/org/example/entregafinal/view/cliente/depositar.fxml", "Depositar Dinero", actionEvent);
    }

    @FXML
    public void irARetiro(ActionEvent actionEvent) {
        cargarVista("/org/example/entregafinal/view/cliente/retirar.fxml", "Retirar Dinero", actionEvent);
    }

    @FXML
    public void irATransferencia(ActionEvent actionEvent) {
        cargarVista("/org/example/entregafinal/view/cliente/transferir.fxml", "Transferencia", actionEvent);
    }

    @FXML
    public void cerrarSesion(ActionEvent actionEvent) {
        Sesion.cerrarSesion();
        cargarVista("/org/example/entregafinal/view/login.fxml", "Iniciar Sesión", actionEvent);
    }

    private void cargarVista(String ruta, String titulo, ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Mi Banquito - " + titulo);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

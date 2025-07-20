package org.example.entregafinal.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.entregafinal.Sesion;

import java.io.IOException;

public class MenuAdminController {
    @FXML
    public void irARegistrarCliente(ActionEvent actionEvent) {
        cargarVista("/org/example/entregafinal/view/admin/registrar-cliente.fxml", "Registrar Cliente", actionEvent);
    }

    @FXML
    public void irACrearCuentaParaCliente(ActionEvent actionEvent) {
        cargarVista("/org/example/entregafinal/view/admin/crear-cuenta-cliente.fxml", "Crear Cuenta para Cliente", actionEvent);
    }

    public void irAGestionClientes(ActionEvent actionEvent) {
        cargarVista("/org/example/entregafinal/view/admin/gestion-clientes.fxml", "Gestión de Clientes", actionEvent);
    }

    public void irAEditarMiPerfil(ActionEvent actionEvent) {
        cargarVista("/org/example/entregafinal/view/admin/editar-admin.fxml", "Editar Perfil Administrador", actionEvent);
    }

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

package org.example.entregafinal.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.entregafinal.Sesion;
import org.example.entregafinal.alertas.AlertaError;
import org.example.entregafinal.alertas.AlertaExito;
import org.example.entregafinal.dao.impl.AdministradorDAOIO;
import org.example.entregafinal.dao.impl.ClienteDAOIO;
import org.example.entregafinal.model.Administrador;
import org.example.entregafinal.model.Cliente;
import org.example.entregafinal.model.Usuario;
import org.example.entregafinal.service.UsuarioService;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField cedulaField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label mensajeLabel;

    private UsuarioService usuarioService;

    public void initialize() {
        usuarioService = new UsuarioService(
                new AdministradorDAOIO(),
                new ClienteDAOIO()
        );
    }

    @FXML
    public void iniciarSesion() {
        String cedula = cedulaField.getText().trim();
        String password = passwordField.getText().trim();

        Usuario usuario = usuarioService.iniciarSesion(cedula, password);

        if (usuario == null) {
            AlertaError.mostrar("Operación Fallida", "Credenciales incorrectas. Intente nuevamente");
        } else {

            Sesion.establecerUsuario(usuario);
            AlertaExito.mostrar("Operación Exitosa", "Bienvenido a Mi Banquito");

            try {
                Stage stage = (Stage) cedulaField.getScene().getWindow();
                if (usuario instanceof Administrador) {
                    Parent root = FXMLLoader.load(getClass().getResource("/org/example/entregafinal/view/admin/menu-admin.fxml"));
                    stage.setTitle("Mi Banquito - Menú Administrador");
                    stage.setScene(new Scene(root));
                    stage.show();
                } else if (usuario instanceof Cliente) {
                    Parent root = FXMLLoader.load(getClass().getResource("/org/example/entregafinal/view/cliente/menu-cliente.fxml"));
                    stage.setTitle("Mi Banquito - Menú Cliente");
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            } catch (IOException e) {
                mensajeLabel.setText("Error al cargar la vista. Intente nuevamente.");
                e.printStackTrace();
            }
        }
    }
}

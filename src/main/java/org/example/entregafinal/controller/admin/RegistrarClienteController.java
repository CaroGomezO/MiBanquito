package org.example.entregafinal.controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.entregafinal.alertas.AlertaError;
import org.example.entregafinal.alertas.AlertaExito;
import org.example.entregafinal.dao.impl.AdministradorDAOIO;
import org.example.entregafinal.dao.impl.ClienteDAOIO;
import org.example.entregafinal.dao.impl.CuentaDAOIO;
import org.example.entregafinal.model.Cliente;
import org.example.entregafinal.service.AdministradorService;
import org.example.entregafinal.service.UsuarioService;

public class RegistrarClienteController {
    @FXML
    private TextField cedulaField;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField apellidoField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField telefonoField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField direccionField;

    @FXML
    private Label mensajeLabel;

    private AdministradorService adminService;

    public void initialize() {
        UsuarioService usuarioService = new UsuarioService(
                new AdministradorDAOIO(),
                new ClienteDAOIO()
        );

        adminService = new AdministradorService(
                new AdministradorDAOIO(),
                new ClienteDAOIO(),
                new CuentaDAOIO(),
                usuarioService
        );
    }

    @FXML
    public void registrarCliente() {
        String cedula = cedulaField.getText().trim();
        String nombre = nombreField.getText().trim();
        String apellido = apellidoField.getText().trim();
        String email = emailField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String password = passwordField.getText().trim();
        String direccion = direccionField.getText().trim();

        Cliente cliente = new Cliente(cedula, nombre, apellido, email, telefono, password, direccion);

        String resultado = adminService.registrarCliente(cliente);

        if (resultado.toLowerCase().contains("exitosamente")) {
            AlertaExito.mostrar("Operación Exitosa", resultado);
            limpiarCampos();
            volverMenu();
        } else {
            AlertaError.mostrar("Operación Fallida", resultado);
        }
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        mensajeLabel.setText(mensaje);
        if (esError) {
            mensajeLabel.setStyle("-fx-text-fill: red;");
        } else {
            mensajeLabel.setStyle("-fx-text-fill: green;");
        }
    }

    private void limpiarCampos() {
        cedulaField.clear();
        nombreField.clear();
        apellidoField.clear();
        emailField.clear();
        telefonoField.clear();
        passwordField.clear();
        direccionField.clear();
        mensajeLabel.setText("");
    }

    public void volverMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/entregafinal/view/admin/menu-admin.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) cedulaField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Mi Banquito - Menú Administrador");
        } catch (Exception e) {
            mostrarMensaje("Error al volver al menú: " + e.getMessage(), true);
        }
    }
}

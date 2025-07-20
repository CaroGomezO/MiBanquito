package org.example.entregafinal.controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import org.example.entregafinal.model.Administrador;
import org.example.entregafinal.service.AdministradorService;
import org.example.entregafinal.service.UsuarioService;

import java.io.IOException;

public class EditarAdminController {
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

    private Administrador admin;

    @FXML
    public void initialize() {
        adminService = new AdministradorService(
                new AdministradorDAOIO(),
                new ClienteDAOIO(),
                new CuentaDAOIO(),
                new UsuarioService(new AdministradorDAOIO(), new ClienteDAOIO())
        );

        admin = new AdministradorDAOIO().obtenerAdministrador();

        if (admin != null) {
            cedulaField.setText(admin.getCedula());
            nombreField.setText(admin.getNombre());
            apellidoField.setText(admin.getApellido());
            emailField.setText(admin.getEmail());
            telefonoField.setText(admin.getTelefono());
            passwordField.setText(admin.getPassword());
            direccionField.setText(admin.getDireccion());
        }
    }

    @FXML
    public void guardarCambios() {
        String email = emailField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String password = passwordField.getText().trim();
        String direccion = direccionField.getText().trim();

        String resultado = adminService.editarInformacionPropia(email, telefono, password, direccion);

        if (resultado.contains("exitosamente")) {
            AlertaExito.mostrar("Operación Exitosa", resultado);
            volverMenu();
        } else {
            AlertaError.mostrar("Operación Fallida", resultado);
        }
    }

    @FXML
    public void volverMenu() {
        try {
            Stage stage = (Stage) cedulaField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/entregafinal/view/admin/menu-admin.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Mi Banquito - Menú Administrador");
            stage.show();
        } catch (IOException e){
            mensajeLabel.setText("Error al cargar el menú: " + e.getMessage());
        }

    }
}

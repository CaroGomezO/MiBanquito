package org.example.entregafinal.controller.cliente;

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
import org.example.entregafinal.dao.impl.ClienteDAOIO;
import org.example.entregafinal.dao.impl.CuentaDAOIO;
import org.example.entregafinal.dao.impl.MovimientoDAOIO;
import org.example.entregafinal.model.Cliente;
import org.example.entregafinal.service.ClienteService;
import org.example.entregafinal.service.CuentaService;
import org.example.entregafinal.service.UsuarioService;

import java.io.IOException;

public class EditarPerfilClienteController {
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

    private ClienteService clienteService;
    private UsuarioService usuarioService;

    public void initialize() {
        clienteService = new ClienteService(new ClienteDAOIO(), new CuentaDAOIO(), new MovimientoDAOIO(),
                new UsuarioService(null, new ClienteDAOIO()),
                new CuentaService(new CuentaDAOIO(), new MovimientoDAOIO(), new ClienteDAOIO()));

        usuarioService = new UsuarioService(null, new ClienteDAOIO());

        Cliente cliente = (Cliente) Sesion.obtenerUsuario();

        if (cliente != null) {
            cedulaField.setText(cliente.getCedula());
            nombreField.setText(cliente.getNombre());
            apellidoField.setText(cliente.getApellido());
            emailField.setText(cliente.getEmail());
            telefonoField.setText(cliente.getTelefono());
            passwordField.setText(cliente.getPassword());
            direccionField.setText(cliente.getDireccion());
        }
    }

    public void guardarCambios() {
        Cliente cliente = (Cliente) Sesion.obtenerUsuario();
        if (cliente == null) {
            return;
        }

        String email = emailField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String password = passwordField.getText().trim();

        String resultado = clienteService.actualizarInformacionCliente(cliente.getCedula(), email, telefono, password);

        if (resultado.contains("exitosamente")) {
            if (!email.trim().isEmpty()) {
                cliente.setEmail(email.trim());
            }

            if (!telefono.trim().isEmpty()) {
                cliente.setTelefono(telefono.trim());
            }

            if (!password.trim().isEmpty()) {
                cliente.setPassword(password.trim());
            }

            AlertaExito.mostrar("Operación Exitosa", resultado);
            volverMenu();
        } else {
            AlertaError.mostrar("Operación Fallida", resultado);
        }
    }

    public void volverMenu() {
        try {
            Stage stage = (Stage) cedulaField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/entregafinal/view/cliente/menu-cliente.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Mi Banquito - Menú Cliente");
            stage.show();
        } catch (IOException e) {
            mensajeLabel.setText("Error al cargar el menú: " + e.getMessage());
        }
    }
}

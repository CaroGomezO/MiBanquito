package org.example.entregafinal.controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.entregafinal.alertas.AlertaError;
import org.example.entregafinal.alertas.AlertaExito;
import org.example.entregafinal.dao.impl.AdministradorDAOIO;
import org.example.entregafinal.dao.impl.ClienteDAOIO;
import org.example.entregafinal.dao.impl.CuentaDAOIO;
import org.example.entregafinal.service.AdministradorService;

public class CrearCuentaClienteController {
    @FXML
    private TextField cedulaField;

    @FXML
    private ComboBox<String> tipoCuentaComboBox;

    @FXML
    private TextField numeroCuentaField;

    @FXML
    private Label mensajeLabel;

    private AdministradorService adminService;

    @FXML
    public void initialize() {
        adminService = new AdministradorService(
                new AdministradorDAOIO(),
                new ClienteDAOIO(),
                new CuentaDAOIO(),
                null
        );
    }

    @FXML
    public void crearCuenta() {
        String cedula = cedulaField.getText().trim();
        String tipoCuenta = tipoCuentaComboBox.getValue();
        String numeroCuenta = numeroCuentaField.getText().trim();

        String resultado = adminService.crearCuentaParaCliente(cedula, tipoCuenta, numeroCuenta);

        if (resultado.contains("exitosamente")) {
            AlertaExito.mostrar("Operación exitosa", resultado);
            volverMenu();
            limpiarCampos();
        } else {
            AlertaError.mostrar("Operación Fallida", resultado);
        }
    }

    private void limpiarCampos() {
        cedulaField.clear();
        tipoCuentaComboBox.getSelectionModel().clearSelection();
        numeroCuentaField.clear();
        mensajeLabel.setText("");
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        mensajeLabel.setText(mensaje);
        if (esError) {
            mensajeLabel.setStyle("-fx-text-fill: red;");
        } else {
            mensajeLabel.setStyle("-fx-text-fill: green;");
        }
    }

    public void volverMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/entregafinal/view/admin/menu-admin.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) cedulaField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Mi Banquito - Menú Administrador");
        } catch (Exception e) {
            mostrarMensaje("Error al redirigir al menú principal: " + e.getMessage(), true);
        }
    }
}

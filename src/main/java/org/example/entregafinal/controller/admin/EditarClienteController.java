package org.example.entregafinal.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

public class EditarClienteController {
    @FXML
    private TextField emailField;

    @FXML
    private TextField telefonoField;

    @FXML
    private TextField direccionField;

    @FXML
    private Label mensajeLabel;

    private AdministradorService adminService;
    private Cliente cliente;

    public void inicializarConCliente(Cliente cliente) {
        this.cliente = cliente;

        emailField.setText(cliente.getEmail());
        telefonoField.setText(cliente.getTelefono());
        direccionField.setText(cliente.getDireccion());

        adminService = new AdministradorService(
                new AdministradorDAOIO(),
                new ClienteDAOIO(),
                new CuentaDAOIO(),
                new UsuarioService(new AdministradorDAOIO(), new ClienteDAOIO())
        );
    }

    @FXML
    public void guardarCambios() {
        String email = emailField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String direccion = direccionField.getText().trim();

        String resultado = adminService.editarCliente(cliente.getCedula(), email, telefono, direccion);

        if (resultado.contains("exitosamente")) {
            AlertaExito.mostrar("Operación Exitosa", resultado);
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.close();
        } else {
            AlertaError.mostrar("Operación Fallida", resultado);
        }
    }
}

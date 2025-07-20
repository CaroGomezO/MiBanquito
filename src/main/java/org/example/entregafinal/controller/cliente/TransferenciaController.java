package org.example.entregafinal.controller.cliente;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.entregafinal.Sesion;
import org.example.entregafinal.alertas.AlertaConfirmacion;
import org.example.entregafinal.alertas.AlertaError;
import org.example.entregafinal.alertas.AlertaExito;
import org.example.entregafinal.dao.impl.ClienteDAOIO;
import org.example.entregafinal.dao.impl.CuentaDAOIO;
import org.example.entregafinal.dao.impl.MovimientoDAOIO;
import org.example.entregafinal.model.Cliente;
import org.example.entregafinal.service.CuentaService;

import java.io.IOException;

public class TransferenciaController {
    @FXML
    private TextField cuentaDestinoField;

    @FXML
    private TextField montoField;

    @FXML
    private Label mensajeLabel;

    private CuentaService cuentaService;

    public void initialize() {
        cuentaService = new CuentaService(
                new CuentaDAOIO(),
                new MovimientoDAOIO(),
                new ClienteDAOIO()
        );
    }

    @FXML
    public void realizarTransferencia (){
        Cliente cliente = (Cliente) Sesion.obtenerUsuario();

        if (cliente == null || cliente.getCuenta() == null) {
            AlertaError.mostrar("Error", "No se encontró la cuenta de origen.");
            return;
        }

        String numerocuentaDestino = cuentaDestinoField.getText().trim();
        String textoMonto = montoField.getText().trim();

        double monto;

        try {
            monto = Double.parseDouble(textoMonto);
        } catch (NumberFormatException e) {
            AlertaError.mostrar("Error", "El monto es inválido");
            return;
        }

        if (monto <= 0) {
            AlertaError.mostrar("Error", "El monto debe ser mayor a cero.");
            return;
        }

        if (monto > 2000000) {
            AlertaError.mostrar("Error", "El monto máximo para transferencias es de $2.000.000.");
            return;
        }

        Sesion.guardarUsuario(cliente);

        boolean decision = AlertaConfirmacion.mostrar("¿Deseas Realizar la Transferencia?", "Esta acción no se puede deshacer.");

        if (decision) {
            String resultado = cuentaService.realizarTransferencia(cliente, numerocuentaDestino, monto);

            if (resultado.contains("exitosamente")) {
                AlertaExito.mostrar("Operación Exitosa", resultado);
                volverMenu();
            } else {
                AlertaError.mostrar("Operación Fallida", resultado);
            }
        } else {
            AlertaError.mostrar("Operación Cancelada", "No se realizó ninguna transferencia.");
        }

    }

    public void volverMenu() {
        try {
            Stage stage = (Stage) montoField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/entregafinal/view/cliente/menu-cliente.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Mi Banquito - Menú Cliente");
            stage.show();
        } catch (IOException e) {
            mensajeLabel.setText("Error al volver al menú");
        }
    }
}

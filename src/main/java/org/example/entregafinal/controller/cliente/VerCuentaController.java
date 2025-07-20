package org.example.entregafinal.controller.cliente;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.entregafinal.Sesion;
import org.example.entregafinal.alertas.AlertaError;
import org.example.entregafinal.dao.impl.ClienteDAOIO;
import org.example.entregafinal.dao.impl.CuentaDAOIO;
import org.example.entregafinal.dao.impl.MovimientoDAOIO;
import org.example.entregafinal.model.Cliente;
import org.example.entregafinal.model.Cuenta;
import org.example.entregafinal.model.CuentaCorriente;
import org.example.entregafinal.service.CuentaService;

import java.io.IOException;

public class VerCuentaController {

    @FXML
    private Label numeroCuentaLabel;

    @FXML
    private Label tipoCuentaLabel;

    @FXML
    private Label saldoLabel;

    @FXML
    private Label mensajeLabel;

    private CuentaService cuentaService;

    public void initialize() {
        cuentaService = new CuentaService(
                new CuentaDAOIO(),
                new MovimientoDAOIO(),
                new ClienteDAOIO()
        );

        Cliente cliente = (Cliente) Sesion.obtenerUsuario();

        if (cliente == null || cliente.getCuenta() == null) {
            AlertaError.mostrar("Error", "No se pudo obtener la cuenta.");
            return;
        }

        Cuenta cuenta = cuentaService.buscarCuentaPorNumero(cliente.getCuenta().getNumeroCuenta());

        if (cuenta == null) {
            AlertaError.mostrar("Error", "La cuenta no existe");
            return;
        }

        if (cliente.getCuenta() == null || !cliente.getCuenta().getNumeroCuenta().equals(cuenta.getNumeroCuenta())) {
            cliente.setCuenta(cuenta);
        }

        Sesion.guardarUsuario(cliente);

        numeroCuentaLabel.setText(cuenta.getNumeroCuenta());
        tipoCuentaLabel.setText(cuenta instanceof CuentaCorriente ? "Cuenta Corriente" : "Cuenta de Ahorro");
        saldoLabel.setText("$"  + String.format("%.2f", cuenta.getSaldo()));
    }

    @FXML
    public void irAMovimientos() {
        try {
            Stage stage = (Stage) numeroCuentaLabel.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/entregafinal/view/cliente/movimientos.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Mi Banquito - Movimientos de la Cuenta");
            stage.show();
        } catch (IOException e) {
            mensajeLabel.setText("Error al cargar los movimientos.");
        }
    }

    @FXML
    public void volverMenu() {
        try {
            Stage stage = (Stage) numeroCuentaLabel.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/entregafinal/view/cliente/menu-cliente.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Mi Banquito - Menú Cliente");
            stage.show();
        } catch (IOException e) {
            mensajeLabel.setText("Error al volver al menú.");
        }
    }
}

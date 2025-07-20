package org.example.entregafinal.controller.cliente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.entregafinal.Sesion;
import org.example.entregafinal.alertas.AlertaError;
import org.example.entregafinal.dao.impl.MovimientoDAOIO;
import org.example.entregafinal.model.Cliente;
import org.example.entregafinal.model.Movimiento;
import org.example.entregafinal.service.MovimientoService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MovimientosClienteController {

    @FXML
    private TableView<Movimiento> tablaMovimientos;

    @FXML
    private TableColumn<Movimiento, String> colFecha;

    @FXML
    private TableColumn<Movimiento, String> colTipo;

    @FXML
    private TableColumn<Movimiento, String> colMonto;

    @FXML
    private TableColumn<Movimiento, String> colCuentaOrigen;

    @FXML
    private TableColumn<Movimiento, String> colCuentaDestino;

    @FXML
    private TableColumn<Movimiento, String> colSaldoAnterior;

    @FXML
    private TableColumn<Movimiento, String> colSaldoNuevo;

    @FXML
    private TableColumn<Movimiento, String> colEstado;

    @FXML
    private ComboBox<String> tipoCombo;

    @FXML
    private DatePicker fechaInicioPicker;

    @FXML
    private DatePicker fechaFinPicker;

    private MovimientoService movimientoService;
    private ObservableList<Movimiento> listaOriginal;

    @FXML
    public void initialize() {
        movimientoService = new MovimientoService(new MovimientoDAOIO());

        colFecha.setCellValueFactory(data ->
                javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue().getFecha().toString()));

        colTipo.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(data.getValue()::getTipoOperacion));
        colMonto.setCellValueFactory(data ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        String.format("$%,.2f", data.getValue().getMonto())
                ));
        colCuentaOrigen.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(data.getValue()::getNumeroCuentaOrigen));
        colCuentaDestino.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(data.getValue()::getNumeroCuentaDestino));
        colSaldoAnterior.setCellValueFactory(data ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        String.format("$%,.2f", data.getValue().getSaldoAnterior())
                ));
        colSaldoNuevo.setCellValueFactory(data ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        String.format("$%,.2f", data.getValue().getSaldoNuevo())
                ));
        colEstado.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(data.getValue()::getEstado));

        tipoCombo.getItems().addAll("Depósito", "Retiro", "Transferencia Enviada", "Transferencia Recibida");

        Cliente cliente = (Cliente) Sesion.obtenerUsuario();
        if (cliente != null && cliente.getCuenta() != null) {
            String numeroCuenta = cliente.getCuenta().getNumeroCuenta();
            List<Movimiento> movimientos = movimientoService.obtenerMovimientosPorCuenta(numeroCuenta);
            listaOriginal = FXCollections.observableArrayList(movimientos);
            tablaMovimientos.setItems(listaOriginal);
        }
    }

    @FXML
    public void filtrarMovimientos() {
        Cliente cliente = (Cliente) Sesion.obtenerUsuario();
        if (cliente == null || cliente.getCuenta() == null) {
            AlertaError.mostrar("Error", "No se pudo obtener la cuenta.");
            return;
        }

        LocalDate desde = fechaInicioPicker.getValue();
        LocalDate hasta = fechaFinPicker.getValue();
        String tipoSeleccionado = tipoCombo.getValue();

        ObservableList<Movimiento> filtrados = listaOriginal.filtered(m -> {
            boolean coincideTipo = (tipoSeleccionado == null || m.getTipoOperacion().equalsIgnoreCase(tipoSeleccionado));
            boolean dentroRango = true;
            if (desde != null && hasta != null) {
                dentroRango = !m.getFecha().isBefore(desde) && !m.getFecha().isAfter(hasta);
            } else if (desde != null) {
                dentroRango = !m.getFecha().isBefore(desde);
            } else if (hasta != null) {
                dentroRango = !m.getFecha().isAfter(hasta);
            }
            return coincideTipo && dentroRango;
        });

        if (filtrados.isEmpty()) {
            AlertaError.mostrar("Error", "No se encontraron movimientos que coincidan con los filtros aplicados.");
        }

        tablaMovimientos.setItems(filtrados);
    }

    @FXML
    public void mostrarTodos() {
        tablaMovimientos.setItems(listaOriginal);
        tipoCombo.getSelectionModel().clearSelection();
        fechaInicioPicker.setValue(null);
        fechaFinPicker.setValue(null);
    }

    @FXML
    public void volverAlMenu() {
        try {
            Stage stage = (Stage) tablaMovimientos.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/entregafinal/view/cliente/menu-cliente.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Mi Banquito - Menú Cliente");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

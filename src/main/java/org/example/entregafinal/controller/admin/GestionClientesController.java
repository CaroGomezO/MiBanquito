package org.example.entregafinal.controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.entregafinal.alertas.AlertaConfirmacion;
import org.example.entregafinal.alertas.AlertaError;
import org.example.entregafinal.alertas.AlertaExito;
import org.example.entregafinal.dao.impl.AdministradorDAOIO;
import org.example.entregafinal.dao.impl.ClienteDAOIO;
import org.example.entregafinal.dao.impl.CuentaDAOIO;
import org.example.entregafinal.model.Cliente;
import org.example.entregafinal.service.AdministradorService;
import org.example.entregafinal.service.UsuarioService;

import java.io.IOException;
import java.util.List;

public class GestionClientesController {

    @FXML
    private TextField busquedaField;

    @FXML
    private TableView<Cliente> tablaClientes;

    @FXML
    private TableColumn<Cliente, String> colCedula;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private TableColumn<Cliente, String> colEmail;

    @FXML
    private TableColumn<Cliente, String> colTelefono;

    @FXML
    private TableColumn<Cliente, Void> colAcciones;

    @FXML
    private Label mensajeLabel;

    private AdministradorService adminService;

    @FXML
    public void initialize() {
        adminService = new AdministradorService(
                new AdministradorDAOIO(),
                new ClienteDAOIO(),
                new CuentaDAOIO(),
                new UsuarioService(new AdministradorDAOIO(), new ClienteDAOIO())
        );

        colCedula.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(data.getValue()::getCedula));
        colNombre.setCellValueFactory(data -> {
            Cliente cliente = data.getValue();
            return javafx.beans.binding.Bindings.createStringBinding(() -> cliente.getNombre() + " " + cliente.getApellido());
        });

        colEmail.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(data.getValue()::getEmail));
        colTelefono.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(data.getValue()::getTelefono));

        cargarClientes(adminService.consultarTodosLosClientes());
    }

    private void cargarClientes(List<Cliente> clientes) {
        ObservableList<Cliente> lista = FXCollections.observableArrayList(clientes);
        tablaClientes.setItems(lista);
        agregarBotonesAcciones();
    }

    private void agregarBotonesAcciones() {
        colAcciones.setCellFactory(col -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");
            private final HBox hbox = new HBox(5, btnEditar, btnEliminar);

            {
                btnEditar.getStyleClass().add("table_action_btn");
                btnEliminar.getStyleClass().add("table_action_btn");

                btnEditar.setOnAction(event -> {
                    Cliente cliente = getTableView().getItems().get(getIndex());

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/entregafinal/view/admin/editar-cliente.fxml"));
                        Parent root = loader.load();

                        EditarClienteController controller = loader.getController();
                        controller.inicializarConCliente(cliente);

                        Stage stage = new Stage();
                        stage.setTitle("Mi Banquito - Editar Cliente");
                        stage.setScene(new Scene(root));
                        stage.showAndWait();

                        mostrarTodos();
                    } catch (IOException e) {
                        mensajeLabel.setText("Error al abrir la ventana de edición: " + e.getMessage());
                    }
                });

                btnEliminar.setOnAction(event -> {
                    Cliente cliente = getTableView().getItems().get(getIndex());

                    boolean decision = AlertaConfirmacion.mostrar("¿Desea Eliminar el Cliente?", "Esta acción no se puede deshacer.");

                    if (decision) {
                        String resultado = adminService.eliminarCliente(cliente.getCedula());
                        if (resultado.contains("exitosamente")) {
                            AlertaExito.mostrar("Operación Exitosa", resultado);
                        } else {
                            AlertaError.mostrar("Operación Fallida", resultado);
                        }

                    } else {
                        AlertaError.mostrar("Operación Cancelada", "No se eliminó al cliente.");
                    }
                    mostrarTodos();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(hbox);
                }
            }

        });
    }

    @FXML
    public void buscarCliente() {
        String texto = busquedaField.getText().trim();
        if (texto.trim().isEmpty()) {
            AlertaError.mostrar("Operación Fallida", "El campo de búsqueda no puede estar vacío.");
            return;
        }

        List<Cliente> resultados;
        if (texto.matches("\\d+")) {
            Cliente cliente = adminService.buscarClientePorCedula(texto);
            resultados = (cliente != null) ? List.of(cliente) : List.of();
        } else {
            resultados = adminService.buscarClientesPorNombre(texto);
        }

        if (resultados.isEmpty()) {
            AlertaError.mostrar("Error", "No se encontraron clientes con el criterio de búsqueda");
        } else {
            mensajeLabel.setText("");
        }
        cargarClientes(resultados);
    }

    @FXML
    public void mostrarTodos() {
        cargarClientes(adminService.consultarTodosLosClientes());
        mensajeLabel.setText("");
        busquedaField.clear();
    }

    public void volverAlMenuPrincipal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/entregafinal/view/admin/menu-admin.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) busquedaField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Mi Banquito - Menú Administrador");
        } catch (IOException e) {
            mensajeLabel.setText("Error al redirigir al menú principal: " + e.getMessage());
        }
    }
}

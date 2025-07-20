package org.example.entregafinal.service;

import org.example.entregafinal.dao.AdministradorDAO;
import org.example.entregafinal.dao.ClienteDAO;
import org.example.entregafinal.dao.CuentaDAO;
import org.example.entregafinal.model.*;

import java.util.List;

public class AdministradorService {
    private final AdministradorDAO administradorDAO;
    private final ClienteDAO clienteDAO;
    private final CuentaDAO cuentaDAO;
    private final UsuarioService usuarioService;

    public AdministradorService(AdministradorDAO administradorDAO, ClienteDAO clienteDAO, CuentaDAO cuentaDAO, UsuarioService usuarioService) {
        this.administradorDAO = administradorDAO;
        this.clienteDAO = clienteDAO;
        this.cuentaDAO = cuentaDAO;
        this.usuarioService = usuarioService;
    }

    public String registrarAdministrador(Administrador admin) {
        if (administradorDAO.obtenerAdministrador() != null) {
            return "Ya existe un administrador registrado.";
        }

        if (!usuarioService.validarFormatoCedula(admin.getCedula())) {
            return "La cédula debe tener entre 6 y 15 dígitos.";
        }

        if (!usuarioService.validarFormatoContrasena(admin.getPassword())) {
            return "La contraseña debe tener entre 8 y 15 caracteres, incluyendo al menos una mayúscula, una minúscula y un número.";
        }

        if (!usuarioService.validarFormatoEmail(admin.getEmail())) {
            return "El formato del email es inválido.";
        }

        if (!usuarioService.validarFormatoTelefono(admin.getTelefono())) {
            return "El teléfono debe tener entre 7 y 15 dígitos.";
        }

        if (!usuarioService.validarLongitudCampo(admin.getNombre(), 2, 15)) {
            return "El nombre debe tener entre 2 y 15 caracteres.";
        }

        if (!usuarioService.validarLongitudCampo(admin.getApellido(), 2, 20)) {
            return "El apellido debe tener entre 2 y 20 caracteres.";
        }

        if (!usuarioService.validarLongitudCampo(admin.getDireccion(), 5, 30)) {
            return "La dirección debe tener entre 5 y 30 caracteres.";
        }

        if (!usuarioService.validarCamposObligatorios(admin.getCedula(), admin.getNombre(), admin.getApellido(), admin.getEmail(),
                admin.getTelefono(), admin.getPassword(), admin.getDireccion())) {
            return "Todos los campos son obligatorios.";
        }

        if (clienteDAO.buscarPorCedula(admin.getCedula().trim()) != null) {
            return "Ya existe un usuario con esa cédula.";
        }

        boolean guardado = administradorDAO.guardarAdministrador(admin);

        if (guardado) {
            return "Administrador registrado exitosamente.";
        } else {
            return "Error al registrar el administrador.";
        }
    }

    public String editarInformacionPropia(String email, String telefono, String password, String direccion) {
        Administrador admin = administradorDAO.obtenerAdministrador();
        if (admin == null) {
            return "No hay un administrador registrado.";
        }

        if (password != null && !password.trim().isEmpty()) {
            if (!usuarioService.validarFormatoContrasena(password.trim())) {
                return "La contraseña debe tener entre 8 y 15 caracteres, incluyendo al menos una mayúscula, una minúscula y un número.";
            }
            admin.setPassword(password.trim());
        }

        if (email != null && !email.trim().isEmpty()) {
            if (!usuarioService.validarFormatoEmail(email.trim())) {
                return "El formato del email es inválido.";
            }
            admin.setEmail(email.trim());
        }

        if (telefono != null && !telefono.trim().isEmpty()) {
            if (!usuarioService.validarFormatoTelefono(telefono.trim())) {
                return "El teléfono debe tener entre 7 y 15 dígitos.";
            }
            admin.setTelefono(telefono.trim());
        }

        if (direccion != null && !direccion.trim().isEmpty()) {
            if (!usuarioService.validarLongitudCampo(direccion.trim(), 5, 30)) {
                return "La dirección debe tener entre 5 y 30 caracteres.";
            }
            admin.setDireccion(direccion.trim());
        }

        boolean actualizado = administradorDAO.actualizarAdministrador(admin);

        if (actualizado) {
            return "Información del administrador actualizada exitosamente.";
        } else {
            return "Error al actualizar la información del administrador.";
        }
    }

    public String registrarCliente(Cliente cliente) {
        if (!usuarioService.validarCamposObligatorios(cliente.getCedula(), cliente.getNombre(), cliente.getApellido(),
                cliente.getEmail(), cliente.getTelefono(), cliente.getPassword(), cliente.getDireccion())) {
            return "Todos los campos son obligatorios.";
        }

        if (!usuarioService.validarFormatoContrasena(cliente.getPassword().trim())) {
            return "La contraseña debe tener entre 8 y 15 caracteres, incluyendo al menos una mayúscula, una minúscula y un número.";
        }

        if (!usuarioService.validarFormatoCedula(cliente.getCedula().trim())) {
            return "La cédula debe tener entre 6 y 15 dígitos.";
        }

        if (!usuarioService.validarFormatoEmail(cliente.getEmail().trim())) {
            return "El formato del email es inválido.";
        }

        if (!usuarioService.validarFormatoTelefono(cliente.getTelefono().trim())) {
            return "El teléfono debe tener entre 7 y 15 dígitos.";
        }

        if (!usuarioService.validarLongitudCampo(cliente.getNombre().trim(), 2, 15)) {
            return "El nombre debe tener entre 2 y 15 caracteres.";
        }

        if (!usuarioService.validarLongitudCampo(cliente.getApellido().trim(), 2, 20)) {
            return "El apellido debe tener entre 2 y 20 caracteres.";
        }

        if (!usuarioService.validarLongitudCampo(cliente.getDireccion().trim(), 5, 30)) {
            return "La dirección debe tener entre 5 y 30 caracteres.";
        }

        Administrador admin = administradorDAO.obtenerAdministrador();
        if (clienteDAO.buscarPorCedula(cliente.getCedula().trim()) != null || (admin != null && admin.getCedula().trim().equals(cliente.getCedula().trim()))) {
            return "Ya existe un usuario con esa cédula.";
        }

        boolean guardado = clienteDAO.guardarCliente(cliente);

        if (guardado) {
            return "Cliente registrado exitosamente.";
        } else {
            return "Error al registrar el cliente.";
        }
    }

    public boolean validarNumeroCuentaCliente(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) {
            return false;
        }
        return numeroCuenta.matches("\\d{10}");
    }

    public String crearCuentaParaCliente(String cedula, String tipoCuenta, String numeroCuenta) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return "La cédula no puede estar vacía.";
        }

        if (tipoCuenta == null || tipoCuenta.trim().isEmpty()) {
            return "El tipo de cuenta no puede estar vacío.";
        }

        if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) {
            return "El número de cuenta no puede estar vacío.";
        }

        Cliente cliente = clienteDAO.buscarPorCedula(cedula.trim());
        if (cliente == null) {
            return "Cliente no encontrado.";
        }

        if (!validarNumeroCuentaCliente(numeroCuenta.trim())) {
            return "El número de cuenta debe tener 10 dígitos.";
        }

        if (cliente.getCuenta() != null) {
            return "El cliente ya tiene una cuenta asignada.";
        }

        if (cuentaDAO.buscarCuentaPorNumeroCuenta(numeroCuenta.trim()) != null) {
            return "Ya existe una cuenta con ese número.";
        }

        Cuenta nuevaCuenta;
        if ("Cuenta de Ahorro".equalsIgnoreCase(tipoCuenta)) {
            nuevaCuenta = new CuentaAhorro(numeroCuenta, cliente);
        } else if ("Cuenta Corriente".equalsIgnoreCase(tipoCuenta)) {
            nuevaCuenta = new CuentaCorriente(numeroCuenta, cliente);
        } else {
            return "Tipo de cuenta no válido.";
        }

        cliente.setCuenta(nuevaCuenta);
        boolean cuentaGuardada = cuentaDAO.guardarCuenta(nuevaCuenta);
        boolean clienteActualizado = clienteDAO.actualizarCliente(cliente);

        if (cuentaGuardada && clienteActualizado) {
            return "Cuenta creada exitosamente para el cliente con cédula: " + cedula + ".";
        } else {
            return "Error al crear la cuenta para el cliente.";
        }
    }

    public String editarCliente(String cedula, String email, String telefono, String direccion) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return "La cédula no puede estar vacía.";
        }

        Cliente cliente = clienteDAO.buscarPorCedula(cedula.trim());
        if (cliente == null) {
            return "Cliente no encontrado.";
        }

        if (email != null && !email.trim().isEmpty()) {
            if (!usuarioService.validarFormatoEmail(email.trim())) {
                return "El formato del email es inválido.";
            }
            cliente.setEmail(email.trim());
        }

        if (telefono != null && !telefono.trim().isEmpty()) {
            if (!usuarioService.validarFormatoTelefono(telefono.trim())) {
                return "El teléfono debe tener entre 7 y 15 dígitos.";
            }
            cliente.setTelefono(telefono.trim());
        }

        if (direccion != null && !direccion.trim().isEmpty()) {
            if (!usuarioService.validarLongitudCampo(direccion.trim(), 5, 30)) {
                return "La dirección debe tener entre 5 y 30 caracteres.";
            }
            cliente.setDireccion(direccion.trim());
        }

        boolean actualizado = clienteDAO.actualizarCliente(cliente);

        if (actualizado) {
            return "Cliente actualizado exitosamente.";
        } else {
            return "Error al actualizar el cliente.";
        }
    }

    public String eliminarCliente(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return "La cédula no puede estar vacía.";
        }

        Cliente cliente = clienteDAO.buscarPorCedula(cedula.trim());
        if (cliente == null) {
            return "Cliente no encontrado.";
        }

        if (cliente.getCuenta() != null) {
            boolean cuentaEliminada = cuentaDAO.eliminarCuenta(cliente.getCuenta().getNumeroCuenta());
            if (!cuentaEliminada) {
                return "Error al eliminar la cuenta del cliente.";
            }
        }

        boolean eliminado = clienteDAO.eliminarCliente(cedula.trim());

        if (eliminado) {
            return "Cliente eliminado exitosamente.";
        } else {
            return "Error al eliminar el cliente.";
        }
    }

    public Cliente buscarClientePorCedula(String cedula) {
        return clienteDAO.buscarPorCedula(cedula.trim());
    }

    public List<Cliente> buscarClientesPorNombre(String nombre) {
        return clienteDAO.buscarPorNombre(nombre.trim());
    }

    public List<Cliente> consultarTodosLosClientes() {
        return clienteDAO.obtenerTodosLosClientes();
    }

}

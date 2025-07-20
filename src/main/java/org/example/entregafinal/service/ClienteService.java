package org.example.entregafinal.service;

import org.example.entregafinal.dao.ClienteDAO;
import org.example.entregafinal.dao.CuentaDAO;
import org.example.entregafinal.dao.MovimientoDAO;
import org.example.entregafinal.model.Cliente;

public class ClienteService {
    private final ClienteDAO clienteDAO;
    private final CuentaDAO cuentaDAO;
    private final MovimientoDAO movimientoDAO;
    private final UsuarioService usuarioService;
    private final CuentaService cuentaService;

    public ClienteService(ClienteDAO clienteDAO, CuentaDAO cuentaDAO, MovimientoDAO movimientoDAO, UsuarioService usuarioService, CuentaService cuentaService) {
        this.clienteDAO = clienteDAO;
        this.cuentaDAO = cuentaDAO;
        this.movimientoDAO = movimientoDAO;
        this.usuarioService = usuarioService;
        this.cuentaService = cuentaService;
    }

    public String actualizarInformacionCliente(String cedula, String email, String telefono, String password) {
        Cliente cliente = clienteDAO.buscarPorCedula(cedula.trim());
        if (cliente == null) {
            return "Cliente no encontrado.";
        }

        if (password != null && !password.trim().isEmpty()) {
            if (!usuarioService.validarFormatoContrasena(password.trim())) {
                return "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial.";
            }
            cliente.setPassword(password.trim());
        }

        if (email != null && !email.trim().isEmpty()) {
            if (!usuarioService.validarFormatoEmail(email.trim())) {
                return "El formato del email es inválido";
            }
            cliente.setEmail(email.trim());
        }

        if (telefono != null && !telefono.trim().isEmpty()) {
            if (!usuarioService.validarFormatoTelefono(telefono.trim())) {
                return "El formato del teléfono es inválido.";
            }
            cliente.setTelefono(telefono.trim());
        }

        boolean actualizado = clienteDAO.actualizarCliente(cliente);

        if (actualizado) {
            return "Información del cliente actualizada exitosamente.";
        } else {
            return "Error al actualizar la información del cliente.";
        }
    }
}

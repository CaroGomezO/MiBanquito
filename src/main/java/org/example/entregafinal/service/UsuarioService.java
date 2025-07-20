package org.example.entregafinal.service;

import org.example.entregafinal.dao.AdministradorDAO;
import org.example.entregafinal.dao.ClienteDAO;
import org.example.entregafinal.model.Administrador;
import org.example.entregafinal.model.Cliente;
import org.example.entregafinal.model.Usuario;

import java.util.regex.Pattern;

public class UsuarioService {
    private final AdministradorDAO administradorDAO;
    private final ClienteDAO clienteDAO;

    public UsuarioService(AdministradorDAO administradorDAO, ClienteDAO clienteDAO) {
        this.administradorDAO = administradorDAO;
        this.clienteDAO = clienteDAO;
    }

    public Usuario iniciarSesion(String cedula, String password) {
        if (cedula == null || cedula.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return null;
        }

        Administrador admin = administradorDAO.obtenerAdministrador();
        if (admin != null && admin.getCedula().equals(cedula) && admin.getPassword().equals(password)) {
            return admin;
        }

        Cliente cliente = clienteDAO.buscarPorCedula(cedula);
        if (cliente != null && cliente.getPassword().equals(password)) {
            return cliente;
        }
        return null;
    }

    public boolean validarFormatoCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return false;
        }
        return cedula.matches("\\d{6,15}");
    }

    public boolean validarFormatoContrasena(String password) {
        if (password == null || password.trim().length() < 8 || password.trim().length() > 15) {
            return false;
        }

        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneNumero = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                tieneMayuscula = true;
            } else if (Character.isLowerCase(c)) {
                tieneMinuscula = true;
            } else if (Character.isDigit(c)) {
                tieneNumero = true;
            }
        }
        return tieneMayuscula && tieneMinuscula && tieneNumero;
    }

    public boolean validarFormatoTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty() || !telefono.matches("\\d{7,15}")) {
            return false;
        }
        return true;
    }

    public boolean validarFormatoEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        if (email.trim().length() > 50) {
            return false;
        }

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email.trim());
    }

    public boolean validarLongitudCampo(String campo, int min, int max) {
        if (campo == null || campo.trim().isEmpty()) {
            return false;
        }

        int longitud = campo.trim().length();
        return longitud >= min && longitud <= max;
    }

    public boolean validarCamposObligatorios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}

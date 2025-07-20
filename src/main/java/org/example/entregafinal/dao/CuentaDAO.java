package org.example.entregafinal.dao;

import org.example.entregafinal.model.Cliente;
import org.example.entregafinal.model.Cuenta;

import java.util.List;

public interface CuentaDAO {
    boolean guardarCuenta(Cuenta cuenta);
    Cuenta buscarCuentaPorNumeroCuenta(String numeroCuenta);
    boolean actualizarCuenta(Cuenta cuenta);
    boolean eliminarCuenta(String numeroCuenta);
}

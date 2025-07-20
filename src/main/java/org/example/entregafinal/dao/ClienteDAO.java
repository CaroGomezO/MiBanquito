package org.example.entregafinal.dao;

import org.example.entregafinal.model.Cliente;

import java.util.List;

public interface ClienteDAO {

    boolean guardarCliente(Cliente cliente);
    List<Cliente> obtenerTodosLosClientes();
    Cliente buscarPorCedula(String cedula);
    List<Cliente> buscarPorNombre(String nombre);
    Cliente buscarPorNumeroCuenta (String numeroCuenta);
    boolean actualizarCliente(Cliente cliente);
    boolean eliminarCliente(String cedula);
}

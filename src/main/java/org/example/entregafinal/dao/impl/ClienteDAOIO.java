package org.example.entregafinal.dao.impl;

import org.example.entregafinal.dao.ClienteDAO;
import org.example.entregafinal.model.Cliente;
import org.example.entregafinal.model.CuentaAhorro;
import org.example.entregafinal.model.CuentaCorriente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOIO implements ClienteDAO {
    private File clienteDB;
    private static final String SEPARADOR = ";";

    public ClienteDAOIO() {
        this.clienteDB = new File("clientes.txt");
        if (!clienteDB.exists()) {
            try {
                clienteDB.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de clientes: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean guardarCliente(Cliente cliente) {
        try (FileWriter fw = new FileWriter(clienteDB, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String linea = transformarClienteAString(cliente);
            bw.write(linea);
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar el cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> listaClientes = new ArrayList<>();
        try (FileReader fr = new FileReader(clienteDB);
             BufferedReader br = new BufferedReader(fr)){

            String linea;

            while ((linea = br.readLine()) != null) {
                Cliente cliente = transformarStringACliente(linea);
                if (cliente != null) {
                    listaClientes.add(cliente);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de clientes: " + e.getMessage());
        }
        return listaClientes;
    }

    @Override
    public Cliente buscarPorCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            System.err.println("La cédula no puede estar vacía.");
            return null;
        }

        cedula = cedula.trim();

        List<Cliente> clientes = obtenerTodosLosClientes();
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                return cliente;
            }
        }
        return null;
    }

    @Override
    public List<Cliente> buscarPorNombre(String nombre) {
        List<Cliente> encontrados = new ArrayList<>();
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("El nombre no puede estar vacío.");
            return encontrados;
        }

        nombre = nombre.trim();

        List<Cliente> clientes = obtenerTodosLosClientes();
        for (Cliente cliente : clientes) {
            if (cliente.getNombre().equalsIgnoreCase(nombre)) {
                encontrados.add(cliente);
            }
        }
        return encontrados;
    }

    @Override
    public Cliente buscarPorNumeroCuenta(String numeroCuenta) {
        List<Cliente> clientes = obtenerTodosLosClientes();

        for (Cliente cliente : clientes) {
            if (cliente.getCuenta() != null && cliente.getCuenta().getNumeroCuenta().equals(numeroCuenta)) {
                return cliente;
            }
        }
        return null;
    }

    @Override
    public boolean actualizarCliente(Cliente clienteActualizado) {
        List<Cliente> clientes = obtenerTodosLosClientes();
        boolean actualizado = false;

        try (FileWriter fw = new FileWriter(clienteDB);
             BufferedWriter bw = new BufferedWriter(fw)) {


            if (clienteActualizado == null || clienteActualizado.getCedula() == null) {
                System.err.println("Cliente o cédula no pueden ser nulos.");
                return false;
            }

            for (Cliente c : clientes) {
                if (c.getCedula().equals(clienteActualizado.getCedula())) {
                    bw.write(transformarClienteAString(clienteActualizado));
                    actualizado = true;
                } else {
                    bw.write(transformarClienteAString(c));
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar el cliente: " + e.getMessage());
        }
        return actualizado;
    }

    @Override
    public boolean eliminarCliente(String cedula) {
        List<Cliente> clientes = obtenerTodosLosClientes();
        boolean eliminado = false;

        try (FileWriter fw = new FileWriter(clienteDB);
             BufferedWriter bw = new BufferedWriter(fw)){

            if (cedula == null || cedula.trim().isEmpty()) {
                System.err.println("La cédula no puede estar vacía.");
                return false;
            }

            for (Cliente c : clientes) {
                if (!c.getCedula().equals(cedula)) {
                    bw.write(transformarClienteAString(c));
                    bw.newLine();
                } else {
                    eliminado = true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar el cliente: " + e.getMessage());
        }
        return eliminado;
    }

    private String transformarClienteAString(Cliente cliente) {
        String cuentaStr;
        if (cliente.getCuenta() != null) {
            cuentaStr = cliente.getCuenta().getNumeroCuenta() + SEPARADOR +
                    cliente.getCuenta().getTipoCuenta() + SEPARADOR +
                    cliente.getCuenta().getSaldo();
        } else {
            cuentaStr = "null" + SEPARADOR + "null" + SEPARADOR + "0.0";
        }
        return cliente.getCedula() + SEPARADOR +
                cliente.getNombre() + SEPARADOR +
                cliente.getApellido() + SEPARADOR +
                cliente.getEmail() + SEPARADOR +
                cliente.getTelefono() + SEPARADOR +
                cliente.getPassword() + SEPARADOR +
                cliente.getDireccion() + SEPARADOR +
                cuentaStr;
    }

    private Cliente transformarStringACliente(String linea) {
        String[] partes = linea.split(SEPARADOR);
        if (partes.length != 10) {
            System.err.println("Formato de cliente inválido: " + linea);
            return null;
        }

        Cliente cliente = new Cliente(
                partes[0],
                partes[1],
                partes[2],
                partes[3],
                partes[4],
                partes[5],
                partes[6]
        );

        String numeroCuenta = partes[7];
        String tipoCuenta = partes[8];
        double saldo;

        try {
            saldo = Double.parseDouble(partes[9]);
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir el saldo a número: " + e.getMessage());
            saldo = 0.0;
        }

        if (!numeroCuenta.equals("null")) {
            if (tipoCuenta.equals("Cuenta de Ahorro")) {
                cliente.setCuenta(new CuentaAhorro(numeroCuenta, saldo, cliente));
            } else if (tipoCuenta.equals("Cuenta Corriente")) {
                cliente.setCuenta(new CuentaCorriente(numeroCuenta, saldo, cliente));
            } else {
                System.err.println("Tipo de cuenta desconocido: " + tipoCuenta);
            }
        }
        return cliente;
    }
}

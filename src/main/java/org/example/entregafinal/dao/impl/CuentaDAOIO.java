package org.example.entregafinal.dao.impl;

import org.example.entregafinal.dao.CuentaDAO;
import org.example.entregafinal.model.Cliente;
import org.example.entregafinal.model.Cuenta;
import org.example.entregafinal.model.CuentaAhorro;
import org.example.entregafinal.model.CuentaCorriente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAOIO implements CuentaDAO {
    private File cuentasDB;
    private static final String SEPARADOR = ";";

    public CuentaDAOIO() {
        this.cuentasDB = new File("cuentas.txt");
        if (!cuentasDB.exists()) {
            try {
                cuentasDB.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de cuentas: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean guardarCuenta(Cuenta cuenta) {
        try (FileWriter fw = new FileWriter(cuentasDB, true);
             BufferedWriter bw = new BufferedWriter(fw)){

            String linea = transformarCuentaAString(cuenta);
            bw.write(linea);
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar la cuenta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Cuenta buscarCuentaPorNumeroCuenta(String numeroCuenta) {
        try (FileReader fr = new FileReader(cuentasDB);
             BufferedReader br = new BufferedReader(fr)){

            String linea;
            while ((linea = br.readLine()) != null) {
                Cuenta cuenta = transformarStringACuenta(linea);
                if (cuenta != null && cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                    return cuenta;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de cuentas: " + e.getMessage());
        }
        return null;
    }


    @Override
    public boolean actualizarCuenta(Cuenta cuentaActualizada) {
        List<Cuenta> cuentas = obtenerTodasLasCuentas();
        boolean actualizado = false;

        try (FileWriter fw = new FileWriter(cuentasDB);
             BufferedWriter bw = new BufferedWriter(fw)){

            if (cuentas.isEmpty()) {
                System.err.println("No hay cuentas para actualizar.");
                return false;
            }

            for (Cuenta cuenta : cuentas) {
                if (cuenta.getNumeroCuenta().equals(cuentaActualizada.getNumeroCuenta())) {
                    bw.write(transformarCuentaAString(cuentaActualizada));
                    actualizado = true;
                } else {
                    bw.write(transformarCuentaAString(cuenta));
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar la cuenta: " + e.getMessage());
        }
        return actualizado;
    }

    @Override
    public boolean eliminarCuenta(String numeroCuenta) {
        List<Cuenta> cuentas = obtenerTodasLasCuentas();
        boolean eliminado = false;

        try (FileWriter fw = new FileWriter(cuentasDB);
             BufferedWriter bw = new BufferedWriter(fw)){

            if (cuentas.isEmpty()) {
                System.err.println("No hay cuentas para eliminar.");
                return false;
            }

            for (Cuenta cuenta : cuentas) {
                if (!cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                    bw.write(transformarCuentaAString(cuenta));
                    bw.newLine();
                } else {
                    eliminado = true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar la cuenta: " + e.getMessage());
        }
        return eliminado;
    }

    private List<Cuenta> obtenerTodasLasCuentas() {
        List<Cuenta> cuentas = new ArrayList<>();
        try (FileReader fr = new FileReader(cuentasDB);
             BufferedReader br = new BufferedReader(fr)){

            String linea;
            while ((linea = br.readLine()) != null) {
                Cuenta cuenta = transformarStringACuenta(linea);
                if (cuenta != null) {
                    cuentas.add(cuenta);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de cuentas: " + e.getMessage());
        }
        return cuentas;
    }

    private String transformarCuentaAString(Cuenta cuenta) {
        return cuenta.getNumeroCuenta() + SEPARADOR +
                cuenta.getTipoCuenta() + SEPARADOR +
                cuenta.getSaldo() + SEPARADOR +
                cuenta.getCliente().getCedula();
    }

    private Cuenta transformarStringACuenta(String linea) {
        String[] partes = linea.split(SEPARADOR);
        if (partes.length != 4) {
            System.err.println("Formato de cuenta inválido: " + linea);
            return null;
        }

        String numeroCuenta = partes[0];
        String tipoCuenta = partes[1];
        double saldo;

        try {
            saldo = Double.parseDouble(partes[2]);
        } catch (NumberFormatException e) {
            System.err.println("Saldo inválido en la cuenta: " + numeroCuenta);
            saldo = 0.0;
        }

        String cedulaCliente = partes[3];
        Cliente clienteCuenta = new Cliente(cedulaCliente, "", "", "", "", "", "");

        if (tipoCuenta.equalsIgnoreCase("Cuenta de Ahorro")) {
            return new CuentaAhorro(numeroCuenta, saldo, clienteCuenta);
        } else if (tipoCuenta.equalsIgnoreCase("Cuenta Corriente")) {
            return new CuentaCorriente(numeroCuenta, saldo, clienteCuenta);
        } else {
            System.err.println("Tipo de cuenta desconocido: " + tipoCuenta);
            return null;
        }
    }
}

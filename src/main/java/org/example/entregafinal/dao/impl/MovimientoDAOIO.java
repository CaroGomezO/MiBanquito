package org.example.entregafinal.dao.impl;

import org.example.entregafinal.dao.MovimientoDAO;
import org.example.entregafinal.model.Movimiento;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAOIO implements MovimientoDAO {
    private final File movimientoDB;
    private static final String SEPARADOR = ";";

    public MovimientoDAOIO() {
        this.movimientoDB = new File("movimientos.txt");
        if (!movimientoDB.exists()) {
            try {
                movimientoDB.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de movimientos: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean guardarMovimiento(Movimiento movimiento) {
        if (movimiento == null) {
            return false;
        }

        try ( FileWriter fw = new FileWriter(movimientoDB, true);
              BufferedWriter bw = new BufferedWriter(fw)){

            String linea = transformarMovimientoAString(movimiento);
            bw.write(linea);
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar el movimiento: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Movimiento> obtenerMovimientosPorCuenta(String numeroCuenta) {
        List<Movimiento> resultado = new ArrayList<>();
        for (Movimiento movimiento : obtenerTodos()) {
            if (movimiento.getNumeroCuentaOrigen().equals(numeroCuenta) && movimiento.getTipoOperacion().equals("Transferencia Enviada")) {
                resultado.add(movimiento);
            } else if (movimiento.getNumeroCuentaDestino().equals(numeroCuenta) && movimiento.getTipoOperacion().equals("Transferencia Recibida")) {
                resultado.add(movimiento);
            } else if (movimiento.getNumeroCuentaOrigen().equals(numeroCuenta) && !movimiento.getTipoOperacion().startsWith("Transferencia")) {
                resultado.add(movimiento);
            }
        }
        return resultado;
    }

    private List<Movimiento> obtenerTodos() {
        List<Movimiento> movimientos = new ArrayList<>();
        try (FileReader fr = new FileReader(movimientoDB);
             BufferedReader br = new BufferedReader(fr)){

            String linea;
            while ((linea = br.readLine()) != null) {
                Movimiento movimiento = transformarStringAMovimiento(linea);
                if (movimiento != null) {
                    movimientos.add(movimiento);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer los movimientos: " + e.getMessage());
        }
        return movimientos;
    }

    private String transformarMovimientoAString(Movimiento movimiento) {
        return movimiento.getFecha() + SEPARADOR +
               movimiento.getTipoOperacion() + SEPARADOR +
                movimiento.getMonto() + SEPARADOR +
                movimiento.getNumeroCuentaOrigen() + SEPARADOR +
                movimiento.getNumeroCuentaDestino() + SEPARADOR +
                movimiento.getSaldoAnterior() + SEPARADOR +
                movimiento.getSaldoNuevo() + SEPARADOR +
                movimiento.getEstado();
    }

    private Movimiento transformarStringAMovimiento(String linea) {
        try {
            String[] partes = linea.split(SEPARADOR);
            if (partes.length != 8) {
                System.err.println("Formato de línea incorrecto: " + linea);
                return null;
            }

            LocalDate fecha = LocalDate.parse(partes[0]);
            String tipoOperacion = partes[1];
            double monto = Double.parseDouble(partes[2]);
            String numeroCuentaOrigen = partes[3];
            String numeroCuentaDestino = partes[4];
            double saldoAnterior = Double.parseDouble(partes[5]);
            double saldoNuevo = Double.parseDouble(partes[6]);
            String estado = partes[7];

            return new Movimiento(fecha, tipoOperacion, monto, numeroCuentaOrigen, numeroCuentaDestino, saldoAnterior, saldoNuevo, estado);

        } catch (NumberFormatException e) {
            System.err.println("Error al parsear saldo o monto: " + e.getMessage());
            return null;
        } catch (DateTimeException e) {
            System.err.println("Error al parsear la fecha: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error inesperado al transformar la línea a Movimiento: " + e.getMessage());
        }
        return null;
    }
}

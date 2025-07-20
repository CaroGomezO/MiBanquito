package org.example.entregafinal.dao;

import org.example.entregafinal.model.Movimiento;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoDAO {
    boolean guardarMovimiento(Movimiento movimiento);
    List<Movimiento> obtenerMovimientosPorCuenta(String numeroCuenta);

}

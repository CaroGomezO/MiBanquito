package org.example.entregafinal.service;

import org.example.entregafinal.dao.MovimientoDAO;
import org.example.entregafinal.model.Movimiento;
import java.util.List;

public class MovimientoService {
    private final MovimientoDAO movimientoDAO;

    public MovimientoService(MovimientoDAO movimientoDAO) {
        this.movimientoDAO = movimientoDAO;
    }

    public List<Movimiento> obtenerMovimientosPorCuenta(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) {
            return List.of();
        }
        return movimientoDAO.obtenerMovimientosPorCuenta(numeroCuenta.trim());
    }
}

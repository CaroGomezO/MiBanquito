package org.example.entregafinal.service;

import org.example.entregafinal.dao.ClienteDAO;
import org.example.entregafinal.dao.CuentaDAO;
import org.example.entregafinal.dao.MovimientoDAO;
import org.example.entregafinal.model.*;

public class CuentaService {
    private final CuentaDAO cuentaDAO;
    private final MovimientoDAO movimientoDAO;
    private final ClienteDAO clienteDAO;

    public CuentaService(CuentaDAO cuentaDAO, MovimientoDAO movimientoDAO, ClienteDAO clienteDAO) {
        this.cuentaDAO = cuentaDAO;
        this.movimientoDAO = movimientoDAO;
        this.clienteDAO = clienteDAO;
    }

    public Cuenta buscarCuentaPorNumero(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) {
            return null;
        }
        return cuentaDAO.buscarCuentaPorNumeroCuenta(numeroCuenta.trim());
    }

    public String realizarDeposito(Cliente cliente, double monto) {
        if (cliente == null || cliente.getCuenta() == null ) {
            return "Cliente o cuenta no válidos.";
        }

        if (monto <= 0) {
            return "El monto del depósito debe ser mayor a cero.";
        }

        Cuenta cuenta = cuentaDAO.buscarCuentaPorNumeroCuenta(cliente.getCuenta().getNumeroCuenta());
        double saldoAnterior = cuenta.getSaldo();
        double nuevoSaldo = saldoAnterior;

        if (cuenta instanceof CuentaAhorro) {
            double interes = monto * 0.015;
            nuevoSaldo += monto + interes;

        } else if (cuenta instanceof CuentaCorriente) {
            if (saldoAnterior < 0) {
                double faltante = -saldoAnterior;

                if (monto >= faltante) {
                    nuevoSaldo = 0 + (monto - faltante);
                } else {
                    nuevoSaldo = saldoAnterior + monto;
                }
            } else {
                nuevoSaldo += monto;
            }
        }

        cuenta.setSaldo(nuevoSaldo);
        cliente.setCuenta(cuenta);
        cuentaDAO.actualizarCuenta(cuenta);
        clienteDAO.actualizarCliente(cliente);

        Movimiento movimiento = new Movimiento(
                java.time.LocalDate.now(),
                "Depósito",
                monto,
                cuenta.getNumeroCuenta(),
                cuenta.getNumeroCuenta(),
                saldoAnterior,
                nuevoSaldo,
                "Exitosa"
        );

        movimientoDAO.guardarMovimiento(movimiento);
        return "Depósito realizado exitosamente.";

    }

    public String realizarRetiro(Cliente cliente, double monto) {
        if (cliente == null || cliente.getCuenta() == null) {
            return "Cliente o cuenta no válidos.";
        }

        if (monto <= 0) {
            return "El monto del retiro debe ser mayor a cero.";
        }

        Cuenta cuenta = cuentaDAO.buscarCuentaPorNumeroCuenta(cliente.getCuenta().getNumeroCuenta());
        double saldoAnterior = cuenta.getSaldo();
        double nuevoSaldo;

        if (cuenta instanceof CuentaAhorro) {
            if (monto > saldoAnterior) {
                return "Fondos insuficientes para el retiro.";
            }
            nuevoSaldo = saldoAnterior - monto;

        } else if (cuenta instanceof CuentaCorriente) {
            double limiteSobregiro = -200000;
            if ((saldoAnterior - monto) < limiteSobregiro) {
                return "El retiro excede el límite de sobregiro permitido.";
            }
            nuevoSaldo = saldoAnterior - monto;

        } else {
            return "Tipo de cuenta desconocido.";
        }

        cuenta.setSaldo(nuevoSaldo);
        cliente.setCuenta(cuenta);
        cuentaDAO.actualizarCuenta(cuenta);
        clienteDAO.actualizarCliente(cliente);


        Movimiento movimiento = new Movimiento(
                java.time.LocalDate.now(),
                "Retiro",
                monto,
                cuenta.getNumeroCuenta(),
                cuenta.getNumeroCuenta(),
                saldoAnterior,
                nuevoSaldo,
                "Exitosa"
        );

        movimientoDAO.guardarMovimiento(movimiento);
        return "Retiro realizado exitosamente.";
    }

    public String realizarTransferencia(Cliente clienteOrigen, String numeroCuentaDestino, double monto) {
        if (clienteOrigen == null || clienteOrigen.getCuenta() == null) {
            return "Cliente o cuenta de origen no válidos.";
        }

        Cliente clienteDestino = clienteDAO.buscarPorNumeroCuenta(numeroCuentaDestino);

        if (clienteDestino == null || clienteDestino.getCuenta() == null) {
            return "Cliente o cuenta de destino no válidos.";
        }

        if (monto <= 0) {
            return "El monto de la transferencia debe ser mayor a cero.";
        }

        Cuenta cuentaOrigen = cuentaDAO.buscarCuentaPorNumeroCuenta(clienteOrigen.getCuenta().getNumeroCuenta());
        Cuenta cuentaDestino = cuentaDAO.buscarCuentaPorNumeroCuenta(clienteDestino.getCuenta().getNumeroCuenta());

        if (cuentaOrigen.getNumeroCuenta().equals(cuentaDestino.getNumeroCuenta())) {
            return "No se puede transferir a la misma cuenta.";
        }

        double saldoOrigen = cuentaOrigen.getSaldo();
        double saldoDestino = cuentaDestino.getSaldo();
        double nuevoSaldoOrigen;
        double nuevoSaldoDestino;

        if (cuentaOrigen instanceof CuentaAhorro) {
            if (monto > saldoOrigen) {
                return "Fondos insuficientes para la transferencia.";
            }
            nuevoSaldoOrigen = saldoOrigen - monto;

        } else if (cuentaOrigen instanceof CuentaCorriente) {
            double limiteSobregiro = -200000;
            if ((saldoOrigen - monto) < limiteSobregiro) {
                return "La transferencia excede el límite de sobregiro permitido.";
            }
            nuevoSaldoOrigen = saldoOrigen - monto;

        } else {
            return "Tipo de cuenta de origen desconocido.";
        }

        if (cuentaDestino instanceof CuentaCorriente && saldoDestino < 0) {
            double faltante = -saldoDestino;
            if (monto >= faltante) {
                nuevoSaldoDestino = 0 + (monto - faltante);
            } else {
                nuevoSaldoDestino = saldoDestino + monto;
            }
        } else {
            nuevoSaldoDestino = saldoDestino + monto;
        }

        cuentaOrigen.setSaldo(nuevoSaldoOrigen);
        cuentaDestino.setSaldo(nuevoSaldoDestino);

        cuentaDAO.actualizarCuenta(cuentaOrigen);
        cuentaDAO.actualizarCuenta(cuentaDestino);

        clienteOrigen.setCuenta(cuentaOrigen);
        clienteDestino.setCuenta(cuentaDestino);

        clienteDAO.actualizarCliente(clienteOrigen);
        clienteDAO.actualizarCliente(clienteDestino);

        Movimiento movimientoOrigen = new Movimiento(
                java.time.LocalDate.now(),
                "Transferencia Enviada",
                monto,
                cuentaOrigen.getNumeroCuenta(),
                cuentaDestino.getNumeroCuenta(),
                saldoOrigen,
                nuevoSaldoOrigen,
                "Exitosa"
        );

        Movimiento movimientoDestino = new Movimiento(
                java.time.LocalDate.now(),
                "Transferencia Recibida",
                monto,
                cuentaOrigen.getNumeroCuenta(),
                cuentaDestino.getNumeroCuenta(),
                saldoDestino,
                nuevoSaldoDestino,
                "Exitosa"
        );

        movimientoDAO.guardarMovimiento(movimientoOrigen);
        movimientoDAO.guardarMovimiento(movimientoDestino);

        return "Transferencia realizada exitosamente.";
    }
}

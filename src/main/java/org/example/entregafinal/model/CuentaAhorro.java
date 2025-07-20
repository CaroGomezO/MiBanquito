package org.example.entregafinal.model;

public class CuentaAhorro extends Cuenta {
    private final double tasaInteres = 0.015;

    public CuentaAhorro(String numeroCuenta, Cliente cliente) {
        super(numeroCuenta, cliente);
        setTipoCuenta("Cuenta de Ahorro");
    }

    public CuentaAhorro(String numeroCuenta, double saldo, Cliente cliente) {
        super(numeroCuenta, saldo, cliente);
        setTipoCuenta("Cuenta de Ahorro");
    }

    public double getTasaInteres() {
        return tasaInteres;
    }
}

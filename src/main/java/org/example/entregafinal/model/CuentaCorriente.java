package org.example.entregafinal.model;

public class CuentaCorriente extends Cuenta {
    private final double limiteSobregiro = 200000;

    public CuentaCorriente(String numeroCuenta, Cliente cliente) {
        super(numeroCuenta, cliente);
        setTipoCuenta("Cuenta Corriente");
    }

    public CuentaCorriente(String numeroCuenta, double saldo, Cliente cliente) {
        super(numeroCuenta, saldo, cliente);
        setTipoCuenta("Cuenta Corriente");
    }

    public double getLimiteSobregiro() {
        return limiteSobregiro;
    }
}
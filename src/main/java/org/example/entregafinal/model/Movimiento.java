package org.example.entregafinal.model;

import java.time.LocalDate;

public class Movimiento {
    private LocalDate fecha;
    private String tipoOperacion;
    private double monto;
    private String numeroCuentaOrigen;
    private String numeroCuentaDestino;
    private double saldoAnterior;
    private double saldoNuevo;
    private String estado;

    public Movimiento(LocalDate fecha, String tipoOperacion, double monto, String numeroCuentaOrigen, String numeroCuentaDestino, double saldoAnterior, double saldoNuevo, String estado) {
        this.fecha = fecha;
        this.tipoOperacion = tipoOperacion;
        this.monto = monto;
        this.numeroCuentaOrigen = numeroCuentaOrigen;
        this.numeroCuentaDestino = numeroCuentaDestino;
        this.saldoAnterior = saldoAnterior;
        this.saldoNuevo = saldoNuevo;
        this.estado = estado;
    }

    public Movimiento() {

    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getNumeroCuentaOrigen() {
        return numeroCuentaOrigen;
    }

    public void setNumeroCuentaOrigen(String numeroCuentaOrigen) {
        this.numeroCuentaOrigen = numeroCuentaOrigen;
    }

    public String getNumeroCuentaDestino() {
        return numeroCuentaDestino;
    }

    public void setNumeroCuentaDestino(String numeroCuentaDestino) {
        this.numeroCuentaDestino = numeroCuentaDestino;
    }

    public double getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(double saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public double getSaldoNuevo() {
        return saldoNuevo;
    }

    public void setSaldoNuevo(double saldoNuevo) {
        this.saldoNuevo = saldoNuevo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}

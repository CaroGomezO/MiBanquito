package org.example.entregafinal.model;

public class Cliente extends Usuario{

    private Cuenta cuenta;

    public Cliente(String cedula, String nombre, String apellido, String email, String telefono, String password, String direccion) {
        super(cedula, nombre, apellido, email, telefono, password, direccion);
        this.cuenta = null;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
}
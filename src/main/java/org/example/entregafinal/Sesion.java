package org.example.entregafinal;

import org.example.entregafinal.model.Usuario;

public class Sesion {
    private static Usuario usuarioActual;

    public static void establecerUsuario(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static void guardarUsuario(Usuario usuario) {
        Sesion.usuarioActual = usuario;
    }

    public static Usuario obtenerUsuario() {
        return usuarioActual;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }

    public static boolean haySesionActiva () {
        return usuarioActual != null;
    }
}

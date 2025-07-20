package org.example.entregafinal.dao;

import org.example.entregafinal.model.Administrador;

public interface AdministradorDAO {

    Administrador obtenerAdministrador();
    boolean guardarAdministrador(Administrador admin);
    boolean actualizarAdministrador(Administrador admin);
}

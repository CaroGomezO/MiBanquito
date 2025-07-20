package org.example.entregafinal.dao.impl;

import org.example.entregafinal.dao.AdministradorDAO;
import org.example.entregafinal.model.Administrador;

import java.io.*;

public class AdministradorDAOIO implements AdministradorDAO {
    private File adminDB;
    private static final String SEPARADOR = ";";

    public AdministradorDAOIO() {
        this.adminDB = new File("admin.txt");
        if (!adminDB.exists()) {
            try {
                adminDB.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de administrador: " + e.getMessage());
            }
        }
    }

    @Override
    public Administrador obtenerAdministrador() {
        try (FileReader fr = new FileReader(adminDB);
             BufferedReader br = new BufferedReader(fr)) {

            String linea = br.readLine();

            if (linea != null && !linea.isEmpty()) {
                return transformarAdministradorString(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el administrador: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean guardarAdministrador(Administrador admin) {
        try (FileWriter fw = new FileWriter(adminDB);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String linea = admin.getCedula() + SEPARADOR +
                    admin.getNombre() + SEPARADOR +
                    admin.getApellido() + SEPARADOR +
                    admin.getEmail() + SEPARADOR +
                    admin.getTelefono() + SEPARADOR +
                    admin.getPassword() + SEPARADOR +
                    admin.getDireccion() + "\n";

            bw.write(linea);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar el administrador: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizarAdministrador(Administrador admin) {
        return guardarAdministrador(admin);
    }

    private Administrador transformarAdministradorString(String linea) {
        String[] partes = linea.split(SEPARADOR);
        if (partes.length != 7) {
            return null;
        }
        return new Administrador(
                partes[0], partes[1], partes[2],
                partes[3], partes[4], partes[5], partes[6]
        );
    }
}


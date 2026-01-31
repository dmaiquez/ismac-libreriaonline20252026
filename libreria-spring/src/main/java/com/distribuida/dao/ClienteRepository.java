package com.distribuida.dao;

import com.distribuida.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// @Repository // Este es un bean
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // Metodos del CRUD findAll(); findOne(); save(); update(); delete();

    Cliente findByCedula(String cedula);
    Cliente findByNombreAndApellido(String nombre, String apellido);

}

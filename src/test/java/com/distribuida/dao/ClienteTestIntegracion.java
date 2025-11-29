package com.distribuida.dao;

import com.distribuida.model.Cliente;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
public class ClienteTestIntegracion {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void findAll(){
        List<Cliente> clientes = clienteRepository.findAll();

        assertNotNull(clientes);
        assertTrue(clientes.size() > 0);

        for(Cliente item: clientes){
            System.out.println(item.toString());
        }
    }

    @Test
    public void findOne(){
        Optional<Cliente> cliente = clienteRepository.findById(1);
        assertTrue(cliente.isPresent());
        assertEquals("Puro", cliente.orElse(null).getNombre());
        assertEquals("Hueso", cliente.orElse(null).getApellido());
        System.out.println(cliente.toString());
    }

    @Test
    public void save(){
        Cliente cliente = new Cliente(0, "1701234567","Juan66","Taipe66","Direccion66","0987652666","jtaipe66@correo.com");
        Cliente clienteGuardado = clienteRepository.save(cliente);
        assertEquals("1701234567", clienteGuardado.getCedula());
        assertEquals("Juan66", clienteGuardado.getNombre());
    }

    @Test
    public void update(){
        Optional<Cliente> cliente = clienteRepository.findById(40);

        assertNotNull(cliente.isPresent());

        cliente.orElse(null).setCedula("1701234777");
        cliente.orElse(null).setNombre("Juan777");
        cliente.orElse(null).setApellido("Taipe777");
        cliente.orElse(null).setDireccion("Direccion777");
        cliente.orElse(null).setTelefono("0987653777");
        cliente.orElse(null).setCorreo("jtaipe777@correo.com");

        Cliente clienteActualizado = clienteRepository.save(cliente.orElse(null));
        assertEquals("Juan777", clienteActualizado.getNombre());
        assertEquals("Direccion777", clienteActualizado.getDireccion());

    }

    @Test
    public void delete(){
        if(clienteRepository.existsById(40)){
            clienteRepository.deleteById(40);
        }
        assertFalse(clienteRepository.existsById(40),"EL DATO FUE BORRADO***");

    }

}

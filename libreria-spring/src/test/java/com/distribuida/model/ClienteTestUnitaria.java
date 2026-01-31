package com.distribuida.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTestUnitaria {

    // comentarios en clase test

    private Cliente cliente;

    @BeforeEach
    public void setup(){
        cliente = new Cliente(1,"1701234567","Juan","Taipe","Av. por ahi y mas alla","0987654321","jtaipe@correo.com");
    }

    @Test
    public void testClienteConstructor(){
        assertAll("Validar datos del cliente - Constructor",
                () -> assertEquals(1, cliente.getIdCliente() ),
                () -> assertEquals("1701234567", cliente.getCedula()),
                () -> assertEquals("Juan", cliente.getNombre()),
                () -> assertEquals("Taipe", cliente.getApellido()),
                () -> assertEquals("Av. por ahi y mas alla", cliente.getDireccion()),
                () -> assertEquals("0987654321", cliente.getTelefono()),
                () -> assertEquals("jtaipe@correo.com", cliente.getCorreo())
        );
    }

    @Test
    public void testClienteSetters(){
        cliente.setIdCliente(2);
        cliente.setCedula("17012345622");
        cliente.setNombre("Juan2");
        cliente.setApellido("Taipe2");
        cliente.setDireccion("Direccion2");
        cliente.setTelefono("0987654322");
        cliente.setCorreo("jtaipe2@correo.com");

        assertAll("Validar datos del cliente - Setters" ,
                () -> assertEquals(2,cliente.getIdCliente()),
                () -> assertEquals( "17012345622",cliente.getCedula()),
                () -> assertEquals( "Juan2",cliente.getNombre()),
                () -> assertEquals( "Taipe2",cliente.getApellido()),
                () -> assertEquals( "Direccion2",cliente.getDireccion()),
                () -> assertEquals( "0987654322",cliente.getTelefono()),
                () -> assertEquals( "jtaipe2@correo.com",cliente.getCorreo())
        );


    }


    @Test
    public void testClienteToString(){
        String str = cliente.toString();
        assertAll("Validar datos del cliente - toString",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("1701234567")),
                () -> assertTrue(str.contains("Juan")),
                () -> assertTrue(str.contains("Taipe")),
                () -> assertTrue(str.contains("Av. por ahi y mas alla")),
                () -> assertTrue(str.contains("0987654321")),
                () -> assertTrue(str.contains("jtaipe@correo.com"))
                );
    }

}

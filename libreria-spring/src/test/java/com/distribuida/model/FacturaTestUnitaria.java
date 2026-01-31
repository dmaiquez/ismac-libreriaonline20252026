package com.distribuida.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class FacturaTestUnitaria {

    private Cliente cliente;
    private Factura factura;

    @BeforeEach
    public void setUp(){
        cliente = new Cliente(1,"1701234567","Juan","Taipe","Direccion1","0987654321","jtaipe@correo.com");

        factura = new Factura();
        factura.setIdFactura(1);
        factura.setNumFactura("FAC-0001");
        factura.setFecha(new Date());
        factura.setTotalNeto(100.00);
        factura.setIva(15.00);
        factura.setTotal(115.00);
        // inyeccion de dependencias
        factura.setCliente(cliente);

    }

    @Test
    public void testFacturaConstructor(){
        assertAll("Validar constructor - Factura",
                () -> assertEquals(1,factura.getIdFactura()),
                () -> assertEquals("FAC-0001",factura.getNumFactura()),
               // () -> assertEquals(new Date(),factura.getFecha()),
                () -> assertEquals(100.00,factura.getTotalNeto()),
                () -> assertEquals(15.00,factura.getIva()),
                () -> assertEquals(115.00,factura.getTotal()),
                () -> assertEquals("Juan",factura.getCliente().getNombre())
                );
    }

    @Test
    public void testFacturaSetters(){
        cliente = new Cliente(2,"1701234522","Juan22","Taipe22","Direccion22","0987654322","jtaipe22@correo.com");


        factura.setIdFactura(2);
        factura.setNumFactura("FAC-0002");
        factura.setFecha(new Date());
        factura.setTotalNeto(200.00);
        factura.setIva(30.00);
        factura.setTotal(230.00);
        // inyeccion de dependencias
        factura.setCliente(cliente);

        assertAll("Validar setters - Factura",
                () -> assertEquals(2,factura.getIdFactura()),
                () -> assertEquals("FAC-0002",factura.getNumFactura()),
                // () -> assertEquals(new Date(),factura.getFecha()),
                () -> assertEquals(200.00,factura.getTotalNeto()),
                () -> assertEquals(30.00,factura.getIva()),
                () -> assertEquals(230.00,factura.getTotal()),
                () -> assertEquals("Juan22",factura.getCliente().getNombre())
        );
    }

    @Test
    public void testFacturaToString(){
        String str = factura.toString();
        assertAll("Validar toString - Factura",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("FAC-0001")),
                () -> assertTrue(str.contains("100.0")),
                () -> assertTrue(str.contains("15.0")),
                () -> assertTrue(str.contains("115.0")),
                () -> assertTrue(str.contains("Juan"))
                );

    }

    @Test
    public void testFacturaInyeccion(){
        assertAll("Validar inyector - Factura",
                () -> assertNotNull(factura.getCliente()),
                () -> assertEquals("Taipe", factura.getCliente().getApellido())
                );
    }

    @Test
    public void testFacturaValoresNegativos(){
        factura.setTotal(-100.00);
        assertAll("Validar valores negativos - Factura",
                () -> assertEquals(-100.00, factura.getTotal())
                );

        //  validar los valores numericos en la factura para que no sean negativos.
    }

}

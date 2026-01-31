package com.distribuida.dao;


import com.distribuida.model.Cliente;
import com.distribuida.model.Factura;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
public class FacturaTestIntegracion {


    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void findAll(){
        List<Factura> facturas  = facturaRepository.findAll();
        assertNotNull(facturas);
        assertTrue(facturas.size() > 0);
        facturas.forEach(System.out::println);
    }

    @Test
    public void findOne(){
        Optional<Factura> factura = facturaRepository.findById(1);
        assertTrue(factura.isPresent());
        assertEquals("FAC-0001", factura.orElse(null).getNumFactura());
        assertEquals(150.96, factura.orElse(null).getTotal());
        System.out.println(factura);

    }

    @Test
    public void save(){
        Optional<Cliente> cliente = clienteRepository.findById(1);

        assertNotNull(cliente.isPresent());

        Factura factura = new Factura();
        factura.setIdFactura(0);
        factura.setNumFactura("FAC-0002");
        factura.setFecha(new Date());
        factura.setTotalNeto(100.00);
        factura.setIva(15.00);
        factura.setTotal(115.00);
        factura.setCliente(cliente.orElse(null));

        Factura facturaGuardada = facturaRepository.save(factura);
        assertEquals("FAC-0002", facturaGuardada.getNumFactura());

    }

    @Test
    public void update(){
        Optional<Factura> factura = facturaRepository.findById(86);

        assertNotNull(factura.isPresent());

        Optional<Cliente> cliente2 = clienteRepository.findById(2);

        assertNotNull(cliente2.isPresent());

        factura.orElse(null).setNumFactura("FAC-0066");
        factura.orElse(null).setFecha(new Date());
        factura.orElse(null).setTotalNeto(200.00);
        factura.orElse(null).setIva(60.00);
        factura.orElse(null).setTotal(260.00);
        factura.orElse(null).setCliente(cliente2.orElse(null));

        Factura facturaActualizada = facturaRepository.save(factura.orElse(null));
        assertEquals("FAC-0066", facturaActualizada.getNumFactura());

    }

    @Test
    public void delete(){
        if(facturaRepository.existsById(86)){
            facturaRepository.deleteById(86);
        }
        assertFalse(facturaRepository.existsById(86),"EL DATO NO EXISTE");

    }

}

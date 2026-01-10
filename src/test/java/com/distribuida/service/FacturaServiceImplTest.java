package com.distribuida.service;

import com.distribuida.dao.FacturaRepository;
import com.distribuida.model.Cliente;
import com.distribuida.model.Factura;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacturaServiceImplTest {

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private FacturaServiceImpl facturaService;

    private Factura factura;

    private Cliente cliente;

    @BeforeEach
    public void setUp(){
        cliente = new Cliente(1, "1701234567", "Juan","Taipe","direccion11","0987654321","jtaipe@correo.com");

        factura = new Factura();
        factura.setIdFactura(1);
        factura.setNumFactura("FAC-0001");
        factura.setFecha(new Date());
        factura.setTotalNeto(100.00);
        factura.setIva(15.00);
        factura.setTotal(115.00);
        factura.setCliente(cliente);

    }

    @Test
    public void testFindAll(){
        when(facturaRepository.findAll()).thenReturn( List.of(factura));
        List<Factura> facturas = facturaService.findAll();

        assertNotNull(facturas);
        assertEquals(1, facturas.size());
        verify(facturaRepository,times(1)).findAll();
    }

    @Test
    public void findOneExistente(){
        when(facturaRepository.findById(1)).thenReturn(Optional.ofNullable(factura));
        Optional<Factura> factura1 = facturaService.findOne(1);
        assertNotNull(factura1);
        assertEquals("FAC-0001", factura1.orElse(null).getNumFactura());

    }

    @Test
    public void findOneNoExistente(){
        when(facturaRepository.findById(999)).thenReturn(null);
        Optional<Factura> resultado = facturaService.findOne(999);
        assertNull(resultado);
    }

    @Test
    public void save(){
        when(facturaRepository.save(factura)).thenReturn(factura);
        Factura resultado = facturaService.save(factura);
        assertNotNull(resultado);
        assertEquals("FAC-0001", resultado.getNumFactura());
    }

    @Test
    public void updateExistente(){
        Factura facturaActualizada = new Factura();

        facturaActualizada.setNumFactura("FAC-00099");
        facturaActualizada.setFecha(new Date());
        facturaActualizada.setTotalNeto(100.99);
        facturaActualizada.setIva(15.99);
        facturaActualizada.setTotal(115.99);
        facturaActualizada.setCliente(cliente);

        when(facturaRepository.findById(1)).thenReturn(Optional.ofNullable(factura));
        when(facturaRepository.save(any())).thenReturn(facturaActualizada);

        Factura resultado = facturaService.update(1, facturaActualizada);

        assertNotNull(resultado);
        assertEquals("FAC-00099", resultado.getNumFactura());
        verify(facturaRepository, times(1)).save(factura);
    }

    @Test
    public void updateNoExistente(){
        Factura facturaNuevo = new Factura();
        when(facturaRepository.findById(999)).thenReturn(Optional.empty());
        Factura resultado = facturaService.update(999, facturaNuevo);
        assertNull(resultado);
        verify(facturaRepository, never()).save(any());
    }

    @Test
    public void deleteExistente(){
        when(facturaRepository.existsById(1)).thenReturn(true);
        facturaService.delete(1);
        verify(facturaRepository).deleteById(1);
    }

    @Test
    public void deleteNoExistente(){

        when(facturaRepository.existsById(999)).thenReturn(false);
        facturaService.delete(999);
        verify(facturaRepository,never()).deleteById(999);

    }


}

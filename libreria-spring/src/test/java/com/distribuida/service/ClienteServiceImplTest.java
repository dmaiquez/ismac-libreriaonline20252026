package com.distribuida.service;

import com.distribuida.dao.ClienteRepository;
import com.distribuida.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp(){
        cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setCedula("1701234567");
        cliente.setNombre("Juan");
        cliente.setApellido("Taipe");
        cliente.setDireccion("Av. por ahi y mas alla");
        cliente.setTelefono("0987654321");
        cliente.setCorreo("jtaipe@correo.com");
    }

    @Test
    public void testfindAll(){
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        List<Cliente> clientes = clienteService.findAll();

        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        verify(clienteRepository, times(1)).findAll();
    }


    @Test
    public void testFindOneExistente(){
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.findOne(1);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.orElse(null).getNombre());
    }


    @Test
    public void testFindOneNoexistente(){
        when(clienteRepository.findById(2)).thenReturn(null);

        Optional<Cliente> resultado = clienteService.findOne(2);

        assertNull(resultado);
    }

    @Test
    public void testSave(){
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente cliente1 = clienteService.save(cliente);

        assertNotNull(cliente1);
        assertEquals("Juan", cliente1.getNombre());

    }

    @Test
    public void testUpdateExistente(){
        Cliente clienteActualizado = new Cliente();

        clienteActualizado.setCedula("170123456222");
        clienteActualizado.setNombre("Juan22");
        clienteActualizado.setApellido("Taipe22");
        clienteActualizado.setDireccion("Direccion22");
        clienteActualizado.setTelefono("09876543222");
        clienteActualizado.setCorreo("jtaipe22@correo.com");

        when(clienteRepository.findById(1)).thenReturn(Optional.ofNullable(cliente));
        when(clienteRepository.save(any())).thenReturn(clienteActualizado);

        Cliente resultado = clienteService.update(1, clienteActualizado);

        assertNotNull(resultado);
        assertEquals("Juan22", resultado.getNombre());
        verify(clienteRepository, times(1)).save(cliente);


    }

    @Test
    public void testUpdateNoExistente(){
        Cliente clienteNuevo = new Cliente();
       when(clienteRepository.findById(999)).thenReturn(Optional.empty());
       Cliente resultado = clienteService.update(999, clienteNuevo);
       assertNull(resultado);
       verify(clienteRepository, never()).save(any());
    }

    @Test
    public void testDeleteExistente(){
        when(clienteRepository.existsById(1)).thenReturn(true);
        clienteService.delete(1);
        verify(clienteRepository).deleteById(1);
    }

    @Test
    public void testDeleteNoExistente(){
        when(clienteRepository.existsById(999)).thenReturn(false);
        clienteService.delete(999);
        verify(clienteRepository, never()).deleteById(anyInt());
    }
}

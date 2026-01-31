package com.distribuida.service;

import com.distribuida.dao.ClienteRepository;
import com.distribuida.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository clienteRepository;


    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> findOne(int id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente update(int id, Cliente cliente) {
        Optional<Cliente> cliente2 = clienteRepository.findById(id);

        if(cliente2.isPresent()){

            Cliente clienteExistente = cliente2.get();
            clienteExistente.setCedula(cliente.getCedula());
            clienteExistente.setNombre(cliente.getNombre());
            clienteExistente.setApellido(cliente.getApellido());
            clienteExistente.setDireccion(cliente.getDireccion());
            clienteExistente.setTelefono(cliente.getTelefono());
            clienteExistente.setCorreo(cliente.getCorreo());


            return clienteRepository.save(clienteExistente);
        }else{
            return null;
        }


    }

    @Override
    public void delete(int id) {
        if(clienteRepository.existsById(id)){
            clienteRepository.deleteById(id);
        }
    }
}


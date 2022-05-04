package com.pruebatecnica.pruebatecnica.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.pruebatecnica.pruebatecnica.dto.ConnectionDTO;
import com.pruebatecnica.pruebatecnica.models.Connection;
import com.pruebatecnica.pruebatecnica.services.IConnectionService;
import com.pruebatecnica.pruebatecnica.utils.Mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/connections")
@RequiredArgsConstructor
public class ConnectionController {
    
    private final IConnectionService connectionService;
    private final Mapper mapper;

    @GetMapping()
    public List<ConnectionDTO> getConnections() {
        return connectionService.findAll()
                .stream()
                .map(connection -> mapper.modelMapper().map(connection, ConnectionDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping()
    public ResponseEntity<ConnectionDTO> saveConnection(@RequestBody ConnectionDTO connectionDTO) {
        //Debería hacer el mapaeo de DTO a Entity
        //Luego de guardar entonces tomo el entity que retorna el service y lo convierto a DTO
        Connection connectionRequest = mapper.modelMapper().map(connectionDTO, Connection.class);
        Connection connection = connectionService.save(connectionRequest);

        ConnectionDTO connectionResponse = mapper.modelMapper().map(connection, ConnectionDTO.class);
        return new ResponseEntity<>(connectionResponse, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ConnectionDTO> getConnectionById(@PathVariable("id") Long id) {

        Connection connection = connectionService.findById(id);

        ConnectionDTO connectionResponse = mapper.modelMapper().map(connection, ConnectionDTO.class);

        return ResponseEntity.ok().body(connectionResponse);
    }

    @DeleteMapping(path = "/{id}") 
    public void deleteById(@PathVariable("id") Long id) {
        connectionService.delete(id);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ConnectionDTO> updateConnection(@PathVariable("id") Long id, @RequestBody ConnectionDTO connectionDTO) {
        Connection connectionRequest = mapper.modelMapper().map(connectionDTO, Connection.class);
        Connection connection = connectionService.save(connectionRequest);
        connection.setId(id);

        ConnectionDTO connectionResponse = mapper.modelMapper().map(connection, ConnectionDTO.class);

        return ResponseEntity.ok().body(connectionResponse);
    }
    

}

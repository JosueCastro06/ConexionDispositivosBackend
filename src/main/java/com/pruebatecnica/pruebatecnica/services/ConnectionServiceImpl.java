package com.pruebatecnica.pruebatecnica.services;

import java.util.List;

import com.pruebatecnica.pruebatecnica.models.Connection;
import com.pruebatecnica.pruebatecnica.repository.IConnectionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements IConnectionService {

    private final IConnectionRepository connectionRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Connection> findAll() {
        return connectionRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Connection findById(long id) {
        return connectionRepo.findById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        connectionRepo.deleteById(id);
    }

    @Override
    @Transactional
    public Connection save(Connection connection) {

        return connectionRepo.save(connection);

    }

    @Override
    @Transactional
    public Connection update(Connection connection) {
        return connectionRepo.save(connection);
    }

}

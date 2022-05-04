package com.pruebatecnica.pruebatecnica.services;

import java.util.List;

import com.pruebatecnica.pruebatecnica.models.Connection;


public interface IConnectionService {

    public Connection save(Connection connection);

    public List<Connection> findAll();

    public void delete(Long id);

    public Connection findById(long id);

    public Connection update(Connection connection);

}

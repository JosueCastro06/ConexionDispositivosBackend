package com.pruebatecnica.pruebatecnica.repository;

import java.util.List;

import com.pruebatecnica.pruebatecnica.models.Connection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IConnectionRepository extends JpaRepository<Connection, Long> {
    
    @Query(value = "select c from Connection c")
    public List<Connection> findAll();

    @Query(value = "select c from Connection c where c.id = :id")
    public Connection findById(long id);

}

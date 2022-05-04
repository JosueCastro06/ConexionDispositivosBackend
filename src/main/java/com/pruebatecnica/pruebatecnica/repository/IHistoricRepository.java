package com.pruebatecnica.pruebatecnica.repository;

import java.util.List;

import com.pruebatecnica.pruebatecnica.models.Historic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IHistoricRepository extends JpaRepository<Historic, Long> {
 
    @Query(value = "select h from Historic h")
    public List<Historic> findAll();
    
}

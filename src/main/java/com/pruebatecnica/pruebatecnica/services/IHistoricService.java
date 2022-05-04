package com.pruebatecnica.pruebatecnica.services;

import java.util.List;

import com.pruebatecnica.pruebatecnica.models.Historic;

public interface IHistoricService {
    
    public List<Historic> findAll();

    public Historic save(Historic historic);

}

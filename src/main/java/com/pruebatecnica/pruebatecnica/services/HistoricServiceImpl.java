package com.pruebatecnica.pruebatecnica.services;

import java.util.List;

import com.pruebatecnica.pruebatecnica.models.Historic;
import com.pruebatecnica.pruebatecnica.repository.IHistoricRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoricServiceImpl implements IHistoricService {
    
    private final IHistoricRepository historicRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Historic> findAll() {
        return historicRepository.findAll();
    }

    @Override
    @Transactional
    public Historic save(Historic historic) {
        return historicRepository.save(historic);
    }

}

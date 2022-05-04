package com.pruebatecnica.pruebatecnica.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.pruebatecnica.pruebatecnica.dto.HistoricDTO;
import com.pruebatecnica.pruebatecnica.models.Historic;
import com.pruebatecnica.pruebatecnica.services.IHistoricService;
import com.pruebatecnica.pruebatecnica.utils.Mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/historic")
@RequiredArgsConstructor
public class HistoricController {
    
    private final IHistoricService historicService;
    private final Mapper mapper;

    @GetMapping()
    public List<HistoricDTO> getHistoric() {
        return historicService.findAll()
                .stream()
                .map(historic -> mapper.modelMapper().map(historic, HistoricDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping()
    public ResponseEntity<HistoricDTO> saveHistoric(@RequestBody HistoricDTO historicDTO) {

        Historic historicRequest = mapper.modelMapper().map(historicDTO, Historic.class);
        Historic historic = historicService.save(historicRequest);

        HistoricDTO historicResponse = mapper.modelMapper().map(historic, HistoricDTO.class);

        return new ResponseEntity<>(historicResponse, HttpStatus.CREATED);
    }

}

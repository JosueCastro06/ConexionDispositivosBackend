package com.pruebatecnica.pruebatecnica.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class HistoricDTO implements Serializable{
    
    private Long id;
    private Long id_device;
    private String ip;
    private String mac;
    private String type_device;
    private Long id_connection;
    private Integer type_connection;
    private String name;
    private String encryption;

}

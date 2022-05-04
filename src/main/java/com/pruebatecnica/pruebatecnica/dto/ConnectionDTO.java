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
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionDTO implements Serializable{
    
    private Long id;
    private Integer type;
    private String name;
    private String encryption;
    private String nameco;
    private String pass;
    
}

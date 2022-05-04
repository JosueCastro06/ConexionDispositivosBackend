package com.pruebatecnica.pruebatecnica.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table( name = "Connection" )
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter 
    @Setter
    private Long id;

    @NotEmpty(message = "The field must not be empty")
    @Column(name = "type")
    @Getter 
    @Setter
    private Integer type;

    @NotEmpty(message = "The field must not be empty")
    @Column(name = "name")
    @Getter 
    @Setter
    private String name;

    @Column(name = "encryption")
    @Getter 
    @Setter
    private String encryption;

    @NotEmpty(message = "The field must not be empty")
    @Column(name = "nameco")
    @Getter 
    @Setter
    private String nameco;

    @NotEmpty(message = "The field must not be empty")
    @Column(name = "pass")
    @Getter 
    @Setter
    private String pass;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "connection")
    private List<Device> devices;


}

package com.pruebatecnica.pruebatecnica.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Historic")
@Setter
@Getter

public class Historic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_device")
    private Long id_device;

    @Column(name = "ip")
    private String ip;

    @Column(name = "mac")
    private String mac;

    @Column(name = "type_device")
    private String type_device;

    @Column(name = "idConnection")
    private Long id_connection;

    @Column(name = "typeConnection")
    private Integer type_connection;

    @Column(name = "name")
    private String name;

    @Column(name = "encryption")
    private String encryption;

}

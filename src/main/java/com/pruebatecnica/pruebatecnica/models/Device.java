package com.pruebatecnica.pruebatecnica.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table( name = "Device" )
@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true, nullable = false)
    private Long id;

    @NotEmpty(message = "The field must not be empty")
    @Column(name = "mac")
    private String mac;

    @NotEmpty(message = "The field must not be empty")
    @Column(name = "type")
    private String type;

    @Column(name = "connected")
    private Boolean connected;
    
    @NotEmpty(message = "The field must not be empty")
    @Column(name = "ip")
    private String ip;
    
    @NotEmpty(message = "The field must not be empty")
    @Column(name = "trademark")
    private String trademark;

    @JoinColumn(name = "connection_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @NotNull
    private Connection connection;

}

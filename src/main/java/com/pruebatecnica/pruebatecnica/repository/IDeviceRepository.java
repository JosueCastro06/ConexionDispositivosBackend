package com.pruebatecnica.pruebatecnica.repository;

import java.util.List;

import com.pruebatecnica.pruebatecnica.models.Device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeviceRepository extends JpaRepository<Device, Long> {

    @Query(value = "select d from Device d left join fetch d.connection")
    public List<Device> findAll();

    @Query("select count(d) from Device d left join d.connection c where c.id = :id")
    public Long countById(long id);

    @Query(value = "select d from Device d left join fetch d.connection where d.id = :id")
    public Device findById(long id);
    
}

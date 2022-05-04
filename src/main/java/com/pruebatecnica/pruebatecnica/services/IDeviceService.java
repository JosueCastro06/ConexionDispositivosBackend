package com.pruebatecnica.pruebatecnica.services;

import java.util.List;

import com.pruebatecnica.pruebatecnica.exceptions.MacAdreessInUseException;
import com.pruebatecnica.pruebatecnica.exceptions.NumberOfDevicesExceededException;
import com.pruebatecnica.pruebatecnica.models.Device;


public interface IDeviceService {

    public List<Device> findAll();

    public Device findById(long id);

    public void delete(Long id);

    public Device save(Device device) throws NumberOfDevicesExceededException, MacAdreessInUseException;

    public Device update(Device device) throws MacAdreessInUseException, NumberOfDevicesExceededException;
    
}

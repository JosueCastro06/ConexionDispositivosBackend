package com.pruebatecnica.pruebatecnica.services;

import java.util.List;
import java.util.Optional;

import com.pruebatecnica.pruebatecnica.exceptions.MacAdreessInUseException;
import com.pruebatecnica.pruebatecnica.exceptions.NumberOfDevicesExceededException;
import com.pruebatecnica.pruebatecnica.models.Connection;
import com.pruebatecnica.pruebatecnica.models.Device;
import com.pruebatecnica.pruebatecnica.repository.IConnectionRepository;
import com.pruebatecnica.pruebatecnica.repository.IDeviceRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements IDeviceService {

    private final IDeviceRepository deviceRepo;
    private final IConnectionRepository connectionRepo;

    @Value("${repConnections}")
    long repCon;

    @Override
    @Transactional(readOnly = true)
    public List<Device> findAll() {
        return deviceRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Device findById(long id) {
        return deviceRepo.findById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        deviceRepo.deleteById(id);
    }

    @Override
    @Transactional
    public Device save(Device device) throws NumberOfDevicesExceededException, MacAdreessInUseException {

        Connection connection = connectionRepo.findById(device.getConnection().getId()).orElse(new Connection());
        device.setConnection(connection);

        macEqual(device);
        numberDevices(device);

        return deviceRepo.save(device);

    }

    @Override
    @Transactional
    public Device update(Device device) throws MacAdreessInUseException, NumberOfDevicesExceededException {

        macEqual(device);
        numberDevices(device);

        return deviceRepo.save(device);
    }

    public void macEqual(Device device) throws MacAdreessInUseException {

        List<Device> listDevice = deviceRepo.findAll();
        
        Optional<Device> foundDevice = listDevice.stream()
                                                 .filter(d -> d.getMac().equals(device.getMac()))
                                                 .findAny();

        if (foundDevice.isPresent()) {
            throw new MacAdreessInUseException("Mac address in use");
        }

    }

    public void numberDevices(Device device) throws NumberOfDevicesExceededException {
        long rep = deviceRepo.countById(device.getConnection().getId());
        if (rep == repCon) {
            throw new NumberOfDevicesExceededException("Unable to register this device with this connection");
        }
    }

}

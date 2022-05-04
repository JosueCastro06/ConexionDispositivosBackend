package com.pruebatecnica.pruebatecnica.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.pruebatecnica.pruebatecnica.dto.DeviceDTO;
import com.pruebatecnica.pruebatecnica.exceptions.MacAdreessInUseException;
import com.pruebatecnica.pruebatecnica.exceptions.NumberOfDevicesExceededException;
import com.pruebatecnica.pruebatecnica.models.Device;
import com.pruebatecnica.pruebatecnica.services.IDeviceService;
import com.pruebatecnica.pruebatecnica.utils.Mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final IDeviceService deviceService;
    private final Mapper mapper;

    @GetMapping
    public List<DeviceDTO> findAll() {
        return deviceService.findAll()
                .stream()
                .map(device -> mapper.modelMapper().map(device, DeviceDTO.class))
                .collect(Collectors.toList());

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DeviceDTO> findById(@PathVariable Long id) {

        Device device = deviceService.findById(id);

        DeviceDTO deviceResponse = mapper.modelMapper().map(device, DeviceDTO.class);

        return ResponseEntity.ok().body(deviceResponse);
    }

    @PostMapping()
    public  ResponseEntity<Map<String, Object>> insert(@Valid @RequestBody DeviceDTO deviceDTO, BindingResult result) throws NumberOfDevicesExceededException, MacAdreessInUseException {
        Map<String, Object> responseAsMapPost = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntityPost = null;
        
        try {

            Device deviceRequest = mapper.modelMapper().map(deviceDTO, Device.class);
            Device device = deviceService.save(deviceRequest);
            
            responseAsMapPost.put("Device", device);
            responseAsMapPost.put("Message:", "The Device with id " + device.getId() + " was created successfully");
            responseEntityPost = new ResponseEntity<>(responseAsMapPost, HttpStatus.CREATED);
            
        } catch (NumberOfDevicesExceededException|MacAdreessInUseException e) {
            responseAsMapPost.put("Error:", e.getMessage());
            responseEntityPost = new ResponseEntity<>(responseAsMapPost, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            responseAsMapPost.put("Fatal Error:", e.getMessage());
            responseEntityPost = new ResponseEntity<>(responseAsMapPost ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return responseEntityPost;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable long id, @Valid @RequestBody DeviceDTO deviceDTO, BindingResult result) throws MacAdreessInUseException, NumberOfDevicesExceededException  {
        Map<String, Object> responseAsMapPut = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntityPut = null;
        
        try {

            Device deviceRequest = mapper.modelMapper().map(deviceDTO, Device.class);
            Device device = deviceService.update(deviceRequest);
            device.setId(id);
            
            responseAsMapPut.put("Device", device);
            responseAsMapPut.put("Message: ", "The Device with id " + device.getId() + " was updated successfully");
            responseEntityPut = new ResponseEntity<>(responseAsMapPut, HttpStatus.OK);
            
        } catch (NumberOfDevicesExceededException|MacAdreessInUseException e) {
            responseAsMapPut.put("Error: ", e.getMessage());
            responseEntityPut = new ResponseEntity<>(responseAsMapPut, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            responseAsMapPut.put("Fatal Error: ", e.getMessage());
            responseEntityPut = new ResponseEntity<>(responseAsMapPut ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return responseEntityPut;

    }

    @DeleteMapping(value = "/{id}") 
    public void deleteById(@PathVariable("id") Long id) {
        deviceService.delete(id);
    }
    
    
}

package com.pruebatecnica.pruebatecnica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruebatecnica.pruebatecnica.dto.ConnectionDTO;
import com.pruebatecnica.pruebatecnica.dto.DeviceDTO;
import com.pruebatecnica.pruebatecnica.models.Device;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(classes = PruebaTecnicaApplication.class ,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeviceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/schemaTest.sql")
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/device/insertConnection.sql")
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/device/insertDevicesDB.sql")
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/cleanDB.sql")
    void getDevicesTest() throws JsonProcessingException, JSONException {

        ResponseEntity<String> response = restTemplate.getForEntity("/api/devices", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Map<String, Object>> devices = jdbcTemplate.queryForList("SELECT * FROM device");
        List<DeviceDTO> deviceDTOList = objectMapper.readValue(response.getBody(), objectMapper.getTypeFactory().constructCollectionType(List.class, DeviceDTO.class));

        assertNotNull(response.getBody());
        assertEquals(devices.size(), deviceDTOList.size());

    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/schemaTest.sql")
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/device/insertConnection.sql")
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/cleanDB.sql")
    void saveDeviceTest() throws JsonProcessingException, JSONException {

        ConnectionDTO connection = jdbcTemplate.queryForObject("SELECT * FROM connection WHERE id = 1", (rs, rowNum) -> {
            ConnectionDTO connectionDTO = new ConnectionDTO();
            connectionDTO.setId(rs.getLong("id"));
            connectionDTO.setType(rs.getInt("type"));
            connectionDTO.setName(rs.getString("name"));
            connectionDTO.setEncryption(rs.getString("encryption"));
            connectionDTO.setNameco(rs.getString("nameco"));
            connectionDTO.setPass(rs.getString("pass"));
            return connectionDTO;
        });

        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(1L);
        deviceDTO.setMac("B8:D3:2A:86:7F:C1");
        deviceDTO.setIp("192.168.1.1");
        deviceDTO.setConnected(true);
        deviceDTO.setTrademark("Samsung");
        deviceDTO.setType("Computador");
        deviceDTO.setConnection(connection);

        HttpEntity<DeviceDTO> request = new HttpEntity<>(deviceDTO);
        ResponseEntity<DeviceDTO> response = restTemplate.postForEntity("/api/devices", request, DeviceDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        DeviceDTO result = jdbcTemplate.queryForObject("SELECT * FROM device where id = 1", (rs, rowNum) -> {
            DeviceDTO deviceDTO2 = new DeviceDTO();
            deviceDTO2.setId(rs.getLong("id"));
            deviceDTO2.setMac(rs.getString("mac"));
            deviceDTO2.setIp(rs.getString("ip"));
            deviceDTO2.setConnected(rs.getBoolean("connected"));
            deviceDTO2.setTrademark(rs.getString("trademark"));
            deviceDTO2.setType(rs.getString("type"));
            deviceDTO2.setConnection(connection);
            return deviceDTO2;
        });

        assertNotNull(result.getId());
        assertEquals(result, deviceDTO);

    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/schemaTest.sql")
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/device/insertConnection.sql")
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/device/insertDeviceDB.sql")
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/cleanDB.sql")
    void deleteDeviceTest() {

        HttpEntity<Device> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<Device> response = restTemplate.exchange("/api/devices/1", HttpMethod.DELETE, entity, Device.class); 

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, entity.getBody());
        
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/schemaTest.sql")
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/device/insertConnection.sql")
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/device/insertDeviceDB.sql")
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/cleanDB.sql")
    void updateDeviceTest() throws JsonProcessingException, JSONException {

        List<Map<String, Object>> connection = jdbcTemplate.queryForList("SELECT * FROM connection WHERE id = 1");

        String idString = connection.get(0).get("id").toString();
        Long id = Long.parseLong(idString);

        String typesString = connection.get(0).get("type").toString();
        Integer type = Integer.parseInt(typesString);

        ConnectionDTO connectionDTO = new ConnectionDTO();
        connectionDTO.setId(id);
        connectionDTO.setName(connection.get(0).get("name").toString());
        connectionDTO.setNameco(connection.get(0).get("nameco").toString());
        connectionDTO.setType(type);
        connectionDTO.setPass(connection.get(0).get("pass").toString());
        connectionDTO.setEncryption(connection.get(0).get("encryption").toString());

        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(1L);
        deviceDTO.setMac("B8:D3:2A:86:7F:C25");
        deviceDTO.setIp("192.168.1.25");
        deviceDTO.setConnected(true);
        deviceDTO.setTrademark("Xiaomi");
        deviceDTO.setType("Smartphone");
        deviceDTO.setConnection(connectionDTO);

        HttpEntity<DeviceDTO> request = new HttpEntity<>(deviceDTO);
        ResponseEntity<DeviceDTO> response = restTemplate.exchange("/api/devices/1", HttpMethod.PUT ,request, DeviceDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        DeviceDTO result = jdbcTemplate.queryForObject("SELECT * FROM device where id = 1", (rs, rowNum) -> {
            DeviceDTO deviceDTO2 = new DeviceDTO();
            deviceDTO2.setId(rs.getLong("id"));
            deviceDTO2.setMac(rs.getString("mac"));
            deviceDTO2.setIp(rs.getString("ip"));
            deviceDTO2.setConnected(rs.getBoolean("connected"));
            deviceDTO2.setTrademark(rs.getString("trademark"));
            deviceDTO2.setType(rs.getString("type"));
            deviceDTO2.setConnection(connectionDTO);
            return deviceDTO2;
        });

        assertNotNull(result.getId());
        assertEquals(result, deviceDTO);

    }



}


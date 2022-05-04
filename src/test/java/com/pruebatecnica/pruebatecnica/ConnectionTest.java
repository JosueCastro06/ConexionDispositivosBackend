package com.pruebatecnica.pruebatecnica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruebatecnica.pruebatecnica.dto.ConnectionDTO;
import com.pruebatecnica.pruebatecnica.models.Connection;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
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
class ConnectionTest {

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
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/connection/insertConnectionsDB.sql")
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/cleanDB.sql")
    void getConnectionsTest() throws JsonProcessingException, JSONException {

        ResponseEntity<String> response = restTemplate.getForEntity("/api/connections", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM Connection");

        String expected = objectMapper.writeValueAsString(result);

        assertNotNull(response);
        JSONAssert.assertEquals(expected, response.getBody(), false);

    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/schemaTest.sql")
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/cleanDB.sql")        
    void saveConnectionTest() throws JsonProcessingException, JSONException {

        ConnectionDTO connectionDTO = new ConnectionDTO();
        connectionDTO.setId(1L);
        connectionDTO.setType(2);
        connectionDTO.setName("Home");
        connectionDTO.setEncryption("");
        connectionDTO.setNameco("Red");
        connectionDTO.setPass("1234");

        HttpEntity<ConnectionDTO> request = new HttpEntity<>(connectionDTO);
        ResponseEntity<ConnectionDTO> response = restTemplate.postForEntity("/api/connections", request, ConnectionDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        ConnectionDTO result = jdbcTemplate.queryForObject("SELECT * FROM Connection where id = 1", (rs, rowNum) -> {
            ConnectionDTO connection = new ConnectionDTO();
            connection.setId(rs.getLong("id"));
            connection.setType(rs.getInt("type"));
            connection.setName(rs.getString("name"));
            connection.setEncryption(rs.getString("encryption"));
            connection.setNameco(rs.getString("nameco"));
            connection.setPass(rs.getString("pass"));
            return connection;
        });

        assertNotNull(result.getId());
        assertEquals(result, connectionDTO);

    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/schemaTest.sql")
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/connection/insertConnectionDB.sql")
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/cleanDB.sql")
    void deleteConnectionTest () {

        HttpEntity<Connection> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<Connection> response = restTemplate.exchange("/api/connections/2", HttpMethod.DELETE, entity, Connection.class); 

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, entity.getBody());

    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/schemaTest.sql")
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/connection/insertConnectionDB.sql")
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/cleanDB.sql")
    void updateConnectionTest () throws JsonProcessingException, JSONException {

        ConnectionDTO connectionDTO = new ConnectionDTO();
        connectionDTO.setId(2L);
        connectionDTO.setType(2);
        connectionDTO.setName("HomeEdit");
        connectionDTO.setEncryption("");
        connectionDTO.setNameco("Red5Edit");
        connectionDTO.setPass("1234");

        HttpEntity<ConnectionDTO> request = new HttpEntity<>(connectionDTO);
        ResponseEntity<ConnectionDTO> response = restTemplate.exchange("/api/connections/2", HttpMethod.PUT ,request, ConnectionDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ConnectionDTO result = jdbcTemplate.queryForObject("SELECT * FROM Connection where id = 2", (rs, rowNum) -> {
            ConnectionDTO connection = new ConnectionDTO();
            connection.setId(rs.getLong("id"));
            connection.setType(rs.getInt("type"));
            connection.setName(rs.getString("name"));
            connection.setEncryption(rs.getString("encryption"));
            connection.setNameco(rs.getString("nameco"));
            connection.setPass(rs.getString("pass"));
            return connection;
        });

        assertNotNull(result.getId());
        assertEquals(result, connectionDTO);

    }

}

package com.pruebatecnica.pruebatecnica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruebatecnica.pruebatecnica.dto.HistoricDTO;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(classes = PruebaTecnicaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HistoricTest {

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
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/historic/insertHistoricsDB.sql")
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/cleanDB.sql")
    void getHistoricTest() throws JsonProcessingException, JSONException {

        ResponseEntity<String> response = restTemplate.getForEntity("/api/historic", String.class);
        System.out.println(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM Historic");

        String expected = objectMapper.writeValueAsString(result);

        System.out.println("Expected!!!!!!!!!!!!!!" + expected);
        System.out.println("Response Body !!!!!!!!" + response.getBody());

        assertNotNull(response);
        JSONAssert.assertEquals(expected, response.getBody(), false);

    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/schemaTest.sql")
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/cleanDB.sql")
    void saveHistoricTest () throws JsonProcessingException, JSONException {

        HistoricDTO historicDTO = new HistoricDTO();
        historicDTO.setId(1L);
        historicDTO.setEncryption("");
        historicDTO.setId_connection(1L);
        historicDTO.setId_device(1L);
        historicDTO.setIp("192.168.1.1");
        historicDTO.setMac("B8:D3:2A:86:7F:C1");
        historicDTO.setName("Home");
        historicDTO.setType_connection(1);
        historicDTO.setType_device("Computador");

        HttpEntity<HistoricDTO> request = new HttpEntity<>(historicDTO);
        ResponseEntity<HistoricDTO> response = restTemplate.postForEntity("/api/historic", request, HistoricDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        HistoricDTO result = jdbcTemplate.queryForObject("SELECT * FROM Historic where id = 1", (rs, rowNum) -> {
            HistoricDTO historic = new HistoricDTO();
            historic.setId(rs.getLong("id"));
            historic.setEncryption(rs.getString("encryption"));
            historic.setId_connection(rs.getLong("id_connection"));
            historic.setId_device(rs.getLong("id_device"));
            historic.setIp(rs.getString("ip"));
            historic.setMac(rs.getString("mac"));
            historic.setName(rs.getString("name"));
            historic.setType_connection(rs.getInt("type_connection"));
            historic.setType_device(rs.getString("type_device"));
            return historic;
        });

        assertNotNull(result.getId());
        assertEquals(result, historicDTO);

    }

}

package com.embl.backend;

import com.embl.backend.dto.PersonDto;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class
)
@AutoConfigureMockMvc(addFilters = false)
public class ApplicationIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    final Gson gson = new Gson();

    @Test
    public void testShouldKnowHowToCreatePerson() throws Exception {
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/api/v1/person")
                .accept(MediaType.APPLICATION_JSON)
                .content(getRequestBody())
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.age").value("33"))
                .andExpect(jsonPath("$.first_name").value("John"));
    }

    @Test
    public void testShouldHowToHandleWhenMandatoryFieldsNotInRequestToCreatePerson() throws Exception {
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/api/v1/person")
                .accept(MediaType.APPLICATION_JSON)
                .content(getInvalidRequestBody())
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:insertPerson.sql" })
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = { "classpath:deletePerson.sql" })
    public void testShouldKnowHowToRetrievePerson() throws Exception {
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/person").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].first_name").value("Harsh"));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:insertPerson.sql" })
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = { "classpath:deletePerson.sql" })
    public void testShouldKnowHowToRetrievePersonById() throws Exception {
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/person/{id}", 101).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value("33"))
                .andExpect(jsonPath("$.id").value("101"));
    }

    @Test
    public void testShouldKnowHowToRetrievePersonById_WhenPersonNotExist() throws Exception {
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/person/{id}", 4).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:insertPerson.sql" })
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = { "classpath:deletePerson.sql" })
    public void testShouldKnowHowToUpdatePerson() throws Exception {
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/person/{id}", 101)
                .accept(MediaType.APPLICATION_JSON)
                .content(getRequestBody())
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value("John"));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:insertPerson.sql" })
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = { "classpath:deletePerson.sql" })
    public void testShouldKnowHowToDeletePerson() throws Exception {
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/person/{id}", 101);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    private String getRequestBody() {
        final PersonDto personDto = new PersonDto();
        personDto.setAge(33);
        personDto.setFavourite_colour("red");
        personDto.setFirst_name("John");
        personDto.setLast_name("Smith");
        personDto.setHobby(Arrays.asList("shopping"));
        personDto.setId(1L);
        return gson.toJson(personDto);
    }

    private String getInvalidRequestBody() {
        final PersonDto personDto = new PersonDto();
        personDto.setAge(-1);
        personDto.setFavourite_colour("red");
        personDto.setFirst_name("John");
        personDto.setLast_name("Smith");
        personDto.setHobby(Arrays.asList("shopping"));
        personDto.setId(1L);
        return gson.toJson(personDto);
    }


}

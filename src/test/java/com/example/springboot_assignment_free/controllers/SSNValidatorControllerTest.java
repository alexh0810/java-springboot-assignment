package com.example.springboot_assignment_free.controllers;

import com.example.springboot_assignment_free.model.SSN;
import com.example.springboot_assignment_free.services.ValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SSNValidatorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ValidationService validationService;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();


    @Test
    public void validateCorrectFormattedSSN() throws Exception {
        SSN request_body = new SSN("131052-308T", "FI");
        String content = objectWriter.writeValueAsString(request_body);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/validate-ssn")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(content().string("Social security number is valid!"));
    }

    @Test
    public void notEnoughCharactersSSN() throws Exception {
            SSN request_body = new SSN("13052-308T", "FI");
            String content = objectWriter.writeValueAsString(request_body);
            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/validate-ssn")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(content);

            mockMvc.perform(mockRequest).andExpect(status().isUnprocessableEntity()).andExpect(content().string("Social security number is invalid!"));
    }

    @Test
    public void wrongControlCharacterSSN() throws Exception {
        SSN request_body = new SSN("131052-308D", "FI");
        String content = objectWriter.writeValueAsString(request_body);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/validate-ssn")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest).andExpect(status().isUnprocessableEntity()).andExpect(content().string("Social security number is invalid!"));
    }

    @Test
    public void wrongCountryCode() throws Exception {
        SSN request_body = new SSN("131052-308T", "DE");
        String content = objectWriter.writeValueAsString(request_body);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/validate-ssn")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest).andExpect(status().isUnprocessableEntity()).andExpect(content().string("Country code is not supported"));
    }

    @Test
    public void outOfRangeIndividualNumber() throws Exception {
        SSN request_body = new SSN("131052-999T", "FI");
        String content = objectWriter.writeValueAsString(request_body);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/validate-ssn")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest).andExpect(status().isUnprocessableEntity()).andExpect(content().string("Social security number is invalid!"));
    }
    @Test
    public void outOfRangeIndividualNumber2() throws Exception {
        SSN request_body = new SSN("131052-001T", "FI");
        String content = objectWriter.writeValueAsString(request_body);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/validate-ssn")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest).andExpect(status().isUnprocessableEntity()).andExpect(content().string("Social security number is invalid!"));
    }

    @Test
    public void wrongDelimiter() throws Exception {
        SSN request_body = new SSN("131052+308T", "FI");
        String content = objectWriter.writeValueAsString(request_body);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/validate-ssn")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest).andExpect(status().isUnprocessableEntity()).andExpect(content().string("Social security number is invalid!"));
    }


}

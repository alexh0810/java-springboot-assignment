package com.example.springboot_assignment_free.controllers;

import com.example.springboot_assignment_free.model.SSN;
import com.example.springboot_assignment_free.services.SsnValidationService;
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

@SpringBootTest(properties = "app.exchange_rate.api_key=abc")
@AutoConfigureMockMvc
public class SSNValidatorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private SsnValidationService validationService;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();


    @Test
    public void returnOkIfSsnIsCorrect() throws Exception {
        SSN request_body = new SSN("131052-308T", "FI");
        String content = objectWriter.writeValueAsString(request_body);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/validate-ssn")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ssn_valid").value(true));
    }

    @Test
    public void returnNotOkIfSsnIsNotEnoughCharacters() throws Exception {
            SSN request_body = new SSN("13052-308T", "FI");
            String content = objectWriter.writeValueAsString(request_body);
            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/validate-ssn")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(content);

            mockMvc.perform(mockRequest)
                    .andExpect(status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.ssn_valid").value(false));
    }

    @Test
    public void returnNotOkIfSsnHasWrongControlCharacter() throws Exception {
        SSN request_body = new SSN("131052-308D", "FI");
        String content = objectWriter.writeValueAsString(request_body);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/validate-ssn")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ssn_valid").value(false));
    }

    @Test
    public void returnNotOkIfSsnHasWrongCountryCode() throws Exception {
        SSN request_body = new SSN("131052-308T", "DE");
        String content = objectWriter.writeValueAsString(request_body);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/validate-ssn")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ssn_valid").value(false));
    }

    @Test
    public void returnNotOkIfSsnHasOutOfRangeIndividualNumber() throws Exception {
        SSN request_body = new SSN("131052-999T", "FI");
        String content = objectWriter.writeValueAsString(request_body);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/validate-ssn")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ssn_valid").value(false));
    }
    @Test
    public void returnNotOkIfSsnHasOutOfRangeIndividualNumber2() throws Exception {
        SSN request_body = new SSN("131052-001T", "FI");
        String content = objectWriter.writeValueAsString(request_body);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/validate-ssn")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ssn_valid").value(false));
    }

    @Test
    public void returnNotOkIfSsnHasWrongDelimiter() throws Exception {
        SSN request_body = new SSN("131052+308T", "FI");
        String content = objectWriter.writeValueAsString(request_body);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/validate-ssn")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ssn_valid").value(false));
    }


}

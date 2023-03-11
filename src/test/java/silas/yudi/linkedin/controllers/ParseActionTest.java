package silas.yudi.linkedin.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import silas.yudi.linkedin.dtos.request.BinaryFile;
import silas.yudi.linkedin.dtos.response.Profile;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ParseActionTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    public void whenUploadFileThenParse() throws Exception {
        byte[] pdf = getClass().getResourceAsStream("/Profile.pdf").readAllBytes();
        byte[] expected = getClass().getResourceAsStream("/expected.json").readAllBytes();
        Profile expectedProfile = objectMapper.readValue(expected, Profile.class);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "Profile.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                pdf);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.multipart("/parse/multipart").file(file))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(
                expectedProfile,
                objectMapper.readValue(result.getResponse().getContentAsByteArray(), Profile.class));
    }

    @Test
    public void whenPostBase64ThenParse() throws Exception {
        byte[] pdf = getClass().getResourceAsStream("/Profile.pdf").readAllBytes();
        BinaryFile body = new BinaryFile(pdf);

        byte[] expected = getClass().getResourceAsStream("/expected.json").readAllBytes();
        Profile expectedProfile = objectMapper.readValue(expected, Profile.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/parse")
                .content(objectMapper.writeValueAsBytes(body))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(
                expectedProfile,
                objectMapper.readValue(result.getResponse().getContentAsByteArray(), Profile.class));
    }

    @Test
    public void whenUploadEmptyFileShouldThrowException() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "Profile.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                new byte[0]
        );

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.multipart("/parse/multipart").file(file))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("The file is empty or could not be uploaded.", result.getResponse().getErrorMessage());
    }

    @Test
    public void whenUploadNotPdfFileShouldThrowException() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "text.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "text".getBytes()
        );

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.multipart("/parse/multipart").file(file))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("This file is not PDF.", result.getResponse().getErrorMessage());
    }
}

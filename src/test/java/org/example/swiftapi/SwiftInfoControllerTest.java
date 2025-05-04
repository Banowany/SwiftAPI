package org.example.swiftapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.swiftapi.dtos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SwiftInfoControllerTest {

    @Mock
    private SwiftInfoService swiftInfoService;

    @InjectMocks
    private SwiftInfoController swiftInfoController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(swiftInfoController).build();
    }

    @Test
    void testGetSwiftInfo_ValidSwiftCode_WithExpectedDTO() throws Exception {
        // Arrange
        String swiftCode = "ABCDEFGHXXX";
        SwiftInfoUniversalResponseDTO expectedDTO = new SwiftInfoUniversalResponseDTO();
        expectedDTO.setSwiftCode(swiftCode);
        expectedDTO.setAddress("TEST ADDRESS");
        expectedDTO.setBankName("TEST BANK");
        expectedDTO.setCountryISO2("PL");
        expectedDTO.setCountryName("POLAND");
        expectedDTO.setHeadquarter(true);

        when(swiftInfoService.findSwiftInfo(swiftCode)).thenReturn(Optional.of(expectedDTO));

        // Act & Assert
        mockMvc.perform(get("/v1/swift-codes/{swift-code}", swiftCode))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    SwiftInfoUniversalResponseDTO actualDTO = new ObjectMapper().readValue(jsonResponse, SwiftInfoUniversalResponseDTO.class);
                    assertEquals(expectedDTO, actualDTO);
                });
    }

    @Test
    void testGetSwiftInfo_InvalidSwiftCode() throws Exception {
        // Arrange
        String swiftCode = "ABCDEFGH";

        // Act & Assert
        mockMvc.perform(get("/v1/swift-codes/{swift-code}", swiftCode))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetSwiftInfo_NotFound() throws Exception {
        // Arrange
        String swiftCode = "ABCDEFGHXXX";
        when(swiftInfoService.findSwiftInfo(swiftCode)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/v1/swift-codes/{swift-code}", swiftCode))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetCountrySwiftInfo_ValidCountryCode() throws Exception {
        // Arrange
        String countryISO2 = "PL";

        ShortSwiftInfoDTO shortSwiftInfo = new ShortSwiftInfoDTO();
        shortSwiftInfo.setSwiftCode("ABCDEFGHXXX");
        shortSwiftInfo.setAddress("TEST ADDRESS");
        shortSwiftInfo.setBankName("TEST BANK");
        shortSwiftInfo.setCountryISO2(countryISO2);
        shortSwiftInfo.setHeadquarter(true);

        SwiftInfoCountryResponseDTO responseDTO = new SwiftInfoCountryResponseDTO();
        responseDTO.setCountryISO2(countryISO2);
        responseDTO.setCountryName("POLAND");
        responseDTO.setSwiftCodes(List.of(shortSwiftInfo));

        when(swiftInfoService.findSwiftInfosRelatedWithCountry(countryISO2)).thenReturn(Optional.of(responseDTO));

        // Act & Assert
        mockMvc.perform(get("/v1/swift-codes/country/{countryISO2code}", countryISO2))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    SwiftInfoCountryResponseDTO actualDTO = new ObjectMapper().readValue(jsonResponse, SwiftInfoCountryResponseDTO.class);
                    assertEquals(responseDTO, actualDTO);
                });
    }

    @Test
    void testGetCountrySwiftInfo_InvalidCountryCode() throws Exception {
        // Arrange
        String countryISO2 = "PLL";

        // Act & Assert
        mockMvc.perform(get("/v1/swift-codes/country/{countryISO2code}", countryISO2))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateSwiftInfo_ValidRequest() throws Exception {
        String responseMessage = "ABCDEFGHXXX saved successfully";
        when(swiftInfoService.createSwiftInfo(any(AddSwiftInfoRequestDTO.class))).thenReturn(responseMessage);

        // Act & Assert
        mockMvc.perform(post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "swiftCode": "ABCDEFGHXXX",
                                    "address": "Test Address",
                                    "bankName": "Test Bank",
                                    "countryISO2": "PL",
                                    "countryName": "Poland",
                                    "isHeadquarter": "true"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    OnlyMessageResponseDTO responseDTO = new ObjectMapper().readValue(jsonResponse, OnlyMessageResponseDTO.class);
                    var expectedResponse = new OnlyMessageResponseDTO();
                    expectedResponse.setMessage(responseMessage);
                    assertEquals(expectedResponse, responseDTO);
                });
    }

    @Test
    void testCreateSwiftInfo_InvalidRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "swiftCode": "INVALID",
                                    "address": "Test Address",
                                    "notRelated": "123"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteSwiftInfo_ValidSwiftCode() throws Exception {
        // Arrange
        String swiftCode = "ABCDEFGHXXX";
        String responseMessage = "Swift information deleted successfully";
        when(swiftInfoService.deleteSwiftInfo(swiftCode)).thenReturn(responseMessage);

        // Act & Assert
        mockMvc.perform(delete("/v1/swift-codes/{swift-code}", swiftCode))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    OnlyMessageResponseDTO responseDTO = new ObjectMapper().readValue(jsonResponse, OnlyMessageResponseDTO.class);
                    var expectedResponse = new OnlyMessageResponseDTO();
                    expectedResponse.setMessage(responseMessage);
                    assertEquals(expectedResponse, responseDTO);
                });
    }

    @Test
    void testDeleteSwiftInfo_InvalidSwiftCode() throws Exception {
        // Arrange
        String swiftCode = "ABCDEFGHXXXAAA";

        // Act & Assert
        mockMvc.perform(delete("/v1/swift-codes/{swift-code}", swiftCode))
                .andExpect(status().isBadRequest());
    }
}
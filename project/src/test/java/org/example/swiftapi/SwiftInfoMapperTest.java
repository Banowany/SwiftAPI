package org.example.swiftapi;

import org.example.swiftapi.dtos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SwiftInfoMapperTest {

    private SwiftInfoMapper swiftInfoMapper;

    @BeforeEach
    void setUp() {
        swiftInfoMapper = new SwiftInfoMapper();
    }

    @Test
    void testToShortSwiftInfoDTO() {
        // Arrange
        SwiftInfo swiftInfo = new SwiftInfo();
        swiftInfo.setAddress("Test Address");
        swiftInfo.setBankName("Test Bank");
        swiftInfo.setCountryISO2("PL");
        swiftInfo.setCountryName("Test Country");
        swiftInfo.setSwiftCode("ABCDEFGHXXX");

        ShortSwiftInfoDTO expected = new ShortSwiftInfoDTO();
        expected.setAddress("Test Address");
        expected.setBankName("Test Bank");
        expected.setCountryISO2("PL");
        expected.setHeadquarter(true);
        expected.setSwiftCode("ABCDEFGHXXX");


        // Act
        ShortSwiftInfoDTO result = swiftInfoMapper.toShortSwiftInfoDTO(swiftInfo);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void testToCountryResponseDTO() {
        // Arrange
        SwiftInfo swiftInfo1 = new SwiftInfo();
        swiftInfo1.setSwiftCode("ABCDEFGHXXX");
        swiftInfo1.setAddress("ADDRESS 1");
        swiftInfo1.setBankName("BANK 1");
        swiftInfo1.setCountryISO2("PL");
        swiftInfo1.setCountryName("POLAND");

        SwiftInfo swiftInfo2 = new SwiftInfo();
        swiftInfo2.setSwiftCode("ABCDEFGHAAA");
        swiftInfo2.setAddress("ADDRESS 2");
        swiftInfo2.setBankName("BANK 2");
        swiftInfo2.setCountryISO2("PL");
        swiftInfo2.setCountryName("POLAND");

        List<SwiftInfo> swiftInfos = List.of(swiftInfo1, swiftInfo2);

        ShortSwiftInfoDTO shortSwiftInfo1 = new ShortSwiftInfoDTO();
        shortSwiftInfo1.setSwiftCode("ABCDEFGHXXX");
        shortSwiftInfo1.setAddress("ADDRESS 1");
        shortSwiftInfo1.setBankName("BANK 1");
        shortSwiftInfo1.setCountryISO2("PL");
        shortSwiftInfo1.setHeadquarter(true);

        ShortSwiftInfoDTO shortSwiftInfo2 = new ShortSwiftInfoDTO();
        shortSwiftInfo2.setSwiftCode("ABCDEFGHAAA");
        shortSwiftInfo2.setAddress("ADDRESS 2");
        shortSwiftInfo2.setBankName("BANK 2");
        shortSwiftInfo2.setCountryISO2("PL");
        shortSwiftInfo2.setHeadquarter(false);

        List<ShortSwiftInfoDTO> shortSwiftInfos = List.of(
                shortSwiftInfo1,
                shortSwiftInfo2
        );

        SwiftInfoCountryResponseDTO expected = new SwiftInfoCountryResponseDTO();
        expected.setCountryISO2("PL");
        expected.setCountryName("POLAND");
        expected.setSwiftCodes(shortSwiftInfos);

        // Act
        SwiftInfoCountryResponseDTO result = swiftInfoMapper
                .toCountryResponseDTO(
                        swiftInfos,
                        "PL",
                        "POLAND"
                );

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void testToBranchResponseDTO() {
        // Arrange
        SwiftInfo swiftInfo = new SwiftInfo();
        swiftInfo.setSwiftCode("ABCDEFGHAAA");
        swiftInfo.setAddress("BRANCH ADDRESS");
        swiftInfo.setBankName("BRANCH BANK");
        swiftInfo.setCountryISO2("PL");
        swiftInfo.setCountryName("POLAND");

        SwiftInfoBranchResponseDTO expected = new SwiftInfoBranchResponseDTO();
        expected.setAddress("BRANCH ADDRESS");
        expected.setBankName("BRANCH BANK");
        expected.setCountryISO2("PL");
        expected.setCountryName("POLAND");
        expected.setHeadquarter(false);
        expected.setSwiftCode("ABCDEFGHAAA");

        // Act
        SwiftInfoBranchResponseDTO result = swiftInfoMapper
                .toBranchResponseDTO(swiftInfo);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void testToHeadquarterResponseDTO() {
        // Arrange
        SwiftInfo headquarter = new SwiftInfo();
        headquarter.setSwiftCode("ABCDEFGHXXX");
        headquarter.setAddress("HQ ADDRESS");
        headquarter.setBankName("HQ BANK");
        headquarter.setCountryISO2("PL");
        headquarter.setCountryName("POLAND");

        SwiftInfo branch = new SwiftInfo();
        branch.setSwiftCode("ABCDEFGHAAA");
        branch.setAddress("BRANCH ADDRESS");
        branch.setBankName("BRANCH BANK");
        branch.setCountryISO2("PL");
        branch.setCountryName("POLAND");

        List<SwiftInfo> branches = List.of(branch);

        ShortSwiftInfoDTO shortBranch = new ShortSwiftInfoDTO();
        shortBranch.setSwiftCode("ABCDEFGHAAA");
        shortBranch.setAddress("BRANCH ADDRESS");
        shortBranch.setBankName("BRANCH BANK");
        shortBranch.setCountryISO2("PL");
        shortBranch.setHeadquarter(false);
        shortBranch.setSwiftCode("ABCDEFGHAAA");

        List<ShortSwiftInfoDTO> shortBranches = List.of(shortBranch);

        SwiftInfoHeadquarterResponseDTO expected = new SwiftInfoHeadquarterResponseDTO();
        expected.setAddress("HQ ADDRESS");
        expected.setBankName("HQ BANK");
        expected.setCountryISO2("PL");
        expected.setCountryName("POLAND");
        expected.setHeadquarter(true);
        expected.setSwiftCode("ABCDEFGHXXX");
        expected.setBranches(shortBranches);

        // Act
        SwiftInfoHeadquarterResponseDTO result = swiftInfoMapper
                .toHeadquarterResponseDTO(headquarter, branches);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void testFromAddSwiftInfoDTO() {
        // Arrange
        AddSwiftInfoRequestDTO requestDTO = new AddSwiftInfoRequestDTO();
        requestDTO.setAddress("TEST ADDRESS");
        requestDTO.setBankName("TEST BANK");
        requestDTO.setCountryISO2("PL");
        requestDTO.setCountryName("POLAND");
        requestDTO.setHeadquarter(true);
        requestDTO.setSwiftCode("ABCDEFGHXXX");

        SwiftInfo expected = new SwiftInfo();
        expected.setSwiftCode("ABCDEFGHXXX");
        expected.setAddress("TEST ADDRESS");
        expected.setBankName("TEST BANK");
        expected.setCountryISO2("PL");
        expected.setCountryName("POLAND");


        // Act
        SwiftInfo result = swiftInfoMapper.fromAddSwiftInfoDTO(requestDTO);

        // Assert
        assertEquals(expected, result);
    }
}
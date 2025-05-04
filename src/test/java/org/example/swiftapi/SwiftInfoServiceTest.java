package org.example.swiftapi;

import org.example.swiftapi.dtos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SwiftInfoServiceTest {
    @Mock
    private SwiftInfoRepository swiftInfoRepository;

    @Mock
    private SwiftInfoMapper swiftInfoMapper;

    @InjectMocks
    private SwiftInfoService swiftInfoService;

    String swiftHQ;
    String swiftBranch;
    String swiftNonRelated;

    String commonSwift8;
    String nonRelatedSwift8;

    String commonISO2;
    String nonRelatedISO2;

    SwiftInfo swiftInfoHQ;
    SwiftInfo swiftInfoBranch;
    SwiftInfo swiftInfoNonRelated;

    ShortSwiftInfoDTO shortSwiftInfoHqDTO;
    ShortSwiftInfoDTO shortSwiftInfoBranchDTO;
    ShortSwiftInfoDTO shortSwiftInfoNonRelatedDTO;

    SwiftInfoHeadquarterResponseDTO swiftInfoHQResponseForFindSwiftCode;
    SwiftInfoBranchResponseDTO swiftInfoBranchResponseForFindSwiftCode;
    SwiftInfoHeadquarterResponseDTO swiftInfoNonRelatedResponseForFindSwiftCode;

    SwiftInfoCountryResponseDTO commonISO2ResponseForFindCountryCode;
    SwiftInfoCountryResponseDTO nonRelatedISO2ResponseForFindCountryCode;

    AddSwiftInfoRequestDTO newSwiftInfo;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        swiftHQ = "ABCDEFGHXXX";
        swiftBranch = "ABCDEFGHAAA";
        swiftNonRelated = "ABCDEFGIXXX";

        commonSwift8 = "ABCDEFGH";
        nonRelatedSwift8 = "ABCDEFGI";

        commonISO2 = "XX";
        nonRelatedISO2 = "YY";

        swiftInfoHQ = new SwiftInfo();
        swiftInfoBranch = new SwiftInfo();
        swiftInfoNonRelated = new SwiftInfo();

        swiftInfoHQ.setSwiftCode(swiftHQ);
        swiftInfoBranch.setSwiftCode(swiftBranch);
        swiftInfoNonRelated.setSwiftCode(swiftNonRelated);

        swiftInfoHQ.setAddress("something address");
        swiftInfoBranch.setAddress("something address");
        swiftInfoNonRelated.setAddress("something address");

        swiftInfoHQ.setBankName("something bank name");
        swiftInfoBranch.setBankName("something bank name");
        swiftInfoNonRelated.setBankName("something bank name");

        swiftInfoHQ.setCountryISO2(commonISO2);
        swiftInfoBranch.setCountryISO2(commonISO2);
        swiftInfoNonRelated.setCountryISO2(nonRelatedISO2);

        swiftInfoHQ.setCountryName("something country name");
        swiftInfoBranch.setCountryName("something country name");
        swiftInfoNonRelated.setCountryName("something country name");

        swiftInfoHQ.setSwiftCode8(commonSwift8);
        swiftInfoBranch.setSwiftCode8(commonSwift8);
        swiftInfoNonRelated.setSwiftCode8(nonRelatedSwift8);

        shortSwiftInfoHqDTO = new ShortSwiftInfoDTO();
        shortSwiftInfoHqDTO.setAddress(swiftInfoHQ.getAddress());
        shortSwiftInfoHqDTO.setBankName(swiftInfoHQ.getBankName());
        shortSwiftInfoHqDTO.setCountryISO2(swiftInfoHQ.getCountryISO2());
        shortSwiftInfoHqDTO.setHeadquarter(true);
        shortSwiftInfoHqDTO.setSwiftCode(swiftInfoHQ.getSwiftCode());

        shortSwiftInfoBranchDTO = new ShortSwiftInfoDTO();
        shortSwiftInfoBranchDTO.setAddress(swiftInfoBranch.getAddress());
        shortSwiftInfoBranchDTO.setBankName(swiftInfoBranch.getBankName());
        shortSwiftInfoBranchDTO.setCountryISO2(swiftInfoBranch.getCountryISO2());
        shortSwiftInfoBranchDTO.setHeadquarter(false);
        shortSwiftInfoBranchDTO.setSwiftCode(swiftInfoBranch.getSwiftCode());

        shortSwiftInfoNonRelatedDTO  = new ShortSwiftInfoDTO();
        shortSwiftInfoNonRelatedDTO.setAddress(swiftInfoNonRelated.getAddress());
        shortSwiftInfoNonRelatedDTO.setBankName(swiftInfoNonRelated.getBankName());
        shortSwiftInfoNonRelatedDTO.setCountryISO2(swiftInfoNonRelated.getCountryISO2());
        shortSwiftInfoNonRelatedDTO.setHeadquarter(true);
        shortSwiftInfoNonRelatedDTO.setSwiftCode(swiftInfoNonRelated.getSwiftCode());

        var swiftInfoHQResponseForFindSwiftCode = new SwiftInfoHeadquarterResponseDTO();
        swiftInfoHQResponseForFindSwiftCode.setAddress(swiftInfoHQ.getAddress());
        swiftInfoHQResponseForFindSwiftCode.setBankName(swiftInfoHQ.getBankName());
        swiftInfoHQResponseForFindSwiftCode.setCountryISO2(swiftInfoHQ.getCountryISO2());
        swiftInfoHQResponseForFindSwiftCode.setCountryName(swiftInfoHQ.getCountryName());
        swiftInfoHQResponseForFindSwiftCode.setHeadquarter(true);
        swiftInfoHQResponseForFindSwiftCode.setSwiftCode(swiftInfoHQ.getSwiftCode());
        swiftInfoHQResponseForFindSwiftCode.setBranches(List.of(shortSwiftInfoBranchDTO));
        this.swiftInfoHQResponseForFindSwiftCode = swiftInfoHQResponseForFindSwiftCode;

        var swiftInfoBranchResponseForFindSwiftCode = new SwiftInfoBranchResponseDTO();
        swiftInfoBranchResponseForFindSwiftCode.setAddress(swiftInfoBranch.getAddress());
        swiftInfoBranchResponseForFindSwiftCode.setBankName(swiftInfoBranch.getBankName());
        swiftInfoBranchResponseForFindSwiftCode.setCountryISO2(swiftInfoBranch.getCountryISO2());
        swiftInfoBranchResponseForFindSwiftCode.setCountryName(swiftInfoBranch.getCountryName());
        swiftInfoBranchResponseForFindSwiftCode.setHeadquarter(false);
        swiftInfoBranchResponseForFindSwiftCode.setSwiftCode(swiftInfoBranch.getSwiftCode());
        this.swiftInfoBranchResponseForFindSwiftCode = swiftInfoBranchResponseForFindSwiftCode;

        var swiftInfoNonRelatedResponseForFindSwiftCode = new SwiftInfoHeadquarterResponseDTO();
        swiftInfoNonRelatedResponseForFindSwiftCode.setAddress(swiftInfoNonRelated.getAddress());
        swiftInfoNonRelatedResponseForFindSwiftCode.setBankName(swiftInfoNonRelated.getBankName());
        swiftInfoNonRelatedResponseForFindSwiftCode.setCountryISO2(swiftInfoNonRelated.getCountryISO2());
        swiftInfoNonRelatedResponseForFindSwiftCode.setCountryName(swiftInfoNonRelated.getCountryName());
        swiftInfoNonRelatedResponseForFindSwiftCode.setHeadquarter(true);
        swiftInfoNonRelatedResponseForFindSwiftCode.setSwiftCode(swiftInfoNonRelated.getSwiftCode());
        swiftInfoNonRelatedResponseForFindSwiftCode.setBranches(List.of());
        this.swiftInfoNonRelatedResponseForFindSwiftCode = swiftInfoNonRelatedResponseForFindSwiftCode;

        commonISO2ResponseForFindCountryCode = new SwiftInfoCountryResponseDTO();
        commonISO2ResponseForFindCountryCode.setCountryISO2(commonISO2);
        commonISO2ResponseForFindCountryCode.setCountryName("some country name");
        commonISO2ResponseForFindCountryCode.setSwiftCodes(
                List.of(shortSwiftInfoHqDTO, shortSwiftInfoBranchDTO)
        );

        nonRelatedISO2ResponseForFindCountryCode = new SwiftInfoCountryResponseDTO();
        nonRelatedISO2ResponseForFindCountryCode.setCountryISO2(nonRelatedISO2);
        nonRelatedISO2ResponseForFindCountryCode.setCountryName("some country name");
        nonRelatedISO2ResponseForFindCountryCode.setSwiftCodes(
                List.of(shortSwiftInfoNonRelatedDTO)
        );

        newSwiftInfo = new AddSwiftInfoRequestDTO();
        newSwiftInfo.setSwiftCode(swiftInfoNonRelated.getSwiftCode());
        newSwiftInfo.setAddress(swiftInfoNonRelated.getAddress());
        newSwiftInfo.setBankName(swiftInfoNonRelated.getBankName());
        newSwiftInfo.setCountryISO2(swiftInfoNonRelated.getCountryISO2());
        newSwiftInfo.setCountryName(swiftInfoNonRelated.getCountryName());
        newSwiftInfo.setIsHeadquarter("true");
    }

    @Test
    void testFindSwiftInfo_HeadquarterWithBranch() {
        //Arrenge
        when(swiftInfoRepository.findBySwiftCode8(commonSwift8))
                .thenReturn(List.of(swiftInfoHQ, swiftInfoBranch));

        when(swiftInfoMapper.toHeadquarterResponseDTO(
                swiftInfoHQ, List.of(swiftInfoBranch))
        ).thenReturn(swiftInfoHQResponseForFindSwiftCode);

        //Act
        Optional<SwiftInfoUniversalResponseDTO> result =
                swiftInfoService.findSwiftInfo(swiftHQ);

        //Assert
        assertTrue(result.isPresent());
        assertEquals(swiftInfoHQResponseForFindSwiftCode, result.get());
    }

    @Test
    void testFindSwiftInfo_Branch() {
        //Arrenge
        when(swiftInfoRepository.findBySwiftCode(swiftBranch))
                .thenReturn(List.of(swiftInfoBranch));

        when(swiftInfoMapper.toBranchResponseDTO(swiftInfoBranch)
        ).thenReturn(swiftInfoBranchResponseForFindSwiftCode);

        //Act
        Optional<SwiftInfoUniversalResponseDTO> result =
                swiftInfoService.findSwiftInfo(swiftBranch);

        //Assert
        assertTrue(result.isPresent());
        assertEquals(swiftInfoBranchResponseForFindSwiftCode, result.get());
    }

    @Test
    void testFindSwiftInfo_HeadquarterWithoutBranch() {
        //Arrenge
        when(swiftInfoRepository.findBySwiftCode8(nonRelatedSwift8))
                .thenReturn(List.of(swiftInfoNonRelated));

        when(swiftInfoMapper.toHeadquarterResponseDTO(
                swiftInfoNonRelated, List.of()
        )).thenReturn(swiftInfoNonRelatedResponseForFindSwiftCode);

        //Act
        Optional<SwiftInfoUniversalResponseDTO> result =
                swiftInfoService.findSwiftInfo(swiftNonRelated);

        //Assert
        assertTrue(result.isPresent());
        assertEquals(swiftInfoNonRelatedResponseForFindSwiftCode, result.get());
    }

    @Test
    void testFindSwiftInfo_NoSwiftInfo() {
        //Arrenge
        when(swiftInfoRepository.findBySwiftCode8("ABCDEFGHIJK"))
                .thenReturn(List.of());

        //Act
        Optional<SwiftInfoUniversalResponseDTO> result =
                swiftInfoService.findSwiftInfo("ABCDEFGHIJK");

        //Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testFindSwiftInfo_WrongSwiftCode() {
        //Arrenge

        //Act
        Optional<SwiftInfoUniversalResponseDTO> result =
                swiftInfoService.findSwiftInfo("ABCDEFGH");

        //Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testFindSwiftInfosRelatedWithCountry() {
        //Arrenge
        when(swiftInfoRepository.findByCountryISO2(commonISO2))
                .thenReturn(List.of(swiftInfoHQ, swiftInfoBranch));

        when(swiftInfoMapper.toCountryResponseDTO(
                List.of(swiftInfoHQ, swiftInfoBranch),
                commonISO2, "something country name"
        )).thenReturn(commonISO2ResponseForFindCountryCode);

        //Act
        Optional<SwiftInfoCountryResponseDTO> result =
                swiftInfoService.findSwiftInfosRelatedWithCountry(
                        commonISO2
                );

        //Assert
        assertTrue(result.isPresent());
        assertEquals(commonISO2ResponseForFindCountryCode, result.get());
    }

    @Test
    void testFindSwiftInfosRelatedWithCountry_WrongCode() {
        //Arrenge

        //Act
        Optional<SwiftInfoCountryResponseDTO> result =
                swiftInfoService.findSwiftInfosRelatedWithCountry(
                        "ABC"
                );

        //Assert
        assertFalse(result.isPresent());
    }

    @Test
    void createSwiftInfo() {
        //Arrenge
        when(swiftInfoRepository.save(swiftInfoNonRelated))
                .thenReturn(swiftInfoNonRelated);

        when(swiftInfoMapper.fromAddSwiftInfoDTO(newSwiftInfo))
                .thenReturn(swiftInfoNonRelated);

        //Act
        String result =
                swiftInfoService.createSwiftInfo(newSwiftInfo);

        //Assert
        assertEquals(
                swiftNonRelated + " saved successfully",
                result
        );
    }

    @Test
    void createSwiftInfo_ErrorDuringSave() {
        //Arrenge
        when(swiftInfoRepository.save(swiftInfoNonRelated))
                .thenThrow(new RuntimeException("Error during save"));

        when(swiftInfoMapper.fromAddSwiftInfoDTO(newSwiftInfo))
                .thenReturn(swiftInfoNonRelated);

        //Act
        String result =
                swiftInfoService.createSwiftInfo(newSwiftInfo);

        //Assert
        assertEquals(
                "Error while saving swift information",
                result
        );
    }

    @Test
    void deleteSwiftInfo() {
        //Arrenge
        doNothing().when(swiftInfoRepository).deleteById(swiftNonRelated);

        //Act
        String result =
                swiftInfoService.deleteSwiftInfo(swiftNonRelated);

        //Assert
        assertEquals(
                "Swift information deleted successfully",
                result
        );
    }

    @Test
    void deleteSwiftInfo_ErrorDuringDelete() {
        //Arrenge
        doThrow(new RuntimeException("something error"))
                .when(swiftInfoRepository)
                .deleteById(swiftNonRelated);

        //Act
        String result =
                swiftInfoService.deleteSwiftInfo(swiftNonRelated);

        //Assert
        assertEquals(
                "Error while deleting swift information",
                result
        );
    }
}

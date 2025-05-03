package org.example.swiftapi;

import org.example.swiftapi.dtos.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SwiftInfoMapper {
    public ShortSwiftInfoDTO toShortSwiftInfoDTO(
            SwiftInfo swiftInfo
    ) {
        var shortDTO = new ShortSwiftInfoDTO();
        shortDTO.setAddress(swiftInfo.getAddress());
        shortDTO.setBankName(swiftInfo.getBankName());
        shortDTO.setCountryISO2(swiftInfo.getCountryISO2());
        shortDTO.setHeadquarter(swiftInfo.isHeadquarter());
        shortDTO.setSwiftCode(swiftInfo.getSwiftCode());
        return shortDTO;
    }

    public SwiftInfoCountryResponseDTO toCountryResponseDTO(
            List<SwiftInfo> swiftInfosRelatedWithCountry,
            String countryISO2,
            String countryName
    ) {
        var countryResponseDTO = new SwiftInfoCountryResponseDTO();
        countryResponseDTO.setCountryISO2(countryISO2);
        countryResponseDTO.setCountryName(countryName);
        List<ShortSwiftInfoDTO> shortSwiftInfoDTOS =
                swiftInfosRelatedWithCountry.stream().map(this::toShortSwiftInfoDTO).toList();
        countryResponseDTO.setSwiftCodes(shortSwiftInfoDTOS);
        return countryResponseDTO;
    }

    public SwiftInfoBranchResponseDTO toBranchResponseDTO(
            SwiftInfo branchSwiftInfo
    ) {
        var branchResponseDTO = new SwiftInfoBranchResponseDTO();

        branchResponseDTO.setAddress(branchSwiftInfo.getAddress());
        branchResponseDTO.setBankName(branchSwiftInfo.getBankName());
        branchResponseDTO.setCountryISO2(branchSwiftInfo.getCountryISO2());
        branchResponseDTO.setCountryName(branchSwiftInfo.getCountryName());
        branchResponseDTO.setHeadquarter(branchSwiftInfo.isHeadquarter());
        branchResponseDTO.setSwiftCode(branchSwiftInfo.getSwiftCode());
        return branchResponseDTO;
    }

    public SwiftInfoHeadquarterResponseDTO toHeadquarterResponseDTO(
            SwiftInfo headquarterSwiftInfo,
            List<SwiftInfo> swiftInfosRelatedWithHeadquarter
    ) {
        var headquarterResponseDTO = new SwiftInfoHeadquarterResponseDTO();
        headquarterResponseDTO.setAddress(headquarterSwiftInfo.getAddress());
        headquarterResponseDTO.setBankName(headquarterSwiftInfo.getBankName());
        headquarterResponseDTO.setCountryISO2(headquarterSwiftInfo.getCountryISO2());
        headquarterResponseDTO.setCountryName(headquarterSwiftInfo.getCountryName());
        headquarterResponseDTO.setHeadquarter(headquarterResponseDTO.isHeadquarter());
        headquarterResponseDTO.setSwiftCode(headquarterSwiftInfo.getSwiftCode());
        var branches = swiftInfosRelatedWithHeadquarter.stream()
                .map(this::toShortSwiftInfoDTO)
                .toList();
        headquarterResponseDTO.setBranches(branches);
        return headquarterResponseDTO;
    }

    public SwiftInfo fromAddSwiftInfoDTO(
            AddSwiftInfoRequestDTO addSwiftInfoRequestDTO
    ) {
        SwiftInfo swiftInfo = new SwiftInfo();
        swiftInfo.setAddress(addSwiftInfoRequestDTO.getAddress());
        swiftInfo.setBankName(addSwiftInfoRequestDTO.getBankName());
        swiftInfo.setCountryISO2(addSwiftInfoRequestDTO.getCountryISO2());
        swiftInfo.setCountryName(addSwiftInfoRequestDTO.getCountryName());
        swiftInfo.setSwiftCode(addSwiftInfoRequestDTO.getSwiftCode());
        return swiftInfo;
    }
}

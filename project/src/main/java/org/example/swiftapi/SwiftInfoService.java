package org.example.swiftapi;

import org.example.swiftapi.dtos.AddSwiftInfoRequestDTO;
import org.example.swiftapi.dtos.SwiftInfoBranchResponseDTO;
import org.example.swiftapi.dtos.SwiftInfoCountryResponseDTO;
import org.example.swiftapi.dtos.SwiftInfoUniversalResponseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SwiftInfoService {
    private final SwiftInfoRepository swiftInfoRepository;
    private final SwiftInfoMapper swiftInfoMapper;

    public SwiftInfoService(SwiftInfoRepository swiftInfoRepository, SwiftInfoMapper swiftInfoMapper) {
        this.swiftInfoRepository = swiftInfoRepository;
        this.swiftInfoMapper = swiftInfoMapper;
    }

    private Optional<SwiftInfoUniversalResponseDTO> findHeadquarterSwiftInfo(String swiftCode) {
        var swiftCode8 = swiftCode.substring(0, 8);
        List<SwiftInfo> relatedWithHQ = swiftInfoRepository.findBySwiftCode8(swiftCode8);
        if (relatedWithHQ.isEmpty()) {
            return Optional.empty();
        }
        SwiftInfo hq = null;
        List<SwiftInfo> branches = new ArrayList<>();
        for (SwiftInfo swiftInfo : relatedWithHQ) {
            if(swiftInfo.getSwiftCode().equals(swiftCode)) {
                hq = swiftInfo;
            }
            else {
                branches.add(swiftInfo);
            }
        }
        assert hq != null;
        var response = swiftInfoMapper.toHeadquarterResponseDTO(
                hq,
                branches
        );
        return Optional.of(response);
    }

    private Optional<SwiftInfoUniversalResponseDTO> findBranchSwiftInfo(String swiftCode) {
        var relatedWithSwiftCode = swiftInfoRepository.findBySwiftCode(swiftCode);
        if (relatedWithSwiftCode.size() != 1) {
            return Optional.empty();
        }

        SwiftInfo swiftInfo = relatedWithSwiftCode.getFirst();
        SwiftInfoBranchResponseDTO responseDTO = swiftInfoMapper.toBranchResponseDTO(
                swiftInfo
        );
        return Optional.of(responseDTO);
    }

    public Optional<SwiftInfoUniversalResponseDTO> findSwiftInfo(String swiftCode) {
        if (swiftCode == null || swiftCode.length() != 11) {
            return Optional.empty();
        }

        swiftCode = swiftCode.toUpperCase();

        SwiftInfoUniversalResponseDTO info;
        if (swiftCode.startsWith("XXX", 8)) {
            return this.findHeadquarterSwiftInfo(swiftCode);
        }
        return this.findBranchSwiftInfo(swiftCode);
    }

    public Optional<SwiftInfoCountryResponseDTO> findSwiftInfosRelatedWithCountry(String countryISO2code) {
        if (countryISO2code == null || countryISO2code.length() != 2) {
            return Optional.empty();
        }
        countryISO2code = countryISO2code.toUpperCase();

        var relatedWithCountry = swiftInfoRepository.findByCountryISO2(countryISO2code);
        if (relatedWithCountry.isEmpty()) {
            return Optional.empty();
        }
        var countryName = relatedWithCountry.getFirst().getCountryName();
        var responseDTO = swiftInfoMapper.toCountryResponseDTO(
                relatedWithCountry,
                countryISO2code,
                countryName
        );
        return Optional.of(responseDTO);
    }

    public String createSwiftInfo(AddSwiftInfoRequestDTO addSwiftInfoRequestDTO) {
        SwiftInfo newSwiftInfo = swiftInfoMapper.fromAddSwiftInfoDTO(addSwiftInfoRequestDTO);
        try {
            swiftInfoRepository.save(newSwiftInfo);
            return newSwiftInfo.getSwiftCode() + " saved successfully";
        }
        catch (Exception e) {
            return "Error while saving swift information";
        }
    }

    public String deleteSwiftInfo(String swiftCode) {
        try {
            swiftInfoRepository.deleteById(swiftCode);
            return "Swift information deleted successfully";
        } catch (Exception e) {
            return "Error while deleting swift information";
        }
    }
}

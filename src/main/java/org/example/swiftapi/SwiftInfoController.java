package org.example.swiftapi;

import org.example.swiftapi.dtos.AddSwiftInfoRequestDTO;
import org.example.swiftapi.dtos.OnlyMessageResponseDTO;
import org.example.swiftapi.dtos.SwiftInfoCountryResponseDTO;
import org.example.swiftapi.dtos.SwiftInfoUniversalResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftInfoController {
    private final SwiftInfoService swiftInfoService;

    public SwiftInfoController(SwiftInfoService swiftInfoService) {
        this.swiftInfoService = swiftInfoService;
    }

    @GetMapping("/{swift-code}")
    public ResponseEntity<SwiftInfoUniversalResponseDTO> getSwiftInfo(
            @PathVariable("swift-code") String swiftCode
    ) {
        if (swiftCode.length() != 11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Optional<SwiftInfoUniversalResponseDTO> responseDTO =
                swiftInfoService.findSwiftInfo(swiftCode);

        return responseDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("country/{countryISO2code}")
    public ResponseEntity<SwiftInfoCountryResponseDTO> getCountrySwiftInfo(
            @PathVariable("countryISO2code") String countryISO2code
    ) {
        if (countryISO2code.length() != 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Optional<SwiftInfoCountryResponseDTO> responseDTO =
                swiftInfoService.findSwiftInfosRelatedWithCountry(countryISO2code);
        return responseDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OnlyMessageResponseDTO> createSwiftInfo(
            @RequestBody AddSwiftInfoRequestDTO addSwiftInfoRequestDTO
    ) {
        if (!addSwiftInfoRequestDTO.isCorrectSwiftInfo()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String response = swiftInfoService.createSwiftInfo(addSwiftInfoRequestDTO);
        OnlyMessageResponseDTO responseDTO = new OnlyMessageResponseDTO();
        responseDTO.setMessage(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @DeleteMapping("/{swift-code}")
    public ResponseEntity<OnlyMessageResponseDTO> deleteSwiftInfo(@PathVariable("swift-code") String swiftCode) {
        if (swiftCode.length() != 11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String response = swiftInfoService.deleteSwiftInfo(swiftCode);
        OnlyMessageResponseDTO responseDTO = new OnlyMessageResponseDTO();
        responseDTO.setMessage(response);
        return ResponseEntity.ok(responseDTO);
    }
}

package org.example.swiftapi;

import org.example.swiftapi.dtos.SwiftInfoUniversalResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class SwiftInfoController {
    private final SwiftInfoService swiftInfoService;

    public SwiftInfoController(SwiftInfoService swiftInfoService) {
        this.swiftInfoService = swiftInfoService;
    }

    @GetMapping("/swift-codes/{swift-code}")
    public ResponseEntity<SwiftInfoUniversalResponseDTO> getSwiftInfo(
            @PathVariable("swift-code") String swiftCode
    ) {
        Optional<SwiftInfoUniversalResponseDTO> responseDTO =
                swiftInfoService.findSwiftInfo(swiftCode);

        return responseDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

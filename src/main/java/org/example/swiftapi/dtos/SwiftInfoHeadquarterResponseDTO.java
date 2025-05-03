package org.example.swiftapi.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SwiftInfoHeadquarterResponseDTO extends SwiftInfoUniversalResponseDTO {
    private List<ShortSwiftInfoDTO> branches;
}

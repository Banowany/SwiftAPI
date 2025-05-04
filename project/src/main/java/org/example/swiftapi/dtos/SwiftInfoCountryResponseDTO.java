package org.example.swiftapi.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SwiftInfoCountryResponseDTO {
    private String countryISO2;
    private String countryName;
    private List<ShortSwiftInfoDTO> swiftCodes;
}

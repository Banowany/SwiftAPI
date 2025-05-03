package org.example.swiftapi.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ShortSwiftInfoDTO {
    private String address;
    private String bankName;
    private String countryISO2;
    private boolean isHeadquarter;
    private String swiftCode;
}

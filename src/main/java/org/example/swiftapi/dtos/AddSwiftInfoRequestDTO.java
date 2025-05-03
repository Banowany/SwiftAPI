package org.example.swiftapi.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddSwiftInfoRequestDTO {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private String isHeadquarter;
    private String swiftCode;
}

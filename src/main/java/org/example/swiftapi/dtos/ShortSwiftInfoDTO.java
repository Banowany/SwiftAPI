package org.example.swiftapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ShortSwiftInfoDTO {
    private String address;
    private String bankName;
    private String countryISO2;
    @JsonProperty("isHeadquarter")
    private boolean isHeadquarter;
    private String swiftCode;
}

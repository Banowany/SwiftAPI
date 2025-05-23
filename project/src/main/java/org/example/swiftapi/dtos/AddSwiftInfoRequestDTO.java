package org.example.swiftapi.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class AddSwiftInfoRequestDTO {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
    private String swiftCode;

    public boolean isCorrectSwiftInfo() {
        return address != null
                && bankName != null
                && countryISO2 != null
                && countryName != null
                && swiftCode != null
                && swiftCode.length() == 11
                && countryISO2.length() == 2;
    }

    public void normalizeSwiftInfo() {
        address = address.toUpperCase();
        bankName = bankName.toUpperCase();
        countryISO2 = countryISO2.toUpperCase();
        countryName = countryName.toUpperCase();
        swiftCode = swiftCode.toUpperCase();
    }
}

package org.example.swiftapi;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.springframework.stereotype.Indexed;

@Entity
@Table(name = "swift_infos", indexes = {
        @Index(name = "idx_swiftCode8", columnList = "swiftCode8"),
        @Index(name = "idx_countryISO2", columnList = "countryISO2")
})
public class SwiftInfo {
    @Id
    private String swiftCode;
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
    private String swiftCode8;
}

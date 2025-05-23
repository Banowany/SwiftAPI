package org.example.swiftapi;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "swift_infos", indexes = {
        @Index(name = "idx_swiftCode8", columnList = "swiftCode8"),
        @Index(name = "idx_countryISO2", columnList = "countryISO2"),
        @Index(name = "idx_swiftCode", columnList = "swiftCode")
})
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SwiftInfo {
    @Id
    @NotNull
    @Size(min = 11, max = 11)
    private String swiftCode;
    private String address;
    private String bankName;
    @NotNull
    @Size(min = 2, max = 2)
    private String countryISO2;
    private String countryName;
    private String swiftCode8;

    public boolean isHeadquarter() {
        return swiftCode != null && swiftCode.startsWith("XXX", 8);
    }
}

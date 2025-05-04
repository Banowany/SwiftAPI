package org.example.swiftapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwiftInfoRepository extends JpaRepository<SwiftInfo, String> {
    List<SwiftInfo> findBySwiftCode8(String swiftCode8);
    List<SwiftInfo> findByCountryISO2(String countryISO2);
    List<SwiftInfo> findBySwiftCode(String swiftCode);
}

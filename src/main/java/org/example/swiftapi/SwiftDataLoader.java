package org.example.swiftapi;

import com.opencsv.CSVReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.util.List;

@Component
public class SwiftDataLoader implements CommandLineRunner {
    private final SwiftInfoRepository swiftInfoRepository;

    public SwiftDataLoader(SwiftInfoRepository swiftInfoRepository) {
        this.swiftInfoRepository = swiftInfoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (swiftInfoRepository.count() > 0) {
            System.out.println("SWIFT data already exists, skipping CSV load.");
            return;
        }

        try (CSVReader reader = new CSVReader(new InputStreamReader(
                new ClassPathResource("swift.csv").getInputStream()))) {

            List<String[]> rows = reader.readAll();
            rows.removeFirst(); // usuń nagłówek

            for (String[] row : rows) {
                SwiftInfo info = new SwiftInfo();
                info.setCountryISO2(row[0]);
                info.setSwiftCode(row[1]);
                info.setBankName(row[3]);
                info.setAddress(row[4]);
                info.setCountryName(row[6]);
                info.setSwiftCode8(row[1].substring(0, 8));

                swiftInfoRepository.save(info);
            }

            System.out.println("SWIFT data loaded: " + rows.size() + " entries.");
        }
    }
}

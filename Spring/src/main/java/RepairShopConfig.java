import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.ComputerRepairRequestRepository;
import repository.ComputerRepairedFormRepository;
import repository.jdbc.ComputerRepairRequestJdbcRepository;
import repository.jdbc.ComputerRepairedFormJdbcRepository;
import services.ComputerRepairServices;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class RepairShopConfig {

    @Bean
    public Properties getProps() {
        Properties props = new Properties();
        try {
            System.out.println("Searching bd.config in directory "+((new File(".")).getAbsolutePath()));
            props.load(new FileReader("bd.config"));
        }catch (IOException e) {
            System.err.println("Configuration file bd.config not found" + e);
        }
        return props;
    }

    @Bean
    public ComputerRepairRequestRepository requestsRepo() {
        return new ComputerRepairRequestJdbcRepository(getProps());
    }

    @Bean
    public ComputerRepairedFormRepository formsRepo() {
        return new ComputerRepairedFormJdbcRepository(getProps());
    }

    @Bean
    public ComputerRepairServices services() {
        return new ComputerRepairServices(requestsRepo(), formsRepo());
    }
}
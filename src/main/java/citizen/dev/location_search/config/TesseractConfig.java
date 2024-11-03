package citizen.dev.location_search.config;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class TesseractConfig {
    @Value("${tesseract.data.path}")
    private String tessdataPath;

    @Bean
    public Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();
        // Specify the full path to the language data file
        if (StringUtils.isEmpty(System.getenv("TESSDATA_PREFIX"))) {
            // Set the datapath to the default value or a specific value
            tesseract.setDatapath("src/java/resources/tessdata");
            tesseract.setLanguage("deu.traineddata");
        }
        // Additional configurations if needed
        return tesseract;
    }
}

package citizen.dev.location_search.services;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadService {

    private final Tesseract tesseract;

    @Value("${upload.dir}")
    private String uploadDir;

    public FileUploadService(Tesseract tesseract) {
        this.tesseract = tesseract;
    }

    public String uploadAndProcessFile(MultipartFile file) throws IOException {
        // Create a unique filename for the uploaded file
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Save the uploaded file to the server
        saveFile(file, fileName);

        // Extract text from the uploaded file using Tesseract
        String extractedText = processFile(fileName);

        String cleanedText = cleanExtractedText(extractedText);


        return cleanedText;
    }

    private void saveFile(MultipartFile file, String fileName) throws IOException {
        // Ensure the upload directory exists
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save the file to the upload directory
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath);
    }

    private String processFile(String fileName) throws IOException {
        try {
            // Read the file and extract text using Tesseract
            File imageFile = new File(uploadDir + "/" + fileName);
            String extractedText = tesseract.doOCR(imageFile);
            return extractedText;
        } catch (TesseractException e) {
            // Handle Tesseract exception
            throw new IOException("Error extracting text from file: " + e.getMessage());
        }
    }

    private String cleanExtractedText(String extractedText) {
        extractedText = extractedText.replaceAll("[,.\\-+#/()&%>|:]", "");

        extractedText = extractedText.replaceAll("^[\\d\\W]+", "");

        // Trim leading and trailing spaces
        extractedText = extractedText.trim();

        return extractedText;
    }
}
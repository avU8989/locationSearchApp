package citizen.dev.location_search.controller.ocr;

import citizen.dev.location_search.services.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")  // only for testing locally with react
public class OCRController {
    @Autowired
    private FileUploadService fileUploadService;
    private static final Logger logger = LoggerFactory.getLogger(OCRController.class);

    @PostMapping("/image")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            logger.info("uploading");
            // Call the file upload service to process the uploaded file
            String extractedText = fileUploadService.uploadAndProcessFile(file);

            return ResponseEntity.status(HttpStatus.OK).body(extractedText);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process file: " + e.getMessage());
        }
    }
}

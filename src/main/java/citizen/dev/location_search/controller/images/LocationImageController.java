package citizen.dev.location_search.controller.images;

import citizen.dev.location_search.services.WikidataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "http://localhost:3000")  // only for testing locally with react
public class LocationImageController {

    @Autowired
    private WikidataService wikidataService;

    @GetMapping("/ger")
    public ResponseEntity<List<String[]>> fetchImage(@RequestParam String text) {
        List<String[]> imageUrls = wikidataService.fetchImagesByText(text);

        if (imageUrls == null || imageUrls.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        return new ResponseEntity<>(imageUrls, HttpStatus.OK);
    }

    @GetMapping("/eng")
    public ResponseEntity<List<String[]>> fetchImageEng(@RequestParam String text) {
        List<String[]> imageUrls = wikidataService.fetchImagesByTextEnglisch(text);

        if (imageUrls == null) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        if (imageUrls.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(imageUrls, HttpStatus.OK);
    }
}

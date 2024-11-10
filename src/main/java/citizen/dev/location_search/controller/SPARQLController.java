package citizen.dev.location_search.controller;

import citizen.dev.location_search.services.LocationService;
import citizen.dev.location_search.services.SparqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sparql")
@CrossOrigin(origins = "http://localhost:3000")  // only for testing locally with react
public class SPARQLController {
    @Autowired
    private LocationService locationService;

    @Autowired
    private SparqlService sparqlService;

    @GetMapping("/dbpedia/subjects")
    public ResponseEntity<String> getSubjectsFromDbpedia(@RequestParam String subjectName) {
        String subject = sparqlService.fetchSingleSubjectFromDbpedia(subjectName);
        if (subject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(subject);
    }

    @GetMapping("/dbpedia/subject/abstract")
    public ResponseEntity<String> fetchSubjectAbstract(@RequestParam String locationName) {
        String abstractFromSubject = sparqlService.fetchSubjectInfo(locationName);

        if (abstractFromSubject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        String cleanAbstract = abstractFromSubject.replaceFirst("^Abstract:\\s*", "").replaceFirst("@[\\w-]+$", "").trim();
        return ResponseEntity.ok(cleanAbstract);
    }

    @GetMapping("/dbpedia/subjectWithResource/abstract")
    public ResponseEntity<String> fetchAbstractWithResource(@RequestParam String locationName) {
        String abstractFromSubject = sparqlService.fetchAbstractWithResource(locationName);

        if (abstractFromSubject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        String cleanAbstract = abstractFromSubject.replaceFirst("^Abstract:\\s*", "").replaceFirst("@[\\w-]+$", "").trim();
        return ResponseEntity.ok(cleanAbstract);
    }

    @GetMapping("/dbpedia/subject/comment")
    public ResponseEntity<String> fetchSubjectComment(@RequestParam String locationName) {
        String abstractFromSubject = sparqlService.fetchSubjectComment(locationName);

        if (abstractFromSubject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        String cleanAbstract = abstractFromSubject.replaceFirst("^Comment:\\s*", "").replaceFirst("@[\\w-]+$", "").trim();
        return ResponseEntity.ok(cleanAbstract);
    }

}

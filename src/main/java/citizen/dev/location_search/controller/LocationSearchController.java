package citizen.dev.location_search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocationSearchController {

    @GetMapping("/locationSearch")
    public String locationSearch() {
        return "index.html";
    }
}

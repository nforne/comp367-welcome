package nforne;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalTime;

@RestController
public class GreetingController {

    @GetMapping("/")
    public String greet(@RequestParam(required = false) Integer hourOverride) {
        // "to display "Good morning, <your name (e.g., John)>, Welcome to COMP367"; 
        // if it is the afternoon then it should display "Good afternoon, <your name (e.g., John)>, Welcome to COMP367 ""
        int hour = (hourOverride != null) ? hourOverride : LocalTime.now().getHour();
        String part = (hour < 12) ? "Good morning" : "Good afternoon";
        return String.format("%s, Martin, Welcome to COMP367", part);
    }
}

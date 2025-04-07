package uk.org.blackamber.animals;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

//import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(produces = "application/json")
public class AnimalController {
	
    @Autowired
    private final AnimalLogic animalLogic;
    AnimalController (AnimalLogic animalLogic) {
    	this.animalLogic = animalLogic;
    }
    
    @GetMapping(value = "/animals/cat")
    public String getCats(@RequestParam(value = "num", defaultValue = "1") int num) throws URISyntaxException, IOException, InterruptedException {
    	//call Animal Logic.getandsave https://api.thecatapi.com/v1/images/search
    	String uri = "https://api.thecatapi.com/v1/images/search";
    	this.animalLogic.getandsave(uri, num);    
        return ("Grabbed " + num +" images of cats, stored in the database");
    }
    
    @GetMapping(value = "/animals/dog")
    public String getDogs(@RequestParam(value = "num", defaultValue = "1") int num) throws URISyntaxException, IOException, InterruptedException {
    	//call Animal Logic.getandsave https://random.dog/woof.json
    	String uri = "https://random.dog/woof.json";
    	this.animalLogic.getandsave(uri, num);
        return ("Grabbed " + num +" image of dogs and stored in the database");
    }
    
    @GetMapping(value = "/animals/lastimage")
    public ResponseEntity<Resource> lastimage() {
    	return animalLogic.returnLastImageLocation();
    }
    
    @GetMapping(value = "/animals")
    public String helloWorld() {
        return "Hello World! Welcome to animal pictures! Enjoy!";
    }
}
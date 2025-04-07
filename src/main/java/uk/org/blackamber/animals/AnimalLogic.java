package uk.org.blackamber.animals;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Repository
public class AnimalLogic {
	
	private final List<Animal> animals;
	
    AnimalLogic() {
            this.animals = new ArrayList<>();
    }
    
    public boolean getandsave(String uri, int num) throws InterruptedException, IOException {
		
    	for (int i = 0; i < num; i++) {
    	    
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());
            
            if (root.isArray()) {
                ArrayNode array = (ArrayNode) root;
                for (JsonNode item : array) {
                    extractFilenameFromUrl(item);
                }
            } else {
                extractFilenameFromUrl(root);
            }
    	}
    	return true;
    }
    
    public ResponseEntity<Resource> returnLastImageLocation() {
    	try {
    		Animal lastAnimal = animals.get(animals.size()-1);
    		String lastFile = lastAnimal.getFilename();
        	// TODO return the file, rather than the filename
    		//return (lastFile);
    		String currentDir = System.getProperty("user.dir");
    		System.out.println("last Filename from list: " + currentDir + " " + lastFile);
    		Path imagePath = Paths.get(currentDir).resolve(lastFile).normalize();
    		Resource resource = new UrlResource(imagePath.toUri());
    		
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            
            String contentType = Files.probeContentType(imagePath);
            MediaType mediaType = MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream");

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
            
    	} catch (Exception e) {
    		return ResponseEntity.internalServerError().build();
    	}	
    }

	public void saveImage(URL url, String filename) throws IOException {
		InputStream in = new BufferedInputStream(url.openStream());
		OutputStream out = new BufferedOutputStream(new FileOutputStream(filename));

	    byte[] buffer = new byte[1024];
	    int length;
	
	    while ((length = in.read(buffer)) != -1) {
	        out.write(buffer, 0, length);
	    }

	    in.close();
    	out.close();
	}
	
    public void extractFilenameFromUrl(JsonNode node) {
        if (node.has("url")) {
            String fileUrl = node.get("url").asText();
            try {
                URL fileURL = new URL(fileUrl);
                String fileName = fileURL.getPath().substring(fileURL.getPath().lastIndexOf('/') + 1);
                System.out.println("Filename from URL: " + fileName);
                animals.add(new Animal(fileName));
                saveImage(fileURL, fileName);
            } catch (Exception e) {
                System.err.println("Invalid URL: " + fileUrl);
            }
        } else {
            System.out.println("No 'url' field found in JSON object.");
        }
    }
    
}

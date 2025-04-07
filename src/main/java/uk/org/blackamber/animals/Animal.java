package uk.org.blackamber.animals;
public class Animal {
    // Define a filename for the animal
    String filename;

    // Constructor to initialise the ID and filename
    public Animal(String filename) {
        this.filename = filename;
    }

    // Getter for filename
    public String getFilename() {
        return filename;
    }

    // Setter for filename
    public void setFilename(String filename) {
        this.filename = filename;
    }
}

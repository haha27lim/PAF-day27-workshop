package sg.edu.nus.iss.app.day27workshop.models;

import java.time.LocalDateTime;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditedReview {
    
    private String comment;
    private Integer rating;
    private LocalDateTime posted;
    
    // Method to convert EditedReview object to JSON object
    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("comment", this.getComment())
                .add("rating", getRating())
                .add("posted", getPosted().toString())
                .build();
    }
}

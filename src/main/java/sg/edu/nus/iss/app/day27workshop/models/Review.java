package sg.edu.nus.iss.app.day27workshop.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    
    private ObjectId id;
    private String user;
    private Integer rating;
    private String comment;
    private Integer gid;
    private LocalDateTime posted;
    private String boardgame;
    private List<EditedReview> edited; // List of edited reviews
    private Boolean isEdited;
    private LocalDateTime timestamp;
    
    
    public Review(String user, Integer rating, String comment, Integer gid, String boardgame) {
        this.user = user;
        this.rating = rating;
        this.comment = comment;
        this.gid = gid;
        this.boardgame = boardgame;
        this.posted = LocalDateTime.now();
    }

    // Static method to create a new Review object from a MongoDB Document object
    public static Review create(Document d) {
        // Create a new Review object
        Review g = new Review();
        g.setId(d.getObjectId("_id")); // Set the ID using the value from the Document object
        g.setUser(d.getString("user"));
        g.setRating(d.getInteger("rating"));
        g.setComment(d.getString("comment"));
        g.setGid(d.getInteger("gid"));
        // Convert the "posted" field from a Date to a LocalDateTime object
        LocalDateTime postedDt = Instant.ofEpochMilli(d.getDate("posted").getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        g.setPosted(postedDt); // Set the posted date/time using the converted value
        g.setBoardgame(d.getString("name"));

        // Return the new Review object
        return g;
    }

    // Method to convert the Review object to a JSON object
    public JsonObject toJSON(boolean switchProp) {
        // Create a list of JSON objects representing the edited reviews
        List<JsonObject> js = this.getEdited()
                .stream()
                .map(comment -> comment.toJSON())
                .toList();
        // Create a JSON object builder
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("_id", this.getId().toString());
        builder.add("user", this.getUser());
        builder.add("rating", getRating());
        builder.add("comment", getComment());
        builder.add("gid", getGid());
        builder.add("posted", getPosted().toString());
        builder.add("name", getBoardgame());
        // Add the "edited" field to the JSON object, depending on the value of the "switchProp" parameter
        if (switchProp) {
            if (getIsEdited() != null)
                builder.add("edited", getIsEdited());
        } else {
            builder.add("edited", js.toString());
        }

        return builder.build();
    }
}

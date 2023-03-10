package sg.edu.nus.iss.app.day27workshop.controller;

import java.util.NoSuchElementException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.nus.iss.app.day27workshop.models.EditedReview;
import sg.edu.nus.iss.app.day27workshop.models.Review;
import sg.edu.nus.iss.app.day27workshop.services.ReviewServices;

@RestController
public class ReviewRestController {
    
    @Autowired
    private ReviewServices reviewSvc;

    // This method is responsible for handling PUT requests for updating reviews
    @PutMapping(path="/review/{id}", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateReview(@PathVariable("id") ObjectId id, @RequestBody EditedReview json) {
        JsonObject result = null;
        try {
            // Call the updateEdits method of ReviewServices to update the review
            Review review = reviewSvc.updateEdits(id, json);
            // Build a JSON object for the review and its attributes
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("review", review.toJSON(false));
            result = builder.build();

            // Return an HTTP response with the JSON object as its body
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        } catch (NoSuchElementException e) {
            // Return a 404 Not Found error if the review with the given ID is not found
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\": \"Review not found\"}");
        }
    }

    // This method is responsible for handling GET requests for getting a single review by ID
    @GetMapping(path="/review/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getReviewById(@PathVariable("id") ObjectId id) {
        JsonObject result = null;
        // Call the getReview method of ReviewServices to retrieve the review
        Review review = reviewSvc.getReview(id);
        // Build a JSON object for the review and its attributes
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("review", review.toJSON(true));
        result = builder.build();

        // Return an HTTP response with the JSON object as its body
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    // This method is responsible for handling GET requests for getting the edit history of a review by ID
    @GetMapping(path="{id}/history", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getReviewHistory(@PathVariable ObjectId id) {
        JsonObject result = null;
        // Call the getReview method of ReviewServices to retrieve the review
        Review review = reviewSvc.getReview(id);
        // Build a JSON object for the review and its attributes
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("review", review.toJSON(false));
        result = builder.build();

        // Return an HTTP response with the JSON object as its body
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }
}

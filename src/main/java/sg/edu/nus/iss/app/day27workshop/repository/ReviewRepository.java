package sg.edu.nus.iss.app.day27workshop.repository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.app.day27workshop.models.EditedReview;
import sg.edu.nus.iss.app.day27workshop.models.Review;

import static sg.edu.nus.iss.app.day27workshop.Constants.*;


@Repository
public class ReviewRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    // Method to insert a new Review document in the database
    public Review insertReview(Review review) {
        return mongoTemplate.insert(review, REVIEWS_COLLECTION);
    }

    // Method to update an existing review with edited comments
    public Review updateEdits(ObjectId id, EditedReview comment) {
        // Retrieve the review object by its ID
        Review review = mongoTemplate.findById(id, Review.class, REVIEWS_COLLECTION);
        // If the review is found, create a new EditedReview object with the edited comment details
        if (review != null) {
            EditedReview edit = new EditedReview();
            edit.setComment(comment.getComment());
            edit.setRating(comment.getRating());
            edit.setPosted(LocalDateTime.now());
            // If the review already has edited comments, add the new edit to the list
            if (review.getEdited() != null) {
                review.getEdited().add(edit);
            } else { 
                // Otherwise, create a new list with the edited comment and set it to the review object
                List<EditedReview> list   = new LinkedList<EditedReview>();
                list.add(edit);
                review.setEdited(list);
            }
            // Save the updated review object to the database
            mongoTemplate.save(review, REVIEWS_COLLECTION);
        }
        // Return the updated review object
        return review;
    }
    
    // Method to retrieve a Review document from the database by its ID
    public Review getReviewById(ObjectId id) {
        return mongoTemplate.findById(id, Review.class, REVIEWS_COLLECTION);
    }
    

    
}

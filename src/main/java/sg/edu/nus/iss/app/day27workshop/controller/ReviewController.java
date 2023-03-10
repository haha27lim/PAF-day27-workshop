package sg.edu.nus.iss.app.day27workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import sg.edu.nus.iss.app.day27workshop.models.Review;
import sg.edu.nus.iss.app.day27workshop.services.ReviewServices;


@Controller
public class ReviewController {
    
    @Autowired
    private ReviewServices reviewSvc;

    // Define a POST endpoint for adding a review to the database
    @PostMapping(path="/review", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addReview(@RequestBody MultiValueMap<String, String> form, Model model) {
        // Extract review information from the form data
        String username = form.getFirst("user");
        String rating = form.getFirst("rating");
        String comment = form.getFirst("comment");
        String gid = form.getFirst("gid");
        String boardGameName = form.getFirst("name");
        // Create a new review object
        Review review = new Review(username, Integer.parseInt(rating), comment, Integer.parseInt(gid), boardGameName);
        // Insert the new review into the database
        Review insertedRv = reviewSvc.insertReview(review);
        // Add the inserted review object to the model attribute
        model.addAttribute("review", insertedRv);
        // Return the name of the success view to display
        return "success";
    }
}

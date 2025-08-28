package com.telusko.quizservice.controller;

import com.telusko.quizservice.model.QuestionWrapper;
import com.telusko.quizservice.model.QuizDto;
import com.telusko.quizservice.model.Response;
import com.telusko.quizservice.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
@Tag(name = "Quiz Management", description = "APIs for managing quiz operations")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    @Operation(summary = "Create a new quiz", description = "Creates a new quiz with specified category, number of questions, and title")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Quiz created successfully",
            content = @Content(schema = @Schema(example = "Quiz created successfully"))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> createQuiz(
        @Parameter(description = "Quiz details including category, number of questions, and title", required = true)
        @RequestBody QuizDto quizDto){
        return quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle());
    }

    @PostMapping("get/{id}")
    @Operation(summary = "Get quiz questions", description = "Retrieves all questions for a specific quiz by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Questions retrieved successfully",
            content = @Content(schema = @Schema(implementation = QuestionWrapper.class))),
        @ApiResponse(responseCode = "404", description = "Quiz not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(
        @Parameter(description = "ID of the quiz to retrieve questions for", required = true)
        @PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    @Operation(summary = "Submit quiz answers", description = "Submits quiz answers and calculates the final score")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Quiz submitted successfully",
            content = @Content(schema = @Schema(example = "85"))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Quiz not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Integer> submitQuiz(
        @Parameter(description = "ID of the quiz to submit", required = true)
        @PathVariable Integer id, 
        @Parameter(description = "List of student responses to quiz questions", required = true)
        @RequestBody List<Response> responses){
        return quizService.calculateResult(id, responses);
    }


}

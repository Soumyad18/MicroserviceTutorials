package com.telusko.questionservice.controller;


import com.telusko.questionservice.model.Question;
import com.telusko.questionservice.model.QuestionWrapper;
import com.telusko.questionservice.model.Response;
import com.telusko.questionservice.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
@Tag(name = "Question Management", description = "APIs for managing question operations")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("allQuestions")
    @Operation(summary = "Get all questions", description = "Retrieves all questions from the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Questions retrieved successfully",
            content = @Content(schema = @Schema(implementation = Question.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    @Operation(summary = "Get questions by category", description = "Retrieves all questions for a specific category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Questions retrieved successfully",
            content = @Content(schema = @Schema(implementation = Question.class))),
        @ApiResponse(responseCode = "404", description = "Category not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Question>> getQuestionsByCategory(
        @Parameter(description = "Category name to filter questions", required = true)
        @PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    @Operation(summary = "Add a new question", description = "Adds a new question to the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Question added successfully",
            content = @Content(schema = @Schema(example = "Question added successfully"))),
        @ApiResponse(responseCode = "400", description = "Invalid question data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> addQuestion(
        @Parameter(description = "Question object to be added", required = true)
        @RequestBody Question question){
        return  questionService.addQuestion(question);
    }

    @GetMapping("generate")
    @Operation(summary = "Generate questions for quiz", description = "Generates a specified number of random questions for a given category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Question IDs generated successfully",
            content = @Content(schema = @Schema(example = "[1, 5, 12, 23, 45]"))),
        @ApiResponse(responseCode = "400", description = "Invalid parameters"),
        @ApiResponse(responseCode = "404", description = "Category not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
        @Parameter(description = "Category name for quiz questions", required = true)
        @RequestParam String categoryName, 
        @Parameter(description = "Number of questions to generate", required = true)
        @RequestParam Integer numQuestions ){
        return questionService.getQuestionsForQuiz(categoryName, numQuestions);
    }

    @PostMapping("getQuestions")
    @Operation(summary = "Get questions by IDs", description = "Retrieves questions based on a list of question IDs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Questions retrieved successfully",
            content = @Content(schema = @Schema(implementation = QuestionWrapper.class))),
        @ApiResponse(responseCode = "400", description = "Invalid question IDs"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(
        @Parameter(description = "List of question IDs to retrieve", required = true)
        @RequestBody List<Integer> questionIds){
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    @Operation(summary = "Calculate quiz score", description = "Calculates the score based on student responses")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Score calculated successfully",
            content = @Content(schema = @Schema(example = "85"))),
        @ApiResponse(responseCode = "400", description = "Invalid response data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Integer> getScore(
        @Parameter(description = "List of student responses to calculate score", required = true)
        @RequestBody List<Response> responses)
    {
        return questionService.getScore(responses);
    }


    // generate
    // getQuestions (questionid)
    // getScore

}

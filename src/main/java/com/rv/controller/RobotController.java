package com.rv.controller;

import com.rv.entities.Position;
import com.rv.service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rashmi
 */
@RestController
public class RobotController {

    @Autowired
    private RobotService robotService;

    /**
     * API endpoint for "PLACE" function.
     *
     * @param position
     * @return ResponseEntity<String>
     */
    @PostMapping("/place")
    public ResponseEntity<String> place(@RequestBody Position position) {

        try {
            robotService.place(position);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully placed Robot");
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }

    }

    /**
     * API endpoint for "MOVE" function.
     *
     * @return ResponseEntity<Mono < String>>
     */
    @PostMapping("/move")
    public ResponseEntity<String> move() {

        try {
            robotService.move();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Successfully moved Robot to location: " + robotService.report());
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    /**
     * API endpoint for "LEFT" function.
     *
     * @return ResponseEntity<Mono < String>>
     */
    @PostMapping("/left")
    public ResponseEntity<String> left() {

        try {
            robotService.left();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Successfully turned Robot to the left : " + robotService.report());
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    /**
     * API endpoint for "RIGHT" function.
     *
     * @return ResponseEntity<Mono < String>>
     */
    @PostMapping("/right")
    public ResponseEntity<String> right() {

        try {
            robotService.right();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Successfully turned robot to the left, new location is " + robotService.report());
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }


    /**
     * API endpoint for "REPORT" function to generate a final Report
     *
     * @return ResponseEntity<String>
     */
    @GetMapping("/report")
    public ResponseEntity<String> generateReport() {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(robotService.report());
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

}
package com.rv.service;

import com.rv.entities.DirectionFacingEnum;
import com.rv.entities.Position;
import com.rv.entities.RobotEntity;
import com.rv.entities.TableDimention;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RobotService {

    @Autowired
    private RobotEntity robotEntity;

   /* @Autowired
    private ValidationService validationService;*/

    @Autowired
    private TableDimention grid;


    /**
     * Handle "PLACE" command.

     * @throws IllegalArgumentException
     */
    public void place(Position position) throws IllegalArgumentException {
        Position.validate(position,grid);
        robotEntity.setPosition(position);
    }


    /**
     * Handle "REPORT" command.
     *
     * @return The location of the robot and obstacle (if any)
     * @throws IllegalArgumentException
     */
    public String report() throws IllegalArgumentException {

        // Validate robot location
        //Position.validate();

        String reportStr = "Robot is at " + robotEntity.getPosition().getXCordinate() + "," + robotEntity.getPosition().getYCordinate() + ","
                + robotEntity.getPosition().getDirectionEnum();

        return reportStr;
    }



    /**
     * Handle "MOVE" command.
     *
     * @throws IllegalArgumentException
     */
    public void move() throws IllegalArgumentException {

        // Validate the move command
       // validationService.validateMove();

        int XCor = robotEntity.getPosition().getXCordinate();
        int YCor = robotEntity.getPosition().getYCordinate();
        DirectionFacingEnum direction = robotEntity.getPosition().getDirectionEnum();

        if (direction.equals(direction.EAST)) {
            robotEntity.getPosition().setXCordinate(XCor + 1);
        } else if (direction.equals(direction.SOUTH)) {
            robotEntity.getPosition().setYCordinate(YCor - 1);
        } else if (direction.equals(direction.WEST)) {
            robotEntity.getPosition().setXCordinate(XCor - 1);
        } else if (direction.equals(direction.NORTH)) {
            robotEntity.getPosition().setYCordinate(YCor + 1);
        }
    }

    /**
     * Handle "LEFT" command.
     *
     * @throws IllegalArgumentException
     */
    public void left() throws IllegalArgumentException {

        // Validate robot location
       // validationService.validateRobotLocation();

        DirectionFacingEnum direction = robotEntity.getPosition().getDirectionEnum();

        if (direction.equals(DirectionFacingEnum.EAST)) {
            robotEntity.getPosition().setDirectionEnum(DirectionFacingEnum.NORTH);
        } else if (direction.equals(DirectionFacingEnum.SOUTH)) {
            robotEntity.getPosition().setDirectionEnum(DirectionFacingEnum.EAST);
        } else if (direction.equals(DirectionFacingEnum.WEST)) {
            robotEntity.getPosition().setDirectionEnum(DirectionFacingEnum.SOUTH);
        } else if (direction.equals(DirectionFacingEnum.NORTH)) {
            robotEntity.getPosition().setDirectionEnum(DirectionFacingEnum.WEST);
        }
    }

    /**
     * Handle "RIGHT" command.
     *
     * @throws IllegalArgumentException
     */
    public void right() throws IllegalArgumentException {

        // Validate robot location
       // validationService.validateRobotLocation();

        DirectionFacingEnum direction = robotEntity.getPosition().getDirectionEnum();

        if (direction.equals(DirectionFacingEnum.EAST)) {
            robotEntity.getPosition().setDirectionEnum(DirectionFacingEnum.SOUTH);
        } else if (direction.equals(DirectionFacingEnum.SOUTH)) {
            robotEntity.getPosition().setDirectionEnum(DirectionFacingEnum.WEST);
        } else if (direction.equals(DirectionFacingEnum.WEST)) {
            robotEntity.getPosition().setDirectionEnum(DirectionFacingEnum.NORTH);
        } else if (direction.equals(DirectionFacingEnum.NORTH)) {
            robotEntity.getPosition().setDirectionEnum(DirectionFacingEnum.EAST);
        }
    }
}


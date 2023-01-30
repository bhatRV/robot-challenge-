package com.rv.service;

import com.rv.entities.DirectionFacingEnum;
import com.rv.entities.Position;
import com.rv.entities.RobotEntity;
import com.rv.entities.TableDimention;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.rv.util.Constants.ERROR_MSG_OUT_OF_BOUNDRY;
import static com.rv.util.Constants.INVALID_DIRECTION_VALUE;
import static io.vavr.API.*;

@Service
@Slf4j
public class RobotService {

    @Autowired
    private RobotEntity robotEntity;

    @Autowired
    private TableDimention grid;


    /**
     * Initial "PLACE" command to initialize the position of the Robot on a table/Grid
     *
     * @throws IllegalArgumentException
     */
    public void place(Position position) throws IllegalArgumentException {
        Position.validate(position, grid);
        robotEntity.setPosition(position);
    }


    /**
     * Generate "REPORT" command.
     * This provides a final report of the Robot position
     *
     * @return location of the robot
     * @throws IllegalArgumentException
     */
    public String report() throws IllegalArgumentException {

        Position.validate(robotEntity.getPosition(), grid);

        return "Robot is at " + robotEntity.getPosition().getXCordinate() + "," + robotEntity.getPosition().getYCordinate() + ","
                + robotEntity.getPosition().getDirectionEnum();

    }


    /**
     * Execute "MOVE" command.
     * This method is used to Move the Robot by 1 position forward
     *
     * @throws IllegalArgumentException: in case invalid values
     */
    public void move() throws IllegalArgumentException {
        Position.validate(robotEntity.getPosition(), grid);

        int xCordn = robotEntity.getPosition().getXCordinate();
        int yCordn = robotEntity.getPosition().getYCordinate();
        DirectionFacingEnum direction = robotEntity.getPosition().getDirectionEnum();

        validateWithMove(xCordn, yCordn, direction);

        if (direction.equals(direction.EAST)) {
            robotEntity.getPosition().setXCordinate(xCordn + 1);
        } else if (direction.equals(direction.SOUTH)) {
            robotEntity.getPosition().setYCordinate(yCordn - 1);
        } else if (direction.equals(direction.WEST)) {
            robotEntity.getPosition().setXCordinate(xCordn - 1);
        } else if (direction.equals(direction.NORTH)) {
            robotEntity.getPosition().setYCordinate(yCordn + 1);
        }
    }

    /**
     * Execute "LEFT" command.
     * This method is used to Change the direction of the Robot to it's Left
     *
     * @throws IllegalArgumentException: in case invalid values
     */
    public void left() throws IllegalArgumentException {
        Position.validate(robotEntity.getPosition(), grid);

        DirectionFacingEnum direction = robotEntity.getPosition().getDirectionEnum();

        DirectionFacingEnum resultDirection = Match(direction).of(
                Case($(DirectionFacingEnum.EAST), DirectionFacingEnum.NORTH),
                Case($(DirectionFacingEnum.SOUTH), DirectionFacingEnum.EAST),
                Case($(DirectionFacingEnum.WEST), DirectionFacingEnum.SOUTH),
                Case($(DirectionFacingEnum.NORTH), DirectionFacingEnum.WEST)
        );

        robotEntity.getPosition().setDirectionEnum(resultDirection);
    }

    /**
     * Execute "RIGHT" command.
     * This method is used to Change the direction of the Robot to it's right
     *
     * @throws IllegalArgumentException: in case invalid values
     */
    public void right() throws IllegalArgumentException {
        Position.validate(robotEntity.getPosition(), grid);

        DirectionFacingEnum direction = robotEntity.getPosition().getDirectionEnum();

        DirectionFacingEnum resultDirection = Match(direction).of(
                Case($(DirectionFacingEnum.EAST), DirectionFacingEnum.SOUTH),
                Case($(DirectionFacingEnum.SOUTH), DirectionFacingEnum.WEST),
                Case($(DirectionFacingEnum.WEST), DirectionFacingEnum.NORTH),
                Case($(DirectionFacingEnum.NORTH), DirectionFacingEnum.EAST)
        );

        robotEntity.getPosition().setDirectionEnum(resultDirection);

    }


    private void validateWithMove(int xCordn, int yCordin, DirectionFacingEnum currentDirection) {


        switch (currentDirection) {
            case EAST:
                if (xCordn + 1 > grid.getWidth()) {
                    log.error("X-co-ordinate will move out of bound");
                    throw new IllegalArgumentException(ERROR_MSG_OUT_OF_BOUNDRY);
                }
                break;
            case WEST:
                if (xCordn - 1 < 0) {
                    log.error("X-co-ordinate will move out of bound");
                    throw new IllegalArgumentException(ERROR_MSG_OUT_OF_BOUNDRY);
                }
                break;
            case SOUTH:
                if (yCordin - 1 < 0) {
                    log.error("Y-co-ordinate will move out of bound");
                    throw new IllegalArgumentException(ERROR_MSG_OUT_OF_BOUNDRY);
                }
                break;
            case NORTH:
                if (yCordin + 1 > grid.getHeight()) {
                    log.error("Y-co-ordinate will move out of bound");
                    throw new IllegalArgumentException(ERROR_MSG_OUT_OF_BOUNDRY);
                }
                break;
            default:
                //should never come here.
                log.error("Invalid Direction Provided");
                throw new IllegalArgumentException(INVALID_DIRECTION_VALUE);
        }
    }
}


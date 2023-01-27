package com.rv.service;

import com.rv.entities.DirectionFacingEnum;
import com.rv.entities.Position;
import com.rv.entities.RobotEntity;
import com.rv.entities.TableDimention;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.rv.util.Constants.ERROR_MSG_OUT_OF_BOUNDRY;

@Service
@Slf4j
public class RobotService {

    @Autowired
    private RobotEntity robotEntity;

    @Autowired
    private TableDimention grid;


    /**
     * Handle "PLACE" command.
     *
     * @throws IllegalArgumentException
     */
    public void place(Position position) throws IllegalArgumentException {
        Position.validate(position, grid);
        robotEntity.setPosition(position);
    }


    /**
     * Handle "REPORT" command.
     *
     * @return The location of the robot and obstacle (if any)
     * @throws IllegalArgumentException
     */
    public String report() throws IllegalArgumentException {

        Position.validate(robotEntity.getPosition(), grid);

        return "Robot is at " + robotEntity.getPosition().getXCordinate() + "," + robotEntity.getPosition().getYCordinate() + ","
                + robotEntity.getPosition().getDirectionEnum();

    }


    /**
     * Handle "MOVE" command.
     *
     * @throws IllegalArgumentException
     */
    public void move() throws IllegalArgumentException {
        Position.validate(robotEntity.getPosition(), grid);

        int xCordn = robotEntity.getPosition().getXCordinate();
        int yCordn = robotEntity.getPosition().getYCordinate();
        DirectionFacingEnum direction = robotEntity.getPosition().getDirectionEnum();

        validateWithMove(xCordn,yCordn,direction);

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
     * Handle "LEFT" command.
     *
     * @throws IllegalArgumentException
     */
    public void left() throws IllegalArgumentException {
        Position.validate(robotEntity.getPosition(), grid);

        int xCordn = robotEntity.getPosition().getXCordinate();
        int yCordn = robotEntity.getPosition().getYCordinate();
        DirectionFacingEnum direction = robotEntity.getPosition().getDirectionEnum();

        validateWithMove(xCordn,yCordn,direction);

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
        Position.validate(robotEntity.getPosition(), grid);

        int xCordn = robotEntity.getPosition().getXCordinate();
        int yCordn = robotEntity.getPosition().getYCordinate();
        DirectionFacingEnum direction = robotEntity.getPosition().getDirectionEnum();

        validateWithMove(xCordn,yCordn,direction);

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
        }
    }
}


package com.rv.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.function.Predicate;

import static com.rv.util.Constants.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
@AllArgsConstructor
public class Position {
    private int xCordinate;
    private int yCordinate;
    private DirectionFacingEnum directionEnum;

    public static void validate(Position position, TableDimention tableDimention) {
        //check for null object (used by report call)
        if(ObjectUtils.isEmpty(position)) throw new IllegalArgumentException(POSITION_IS_NOT_INITIALIZED);

        //check for X-co-ordinate is going Beyond the boundry or not
        validateCordinate(Integer.valueOf(position.xCordinate), tableDimention.getWidth());

        //check for Y-co-ordinate is going Beyond the boundry or not
        validateCordinate(Integer.valueOf(position.yCordinate), tableDimention.getHeight());

        //check direction is right or not
        validateDirection(position.directionEnum);
    }

    private static void validateDirection(DirectionFacingEnum direction) {
            Predicate<DirectionFacingEnum > isValidDirection = d -> Arrays.stream(DirectionFacingEnum.values())
                    .anyMatch(x -> x.name().equals(d.name()));
            if (!isValidDirection.test(direction)) {
                log.error(INVALID_DIRECTION_VALUE);
                throw new IllegalArgumentException(INVALID_DIRECTION_VALUE);
            }
        }

    private static void validateCordinate(Integer valueOf,Integer limit) {
        try {
            if (valueOf < 0 || valueOf > limit) {
                 log.error(ERROR_MSG_OUT_OF_BOUNDRY + limit);
                throw new IllegalArgumentException(ERROR_MSG_OUT_OF_BOUNDRY);
            }
        } catch (NumberFormatException exception) {
            // All other input error cases
            log.error("Invalid input: co-ordinate is invalid");
            throw new IllegalArgumentException(INVALID_CORDINATE);
        }
    }
}

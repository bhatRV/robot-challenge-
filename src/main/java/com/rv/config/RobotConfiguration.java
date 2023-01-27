package com.rv.config;

import com.rv.entities.RobotEntity;
import com.rv.entities.TableDimention;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurations for the Toy Robot application.
 *
 * @author rashmi
 */
@Configuration
public class RobotConfiguration {

    /**
     * RobotConfiguration .
     *
     * @return Robot bean
     */
    @Bean
    public RobotEntity getRobotEntity() {
        RobotEntity robotEntity = new RobotEntity();
        return robotEntity;
    }


    /**
     * Grid bean.
     *
     * @return Grid bean
     */
    @Bean("grid")
    public TableDimention getGrid() {
        TableDimention grid = new TableDimention();
        return grid;
    }
}

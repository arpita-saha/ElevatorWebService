package com.project.elevator.service.Impl;

import com.project.elevator.Controller.ElevatorController;
import com.project.elevator.model.Elevator;
import com.project.elevator.service.ElevatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElevatorServiceImpl implements ElevatorService {

    @Autowired
    Elevator elevator;

    Logger logger =  LoggerFactory.getLogger(ElevatorServiceImpl.class);

    @Override
    public void callTheElevator(int direction, int sourceFloor) {
        logger.info("Calling Elevator Service to {}, wants to move in direction {}", sourceFloor, direction);
    }

    @Override
    public void moveToFloor(int destinationFloor) {
        logger.info("Requesting Elevator Service to move to floor {}", destinationFloor);
    }
}

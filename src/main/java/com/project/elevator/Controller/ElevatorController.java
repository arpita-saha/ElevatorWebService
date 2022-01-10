package com.project.elevator.Controller;

import com.project.elevator.model.Direction;
import com.project.elevator.service.Impl.ElevatorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElevatorController {
    private static int MAXIMUMALLOWEDELEVATORFLOOR = 10;
    @Autowired
    ElevatorServiceImpl elevatorService;
    Logger logger = LoggerFactory.getLogger(ElevatorController.class);

    @RequestMapping("/callAndMoveElevatorToFloor/direction/{direction}/srcFloor/{srcFloor}/desFloor/{desFloor}")
    public String callAndMoveElevatorToFloor(@PathVariable("direction") Direction direction,
                                             @PathVariable("srcFloor") int srcFloor,
                                             @PathVariable("desFloor") int desFloor) {
        logger.info("Request to elevator is not valid");
        if (!validateInput(direction, srcFloor, desFloor)) {
            return String.format("Request to elevator is not valid, user want to move %s from source floor : %d to " +
                    "destination floor %d which is impossible!, Maximum Allowed Floor %d",
                    direction, srcFloor, desFloor, MAXIMUMALLOWEDELEVATORFLOOR);
        } else {
            elevatorService.callAndMoveElevatorToFloor(direction, srcFloor, desFloor);
            return "Request is successfully added to the queue";
        }
    }

//    @RequestMapping("/callTheElevator/direction/{direction}/srcFloor/{srcFloor}")
//    public void callTheElevator(@PathVariable("direction") Direction direction, @PathVariable("srcFloor") int srcFloor) {
//        elevatorService.callTheElevator(direction, srcFloor);
//    }
//
//    @RequestMapping("/moveToFloor/{destinationFloor}")
//    public void moveToFloor(@PathVariable("destinationFloor") int destinationFloor) {
//        elevatorService.moveToFloor(destinationFloor);
//    }

    private boolean validateInput(Direction directionToGo, int srcFloor, int desFloor) {
        if (directionToGo == Direction.UP && srcFloor > desFloor)
            return false;
        else if (directionToGo == Direction.DOWN && srcFloor < desFloor)
            return false;
        else if (srcFloor < 0 || desFloor > MAXIMUMALLOWEDELEVATORFLOOR)
            return false;
        return true;
    }

}

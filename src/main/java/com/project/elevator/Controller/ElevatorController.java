package com.project.elevator.Controller;

import com.project.elevator.service.ElevatorService;
import com.project.elevator.service.Impl.ElevatorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElevatorController {
    @Autowired
    ElevatorServiceImpl elevatorService;
    Logger logger = LoggerFactory.getLogger(ElevatorController.class);

    @RequestMapping("/callTheElevator/direction/{direction}/srcFloor/{srcFloor}/desFloor/{desFloor}")
    public void callAndMoveElevatorToFLoor(@PathVariable("direction") int direction,
                                           @PathVariable("srcFloor") int srcFloor,
                                           @PathVariable("desFloor") int desFloor) {
        elevatorService.callAndMoveElevatorToFloor(direction, srcFloor, desFloor);
    }

    @RequestMapping("/callTheElevator/direction/{direction}/srcFloor/{srcFloor}")
    public void callTheElevator(@PathVariable("direction") int direction, @PathVariable("srcFloor") int srcFloor) {
        elevatorService.callTheElevator(direction, srcFloor);
    }

    @RequestMapping("/moveToFloor/{destinationFloor}")
    public void moveToFloor(@PathVariable("destinationFloor") int destinationFloor) {
        elevatorService.moveToFloor(destinationFloor);
    }

}

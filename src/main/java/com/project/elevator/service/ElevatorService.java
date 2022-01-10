package com.project.elevator.service;

import com.project.elevator.model.Direction;

public interface ElevatorService {
    public void callTheElevator(Direction directionToMove, int floorNo);
    public void moveToFloor(int floorNo);
    public void callAndMoveElevatorToFloor(Direction directionToMove , int srcFloor, int desFloor);
    public void startElevator();
    public void stopElevator();
    public String getElevatorStatus();
    public boolean checkElevatorIsWorking();

}

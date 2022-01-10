package com.project.elevator.service;

public interface ElevatorService {
    public void callTheElevator(int directionToMove, int floorNo);
    public void moveToFloor(int floorNo);
    public void callAndMoveElevatorToFloor(int directionToMove , int srcFloor, int desFloor);
    public void startElevator();
    public void stopElevator();
    public String getElevatorStatus();

}

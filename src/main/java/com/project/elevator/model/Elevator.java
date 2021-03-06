package com.project.elevator.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Elevator {
    private int currentFloor;
    private Direction currentDirection;
    private State currentState;

    public Elevator() {
        this.currentDirection = Direction.UP;
        this.currentFloor = 0;
        this.currentState = State.STOPPED;
    }

    //    public synchronized static Elevator getInstance(){
//        if(elevator == null){
//            elevator = new Elevator();
//        }
//        return elevator;
//    }
    public synchronized void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public synchronized void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public synchronized void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}

package com.project.elevator.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.TreeSet;

@Component
public class Elevator {
//    public static Elevator elevator;

    private int currentFloor;
    private Direction currentDirection;
    private State currentState;
//    private TreeSet<Request> currentJobs = new TreeSet<>();

    public Elevator() {
        this.currentDirection = Direction.UP;
        this.currentFloor = 0;
        this.currentState = State.IDLE;
    }

//    public synchronized static Elevator getInstance(){
//        if(elevator == null){
//            elevator = new Elevator();
//        }
//        return elevator;
//    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}

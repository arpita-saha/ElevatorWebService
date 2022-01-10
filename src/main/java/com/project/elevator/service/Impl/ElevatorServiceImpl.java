package com.project.elevator.service.Impl;

import com.project.elevator.model.*;
import com.project.elevator.service.ElevatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TreeSet;

@Service
public class ElevatorServiceImpl implements ElevatorService {

    @Autowired
    Elevator elevator;

    private TreeSet<Request> currentJobs = new TreeSet<>();
    private TreeSet<Request> upPendingJobs = new TreeSet<>();
    private TreeSet<Request> downPendingJobs = new TreeSet<>();

    Logger logger =  LoggerFactory.getLogger(ElevatorServiceImpl.class);

    @Override
    public void callTheElevator(Direction direction, int sourceFloor) {
        logger.info("Calling Elevator Service to {}, wants to move in direction {}", sourceFloor, direction);
        RequestBuilder requestBuilder = new RequestBuilder();
        Request request = requestBuilder.buildExternalRequest(sourceFloor, direction).getRequest();
        if(!addJob(request)){
            logger.info("Request to elevator service is failed due to unavailability of elevator");
        }
    }

    @Override
    public void moveToFloor(int destinationFloor) {
        logger.info("Requesting Elevator Service to move to floor {}", destinationFloor);
        RequestBuilder requestBuilder = new RequestBuilder();
        Request request = requestBuilder.buildInternalRequest(destinationFloor).getRequest();
        if(!addJob(request)){
            logger.info("Request to elevator service is failed due to unavailability of elevator");
        }
    }

    @Override
    public void callAndMoveElevatorToFloor(Direction directionToMove, int srcFloor, int destinationFloor) {
        logger.info("Calling Elevator Service to {}, wants to move in direction {} and to floor {} ",
                srcFloor, directionToMove, destinationFloor);
        RequestBuilder requestBuilder = new RequestBuilder();
        Request request = requestBuilder.buildExternalRequest(srcFloor, directionToMove)
                .buildInternalRequest(destinationFloor).getRequest();
        if(!addJob(request)){
            logger.info("Request to elevator service is failed due to unavailability of elevator");
        }
    }

    @Override
    public String getElevatorStatus(){
        return elevator.toString();
    }

    @Override
    public boolean checkElevatorIsWorking(){
        if(elevator.getCurrentState() == State.IDLE || elevator.getCurrentState() == State.MOVING)
            return true;
        else return false;
    }

    @Override
    public synchronized void stopElevator(){
        elevator.setCurrentState(State.STOPPED);
    }

    @Override
    public void startElevator() {
        synchronized (elevator){
            elevator.setCurrentState(State.IDLE);
        }
        while (elevator.getCurrentState() != State.INMAINTAINENCE
                && elevator.getCurrentState() != State.STOPPED) {
            if (checkIfJob()) {
                if (elevator.getCurrentDirection() == Direction.UP) {
                    Request request = currentJobs.pollFirst();
                    processUpRequest(request);
                    if (currentJobs.isEmpty()) {
                        addPendingDownJobsToCurrentJobs();
                    }
                }
                if (elevator.getCurrentDirection() == Direction.DOWN) {
                    Request request = currentJobs.pollLast();
                    processDownRequest(request);
                    if (currentJobs.isEmpty()) {
                        addPendingUpJobsToCurrentJobs();
                    }

                }
            } else{
                synchronized (elevator){
                    elevator.setCurrentState(State.IDLE);
                }
            }
        }
    }

    private synchronized boolean checkIfJob() {

        if (currentJobs.isEmpty()) {
            return false;
        }
        return true;

    }

    private void processUpRequest(Request request) {
        // The elevator is not on the floor where the person has requested it i.e. source floor.
        // So first bring it there.
        int startFloor = elevator.getCurrentFloor();
        if (startFloor < request.getExternalRequest().getSourceFloor()) {
            for (int i = startFloor; i <= request.getExternalRequest().getSourceFloor(); i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("We are crossing floor -- " + i);
                elevator.setCurrentFloor(i);
            }
        }
        // The elevator is now on the floor where the person has requested it i.e. source floor.
        // User can enter and go to the destination floor.
        logger.info("Reached Source Floor--opening door");

        startFloor = elevator.getCurrentFloor();
        for (int i = startFloor; i <= request.getInternalRequest().getDestinationFloor(); i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            logger.info("We are crossing floor -- " + i);
            elevator.setCurrentFloor(i);
            if (checkIfNewJobCanBeProcessed(request)) {
                return;
            }
        }
        logger.info("We have reached destination floor -- " + request.getInternalRequest().getDestinationFloor());
    }

    private void processDownRequest(Request request) {

        int startFloor = elevator.getCurrentFloor();
        if (startFloor > request.getExternalRequest().getSourceFloor()) {
            for (int i = startFloor; i >= request.getExternalRequest().getSourceFloor(); i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                logger.info("We are crossing floor -- " + i);
                elevator.setCurrentFloor(i);
            }
        }

        logger.info("Reached Source Floor--opening door");

        startFloor = elevator.getCurrentFloor();

        for (int i = startFloor; i >= request.getInternalRequest().getDestinationFloor(); i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            logger.info("We are crossing floor -- " + i);
            elevator.setCurrentFloor(i);
            if (checkIfNewJobCanBeProcessed(request)) {
                return;
            }
        }
        logger.info("We have reached destination floor -- " + request.getInternalRequest().getDestinationFloor());
    }

    private synchronized boolean checkIfNewJobCanBeProcessed(Request currentRequest) {
        if (checkIfJob()) {
            if (elevator.getCurrentDirection() == Direction.UP) {
                Request request = currentJobs.pollFirst();
                if (request.getInternalRequest().getDestinationFloor() < currentRequest.getInternalRequest()
                        .getDestinationFloor()) {
                    currentJobs.add(request);
                    currentJobs.add(currentRequest);
                    return true;
                }
                currentJobs.add(request);
            }

            if (elevator.getCurrentDirection() == Direction.DOWN) {
                Request request = currentJobs.pollLast();
                if (request.getInternalRequest().getDestinationFloor() > currentRequest.getInternalRequest()
                        .getDestinationFloor()) {
                    currentJobs.add(request);
                    currentJobs.add(currentRequest);
                    return true;
                }
                currentJobs.add(request);

            }

        }
        return false;

    }

    private void addPendingDownJobsToCurrentJobs() {
        if (!downPendingJobs.isEmpty()) {
            currentJobs = downPendingJobs;
            elevator.setCurrentDirection(Direction.DOWN);
        } else {
            elevator.setCurrentState(State.IDLE);
        }

    }

    private synchronized void addPendingUpJobsToCurrentJobs() {
        if (!upPendingJobs.isEmpty()) {
            currentJobs = upPendingJobs;
            elevator.setCurrentDirection(Direction.UP);
        } else {
            elevator.setCurrentState(State.IDLE);
        }

    }

    private boolean addJob(Request request) {
        if(elevator.getCurrentState() == State.STOPPED || elevator.getCurrentState() == State.INMAINTAINENCE)
            return  false;
        if (elevator.getCurrentState() == State.IDLE) {
            elevator.setCurrentState(State.MOVING);
            elevator.setCurrentDirection(request.getExternalRequest().getDirectionToGo());
            currentJobs.add(request);
        } else if (elevator.getCurrentState() == State.MOVING) {

            if (request.getExternalRequest().getDirectionToGo() != elevator.getCurrentDirection()) {
                addtoPendingJobs(request);
            } else if (request.getExternalRequest().getDirectionToGo() == elevator.getCurrentDirection()) {
                if (elevator.getCurrentDirection() == Direction.UP
                        && request.getInternalRequest().getDestinationFloor() < elevator.getCurrentFloor()) {
                    addtoPendingJobs(request);
                } else if (elevator.getCurrentDirection() == Direction.DOWN
                        && request.getInternalRequest().getDestinationFloor() > elevator.getCurrentFloor()) {
                    addtoPendingJobs(request);
                } else {
                    currentJobs.add(request);
                }

            }
        }
        return true;

    }

    private void addtoPendingJobs(Request request) {
        if (request.getExternalRequest().getDirectionToGo() == Direction.UP) {
            System.out.println("Add to pending up jobs");
            upPendingJobs.add(request);
        } else {
            System.out.println("Add to pending down jobs");
            downPendingJobs.add(request);
        }
    }
}

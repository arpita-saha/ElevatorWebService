package com.project.elevator.model;

public class InternalRequest implements Request{
    private int destinationFloor;

    public InternalRequest(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    @Override
    public int compareTo(Request internalRequest) {
        if (this.getDestinationFloor() == ((InternalRequest) internalRequest).getDestinationFloor())
            return 0;
        else if (this.getDestinationFloor() > ((InternalRequest) internalRequest).getDestinationFloor())
            return 1;
        else
            return -1;
    }

}

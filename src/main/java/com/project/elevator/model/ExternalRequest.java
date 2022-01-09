package com.project.elevator.model;

public class ExternalRequest implements Request{
    private int sourceFloor;
    private Direction direction;

    public ExternalRequest(int sourceFloor, Direction direction) {
        this.sourceFloor = sourceFloor;
        this.direction = direction;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public void setSourceFloor(int sourceFloor) {
        this.sourceFloor = sourceFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public int compareTo(Request internalRequest) {
        if (this.getSourceFloor() == ((ExternalRequest) internalRequest).getSourceFloor())
            return 0;
        else if (this.getSourceFloor() > ((ExternalRequest) internalRequest).getSourceFloor())
            return 1;
        else
            return -1;
    }
}

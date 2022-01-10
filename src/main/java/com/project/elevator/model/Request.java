package com.project.elevator.model;

import lombok.Data;

@Data
public class Request implements Comparable<Request> {

    private InternalRequest internalRequest;
    private ExternalRequest externalRequest;

    @Override
    public int compareTo(Request req) {
        if (this.getInternalRequest().getDestinationFloor() == req.getInternalRequest().getDestinationFloor())
            return 0;
        else if (this.getInternalRequest().getDestinationFloor() > req.getInternalRequest().getDestinationFloor())
            return 1;
        else
            return -1;
    }
}

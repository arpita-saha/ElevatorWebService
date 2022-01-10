package com.project.elevator.model;

import lombok.Data;

public class RequestBuilder {
    Request request;
    public RequestBuilder(){
        request = new Request();
    }

    public RequestBuilder buildInternalRequest(int destinationFloorToGo){
        request.setInternalRequest(new InternalRequest(destinationFloorToGo));
        return this;
    }

    public RequestBuilder buildExternalRequest(int srcFloor, int direction){
        request.setExternalRequest(new ExternalRequest(srcFloor, Direction.UP));
        return this;
    }

    public Request getRequest(){
        return request;
    }

}

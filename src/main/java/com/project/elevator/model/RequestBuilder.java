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

    public RequestBuilder buildExternalRequest(int srcFloor, Direction direction){
        request.setExternalRequest(new ExternalRequest(srcFloor, direction));
        return this;
    }

    public Request getRequest(){
        return request;
    }

}

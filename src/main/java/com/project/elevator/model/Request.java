package com.project.elevator.model;

public interface Request extends Comparable<Request> {

    int compareTo(Request request);
}

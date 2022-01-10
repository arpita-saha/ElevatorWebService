# ElevatorWebService
 This is web service for elevator system

Only Admin has access to start stop and check status of elevator,
-------------Create Admin ---------------
http://localhost:8080/addAdmin/userName/arpita/password/123
http://localhost:8080/getAdminsList
http://localhost:8080/getElevatorStatus/1

----------- Start Elevator Service-------
http://localhost:8080/startElevatorService/1
http://localhost:8080/stopElevatorService/1

------------ Call Elevator --------------

http://localhost:8080/callAndMoveElevatorToFloor/direction/UP/srcFloor/3/desFloor/4
http://localhost:8080/callAndMoveElevatorToFloor/direction/UP/srcFloor/5/desFloor/7
http://localhost:8080/callAndMoveElevatorToFloor/direction/DOWN/srcFloor/5/desFloor/4
http://localhost:8080/callAndMoveElevatorToFloor/direction/DOWN/srcFloor/4/desFloor/0
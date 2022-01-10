package com.project.elevator.Controller;

import com.project.elevator.model.Admin;
import com.project.elevator.model.AdminRepository;
import com.project.elevator.service.ElevatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ElevatorService elevatorService;

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(method = RequestMethod.POST,
            value = "/addAdmin/userName/{userName}/password/{password}")
    public String addAdmin(@PathVariable("userName") String userName,
                           @PathVariable("password") String password) {
        Admin admin = new Admin(userName, password);
        adminRepository.save(admin);
        logger.info("Admin with user name : {} and userId : {} is created", admin.getUserName(), admin.getUserId());
        return ("Generated userId : " + admin.getUserId());
    }

    @GetMapping("/getAdminsList")
    public List<Admin> getAdminsList() {
        return (List<Admin>) adminRepository.findAll();
    }

    @GetMapping("/stopElevatorService/{userId}")
    public void stopElevatorService(@PathVariable("userId") int userId) {
        if (adminRepository.findById(userId).isPresent()) {
            elevatorService.stopElevator();
            logger.info("Admin with userId : {} is stopped the elevator", userId);
        } else logger.info("Admin with userId : {} is not authorised to stop the elevator", userId);

    }

    @GetMapping("/startElevatorService/{userId}")
    public void startElevatorService(@PathVariable("userId") int userId) {
        if (adminRepository.findById(userId).isPresent()) {
            if(elevatorService.checkElevatorIsWorking()){
                logger.info("Elevator is already working.");
            }else {
                logger.info("Admin with userId : {} has started the elevator", userId);
                elevatorService.startElevator();
            }
        } else logger.info("Admin with userId : {} is not authorised to start the elevator", userId);

    }

    @GetMapping("/getElevatorStatus/{userId}")
    public String getElevatorStatus(@PathVariable("userId") int userId) {
        if (adminRepository.findById(userId).isPresent()) {
            String elevatorStr = elevatorService.getElevatorStatus();
            logger.info("Admin with userId : {} is checked elevator service which is {}", userId, elevatorStr);
            return elevatorStr;
        } else logger.info("Admin with userId : {} is not authorised to start the elevator", userId);
        return ("Admin with userId : " + userId + " is not authorised to start the elevator");
    }

}

package com.vmware.portfolio.controllers;

import com.vmware.portfolio.models.Application;
import com.vmware.portfolio.services.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/application")
@Slf4j
public class ApplicationController {

    @Autowired
    ApplicationService appService;

    @GetMapping (produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Application> getAll() {
       return appService.getApplications();
    }

    @PostMapping (consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Application create(@RequestBody Application application) {
        appService.save(application);
        return application;
    }

    @DeleteMapping ("{applicationId}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable String applicationId) {
        appService.delete(applicationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

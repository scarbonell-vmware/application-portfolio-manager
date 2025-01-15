package com.vmware.portfolio.services;

import com.vmware.portfolio.models.Application;
import com.vmware.portfolio.repositories.ApplicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ApplicationService {

    @Autowired
    ApplicationRepository appRepository;

    public List<Application> getApplications () {
        return appRepository.findAll();
    }

    public void save (Application application) {
        appRepository.save(application);
    }

    public void delete (String id) {
        Application application = appRepository.getById(Long.parseLong(id));
        appRepository.delete(application);
    }
}

package com.vmware.portfolio.demo;

import com.vmware.portfolio.models.Application;
import com.vmware.portfolio.services.ApplicationService;
import com.vmware.portfolio.utils.FileUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

@Slf4j
@Configuration
public class SeedUtil {

    @Value("${portfolio.application.manager.seed}")
    private String seedFileName;

    @Autowired
    ApplicationService appService;

    @PostConstruct
    public void init () throws JSONException, IOException {
        log.debug("Running init seeds...");
        List<Application> applications = appService.getApplications();
        if (applications.size() == 0) {
            createApplicationsFromSeeds();
        }
    }

    public void createApplicationsFromSeeds () throws IOException, JSONException {
        String seeds = FileUtils.loadResourceFile(seedFileName);
        List<Application> applications = Application.fromJson(seeds);

        for (Application app: applications) {
            appService.save(app);
        }
    }

}

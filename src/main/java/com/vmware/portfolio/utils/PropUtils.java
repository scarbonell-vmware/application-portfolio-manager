package com.vmware.portfolio.utils;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class PropUtils {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${portfolio.application.manager.version}")
    private String version;

    @Autowired
    private Environment env;

    public String getServiceCredProp (String name, String serviceName, String propName) throws JSONException {
        Map<String, String> envMap = getEnvironmentProps ();

        for (String envName : envMap.keySet()) {
            if (envName.equals("VCAP_SERVICES")) {
                JSONObject props = new JSONObject(envMap.get(envName));
                if (props.has(name)) {
                    JSONArray servicesJson = props.getJSONArray(name);
                    for (int i = 0; i < servicesJson.length(); i++) {
                        JSONObject serviceJson = servicesJson.getJSONObject(i);
                        if (serviceJson.getString("name").equals(serviceName)) {
                            if (serviceJson.has("credentials")) {
                                JSONObject creds = serviceJson.getJSONObject("credentials");
                                if (creds.has(propName)) {
                                    return creds.getString(propName);
                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public Map<String, String> getEnvironmentProps () throws JSONException {
        Map<String, String> props = new HashMap<>();
        //String VCAP_APPLICATION = "{\"application_id\":\"b8f7966d-d0ca-4da8-9e0d-820416add970\",\"application_name\":\"application-portfolio-manager\",\"application_uris\":[\"application-portfolio-manager.apps.dhaka.cf-app.com\"],\"application_version\":\"d635317d-b7a9-45ed-b24e-394ecb6c0db4\",\"cf_api\":\"https://api.sys.dhaka.cf-app.com\",\"host\":\"0.0.0.0\",\"instance_id\":\"afbf513f-3350-4ed3-7222-ffa1\",\"instance_index\":0,\"limits\":{\"disk\":1024,\"fds\":16384,\"mem\":1024},\"name\":\"application-portfolio-manager\",\"organization_id\":\"1b42fa12-d3d5-4029-9f92-22aeb9b98177\",\"organization_name\":\"rpm-practice\",\"port\":8080,\"process_id\":\"b8f7966d-d0ca-4da8-9e0d-820416add970\",\"process_type\":\"web\",\"space_id\":\"70146843-6429-4651-b7db-a309185741ed\",\"space_name\":\"scarbonell-sandbox\",\"uris\":[\"application-portfolio-manager.apps.dhaka.cf-app.com\"],\"version\":\"d635317d-b7a9-45ed-b24e-394ecb6c0db4\"}";
        //String VCAP_SERVICES = "{\"application_id\":\"b8f7966d-d0ca-4da8-9e0d-820416add970\",\"application_name\":\"application-portfolio-manager\",\"application_uris\":[\"application-portfolio-manager.apps.dhaka.cf-app.com\"],\"application_version\":\"d635317d-b7a9-45ed-b24e-394ecb6c0db4\",\"cf_api\":\"https://api.sys.dhaka.cf-app.com\",\"host\":\"0.0.0.0\",\"instance_id\":\"afbf513f-3350-4ed3-7222-ffa1\",\"instance_index\":0,\"limits\":{\"disk\":1024,\"fds\":16384,\"mem\":1024},\"name\":\"application-portfolio-manager\",\"organization_id\":\"1b42fa12-d3d5-4029-9f92-22aeb9b98177\",\"organization_name\":\"rpm-practice\",\"port\":8080,\"process_id\":\"b8f7966d-d0ca-4da8-9e0d-820416add970\",\"process_type\":\"web\",\"space_id\":\"70146843-6429-4651-b7db-a309185741ed\",\"space_name\":\"scarbonell-sandbox\",\"uris\":[\"application-portfolio-manager.apps.dhaka.cf-app.com\"],\"version\":\"d635317d-b7a9-45ed-b24e-394ecb6c0db4\"}";

        props.putAll(System.getenv());
        //props.put("VCAP_APPLICATION", VCAP_APPLICATION);
        //props.put("VCAP_SERVICES", VCAP_SERVICES);
        return props;
    }

    public Map<String, String> getAppProperties () throws JSONException {
        Map<String, String> props = new HashMap<>();
        props.put("spring.datasource.url", databaseUrl);
        props.put("portfolio.application.manager.version", version);
        return props;
    }

    public String getActiveProfile () throws JSONException {
        String[] activeProfiles = env.getActiveProfiles();
        String[] defaultProfiles = env.getDefaultProfiles();

        if (activeProfiles.length > 0) {
            return StringUtils.join(activeProfiles, ",");
        }

        return StringUtils.join(defaultProfiles, ",");
    }
}

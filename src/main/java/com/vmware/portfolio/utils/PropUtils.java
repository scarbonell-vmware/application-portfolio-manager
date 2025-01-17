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
        return System.getenv();
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

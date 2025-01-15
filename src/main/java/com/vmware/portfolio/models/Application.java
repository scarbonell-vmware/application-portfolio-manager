package com.vmware.portfolio.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vmware.portfolio.serializers.ApplicationSerializer;
import jakarta.persistence.*;;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "application")
@JsonSerialize(using = ApplicationSerializer.class)
@Getter
@Setter
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "business_unit")
    private String businessUnit;

    @Column(name = "organization")
    private String organization;

    @Column(name = "businessOwner")
    private String businessOwner;

    public Application () {

    }

    public Application (String name, String identifier, String description, String businessOwner, String businessUnit, String organization) {
        this.name = name;
        this.description = description;
        this.identifier = identifier;
        this.businessOwner = businessOwner;
        this.organization = organization;
        this.businessUnit = businessUnit;
    }

    public static List<Application> fromJson (String json) throws JSONException {
        List<Application> applications = new ArrayList<>();
        JSONArray appsJson = new JSONArray(json);
        for (int i = 0; i < appsJson.length(); i++) {
            Application application = new Application();
            JSONObject appJson = appsJson.getJSONObject(i);

            if (appJson.has("name")) {
                application.setName(appJson.getString("name"));
            }

            if (appJson.has("description")) {
                application.setDescription(appJson.getString("description"));
            }

            if (appJson.has("identifier")) {
                application.setIdentifier(appJson.getString("identifier"));
            }

            if (appJson.has("businessUnit")) {
                application.setBusinessUnit(appJson.getString("businessUnit"));
            }

            if (appJson.has("organization")) {
                application.setOrganization(appJson.getString("organization"));
            }

            if (appJson.has("businessOwner")) {
                application.setBusinessOwner(appJson.getString("businessOwner"));
            }

            applications.add(application);
        }

        return applications;
    }
}

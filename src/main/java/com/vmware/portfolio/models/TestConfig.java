package com.vmware.portfolio.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vmware.portfolio.serializers.ApplicationSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

;

@Entity
@Table(name = "test_config")
@JsonSerialize(using = ApplicationSerializer.class)
@Getter
@Setter
public class TestConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "conf_value")
    private String value;

    public TestConfig() {

    }

    public TestConfig(String name, String value) {
        this.name = name;
    }

}

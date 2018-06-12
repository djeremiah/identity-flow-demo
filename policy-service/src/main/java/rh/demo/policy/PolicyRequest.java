package rh.demo.policy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PolicyRequest {

    private String id;

    private List<Object> need = new ArrayList<>();

    @JsonRawValue
    private List<String> alert = new ArrayList<>();

    public PolicyRequest() {
    }

    public PolicyRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Object> getNeed() {
        return need;
    }

    public void setNeed(List<Object> need) {
        this.need = need;
    }

    public List<String> getAlert() {
        return alert;
    }

    public void setAlert(List<String> alert) {
        this.alert = alert;
    }

    public PolicyRequest need(Object need){
        this.need.add(need);
        return this;
    }

    public PolicyRequest alert(String alert){
        this.alert.add(alert);
        return this;
    }
}

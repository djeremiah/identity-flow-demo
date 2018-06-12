package rh.demo.policy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import rh.demo.util.RawMapSerializer;

import java.util.Map;
import java.util.Set;

@JsonIgnoreProperties({"policy"})
public class PolicyData {

    private Set<Activity> policy;

    @JsonSerialize(using=RawMapSerializer.class)
    private Map<String, Object> data;

    public PolicyData(Set<Activity> policy, Map<String, Object> data) {
        this.policy = policy;
        this.data = data;
    }

    public Set<Activity> getPolicy() {
        return policy;
    }

    public void setPolicy(Set<Activity> policy) {
        this.policy = policy;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}

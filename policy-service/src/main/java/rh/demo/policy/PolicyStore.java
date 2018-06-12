package rh.demo.policy;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class PolicyStore {

    private final AtomicLong nextPolicyId = new AtomicLong();
    private final Map<String, PolicyData> policyStore = new ConcurrentHashMap<>();

    public String newPolicy(){
        String policyId = Long.toString(nextPolicyId.incrementAndGet());

        Map<String, Object> policyData = new HashMap<>();
        Set<Activity> policy = new HashSet<>();
        policy.add(new Activity("image",1, "direct:image-prompt"));
        policy.add(new Activity("phone",2, "direct:phone-prompt"));
        policy.add(new Activity("ssn", 2, "direct:ssn-prompt"));
        policy.add(new Activity("report", 3, "direct:report-policy-data"));


        policyStore.put(policyId, new PolicyData(policy, policyData));

        return policyId;
    }

    public void addPolicyData(Exchange ex){
        String policyId = ex.getIn().getHeader("policy",String.class);
        String field = ex.getIn().getHeader("field",String.class);
        policyStore.get(policyId).getData().put(field, ex.getIn().getBody());
    }

    public PolicyData getPolicyData(Exchange ex){
        String policyId = ex.getIn().getHeader("policy",String.class);
        return policyStore.get(policyId);
    }

    public void completeActivity(Exchange ex){
        String policyId = ex.getIn().getHeader("policy", String.class);
        String activityId = ex.getIn().getHeader("activityId",String.class);
        Set<Activity> activites = policyStore.get(policyId).getPolicy();
        activites.removeIf(activity -> activityId.equals(activity.getId()));
    }

    public String nextActivity(Exchange ex){
        String policyId = ex.getIn().getHeader("policy",String.class);
        Set<Activity> activities = policyStore.get(policyId).getPolicy();

        Map<Integer, List<Activity>> stages = activities.stream().collect(Collectors.groupingBy(activity -> activity.getStage()));
        Integer currentStage = stages.keySet().stream().mapToInt(Integer::intValue).min().getAsInt();

        return stages.get(currentStage).stream().map(Activity::getRoute).collect(Collectors.joining(","));
    }
}

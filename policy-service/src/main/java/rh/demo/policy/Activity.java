package rh.demo.policy;

public class Activity {

    private String id;
    private Integer stage;
    private String route;

    public Activity(String id, Integer stage, String route) {
        this.id = id;
        this.stage = stage;
        this.route = route;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}

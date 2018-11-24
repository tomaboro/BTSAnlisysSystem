package BTSEvents;

public class UserLeft implements BTSEvent {
    private final String id;

    public UserLeft(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

package team2j.com.seg2;

public class Indicator {
    String name;
    String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Indicator(String name, String id) {

        this.name = name;
        this.id = id;
    }
}

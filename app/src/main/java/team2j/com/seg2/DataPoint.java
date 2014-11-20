package team2j.com.seg2;

public class DataPoint {
    Integer year;
    Integer value;

    public DataPoint(Integer year, Integer value) {
        this.year = year;
        this.value = value;
    }

    public Integer getYear() {

        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}

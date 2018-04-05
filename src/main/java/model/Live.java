package model;

public class Live {

    private int value;
    private int hour;
    private String info;


    public Live(){
        this.value = 0;
        this.hour = 0;
        this.info = null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

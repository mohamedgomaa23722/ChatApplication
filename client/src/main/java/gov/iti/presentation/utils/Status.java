package gov.iti.presentation.utils;

public enum Status {
    Offline(0, "offline", "#dadada"), online(1, "Online", "#00FF66"),
    busy(2, "busy", "#cc0d00"), away(3, "Away", "#fb9b00");

    public int id;
    public String text;
    public String color;

    private Status(int id, String text, String color) {
        this.id = id;
        this.text = text;
        this.color = color;

    }
}
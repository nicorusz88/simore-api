package ar.com.simore.simoreapi.entities.utils;

public enum YesNoOptionEnum {
    YES("Yes"),
    NO("No");

   private String text;

    YesNoOptionEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

package ar.com.simore.simoreapi.entities.enums;

public enum YesNoOptionEnum {
    YES("Si"),
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

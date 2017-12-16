package ar.com.simore.simoreapi.entities.resources;

import java.util.Date;

/**
 * Object sent as response for showing recommendations
 */
public class RecommendationResource {
    private String name;
    private String text;
    private Date date;

    public RecommendationResource(String name, String text, Date date) {
        this.name = name;
        this.text = text;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

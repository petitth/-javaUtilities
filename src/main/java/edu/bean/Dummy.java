package edu.bean;

import java.util.Date;

public class Dummy {

    private String id;
    private Date date;

    public Dummy() {
        this.id = null;
        this.date = null;
    }

    public Dummy(String id, Date date) {
        this.id = id;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

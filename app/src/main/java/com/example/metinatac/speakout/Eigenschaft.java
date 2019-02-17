package com.example.metinatac.speakout;

public class Eigenschaft {

    private String id;
    private String name;
    private String typ;
    private String beschreibung;
    private String photourl;

    public Eigenschaft(String id, String name, String typ, String beschreibung, String photourl) {

        this.id = id;
        this.name = name;
        this.typ = typ;
        this.beschreibung = beschreibung;
        this.photourl = photourl;

    }


    public Eigenschaft(){}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }
}

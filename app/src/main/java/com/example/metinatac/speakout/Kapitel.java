package com.example.metinatac.speakout;

public class Kapitel {

    private String id;
    private String name;
    private String kurzbeschreibung;
    private String lesedauer;
    private String content;


    public Kapitel(String id, String name, String kurzbeschreibung, String lesedauer, String content){

        this.id = id;
        this.name = name;
        this.kurzbeschreibung = kurzbeschreibung;
        this.lesedauer = lesedauer;
        this.content = content;

    }

    public Kapitel(){};

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

    public String getKurzbeschreibung() {
        return kurzbeschreibung;
    }

    public void setKurzbeschreibung(String kurzbeschreibung) {
        this.kurzbeschreibung = kurzbeschreibung;
    }

    public String getLesedauer() {
        return lesedauer;
    }

    public void setLesedauer(String lesedauer) {
        this.lesedauer = lesedauer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

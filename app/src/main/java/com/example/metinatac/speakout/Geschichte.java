package com.example.metinatac.speakout;

public class Geschichte {

    private String id;
    private String inhaberid;
    private String genre;
    private String bookcoverurl;
    private String name;
    private String coverurl;
    private String kurzbeschreibung;
    private int anzahlkapitel;
    private int likes;


    public Geschichte(String id, String inhaberid, String genre, String bookcoverurl, String name, String coverurl, String kurzbeschreibung, int anzahlkapitel, int likes) {

        this.id = id;
        this.inhaberid = inhaberid;
        this.name = name;
        this.genre = genre;
        this.bookcoverurl = bookcoverurl;
        this.coverurl = coverurl;
        this.kurzbeschreibung = kurzbeschreibung;
        this.anzahlkapitel = anzahlkapitel;
        this.likes = likes;

    }


    public Geschichte() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public int getAnzahlkapitel() {
        return anzahlkapitel;
    }

    public void setAnzahlkapitel(int anzahlkapitel) {
        this.anzahlkapitel = anzahlkapitel;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getCoverurl() {
        return coverurl;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }

    public String getInhaberid() {
        return inhaberid;
    }

    public void setInhaberid(String inhaberid) {
        this.inhaberid = inhaberid;
    }

    public String getBookcoverurl() {
        return bookcoverurl;
    }

    public void setBookcoverurl(String bookcoverurl) {
        this.bookcoverurl = bookcoverurl;
    }
}

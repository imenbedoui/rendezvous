package com.example.gestionrendevouz.entity;


import java.sql.*;

public class Rendevouz {

    private int id ;
    private String nom , description ,lieu ;



    private Date date ;


    private String telephone;
    private  String status ;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Rendevouz(int id, String nom, String description, String lieu, Date date, String telephone, String status) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.lieu = lieu;
        this.date = date;
        this.telephone = telephone;
        this.status = status;
    }

    public Rendevouz(String nom, String description, String lieu, Date date, String telephone) {

        this.nom = nom;
        this.description = description;
        this.lieu = lieu;
        this.date = date;
        this.telephone = telephone;
    }

    public Rendevouz() {

    }


    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getLieu() {
        return lieu;
    }



    public String getTelephone() {
        return telephone;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Rendevouz{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", lieu='" + lieu + '\'' +
                ", date=" + date +
                ", telephone='" + telephone + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

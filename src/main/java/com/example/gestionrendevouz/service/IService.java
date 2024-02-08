package com.example.gestionrendevouz.service;

import com.example.gestionrendevouz.entity.Rendevouz;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    public void add(T t) ;
    public List<T> afficher();
    void update(T t,int id);
    public Rendevouz afficherById();

}

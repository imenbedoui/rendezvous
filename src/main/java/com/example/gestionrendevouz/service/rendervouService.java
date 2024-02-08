package com.example.gestionrendevouz.service;

import com.example.gestionrendevouz.entity.Rendevouz;
import com.example.gestionrendevouz.tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class rendervouService  implements IService<Rendevouz> {

    Connection cnx =DataSource.getInstance().getCnx();




    public void add(Rendevouz e) {
        try {
            String qry = "INSERT INTO `rendevouz` (`nom`, `description`, `date`, `lieu`, `telephone`,`status`)" +
                    "VALUES ('" + e.getNom() + "','" + e.getDescription() + "','" + e.getDate() + "','" + e.getLieu() + "','" + e.getTelephone()  + "','" + "pending" + "')";

            Statement stm = cnx.createStatement();
            stm.executeUpdate(qry);


        }catch (SQLException q){
            q.printStackTrace();
        }
    }



    @Override
    public List<Rendevouz> afficher() {
        List<Rendevouz> list=new ArrayList<>();
        try {
            String requete ="select * from rendevouz";


            Statement stm=cnx.createStatement();
            ResultSet rs= stm.executeQuery(requete);
            while(rs.next()){
                Rendevouz p = new Rendevouz();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString(2));
                p.setDescription(rs.getString(3));
                p.setLieu(rs.getString(5));
                p.setDate(rs.getDate(4));
                p.setTelephone(rs.getString(6));

                p.setStatus(rs.getString(7));



                list.add(p);

            }
        } catch (SQLException ex) {
            Logger.getLogger(rendervouService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Rendevouz afficherById() {
        Rendevouz p= new Rendevouz();;
        try {
            String requete ="select * from rendevouz where id";


            Statement stm=cnx.createStatement();
            ResultSet rs= stm.executeQuery(requete);
            if (rs.next()) {
                p.setId(rs.getInt(1));
                p.setNom(rs.getString(2));
                p.setDescription(rs.getString(3));
                p.setLieu(rs.getString(5));
                p.setDate(rs.getDate(4));
                p.setTelephone(rs.getString(6));

                p.setStatus(rs.getString(7));



            }
            else {
                System.out.println("aucun réponose");

            }

        } catch (SQLException ex) {
            Logger.getLogger(rendervouService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    @Override
    public void update(Rendevouz rendevouz, int id) {
        String req = "UPDATE rendevouz SET " +
                "nom = ?," +
                "description = ?," +
                "date = ?," +
                "lieu = ?," +
                "telephone = ?," +
                "status = ?" +
                "WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            System.out.println(cnx);
            pst.setString(1, rendevouz.getNom());
            pst.setString(2, rendevouz.getDescription());
            pst.setDate(3, rendevouz.getDate());
            pst.setString(4, rendevouz.getLieu());
            pst.setString(5, rendevouz.getTelephone());
            pst.setString(6, "pending");
            pst.setInt(7, id);
            int result= pst.executeUpdate();

            if(result>0) System.out.println("rendez vous modifié avec succée");
            else  System.out.println("rendez vous inexistant");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public void supprimerid(int id) {
        try {
            String qry = "DELETE FROM `rendevouz` WHERE id='" + id + "'";

            Statement stm = cnx.createStatement();

            stm.executeUpdate(qry);


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }






}

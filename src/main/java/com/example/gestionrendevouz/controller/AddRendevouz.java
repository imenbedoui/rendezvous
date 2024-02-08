package com.example.gestionrendevouz.controller;

import com.example.gestionrendevouz.entity.Rendevouz;
import com.example.gestionrendevouz.service.rendervouService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.w3c.dom.events.Event;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddRendevouz implements Initializable  {
    @FXML
    private TextField nom;
    @FXML
    private TextField desc;
    @FXML
    private TextField lieu;
    @FXML
    private TextField tel;

    @FXML
    private DatePicker datepickerdate;
    private ListeRendevouz listRend;


    public boolean validateInputs() {
        if (nom.getText().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("erreur !");
            alert1.setContentText("remplir le champ du nom  !");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        } else if (desc.getText().chars().allMatch( Character::isDigit)) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("erreur !");
            alert2.setContentText("remplir le champ du description ");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        } else if (lieu.getText().chars().allMatch( Character::isDigit)) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("erreur !");
            alert2.setContentText("remplir le champ du lieu ");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        }else if (tel.getText().isEmpty()) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("erreur !");
            alert2.setContentText("remplir le champ du telephone !");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        }
        return true;
    }

    public void ajouterRender(ActionEvent actionEvent) throws SQLException {
        if(validateInputs()) {
            rendervouService e = new rendervouService();
            Rendevouz f = new Rendevouz();

            f.setNom(nom.getText());
            f.setDescription(desc.getText());

            LocalDate localDate = datepickerdate.getValue();
            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

            f.setDate(sqlDate);
            f.setLieu(lieu.getText());

            f.setTelephone(tel.getText());
            System.out.println();
            e.add(f);
            listRend.refrechi();
            System.out.println(f);


        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setLisyRend(ListeRendevouz listRend) {
        this.listRend=listRend;
    }
}

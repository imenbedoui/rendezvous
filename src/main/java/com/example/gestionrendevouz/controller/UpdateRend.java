package com.example.gestionrendevouz.controller;

import com.example.gestionrendevouz.entity.Rendevouz;
import com.example.gestionrendevouz.service.rendervouService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateRend implements Initializable {
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
    private Rendevouz rendevouz;


    public Rendevouz getRendevouz() {
        return rendevouz;
    }

    public void setRendevouz(Rendevouz rendevouz) {
        this.rendevouz = rendevouz;
    }



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
    public void setData(Rendevouz rendevouz) {
        nom.setText(rendevouz.getNom());
        desc.setText(rendevouz.getDescription());
        lieu.setText(rendevouz.getLieu());
        tel.setText(rendevouz.getTelephone());
        datepickerdate.setValue(rendevouz.getDate().toLocalDate());

    }


    public void modifierRender(ActionEvent actionEvent) throws SQLException {
        if(validateInputs()) {
            rendervouService e = new rendervouService();
            Rendevouz f = rendevouz;
            f.setNom(nom.getText());
            f.setDescription(desc.getText());

            LocalDate localDate = datepickerdate.getValue();
            Date sqlDate = Date.valueOf(localDate);

            f.setDate(sqlDate);
            f.setLieu(lieu.getText());

            f.setTelephone(tel.getText());
            e.update(f,rendevouz.getId());
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




package com.example.gestionrendevouz.controller;

import com.example.gestionrendevouz.HelloApplication;
import com.example.gestionrendevouz.entity.Rendevouz;
import com.example.gestionrendevouz.service.rendervouService;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.events.Event;


import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ListeRendevouz implements Initializable {
    @FXML
    private TableColumn<Rendevouz, Integer> id;
    @FXML
    private TableColumn<Rendevouz, String> status;
    Connection cnx ;

    @FXML
    private TableView<Rendevouz> EVENTTAB;

    @FXML
    private TableColumn<Rendevouz, String> nom;

    @FXML
    private TableColumn<Rendevouz, String> description;

    @FXML
    private TableColumn<Rendevouz, String> lieu;
    @FXML
    private TableColumn<Rendevouz, String> date;

    @FXML
    private Parent borderPaneParent;
    @FXML
    private TableColumn<Rendevouz, String> telephone;

    @FXML
    private javafx.scene.control.TextField search;
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Rendevouz rendevouz = null ;
    ObservableList<Rendevouz> eventlist = FXCollections.observableArrayList();

    rendervouService e = new rendervouService();





    @FXML
    private Parent root;

    //scene ajout
    public void AddRendevou(MouseEvent mouseEvent) throws IOException {
        try {
            FXMLLoader fxmlLoader =new FXMLLoader(HelloApplication.class.getResource("AddRendevouz.fxml"));
            Parent view=fxmlLoader.load();

            AddRendevouz addRendevouz=fxmlLoader.getController();
            addRendevouz.setLisyRend(this);
            Scene scene = new Scene(view);
            Stage stage = new Stage();
            stage.setMinHeight(400);
            stage.setMinWidth(400);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);

            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Add a Consultation");

            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }



    public void refrechi() {
        EVENTTAB.setItems(FXCollections.observableArrayList(e.afficher()));

        id.setCellValueFactory(new PropertyValueFactory<Rendevouz,Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Rendevouz,String>("nom"));

        description.setCellValueFactory(new PropertyValueFactory<Rendevouz,String>("description"));
        lieu.setCellValueFactory(new PropertyValueFactory<Rendevouz,String>("lieu"));

        date.setCellValueFactory(new PropertyValueFactory<Rendevouz,String>("date"));
        telephone.setCellValueFactory(new PropertyValueFactory<Rendevouz,String>("telephone"));
        status.setCellValueFactory(new PropertyValueFactory<Rendevouz,String>("status"));


        EVENTTAB.setEditable(true);

    }



// recherchee(metier)
    public void filter(KeyEvent keyEvent) {

        String searchText = search.getText() != null ? search.getText().toLowerCase() : "";
        ObservableList<Rendevouz> filteredPeople = FXCollections.observableArrayList(e.afficher());

        ObservableList<Rendevouz> newdata = filteredPeople.stream()
                .filter(n -> {
                    String nom = n.getNom() != null ? n.getNom().toLowerCase() : "";
                    String description = n.getDescription() != null ? n.getDescription().toLowerCase() : "";
                    String lieu = n.getLieu() != null ? n.getLieu().toLowerCase() : "";

                    return nom.contains(searchText)
                            || nom.equals(searchText)
                            || description.contains(searchText)
                            || description.equals(searchText)
                            || lieu.contains(searchText)
                            || lieu.equals(searchText);
                })
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        EVENTTAB.setItems(newdata);

    }

    public void imprimer(MouseEvent mouseEvent) {
    }


    //refrech
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        refrechi();


    }


    public void supprimer(MouseEvent actionEvent) throws SQLException {
        int myIndex = EVENTTAB.getSelectionModel().getSelectedIndex();
        String nom = EVENTTAB.getSelectionModel().getSelectedItem().getNom();
        System.out.println(EVENTTAB.getSelectionModel().getSelectedItem());

        e.supprimerid(EVENTTAB.getSelectionModel().getSelectedItem().getId());
        refrechi();

    }

//PDF
    public void generer(MouseEvent mouseEvent) throws IOException {
        long millis = System.currentTimeMillis();
        java.sql.Date DateRapport = new java.sql.Date(millis);

        String DateLyoum = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(DateRapport);
        System.out.println("Date d'aujourdhui : " + DateLyoum);

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(String.valueOf(DateLyoum + ".pdf")));//yyyy-MM-dd

            document.open();
            Paragraph ph1 = new Paragraph("list des rendevouz :" + DateRapport);
            Paragraph ph2 = new Paragraph("imeeeeeeen");
            PdfPTable table = new PdfPTable(5);

            //On créer l'objet cellule.
            PdfPCell cell;

            //contenu du tableau.
            table.addCell("nom");
            table.addCell("Description");
            table.addCell("lieu");
            table.addCell("Date ");
            table.addCell("Telephone");




            Rendevouz r = new Rendevouz();
            e.afficher().forEach(e -> {
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(String.valueOf(e.getNom()));
                table.addCell(String.valueOf(e.getDescription()));
                table.addCell(String.valueOf(e.getLieu()));
                table.addCell(String.valueOf(e.getDate()));
                table.addCell(String.valueOf(e.getTelephone()));



            });
            document.add(ph1);
            document.add(ph2);
            document.add(table);
        } catch (Exception e) {
            System.out.println(e);
        }
        document.close();

        /// choice Open File
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fichier PDF généré");
        alert.setHeaderText("Le fichier PDF a été généré avec succès !");
        alert.setContentText("Voulez-vous ouvrir le fichier PDF maintenant ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            File file = new File(DateLyoum + ".pdf");
            Desktop desktop = Desktop.getDesktop();
            if (file.exists()) //checks file exists or not
            {
                desktop.open(file); //opens the specified file
            }

        }



    }

    public void updateRendevou(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader =new FXMLLoader(HelloApplication.class.getResource("UpdateRend.fxml"));
            Parent view=fxmlLoader.load();
            UpdateRend updateRend=fxmlLoader.getController();
            updateRend.setLisyRend(this);
            updateRend.setRendevouz(EVENTTAB.getSelectionModel().getSelectedItem());
            System.out.println(EVENTTAB.getSelectionModel().getSelectedItem());
            updateRend.setData(EVENTTAB.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(view);
            Stage stage = new Stage();
            stage.setMinHeight(400);
            stage.setMinWidth(400);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Modifier avec succe");

            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}

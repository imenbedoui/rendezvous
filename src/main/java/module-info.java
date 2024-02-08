module com.example.gestionrendevouz {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires itextpdf;
    requires java.desktop;


    opens com.example.gestionrendevouz to javafx.fxml;
    exports com.example.gestionrendevouz;
    exports com.example.gestionrendevouz.entity;
    opens com.example.gestionrendevouz.entity to javafx.fxml;
    exports com.example.gestionrendevouz.controller;
    opens com.example.gestionrendevouz.controller to javafx.fxml;
}
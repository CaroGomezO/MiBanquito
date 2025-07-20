module org.example.entregafinal {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome6;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.entregafinal to javafx.fxml;
    opens org.example.entregafinal.controller to javafx.fxml;
    exports org.example.entregafinal;
    exports org.example.entregafinal.controller;
    exports org.example.entregafinal.controller.admin;
    exports org.example.entregafinal.controller.cliente;
    opens org.example.entregafinal.controller.admin to javafx.fxml;
    opens org.example.entregafinal.controller.cliente to javafx.fxml;
    exports org.example.entregafinal.alertas;
    opens org.example.entregafinal.alertas to javafx.fxml;
}
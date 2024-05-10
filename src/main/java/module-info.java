module fiit.stuba.sk.ehsnr {
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

    exports fiit.stuba.sk.ehsnr.GUI;
    opens fiit.stuba.sk.ehsnr.GUI to javafx.fxml;
    exports fiit.stuba.sk.ehsnr.AL;
    opens fiit.stuba.sk.ehsnr.AL to javafx.fxml;
    exports fiit.stuba.sk.ehsnr.Controller;
    opens fiit.stuba.sk.ehsnr.Controller to javafx.fxml;
}
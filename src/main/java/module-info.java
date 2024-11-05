module com.sentomero.sufeeds.sents_sufeeds {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires fontawesomefx;
    requires java.sql;

    opens com.sentomero.sufeeds.sents_sufeeds to javafx.fxml;
    exports com.sentomero.sufeeds.sents_sufeeds;
    exports com.sentomero.sufeeds.sents_sufeeds.Controllers;
    opens com.sentomero.sufeeds.sents_sufeeds.Controllers to javafx.fxml;
}
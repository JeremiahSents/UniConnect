module com.sentomero.sufeeds.sents_sufeeds {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.sentomero.sufeeds.sents_sufeeds to javafx.fxml;
    exports com.sentomero.sufeeds.sents_sufeeds;
}
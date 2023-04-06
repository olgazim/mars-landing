module com.example.marslanding {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.marslanding to javafx.fxml;
    exports com.example.marslanding;
    exports com.example.marslanding.model;
    opens com.example.marslanding.model to javafx.fxml;
    exports com.example.marslanding.view;
    opens com.example.marslanding.view to javafx.fxml;
}
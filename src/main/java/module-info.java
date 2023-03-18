module com.example.marslanding {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.marslanding to javafx.fxml;
    exports com.example.marslanding;
}
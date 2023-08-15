module com.example.sampc482pa {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sampc482pa to javafx.fxml;
    exports com.example.sampc482pa;
}
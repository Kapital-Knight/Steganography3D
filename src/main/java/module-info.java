module com.example.steganography3d {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.steganography3d to javafx.fxml;
    exports com.example.steganography3d;
}
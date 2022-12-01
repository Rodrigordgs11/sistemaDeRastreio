module pt.ipvc.rastreio.sistemaderastreio {
    requires javafx.controls;
    requires javafx.fxml;


    opens pt.ipvc.rastreio.sistemaderastreio to javafx.fxml;
    exports pt.ipvc.rastreio.sistemaderastreio;
    exports pt.ipvc.rastreio.sistemaderastreio.controller;
    opens pt.ipvc.rastreio.sistemaderastreio.controller to javafx.fxml;
}
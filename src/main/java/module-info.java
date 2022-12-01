module pt.ipvc.rastreio.sistemaderastreio {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens pt.ipvc.rastreio.sistemaderastreio to javafx.fxml;
    exports pt.ipvc.rastreio.sistemaderastreio;
}
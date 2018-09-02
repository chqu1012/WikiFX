package de.dc.fx.spring.wiki.ui.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public abstract class BaseWikiPageController {

    @FXML
    protected AnchorPane previewPane;

    @FXML
    protected TextArea textArea;

    @FXML
    protected abstract void onSaveWikiPageButton(ActionEvent event);

}

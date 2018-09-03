package de.dc.fx.spring.wiki.ui.controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public abstract class BaseWikiPageController {

	@FXML AnchorPane root;
	@FXML Label titleLabel;
	@FXML Label categoryLabel;
	@FXML SplitPane editorSplitPane;
	
	@FXML 
	protected TabPane tabPane;
	
    @FXML
    protected AnchorPane previewPane;

    @FXML
    protected TextArea textArea;

    @FXML
    protected abstract void onSaveWikiPageButton(ActionEvent event);

    protected WebEngine engine;
    protected WebEngine previewEngine;
    
	public void initialize() {
		Platform.runLater(() -> {
			WebView view = new WebView();
			engine = view.getEngine();
			AnchorPane.setBottomAnchor(view, 0d);
			AnchorPane.setTopAnchor(view, 0d);
			AnchorPane.setLeftAnchor(view, 0d);
			AnchorPane.setRightAnchor(view, 0d);
			previewPane.getChildren().add(view);
			WebView preview = new WebView();
			previewEngine = preview.getEngine();
			editorSplitPane.getItems().add(preview);
		});
	}

}

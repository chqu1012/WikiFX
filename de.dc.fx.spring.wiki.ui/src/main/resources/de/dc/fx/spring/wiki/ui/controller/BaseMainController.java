package de.dc.fx.spring.wiki.ui.controller;
import de.dc.fx.spring.wiki.ui.control.TreeViewer;
import de.dc.fx.spring.wiki.ui.model.NavigationModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public abstract class BaseMainController<T> {

    @FXML
    protected TreeViewer<NavigationModel> treeView;

    @FXML
    protected AnchorPane contentPane;

    @FXML
    protected AnchorPane wikiPage;

    @FXML
    protected AnchorPane wikiOverview;

    @FXML
    protected Label titleLabel;

    @FXML
    protected TextField searchText;

    @FXML
    protected Label userLabel;

    @FXML
    protected ImageView preferencesImage;
}

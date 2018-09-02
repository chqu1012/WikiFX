package de.dc.fx.spring.wiki.ui.controller;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

public abstract class BaseWikioverviewController<T> {

    @FXML
    protected ListView<T> lastCreateListView;

    @FXML
    protected ListView<T> lastUpdatedListView;

    @FXML
    protected ListView<T> mostVisitedListView;

    @FXML
    protected TableView<T> wikiTableView;

}

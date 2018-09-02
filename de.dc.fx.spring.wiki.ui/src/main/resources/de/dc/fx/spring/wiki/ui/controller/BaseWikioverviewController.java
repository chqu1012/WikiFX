package de.dc.fx.spring.wiki.ui.controller;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

public abstract class BaseWikioverviewController {

    @FXML
    protected ListView<?> lastCreateListView;

    @FXML
    protected ListView<?> lastUpdatedListView;

    @FXML
    protected ListView<?> mostVisitedListView;

    @FXML
    protected TableView<?> wikiTableView;

}

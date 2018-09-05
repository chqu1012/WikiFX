package de.dc.fx.spring.wiki.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.dc.fx.spring.wiki.ui.factory.WikiPageListCell;
import de.dc.fx.spring.wiki.ui.model.NavigationModel;
import de.dc.fx.spring.wiki.ui.model.WikiPage;
import de.dc.fx.spring.wiki.ui.repository.WikiPageRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;

@Controller
public class WikioverviewController extends BaseWikioverviewController<WikiPage> {

	@Autowired WikiPageRepository wikiPageRepository;
	@Autowired MainController mainController;
	
	protected ObservableList<WikiPage> masterData = FXCollections.observableArrayList();
	protected ObservableList<WikiPage> lastCreatedData = FXCollections.observableArrayList();
	protected ObservableList<WikiPage> lastUpdatedData = FXCollections.observableArrayList();
	protected ObservableList<WikiPage> mostVisitedData = FXCollections.observableArrayList();
	
	public void initialize() {
		masterData.addAll(wikiPageRepository.findAll());
		List<WikiPage> items = wikiPageRepository.findAllByOrderByCreatedDesc();
		int counts = items.size()%11;
		lastCreatedData.addAll(items.subList(0, counts));
		
		lastCreateListView.setItems(lastCreatedData);
		lastCreateListView.setCellFactory(param -> new WikiPageListCell());
		
		lastUpdatedListView.setItems(lastUpdatedData);
		lastUpdatedListView.setCellFactory(param -> new WikiPageListCell());
		
		mostVisitedListView.setItems(mostVisitedData);
		mostVisitedListView.setCellFactory(param -> new WikiPageListCell());
		
		wikiTableView.setItems(masterData);
	}

	@FXML public void onWikiTableMouseClicked(MouseEvent event) {
		if (event.getClickCount()==2) {
			WikiPage page = wikiTableView.getSelectionModel().getSelectedItem();
			mainController.wikiPageToFront(page);
		}
	}
	
	public void addWikiPage(WikiPage page) {
		masterData.add(page);
	}
	
	public void settAllWikiPages() {
		masterData.clear();
		List<WikiPage> pages = wikiPageRepository.findAll();
		masterData.addAll(pages);
	}
	
	public void filterByNavigation(NavigationModel navigationModel) {
		masterData.clear();
		List<WikiPage> pages = wikiPageRepository.findAllByNavigationId(navigationModel.getId());
		masterData.addAll(pages);
	}

	@FXML public void onLastCreatedListClicked(MouseEvent event) {
		if (event.getClickCount()==2) {
			WikiPage page = lastCreateListView.getSelectionModel().getSelectedItem();
			mainController.wikiPageToFront(page);
		}
	}

	@FXML public void onLastUpdatedListClicked(MouseEvent event) {
		if (event.getClickCount()==2) {
			WikiPage page = lastUpdatedListView.getSelectionModel().getSelectedItem();
			mainController.wikiPageToFront(page);
		}
	}

	@FXML public void onMostVisitiedClicked(MouseEvent event) {
		if (event.getClickCount()==2) {
			WikiPage page = mostVisitedListView.getSelectionModel().getSelectedItem();
			mainController.wikiPageToFront(page);
		}
	}
}

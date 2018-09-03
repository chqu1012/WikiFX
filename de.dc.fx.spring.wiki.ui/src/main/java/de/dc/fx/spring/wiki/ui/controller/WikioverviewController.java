package de.dc.fx.spring.wiki.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.dc.fx.spring.wiki.ui.model.NavigationModel;
import de.dc.fx.spring.wiki.ui.model.WikiPage;
import de.dc.fx.spring.wiki.ui.repository.WikiPageRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

@Controller
public class WikioverviewController extends BaseWikioverviewController<WikiPage> {

	@Autowired WikiPageRepository wikiPageRepository;
	@Autowired MainController mainController;
	
	protected ObservableList<WikiPage> masterData = FXCollections.observableArrayList();
	
	public void initialize() {
		masterData.addAll(wikiPageRepository.findAll());
		
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
	
	public void filterByNavigation(NavigationModel navigationModel) {
		masterData.clear();
		List<WikiPage> pages = wikiPageRepository.findAllByNavigationId(navigationModel.getId());
		masterData.addAll(pages);
	}
}

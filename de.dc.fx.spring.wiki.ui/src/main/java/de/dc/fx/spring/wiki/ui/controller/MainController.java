package de.dc.fx.spring.wiki.ui.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.dc.fx.spring.wiki.ui.control.provider.TreeViewContentProvider;
import de.dc.fx.spring.wiki.ui.control.provider.TreeViewerLabelProvider;
import de.dc.fx.spring.wiki.ui.model.NavigationModel;
import de.dc.fx.spring.wiki.ui.model.WikiPage;
import de.dc.fx.spring.wiki.ui.provider.NavigationModelProvider;
import de.dc.fx.spring.wiki.ui.repository.NavigationModelRepository;
import de.dc.fx.spring.wiki.ui.repository.WikiPageRepository;
import de.dc.fx.spring.wiki.ui.util.WikiPageUtil;
import javafx.event.ActionEvent;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

@Controller
public class MainController extends BaseMainController<NavigationModel> {

	@Autowired NavigationModelRepository navigationModelRepository;
	@Autowired WikiPageRepository wikiPageRepository;

	@Autowired WikiPageUtil wikiPageUtil;
	
	@Autowired WikiPageController wikiPageController;
	@Autowired WikioverviewController wikioverviewController;
	
	NavigationModelProvider provider = new NavigationModelProvider();

	public void initialize() {
		initNavigation();

		treeView.setContentProvider(new TreeViewContentProvider());
		treeView.setLabelProvider(new TreeViewerLabelProvider());

		loadModel(provider.buildTree(navigationModelRepository.findAll()));
	}

	private void initNavigation() {
		if (navigationModelRepository.findAll().size() == 0) {
			NavigationModel model = new NavigationModel("NAVIGATION", null);
			model = navigationModelRepository.save(model);
		}
	}

	public void onNewCateory(ActionEvent event) {
		NavigationModel selection = treeView.getSelectionModel().getSelectedItem().getValue();
		Long selectedId = selection.getId();
		
		TextInputDialog dialog = new TextInputDialog("Neue Seite*");
		dialog.setTitle("Text Input Dialog");
		dialog.setHeaderText("Text Input Dialog");
		dialog.setContentText("Bitte geben sie einen Namen für die neue Seite ein:");
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(name ->{
			if (selectedId!=null && name!=null) {
				NavigationModel model = new NavigationModel(name, selectedId);
				navigationModelRepository.save(model);
			}
			
		});

		loadModel(provider.buildTree(navigationModelRepository.findAll()));
	}

	private void loadModel(NavigationModel input) {
		treeView.setInput(input);
		treeView.getRoot().setExpanded(true);
		treeView.getRoot().getChildren().forEach(this::expandNodeAndChilren);
	}

	private void expandNodeAndChilren(TreeItem<NavigationModel> node) {
		node.setExpanded(true);
		node.getChildren().forEach(this::expandNodeAndChilren);
	}

	@FXML public void onNewPageButton(ActionEvent event) {
		TreeItem<NavigationModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
		Long navigationId = selectedItem.getValue().getId();
		
		TextInputDialog dialog = new TextInputDialog("Neue Seite*");
		dialog.setTitle("Text Input Dialog");
		dialog.setHeaderText("Text Input Dialog");
		dialog.setContentText("Bitte geben sie einen Namen für die neue Seite ein:");

		Optional<String> result = dialog.showAndWait();
		result.ifPresent(name ->{
			if (navigationId!=null && name!=null) {
				WikiPage page = new WikiPage(name, navigationId, LocalDateTime.now());
				wikiPageRepository.save(page);
				wikiPageUtil.createFolder(page);
				wikiPageToFront(page);
				wikioverviewController.addWikiPage(page);
			}
		});
		
	}

	public void wikiPageToFront(WikiPage page) {
		wikiPage.toFront();
		wikiPageController.setWikiPage(page);
	}

	@FXML public void onTreeViewerMouseClicked(MouseEvent event) {
		NavigationModel selection = treeView.getSelectionModel().getSelectedItem().getValue();
		wikioverviewController.filterByNavigation(selection);
		wikiOverview.toFront();
	}
}

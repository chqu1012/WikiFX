package de.dc.fx.spring.wiki.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.dc.fx.spring.wiki.ui.control.provider.TreeViewContentProvider;
import de.dc.fx.spring.wiki.ui.control.provider.TreeViewerLabelProvider;
import de.dc.fx.spring.wiki.ui.model.NavigationModel;
import de.dc.fx.spring.wiki.ui.provider.NavigationModelProvider;
import de.dc.fx.spring.wiki.ui.repository.NavigationModelRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;

@Controller
public class MainController extends BaseMainController<NavigationModel> {

	@Autowired
	NavigationModelRepository navigationModelRepository;

	NavigationModelProvider provider = new NavigationModelProvider();

	public void initialize() {
		initNavigation();

		treeView.setContentProvider(new TreeViewContentProvider());
		treeView.setLabelProvider(new TreeViewerLabelProvider());

		loadModel();
	}

	private void initNavigation() {
		if (navigationModelRepository.findAll().size() == 0) {
			NavigationModel model = new NavigationModel("NAVIGATION", null);
			model = navigationModelRepository.save(model);

			NavigationModel dashboard = new NavigationModel("DASHBOARD", model.getId());
			NavigationModel meinBereich = new NavigationModel("MEIN BEREICH", model.getId());
			dashboard = navigationModelRepository.save(dashboard);
			meinBereich = navigationModelRepository.save(meinBereich);

			NavigationModel startseite = new NavigationModel("Startseite", dashboard.getId());
			NavigationModel unternehmensdaten = new NavigationModel("Unternehmensdaten", dashboard.getId());
			NavigationModel benutzer = new NavigationModel("Benutzer", dashboard.getId());
			NavigationModel vertragsuebersich = new NavigationModel("Vertragsübersicht", dashboard.getId());
			NavigationModel rechnungen = new NavigationModel("Rechnungen", dashboard.getId());
			NavigationModel bestellhistorie = new NavigationModel("Bestellhistorie", dashboard.getId());
			NavigationModel statistiken = new NavigationModel("Statistiken", dashboard.getId());
			NavigationModel lizenzen = new NavigationModel("Lizenzen bestellen", dashboard.getId());
			navigationModelRepository.save(startseite);
			navigationModelRepository.save(unternehmensdaten);
			navigationModelRepository.save(benutzer);
			navigationModelRepository.save(vertragsuebersich);
			navigationModelRepository.save(rechnungen);
			navigationModelRepository.save(bestellhistorie);
			navigationModelRepository.save(statistiken);
			navigationModelRepository.save(lizenzen);
		}
	}

	public void onNewCateory(ActionEvent event) {
		NavigationModel selection = treeView.getSelectionModel().getSelectedItem().getValue();
		Long selectedId = selection.getId() == null ? -1L : selection.getId();
		NavigationModel model = new NavigationModel("Hello World", selectedId);
		navigationModelRepository.save(model);

		loadModel();
	}

	private void loadModel() {
		NavigationModel input = provider.buildTree(navigationModelRepository.findAll());
		treeView.setInput(input);
		treeView.getRoot().setExpanded(true);
		treeView.getRoot().getChildren().forEach(this::expandNodeAndChilren);
	}

	private void expandNodeAndChilren(TreeItem<NavigationModel> node) {
		node.setExpanded(true);
		node.getChildren().forEach(this::expandNodeAndChilren);
	}
}

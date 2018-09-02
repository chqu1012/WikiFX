package de.dc.fx.spring.wiki.ui.control.provider;

import java.util.Arrays;
import java.util.List;

import de.dc.fx.spring.wiki.ui.model.NavigationModel;

public class TreeViewContentProvider implements IContentProvider<NavigationModel> {

	@Override
	public boolean hasChildren(NavigationModel element) {
		if (element==null) {
			return false;
		}
		return element.getChildren().size()>0;
	}

	@Override
	public NavigationModel getParent(NavigationModel element) {
		return element.getParent();
	}

	@Override
	public List<NavigationModel> getElements(NavigationModel inputElement) {
		return Arrays.asList(inputElement);
	}

	@Override
	public List<NavigationModel> getChildren(NavigationModel parentElement) {
		return parentElement.getChildren();
	}

}

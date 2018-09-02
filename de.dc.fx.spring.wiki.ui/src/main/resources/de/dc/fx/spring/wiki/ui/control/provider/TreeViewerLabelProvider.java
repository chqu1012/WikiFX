package de.dc.fx.spring.wiki.ui.control.provider;

import de.dc.fx.spring.wiki.ui.model.NavigationModel;
import javafx.scene.image.ImageView;

public class TreeViewerLabelProvider implements ILabelProvider<NavigationModel>{

	@Override
	public String getText(NavigationModel data) {
		return data.getName();
	}

	@Override
	public ImageView getImage(NavigationModel data) {
		return null;
	}

}

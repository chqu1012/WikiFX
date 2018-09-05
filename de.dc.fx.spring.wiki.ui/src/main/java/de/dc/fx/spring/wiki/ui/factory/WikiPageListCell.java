package de.dc.fx.spring.wiki.ui.factory;

import de.dc.fx.spring.wiki.ui.model.WikiPage;
import javafx.scene.control.ListCell;

public class WikiPageListCell extends ListCell<WikiPage>{

	@Override
	protected void updateItem(WikiPage item, boolean empty) {
		super.updateItem(item, empty);
		if (item==null || empty) {
			setText(null);
		}else {
			setText(item.getName());
		}
	}
}

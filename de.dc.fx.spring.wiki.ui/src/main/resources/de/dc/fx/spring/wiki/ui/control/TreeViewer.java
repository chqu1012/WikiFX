package de.dc.fx.spring.wiki.ui.control;

import java.util.List;

import de.dc.fx.spring.wiki.ui.control.provider.IContentProvider;
import de.dc.fx.spring.wiki.ui.control.provider.ILabelProvider;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class TreeViewer<T> extends TreeView<T> implements IViewer<T> {

	private ILabelProvider<T> labelprovider;
	private IContentProvider<T> contentprovider;
	private TreeItem<T> root = new TreeItem<>();

	public TreeViewer() {
		setShowRoot(false);
	}

	@Override
	public void setInput(T input) {
		List<T> elements = contentprovider.getElements(input);
		root.getChildren().clear();
		elements.stream().forEach(element -> buildTree(root, element));
		setRoot(root);
		setCellFactory(view -> new TreeCell<T>() {
			protected void updateItem(T item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setText(null);
				} else if(item!=null){
					setText(labelprovider.getText(item));
				}
			}
		});
	}

	private void buildTree(TreeItem<T> parentItem, T element) {
		javafx.scene.Node image = labelprovider.getImage(element);
		TreeItem<T> currentItem = new TreeItem<T>(element, image);
		parentItem.getChildren().add(currentItem);
		// Build current item
		// Link with parent
		if (contentprovider.hasChildren(element)) {
			// Iterate over all childs
			List<T> children = contentprovider.getChildren(element);
			children.forEach(current -> buildTree(currentItem, current));
		}
	}

	@Override
	public void setLabelProvider(ILabelProvider<T> labelprovider) {
		this.labelprovider = labelprovider;
	}

	@Override
	public void setContentProvider(IContentProvider<T> contentprovider) {
		this.contentprovider = contentprovider;
	}
}

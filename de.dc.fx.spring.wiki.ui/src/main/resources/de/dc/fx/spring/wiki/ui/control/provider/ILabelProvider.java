package de.dc.fx.spring.wiki.ui.control.provider;

import javafx.scene.image.*;

public interface ILabelProvider<T> {
	String getText(T data);
	ImageView getImage(T data);
}

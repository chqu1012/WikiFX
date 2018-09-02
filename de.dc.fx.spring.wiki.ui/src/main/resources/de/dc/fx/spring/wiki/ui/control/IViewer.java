package de.dc.fx.spring.wiki.ui.control;

import de.dc.fx.spring.wiki.ui.control.provider.IContentProvider;
import de.dc.fx.spring.wiki.ui.control.provider.ILabelProvider;

public interface IViewer<T> {
  void setInput(T input);
  void setLabelProvider(ILabelProvider<T> labelprovider);
  void setContentProvider(IContentProvider<T> contentprovider);
}

package de.dc.fx.spring.wiki.ui.control.provider;

import java.util.List;

public interface IContentProvider<T> {
  boolean hasChildren(T element);
  T getParent(T element);
  List<T> getElements(T inputElement);
  List<T> getChildren(T parentElement);
}

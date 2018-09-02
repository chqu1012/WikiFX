package de.dc.fx.spring.wiki.ui.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.dc.fx.spring.wiki.ui.model.NavigationModel;

public class NavigationModelProvider {

	private Map<Long, NavigationModel> registry = new HashMap<Long, NavigationModel>();
	
	public NavigationModel buildTree(List<NavigationModel> models) {
		registry.clear();
		Long firstElement = null;
		for (NavigationModel model : models) {
			if (firstElement==null) {
				firstElement=model.getId();
			}
			registry.put(model.getId(), model);
		}
		registry.values().forEach(nav->{
			Long parentId = nav.getId();
			List<NavigationModel> filteredList = models.stream().filter(e->e.getParentId()==parentId).collect(Collectors.toList());
			nav.getChildren().addAll(filteredList);
		});
		return registry.get(firstElement);
	}
}

package de.dc.fx.spring.wiki.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class NavigationModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NAVIGATION_ID", nullable = false, columnDefinition = "BIGINT")
	private Long id;
	
	@Column(nullable = false)
	private String name;

	@Column(nullable = true)
	private String image;
	
	@Column(nullable = true)
	private Long parentId;

	@Transient
	private NavigationModel parent;
	
	@Transient
	private List<NavigationModel> children = new ArrayList<>();
	
	public NavigationModel() {
	}

	public NavigationModel(String name, Long parentId) {
		this.name = name;
		this.parentId = parentId;
	}
	
	public NavigationModel getParent() {
		return parent;
	}

	public void setParent(NavigationModel parent) {
		this.parent = parent;
	}

	public List<NavigationModel> getChildren() {
		return children;
	}

	public void setChildren(List<NavigationModel> children) {
		this.children = children;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}

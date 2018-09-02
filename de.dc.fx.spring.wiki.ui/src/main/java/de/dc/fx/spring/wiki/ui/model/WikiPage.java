package de.dc.fx.spring.wiki.ui.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WikiPage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "WIKI_PAGE_ID", nullable = false, columnDefinition = "BIGINT")
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private LocalDateTime created;

	@Column(nullable = true)
	private LocalDateTime updated;

	public WikiPage() {
	}

	public WikiPage(String name, LocalDateTime created) {
		this.name = name;
		this.created = created;
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

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}
}

package de.dc.fx.spring.wiki.ui.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.dc.fx.spring.wiki.ui.model.WikiPage;

@Repository
public interface WikiPageRepository extends JpaRepository<WikiPage, Long>{

}
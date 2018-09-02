package de.dc.fx.spring.wiki.ui.controller;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

import de.dc.fx.spring.wiki.ui.model.WikiPage;
import de.dc.fx.spring.wiki.ui.util.WikiPageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

@Controller
public class WikiPageController extends BaseWikiPageController {

	@Autowired WikiPageUtil wikiPageUtil;
	
	private WikiPage currentPage;

	@Override
	protected void onSaveWikiPageButton(ActionEvent event) {
        parseContent(); 
        
        if (currentPage!=null) {
        	wikiPageUtil.writeWikiPageContent(currentPage, textArea.getText());
		}
        
        tabPane.getSelectionModel().selectFirst();
	}

	private void parseContent() {
		LinkedList<Extension> extensions = new LinkedList<Extension>();
	    extensions.add(TablesExtension.create());
	    extensions.add(AttributesExtension.create());
	    extensions.add(WikiLinkExtension.create());
	    extensions.add(TaskListExtension.create());
	    
		Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(textArea.getText());
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        String html = renderer.render(document);
        engine.loadContent(getContent(html), "text/html");
	}
	
	public void setWikiPage(WikiPage wikiPage) {
		this.currentPage=wikiPage;
		String content = wikiPageUtil.getWikiPageContent(wikiPage);
		textArea.setText(content);
		parseContent();
		titleLabel.setText(wikiPage.getName());
		root.requestFocus();
	}
	
	public static String getContent(String flexmarkContent) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>\r\n" + 
				"<head>\r\n" + 
				"<meta charset=\"UTF-8\">\r\n" + 
				"<link rel=\"stylesheet\" href=\"layout-fx.css\">\r\n" + 
				"<link rel=\"stylesheet\" href=\"default-fx.css\">\r\n" + 
				"<link rel=\"stylesheet\" href=\"prism-default.css\">\r\n" + 
				"<title>Document Title</title>\r\n" + 
				"<script src=\"prism.js\"></script>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				flexmarkContent + 
				"</body>\r\n" + 
				"</html>");
		return sb.toString();
	}

	@FXML public void onWikiPage(KeyEvent event) {}

	@FXML public void onWikiPageKeyReleased(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ESCAPE)) {
			root.toBack();
		}
	}

	@FXML public void onBackButton(ActionEvent event) {
		root.toBack();
	}
}

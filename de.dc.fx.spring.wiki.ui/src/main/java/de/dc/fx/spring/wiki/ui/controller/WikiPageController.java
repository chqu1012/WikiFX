package de.dc.fx.spring.wiki.ui.controller;

import java.util.LinkedList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.admonition.AdmonitionExtension;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.aside.AsideExtension;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
import com.vladsch.flexmark.ext.escaped.character.EscapedCharacterExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gfm.users.GfmUsersExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.ext.xwiki.macros.MacroExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.DataHolder;

import de.dc.fx.spring.wiki.ui.model.NavigationModel;
import de.dc.fx.spring.wiki.ui.model.WikiPage;
import de.dc.fx.spring.wiki.ui.preview.MarkDownPreview;
import de.dc.fx.spring.wiki.ui.repository.NavigationModelRepository;
import de.dc.fx.spring.wiki.ui.util.WikiPageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

@Controller
public class WikiPageController extends BaseWikiPageController {

	@Autowired WikiPageUtil wikiPageUtil;
	@Autowired MarkDownPreview preview;
	
	@Autowired NavigationModelRepository navigationModelRepository;
	
	private WikiPage currentPage;

	private Parser parser;
	private HtmlRenderer renderer;
	
	static DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(Extensions.ALL);

	@Override
	public void initialize() {
		super.initialize();
		
		parser = Parser.builder(OPTIONS).build();
		renderer = HtmlRenderer.builder(OPTIONS).build();
		textArea.textProperty().addListener(o ->parseContent());
	}
	
	@Override
	protected void onSaveWikiPageButton(ActionEvent event) {
        parseContent(); 
        
        if (currentPage!=null) {
        	wikiPageUtil.writeWikiPageContent(currentPage, textArea.getText());
		}
        
        tabPane.getSelectionModel().selectFirst();
	}

	private void parseContent() {
        Node document = parser.parse(textArea.getText());
        String html = renderer.render(document);
        String content = preview.getContent(html);
        System.out.println(content);
		previewEngine.loadContent(content, "text/html");
        engine.loadContent(content, "text/html");
	}
	
	public void setWikiPage(WikiPage wikiPage) {
		this.currentPage=wikiPage;
		String content = wikiPageUtil.getWikiPageContent(wikiPage);
		textArea.setText(content);
		parseContent();
		Optional<NavigationModel> navigation = navigationModelRepository.findById(wikiPage.getId());
		navigation.ifPresent(nav->categoryLabel.setText(nav.getName()));
		titleLabel.setText(wikiPage.getName());
		root.requestFocus();
	}
	
//	public static String getContent(String flexmarkContent) {
//		StringBuffer sb = new StringBuffer();
//		sb.append("<html>\r\n" + 
//				"<head>\r\n" + 
//				"<meta charset=\"UTF-8\">\r\n" + 
//				"<link rel=\"stylesheet\" href=\"layout-fx.css\">\r\n" + 
//				"<link rel=\"stylesheet\" href=\"default-fx.css\">\r\n" + 
//				"<link rel=\"stylesheet\" href=\"prism-default.css\">\r\n" + 
//				"<title>Document Title</title>\r\n" + 
//				"<script src=\"prism.js\"></script>\r\n" + 
//				"</head>\r\n" + 
//				"<body>\r\n" + 
//				flexmarkContent + 
//				"</body>\r\n" + 
//				"</html>");
//		return sb.toString();
//	}

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

package de.dc.fx.spring.wiki.ui.controller;

import java.util.LinkedList;

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
import com.vladsch.flexmark.ext.gfm.issues.GfmIssuesExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.SubscriptExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gfm.users.GfmUsersExtension;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.ext.jekyll.front.matter.JekyllFrontMatterExtension;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.ext.media.tags.MediaTagsExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.ext.xwiki.macros.MacroExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.ext.youtube.embedded.YouTubeLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.jira.converter.JiraConverterExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.superscript.SuperscriptExtension;
import com.vladsch.flexmark.youtrack.converter.YouTrackConverterExtension;

import de.dc.fx.spring.wiki.ui.model.WikiPage;
import de.dc.fx.spring.wiki.ui.preview.MarkDownPreview;
import de.dc.fx.spring.wiki.ui.util.WikiPageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

@Controller
public class WikiPageController extends BaseWikiPageController {

	@Autowired WikiPageUtil wikiPageUtil;
	@Autowired MarkDownPreview preview;
	
	private WikiPage currentPage;

	private Parser parser;

	private HtmlRenderer renderer;

	@Override
	public void initialize() {
		super.initialize();
		LinkedList<Extension> extensions = new LinkedList<Extension>();
	    extensions.add(AbbreviationExtension.create());
//	    extensions.add(AdmonitionExtension.create());
//	    extensions.add(AnchorLinkExtension.create());
	    extensions.add(AsideExtension.create());
//	    extensions.add(AttributesExtension.create());
	    extensions.add(AutolinkExtension.create());
	    extensions.add(DefinitionExtension.create());
	    extensions.add(EmojiExtension.create());
//	    extensions.add(EnumeratedReferenceExtension.create());
//	    extensions.add(EscapedCharacterExtension.create());
	    extensions.add(FootnoteExtension.create());
//	    extensions.add(GfmIssuesExtension.create());
	    extensions.add(GfmUsersExtension.create());
//	    extensions.add(InsExtension.create());
//	    extensions.add(JekyllFrontMatterExtension.create());
//	    extensions.add(JekyllTagExtension.create());
//	    extensions.add(JiraConverterExtension.create());
	    extensions.add(MacroExtension.create());
//	    extensions.add(MediaTagsExtension.create());
//	    extensions.add(SimTocExtension.create());
	    extensions.add(StrikethroughExtension.create());
//	    extensions.add(StrikethroughSubscriptExtension.create());
//	    extensions.add(SubscriptExtension.create());
//	    extensions.add(SuperscriptExtension.create());
	    extensions.add(TablesExtension.create());
	    extensions.add(TaskListExtension.create());
	    extensions.add(TocExtension.create());
//	    extensions.add(TypographicExtension.create());
	    extensions.add(WikiLinkExtension.create());
	    extensions.add(YamlFrontMatterExtension.create());
//	    extensions.add(YouTrackConverterExtension.create());
//	    extensions.add(YouTubeLinkExtension.create());
	    
		parser = Parser.builder().extensions(extensions).build();
		renderer = HtmlRenderer.builder().extensions(extensions).build();
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
		previewEngine.loadContent(content, "text/html");
        engine.loadContent(content, "text/html");
	}
	
	public void setWikiPage(WikiPage wikiPage) {
		this.currentPage=wikiPage;
		String content = wikiPageUtil.getWikiPageContent(wikiPage);
		textArea.setText(content);
		parseContent();
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

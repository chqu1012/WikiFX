package de.dc.fx.spring.wiki.ui.controller;

import java.util.LinkedList;

import org.springframework.stereotype.Controller;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

@Controller
public class WikiPageController extends BaseWikiPageController {

	private WebEngine engine;
//	protected List<Extension> extensions = Arrays.asList(TablesExtension.create());
	
	public void initialize() {
		Platform.runLater(() -> {
			WebView view = new WebView();
			engine = view.getEngine();
			AnchorPane.setBottomAnchor(view, 0d);
			AnchorPane.setTopAnchor(view, 0d);
			AnchorPane.setLeftAnchor(view, 0d);
			AnchorPane.setRightAnchor(view, 0d);
			previewPane.getChildren().add(view);
			
		});
	}
	
	@Override
	protected void onSaveWikiPageButton(ActionEvent event) {
		LinkedList<Extension> extensions = new LinkedList<Extension>();
	    extensions.add(TablesExtension.create());
	    extensions.add(AttributesExtension.create());
	    extensions.add(WikiLinkExtension.create());
//	    extensions.add(TocExtension.create());
	    extensions.add(TaskListExtension.create());
//	    extensions.add(StrikethroughExtension.create());
//	    extensions.add(MacroExtension.create());
//	    extensions.add(JiraConverterExtension.create());
//	    extensions.add(EscapedCharacterExtension.create());
//	    extensions.add(EnumeratedReferenceExtension.create());
//	    extensions.add(EmojiExtension.create());
//	    extensions.add(DefinitionExtension.create());
//	    extensions.add(AutolinkExtension.create());
//	    extensions.add(AbbreviationExtension.create());
	    
        MutableDataSet options = new MutableDataSet();
        // change soft break to hard break
        options.set(HtmlRenderer.SOFT_BREAK, "<br/>");
        options.set(HtmlRenderer.ESCAPE_HTML, true);
        
        MutableDataSet dataSet = new MutableDataSet();
        dataSet.set(TablesExtension.TRIM_CELL_WHITESPACE, false);
        dataSet.set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, false);
        dataSet.set(Parser.EXTENSIONS, extensions);
        
        Parser parser = Parser.builder(dataSet).build();
        Node document = parser.parse(textArea.getText());
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        String html = renderer.render(document);
        engine.loadContent(html); 
	}

//	protected void parseWithCommonmark() {
//		Parser parser = Parser.builder().extensions(extensions).build();
//		Node document = parser.parse(textArea.getText());
//		HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
//		String html = renderer.render(document);
//		engine.loadContent(html);
//	}

}

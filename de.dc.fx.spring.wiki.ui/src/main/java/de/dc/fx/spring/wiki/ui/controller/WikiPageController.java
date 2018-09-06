package de.dc.fx.spring.wiki.ui.controller;

import java.io.File;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.proudapes.jlatexmathfx.Control.LateXMathControl;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.admonition.AdmonitionExtension;
import com.vladsch.flexmark.ext.aside.AsideExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.pdf.converter.PdfConverterExtension;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.DataHolder;

import de.dc.fx.spring.wiki.ui.converter.DocxConvertor;
import de.dc.fx.spring.wiki.ui.model.NavigationModel;
import de.dc.fx.spring.wiki.ui.model.WikiPage;
import de.dc.fx.spring.wiki.ui.preview.MarkDownPreview;
import de.dc.fx.spring.wiki.ui.repository.NavigationModelRepository;
import de.dc.fx.spring.wiki.ui.util.AnimatedZoomOperator;
import de.dc.fx.spring.wiki.ui.util.WikiPageUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

@Controller
public class WikiPageController extends BaseWikiPageController {

	@Autowired WikiPageUtil wikiPageUtil;
	@Autowired MarkDownPreview preview;
	
	@Autowired NavigationModelRepository navigationModelRepository;
	@Autowired DocxConvertor docxConvertor;
	
	private WikiPage currentPage;

	private Parser parser;
	private HtmlRenderer renderer;
	private LateXMathControl latexControl = new LateXMathControl();
	
	static DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(Extensions.ALL, AdmonitionExtension.create(), AsideExtension.create());

	AnimatedZoomOperator zoomOperator = new AnimatedZoomOperator();
	
	@Override
	public void initialize() {
		super.initialize();
		OPTIONS.toMutable().set(DefinitionExtension.COLON_MARKER, true)
		.set(DefinitionExtension.TILDE_MARKER, true)
		.set(AsideExtension.IGNORE_BLANK_LINE, true)
		.set(AsideExtension.EXTEND_TO_BLANK_LINE, true);
		
		parser = Parser.builder(OPTIONS).build();
		renderer = HtmlRenderer.builder(OPTIONS).build();
		textArea.textProperty().addListener(o ->parseContent());
		
		AnchorPane.setBottomAnchor(latexControl, 0d);
		AnchorPane.setTopAnchor(latexControl, 0d);
		AnchorPane.setLeftAnchor(latexControl, 0d);
		AnchorPane.setRightAnchor(latexControl, 0d);
		latexPane.getChildren().add(latexControl);
		
		latexPane.setOnScroll(event -> {
		    double zoomFactor = 1.5;
		    if (event.getDeltaY() <= 0) {
		        // zoom out
		        zoomFactor = 1 / zoomFactor;
		    }
		    zoomOperator.zoom(latexPane, zoomFactor, event.getSceneX(), event.getSceneY());
		});
	}
	
	@Override
	protected void onSaveWikiPageButton(ActionEvent event) {
        if (currentPage!=null) {
        	wikiPageUtil.writeWikiPageContent(currentPage, textArea.getText());
		}
        
        tabPane.getSelectionModel().selectFirst();
	}

	private void parseContent() {
        Node document = parser.parse(textArea.getText());
        latexControl.setFormula(textArea.getText());
        latexControl.layout();
        latexControl.autosize();
        String html = renderer.render(document);
        String content = preview.getContent(html);
		previewEngine.loadContent(content, "text/html");
		String htmlPath = wikiPageUtil.writeIndexHtml(currentPage, content);
    	engine.load("file:///"+htmlPath);
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

	@FXML public void onWikiPage(KeyEvent event) {}

	@FXML public void onWikiPageKeyReleased(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ESCAPE)) {
			root.toBack();
		}
	}

	@FXML public void onBackButton(ActionEvent event) {
		root.toBack();
	}

	@FXML public void onExportPdfButton(ActionEvent event) {
		 FileChooser fileChooser = new FileChooser();
		 fileChooser.setInitialFileName("Export.pdf");
         fileChooser.setTitle("Save to pdf");
         File file = fileChooser.showSaveDialog(new Stage());
         if (currentPage!=null && file != null) {
			Node document = parser.parse(textArea.getText());
			String html = renderer.render(document);
			PdfConverterExtension.exportToPdf(file.getAbsolutePath(), preview.getContent(html),"", OPTIONS);
         }
	}

	@FXML public void onExportWordButton(ActionEvent event) {
		 FileChooser fileChooser = new FileChooser();
		 fileChooser.setInitialFileName("Export.docx");
         fileChooser.setTitle("Save to word");
         File file = fileChooser.showSaveDialog(new Stage());
         if (currentPage!=null && file != null) {
        	 docxConvertor.write(textArea.getText(), file);
         }
	}
}

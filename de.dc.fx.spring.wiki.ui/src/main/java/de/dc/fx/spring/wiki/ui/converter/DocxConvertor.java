package de.dc.fx.spring.wiki.ui.converter;

import java.io.File;
import java.util.Arrays;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.stereotype.Component;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.docx.converter.internal.DocxRenderer;
import com.vladsch.flexmark.ext.aside.AsideExtension;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.superscript.SuperscriptExtension;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

@Component
public class DocxConvertor {

	protected static DataHolder OPTIONS = new MutableDataSet().set(HtmlRenderer.INDENT_SIZE, 2).set(Parser.EXTENSIONS,
			Arrays.asList(AsideExtension.create(), AttributesExtension.create(), AutolinkExtension.create(),
					DefinitionExtension.create(), EmojiExtension.create(), EnumeratedReferenceExtension.create(),
					FootnoteExtension.create(), StrikethroughSubscriptExtension.create(), InsExtension.create(),
					SuperscriptExtension.create(), TablesExtension.create(), TocExtension.create(),
					SimTocExtension.create(), WikiLinkExtension.create()))
			.set(DocxRenderer.RENDER_BODY_ONLY, true).set(DocxRenderer.SUPPRESS_HTML, true);

	public void write(String markdown, File file) {
		final Parser PARSER = Parser.builder(OPTIONS).build();
		final DocxRenderer RENDERER = DocxRenderer.builder(OPTIONS).build();

		Node document = PARSER.parse(markdown);

		// to get XML
		String xml = RENDERER.render(document);

		// or to control the package
		final WordprocessingMLPackage template = DocxRenderer.getDefaultTemplate();
		RENDERER.render(document, template);

		try {
			template.save(file, Docx4J.FLAG_SAVE_ZIP_FILE);
		} catch (Docx4JException e) {
			e.printStackTrace();
		}
	}
}

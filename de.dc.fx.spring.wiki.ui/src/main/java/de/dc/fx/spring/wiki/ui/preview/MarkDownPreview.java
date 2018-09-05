package de.dc.fx.spring.wiki.ui.preview;

import java.net.URL;

import org.springframework.stereotype.Component;

@Component
public class MarkDownPreview {

	public String getContent(String flexmarkContent) {
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "<link rel=\"stylesheet\" href=\"" + getClass().getResource("markdownpad-github.css") + "\">\n"
				+ "<style>\n"
				+ ".mwfx-editor-selection {\n"
				+ "  border-right: 5px solid #f47806;\n"
				+ "  margin-right: -5px;\n"
				+ "  background-color: rgb(253, 247, 241);\n"
				+ "}\n"
				+ "</style>\n"
				+ "<script src=\"" + getClass().getResource("preview.js") + "\"></script>\n"
				+ prismSyntaxHighlighting()
				+ "</head>\n"
				+ "<body>\n"
				+ flexmarkContent
				+ "</body>\n"
				+ "<link rel=\"stylesheet\" href=\"" + getClass().getResource("amonition/admonition.css") + "\">\n"
				+ "<script src=\"" + getClass().getResource("amonition/admonition.js") + "\"></script>\n"
				+ "</html>");
		return sb.toString();
	}
	
	private String prismSyntaxHighlighting() {
		// build HTML (only load used languages)
		// Note: not using Prism Autoloader plugin because it lazy loads/highlights, which causes flicker
		//       during fast typing; it also does not work with "alias" languages (e.g. js, html, xml, svg, ...)
		StringBuilder buf = new StringBuilder();
		buf.append("<link rel=\"stylesheet\" href=\"").append(getClass().getResource("prism/prism.css")).append("\">\n");
		buf.append("<script src=\"").append(getClass().getResource("prism/prism-core.min.js")).append("\"></script>\n");
		URL url = getClass().getResource("prism/components/prism-java.min.js");
		buf.append("<script src=\"").append(url).append("\"></script>\n");
		return buf.toString();
	}
}

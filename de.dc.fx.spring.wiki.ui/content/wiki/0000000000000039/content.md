````Java
	private String prismSyntaxHighlighting() {
		// build HTML (only load used languages)
		// Note: not using Prism Autoloader plugin because it lazy loads/highlights, which causes flicker
		//       during fast typing; it also does not work with "alias" languages (e.g. js, html, xml, svg, ...)
		StringBuilder buf = new StringBuilder();
		buf.append("<link rel=\"stylesheet\" href=\"").append(getClass().getResource("prism/prism.css")).append("\">\n");
		buf.append("<script src=\"").append(getClass().getResource("prism/prism-core.min.js")).append("\"></script>\n");
		buf.append("<script src=\"").append(getClass().getResource("highlight/highlight.js")).append("\"></script>\n");
		buf.append("<script>hljs.initHighlightingOnLoad();</script>\n");
		URL url = getClass().getResource("prism/components/prism-java.min.js");
		buf.append("<script src=\"").append(url).append("\"></script>\n");
		return buf.toString();
	}
````
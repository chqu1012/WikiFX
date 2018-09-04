package de.dc.fx.spring.wiki.ui.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import de.dc.fx.spring.wiki.ui.model.WikiPage;

@Component
public class WikiPageUtil extends BaseFolderUtil<WikiPage>{

	@Override
	protected String resFolderName() {
		return "wiki";
	}
	
	public String writeIndexHtml(WikiPage t, String content) {
		String file = getFolderBy(t).getAbsolutePath()+"/index.html";
		try {
			FileUtils.writeStringToFile(new File(file), content, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	public void writeWikiPageContent(WikiPage t, String content) {
		String file = getFolderBy(t).getAbsolutePath()+"/content.md";
		try {
			FileUtils.writeStringToFile(new File(file), content, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getWikiPageContent(WikiPage t) {
		String file = getFolderBy(t).getAbsolutePath()+"/content.md";
		String fileContents="";
		try {
			fileContents = FileUtils.readFileToString(new File(file), Charset.forName("UTF-8"));
		} catch (IOException e) {
			return "";
		}
		return fileContents;
	}

	@Override
	public File getFolderBy(WikiPage t) {
		return getFolderBy(t.getId());
	}

	@Override
	public File createFolder(WikiPage t) {
		return createFolder(t.getId());
	}

}

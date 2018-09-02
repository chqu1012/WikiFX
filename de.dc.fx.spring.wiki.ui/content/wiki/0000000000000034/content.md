Link: http://www.avajava.com/tutorials/lessons/how-do-i-write-a-string-to-a-file-using-commons-io.html

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class WriteStringToFile {

	public static void main(String[] args) throws IOException {
		String string = "This is\na test";
		File file = new File("test.txt");
		FileUtils.writeStringToFile(file, string);
	}
}
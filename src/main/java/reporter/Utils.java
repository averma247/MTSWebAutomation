package reporter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Utils {

	public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss_z";
	private static final Logger logger = LogManager.getLogger(Utils.class);

	private Utils() {
	}

	public static String getCurrentDateTimeAsString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
		Date now = new Date();
		return (dateFormat.format(now).replace(':', '_'));
	}

	/**
	 * Zip the report created.
	 **/
	public static void zipFolder(Path dataPath, Path zipPath) throws IOException {
		logger.info("Move Report to zip file");
		try (ZipOutputStream zipOutput = new ZipOutputStream(new FileOutputStream(zipPath.toFile()))) {
			Files.walkFileTree(
					dataPath,
					new SimpleFileVisitor<Path>() {
						@Override
						public FileVisitResult visitFile(Path file, BasicFileAttributes attributes)
								throws IOException {
							zipOutput.putNextEntry(new ZipEntry(dataPath.relativize(file).toString()));
							Files.copy(file, zipOutput);
							zipOutput.closeEntry();
							Files.delete(file);
							return FileVisitResult.CONTINUE;
						}
					});
		}
	}

	public static Integer getCurrentTempratureAsInt(String currentTemp){
		Integer currentTempInt=null;
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(currentTemp);
		while(m.find()) {
			System.out.println(m.group());
			currentTempInt=Integer.parseInt(m.group());
		}
		return currentTempInt;
	}
}
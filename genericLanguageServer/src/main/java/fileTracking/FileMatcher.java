package ablecLanguageServer;

import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.*;


public class FileMatcher {
  String[] fileExtensions;
  List<String> fileExtensionList;
  String firstLineRegex;
  Logger logger = Logger.getLogger();

  public FileMatcher() {
    fileExtensions = null;
    fileExtensionList = null;
    firstLineRegex = null;
  }
  public FileMatcher(String[] fileExtensions) {
    this.fileExtensions = fileExtensions;
    this.fileExtensionList = Arrays.asList(fileExtensions);
    this.firstLineRegex = null;
  }

  public FileMatcher(String[] fileExtensions, String firstLineRegex) {
    this.fileExtensions = fileExtensions;
    this.fileExtensionList = Arrays.asList(fileExtensions);
    this.firstLineRegex = firstLineRegex;
  }

  public FileMatcher(String firstLineRegex) {
    this.fileExtensions = null;
    this.fileExtensionList = null;
    this.firstLineRegex = firstLineRegex;
  }

  public boolean matches(File f) {
    if (fileExtensions != null) {
      String[] parts = f.getName().split("\\.");
      if (parts.length < 2) { 
        logger.logMessage("File with name " + f.getName() + " has no extension");
        return false;
      }
      String extension = parts[1];
      if (!fileExtensionList.contains(extension)) {
        return false;
      }
    } else {
      logger.logMessage("File extensions is null");
    }
    if (firstLineRegex != null) {
      BufferedReader reader;
      try { 
        reader = new BufferedReader(new FileReader(f));
      } catch (FileNotFoundException fnfExp) {
        return false;
      }
      String firstLine;
      try {
        firstLine = reader.readLine();
      } catch (IOException e) {
        return false;
      }
      if (firstLine == null || !Pattern.matches(firstLineRegex, firstLine)) {
        return false;
      }
    }
    return true;
  }
}

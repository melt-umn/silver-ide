package genericLanguageServer.fileTracking;

import genericLanguageServer.Logger;
import genericLanguageServer.lspInterface.document.DocumentUri;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class FileWatcher {
  private String rootPath;
  private FileMatcher fileMatcher;

  private static final boolean amLogging = true;
  private static Logger logger = Logger.getLogger();

  List<String> watchedFiles; // relatives path from the root

  public FileWatcher(DocumentUri rootUri, FileMatcher matcher) {
    this(UriConverter.getPathFromUri(rootUri), matcher);
  }

  public FileWatcher(String rootPath, FileMatcher matcher) {
    logger.logMethodEntrance("FileWatcher constructor");
    this.rootPath = rootPath;
    this.fileMatcher = matcher;
    watchedFiles = new ArrayList<String>();
    if (rootPath != null) {
      determineFilesToWatch();
    }
    if (amLogging) {
      logger.logMessage("Root Path of FileWatcher is " + rootPath);
    }
    logger.logMethodExit("FileWatcher constructor");
  }

  private void determineFilesToWatch() {
    logger.logMethodEntrance("FileWatcher.determineFilesToWatch");
    File root = new File(rootPath);
    if (!root.isDirectory()) {
      throw new IllegalArgumentException("rootPath was not a directory");
    }
    determineMatchingFiles(new File[] {root});
    if (amLogging) {
      logger.logMessage("Files being watched: ");
      for (String filename : watchedFiles) {
        logger.logMessage(filename);
      }
    }
    logger.logMethodExit("FileWatcher.determineFilesToWatch");
  }

  private void determineMatchingFiles(File[] files) {
    logger.logMethodEntrance("FileWatcher.determineMatchingFiles");
    for (File file : files) {
      logger.logMessage("Checking file with path name " + file.getName());
      if (file.isDirectory()) {
        determineMatchingFiles(file.listFiles());
      } else {
          if (fileMatcher.matches(file)) {
            logger.logMessage("Adding file with path name " + file.getPath());
            watchedFiles.add(file.getPath());
          }
      }
    }
    logger.logMethodExit("FileWatcher.determineMatchingFiles");
  }

  public boolean amWatchingFile(DocumentUri uri) {
    return amWatchingFile(UriConverter.getPathFromUri(uri));
  }

  public boolean amWatchingFile(String path) {
    if (watchedFiles == null) {
      return false;
    }
    return watchedFiles.contains(path);
  }
}

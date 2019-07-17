package genericLanguageServer.fileTracking;

import genericLanguageServer.lspInterface.document.DocumentUri;

public class UriConverter {

  private static final String FILE_STARTER = "file://";
  public static String getPathFromUri(DocumentUri uri) {
    if (uri != null) {
      return getPathFromUri(uri.getUri());
    } else {
      throw new IllegalArgumentException("NULL URI");
    }
  }

  public static String getPathFromUri(String uri) {
    if (uri.startsWith(FILE_STARTER)) {
      return uri.substring(FILE_STARTER.length());
    } else {
      throw new IllegalArgumentException("Unknown URI");
    }
  }
}

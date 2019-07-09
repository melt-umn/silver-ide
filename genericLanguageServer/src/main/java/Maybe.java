package ablecLanguageServer;

// deals with a lot of the optional aspects of the language server protocol
public class Maybe {


  public static boolean getBoolean(Boolean value, boolean ifNullVal) {
    if (value == null) {
      return ifNullVal;
    } else {
      return Boolean.valueOf(value);
    }
  }
}

package genericLanguageServer.lspInterface;

public class Trace {
  private String traceSetting;

  public static final String OFF = "OFF"; // default
  public static final String MESSAGES = "MESSAGES";
  public static final String VERBOSE = "VERBOSE";

  private static final String ILLEGAL_VALUE_ERROR_MSG_BASE =
    "Trace string must be one of\n " +
    OFF + "\n" +
    MESSAGES + "\n" +
    VERBOSE + "\n" +
    "Instead it was: ";

  public Trace(String setting) {
    traceSetting = setting;
    if (!isValid()) {
      throw new IllegalArgumentException(ILLEGAL_VALUE_ERROR_MSG_BASE + traceSetting);
    }
  }

  public Trace() {
    traceSetting = Trace.OFF;
  }

  public String getSetting() {
    return traceSetting;
  }

  public boolean isValid() {
    return
      traceSetting.equals(OFF) ||
      traceSetting.equals(MESSAGES) ||
      traceSetting.equals(VERBOSE);
  }
}

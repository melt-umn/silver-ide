package genericLanguageServer.lspInterface.diagnostics;

import genericLanguageServer.lspInterface.LSPObject;

public class DiagnosticSeverity implements LSPObject {
  private int severity;
  public static final int ERROR = 1;
  public static final int WARNING = 2;
  public static final int INFORMATION = 3;
  public static final int HINT = 4;

  private static final int MIN_VALUE = 1;
  private static final int MAX_VALUE = 4;

  public DiagnosticSeverity(int severity) {
    this.severity = severity;
    if (!isValid()) {
      throw new IllegalArgumentException("Invalid value for DiagnosticSeverity provided");
    }
  }

  public int getSeverity() {
    return severity;
  }

  public boolean isValid() {
    return severity >= MIN_VALUE && severity <= MAX_VALUE;
  }

  public String getJson() {
    return Integer.toString(severity);
  }
}

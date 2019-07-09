package ablecLanguageServer;
public class MessageType implements LSPObject {
  private int type;
	/**
	 * An error message.
	 */
	public static final int ERROR = 1;
	/**
	 * A warning message.
	 */
	public static final int WARNING = 2;
	/**
	 * An information message.
	 */
	public static final int INFO = 3;
	/**
	 * A log message.
	 */
	public static final int LOG = 4;

  private static final int MIN_VALUE = 1;
  private static final int MAX_VALUE = 4;

  public MessageType(Integer type) {
    this.type = type; 
    if (!isValid()) {
      throw new IllegalArgumentException("Invalid value for MessageType provided");
    }
  }

  public boolean isValid() {
    return type >= MIN_VALUE && type <= MAX_VALUE;
  }

  public int getType() {
    return type;
  }

  public String getJson() {
    return Integer.toString(type);
  }
}


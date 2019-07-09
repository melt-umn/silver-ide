package ablecLanguageServer;
/**
 * The file event type.
 */
public class FileChangeType {
  private int type;
	/**
	 * The file got created.
	 */
	public static final int CREATED = 1;
	/**
	 * The file got changed.
	 */
	public static final int CHANGED = 2;
	/**
	 * The file got deleted.
	 */
	public static final int DELETED = 3;

  private static final int MIN_VALUE = 1;
  private static final int MAX_VALUE = 3;
  
  public FileChangeType(int type) {
    this.type = type; 
    if (!isValid()) {
      throw new IllegalArgumentException("Invalid value for FileChangeType provided");
    }
  }

  public int getType() {
    return type;
  }

  public boolean isValid() {
    return type >= MIN_VALUE && type <= MAX_VALUE;
  }
}

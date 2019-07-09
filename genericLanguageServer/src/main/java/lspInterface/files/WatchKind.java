package ablecLanguageServer;
public class WatchKind {
  private int kind;
	/**
	 * Interested in create events.
	 */
	public static final int CREATE = 1;

	/**
	 * Interested in change events
	 */
	public static final int CHANGE = 2;

	/**
	 * Interested in delete events
	 */
	public static final int DELETE = 4;

  public WatchKind() {
    // default is all of them
    kind = CREATE + CHANGE + DELETE;
  }

  public int getKind() {
    return kind;
  }
}

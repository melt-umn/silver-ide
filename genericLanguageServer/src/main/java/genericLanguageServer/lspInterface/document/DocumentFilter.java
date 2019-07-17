package genericLanguageServer.lspInterface.document;

public class DocumentFilter {
	/**
	 * A language id, like `typescript`.
	 */
  private String language;
 	/**
	 * A Uri [scheme](#Uri.scheme), like `file` or `untitled`.
	 */
  private String scheme;
	/**
	 * A glob pattern, like `*.{ts,js}`.
	 *
	 * Glob patterns can have the following syntax:
	 * - `*` to match one or more characters in a path segment
	 * - `?` to match on one character in a path segment
	 * - `**` to match any number of path segments, including none
	 * - `{}` to group conditions (e.g. **\/*.{ts,js}` matches all TypeScript and JavaScript files)
	 * - `[]` to declare a range of characters to match in a path segment (e.g., `example.[0-9]` to match on `example.0`, `example.1`, …)
	 * - `[!...]` to negate a range of characters to match in a path segment (e.g., `example.[!0-9]` to match on `example.a`, `example.b`, but not `example.0`)
	 */
	private String pattern;

  public DocumentFilter(String lang, String scheme, String pattern) {
    this.language = lang;
    this.scheme = scheme;
    this.pattern = pattern;
  }
}

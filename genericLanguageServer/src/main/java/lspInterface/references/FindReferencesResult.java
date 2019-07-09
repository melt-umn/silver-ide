package ablecLanguageServer;

public class FindReferencesResult extends NullableArrayLSPResponse<Location> {
  public FindReferencesResult() {
    super();
  }

  public FindReferencesResult(Location[] result) {
    super(result);
  }
}

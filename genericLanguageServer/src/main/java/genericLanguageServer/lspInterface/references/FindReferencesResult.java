package genericLanguageServer.lspInterface.references;

import genericLanguageServer.lspInterface.generics.NullableArrayLSPResponse;
import genericLanguageServer.lspInterface.document.Location;

public class FindReferencesResult extends NullableArrayLSPResponse<Location> {
  public FindReferencesResult() {
    super();
  }

  public FindReferencesResult(Location[] result) {
    super(result);
  }
}

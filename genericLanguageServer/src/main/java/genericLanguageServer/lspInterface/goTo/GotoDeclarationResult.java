package genericLanguageServer.lspInterface.goTo;

import genericLanguageServer.lspInterface.document.Location;
import genericLanguageServer.lspInterface.document.LocationLink;
import genericLanguageServer.lspInterface.generics.LocationLSPResponse;

public class GotoDeclarationResult extends LocationLSPResponse {
  public GotoDeclarationResult() {
    super();
  }

  public GotoDeclarationResult(Location location) {
    super(location);
  }

  public GotoDeclarationResult(Location[] locations) {
    super(locations);
  }

  public GotoDeclarationResult(LocationLink[] locationLinks) {
    super(locationLinks);
  }
}

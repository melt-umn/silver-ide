package genericLanguageServer.lspInterface.goTo;

import genericLanguageServer.lspInterface.generics.LocationLSPResponse;
import genericLanguageServer.lspInterface.document.Location;
import genericLanguageServer.lspInterface.document.LocationLink;

public class GotoImplementationResult extends LocationLSPResponse {
  public GotoImplementationResult() {
    super();
  }

  public GotoImplementationResult(Location location) {
    super(location);
  }

  public GotoImplementationResult(Location[] locations) {
    super(locations);
  }

  public GotoImplementationResult(LocationLink[] locationLinks) {
    super(locationLinks);
  }
}

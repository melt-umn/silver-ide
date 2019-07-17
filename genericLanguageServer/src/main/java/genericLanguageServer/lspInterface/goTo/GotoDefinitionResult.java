package genericLanguageServer.lspInterface.goTo;

import genericLanguageServer.lspInterface.generics.LocationLSPResponse;
import genericLanguageServer.lspInterface.document.Location;
import genericLanguageServer.lspInterface.document.LocationLink;

public class GotoDefinitionResult extends LocationLSPResponse {
  public GotoDefinitionResult() {
    super();
  }

  public GotoDefinitionResult(Location location) {
    super(location);
  }

  public GotoDefinitionResult(Location[] locations) {
    super(locations);
  }

  public GotoDefinitionResult(LocationLink[] locationLinks) {
    super(locationLinks);
  }
}

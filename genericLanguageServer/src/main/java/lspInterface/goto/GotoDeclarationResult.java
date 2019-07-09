package ablecLanguageServer;

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

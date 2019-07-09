package ablecLanguageServer;

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

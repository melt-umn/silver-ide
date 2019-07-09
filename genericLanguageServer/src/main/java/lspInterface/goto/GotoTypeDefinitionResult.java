package ablecLanguageServer;

public class GotoTypeDefinitionResult extends LocationLSPResponse {
  public GotoTypeDefinitionResult() {
    super();
  }

  public GotoTypeDefinitionResult(Location location) {
    super(location);
  }

  public GotoTypeDefinitionResult(Location[] locations) {
    super(locations);
  }
  
  public GotoTypeDefinitionResult(LocationLink[] locationLinks) {
    super(locationLinks);
  }
}

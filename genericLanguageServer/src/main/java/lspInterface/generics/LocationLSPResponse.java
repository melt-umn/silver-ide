package ablecLanguageServer;

public class LocationLSPResponse implements LSPResponse {
  // only one of these should be set
  private Location location;
  private Location[] locations;
  private LocationLink[] locationLinks;
  private boolean nullResponse;
  public LocationLSPResponse() {
    nullResponse = true;
  }

  public LocationLSPResponse(Location loc) {
    this.location = loc;
    nullResponse = false;
  }

  public LocationLSPResponse(Location[] locs) {
    this.locations = locs;
    nullResponse = false;
  }
  
  public LocationLSPResponse(LocationLink[] locLinks) {
    this.locationLinks = locLinks;
    nullResponse = false;
  }

  public String getJson() {
    if (nullResponse) {
      return "null";
    }
    JSONBuilder json = new JSONBuilder();
    // only 1 of these is non null and will have its json added
    json.addObjectValue(location);
    json.addArrayValue(locations);
    json.addArrayValue(locationLinks);
    return json.getJson();
  }

  public Integer getJsonType() {
    if (nullResponse) {
      return JSONType.NULL_TYPE;
    }
    if (location != null) {
      return JSONType.OBJECT_TYPE;
    }
    return JSONType.ARRAY_TYPE;
  }
}

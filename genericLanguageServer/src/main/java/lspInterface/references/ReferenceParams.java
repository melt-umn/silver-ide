package ablecLanguageServer;

import org.json.*;

public class ReferenceParams extends TextDocumentPositionParams {
  private ReferenceContext context;

  public ReferenceParams(JSONObject params) {
    super(params);
    if (params.has("context")) {
      context = new ReferenceContext(params.getJSONObject("context"));
    }
  }

  public Boolean shouldIncludeDeclaration() {
    return context.shouldIncludeDeclaration();
  }
}

package genericLanguageServer.lspInterface.highlighting;

import genericLanguageServer.lspInterface.generics.NullableArrayLSPResponse;

public class DocumentHighlightResult extends NullableArrayLSPResponse<DocumentHighlight> {
  public DocumentHighlightResult() {
    super();
  }

  public DocumentHighlightResult(DocumentHighlight[] result) {
    super(result);
  }
}

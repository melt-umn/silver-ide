package ablecLanguageServer;

public class DocumentHighlightResult extends NullableArrayLSPResponse<DocumentHighlight> {
  public DocumentHighlightResult() {
    super();
  }

  public DocumentHighlightResult(DocumentHighlight[] result) {
    super(result);
  }
}

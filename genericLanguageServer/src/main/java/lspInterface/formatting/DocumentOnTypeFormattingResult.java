package ablecLanguageServer;

public class DocumentOnTypeFormattingResult extends NullableArrayLSPResponse<TextEdit> {

  public DocumentOnTypeFormattingResult(TextEdit[] edits) {
    super(edits);
  }

  // with null edits
  public DocumentOnTypeFormattingResult() {
    super();
  }
}


package ablecLanguageServer;

public class DocumentFormattingResult extends NullableArrayLSPResponse<TextEdit> {

  public DocumentFormattingResult(TextEdit[] edits) {
    super(edits);
  }

  // with null edits
  public DocumentFormattingResult() {
    super();
  }
}

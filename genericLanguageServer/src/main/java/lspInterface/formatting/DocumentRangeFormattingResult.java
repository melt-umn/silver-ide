package ablecLanguageServer;

public class DocumentRangeFormattingResult extends NullableArrayLSPResponse<TextEdit> {

  public DocumentRangeFormattingResult(TextEdit[] edits) {
    super(edits);
  }

  // with null edits
  public DocumentRangeFormattingResult() {
    super();
  }
}

package ablecLanguageServer;

public class WillSaveWaitUntilTextDocumentResult extends NullableArrayLSPResponse<TextEdit> {

  public WillSaveWaitUntilTextDocumentResult(TextEdit[] edits) {
    super(edits);
  }

  // with null edits
  public WillSaveWaitUntilTextDocumentResult() {
    super();
  }
}

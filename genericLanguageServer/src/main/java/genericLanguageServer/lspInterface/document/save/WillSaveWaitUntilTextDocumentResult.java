package genericLanguageServer.lspInterface.document.save;

import genericLanguageServer.lspInterface.document.TextEdit;
import genericLanguageServer.lspInterface.generics.NullableArrayLSPResponse;

public class WillSaveWaitUntilTextDocumentResult extends NullableArrayLSPResponse<TextEdit> {

  public WillSaveWaitUntilTextDocumentResult(TextEdit[] edits) {
    super(edits);
  }

  // with null edits
  public WillSaveWaitUntilTextDocumentResult() {
    super();
  }
}

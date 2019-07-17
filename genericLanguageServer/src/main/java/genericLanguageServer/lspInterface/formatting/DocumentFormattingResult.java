package genericLanguageServer.lspInterface.formatting;

import genericLanguageServer.lspInterface.generics.NullableArrayLSPResponse;
import genericLanguageServer.lspInterface.document.TextEdit;

public class DocumentFormattingResult extends NullableArrayLSPResponse<TextEdit> {

  public DocumentFormattingResult(TextEdit[] edits) {
    super(edits);
  }

  // with null edits
  public DocumentFormattingResult() {
    super();
  }
}

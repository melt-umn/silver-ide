package genericLanguageServer.lspInterface.formatting;

import genericLanguageServer.lspInterface.generics.NullableArrayLSPResponse;
import genericLanguageServer.lspInterface.document.TextEdit;

public class DocumentRangeFormattingResult extends NullableArrayLSPResponse<TextEdit> {

  public DocumentRangeFormattingResult(TextEdit[] edits) {
    super(edits);
  }

  // with null edits
  public DocumentRangeFormattingResult() {
    super();
  }
}

package genericLanguageServer.lspInterface.formatting;

import genericLanguageServer.lspInterface.generics.NullableArrayLSPResponse;
import genericLanguageServer.lspInterface.document.TextEdit;

public class DocumentOnTypeFormattingResult extends NullableArrayLSPResponse<TextEdit> {

  public DocumentOnTypeFormattingResult(TextEdit[] edits) {
    super(edits);
  }

  // with null edits
  public DocumentOnTypeFormattingResult() {
    super();
  }
}

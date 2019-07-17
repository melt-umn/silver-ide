package genericLanguageServer.lspInterface.signatureHelp;

import genericLanguageServer.lspInterface.generics.NullableLSPResponse;

public class SignatureHelpResult extends NullableLSPResponse<SignatureHelp> {
  public SignatureHelpResult() {
    super();
  }

  public SignatureHelpResult(SignatureHelp result) {
    super(result);
  }
}

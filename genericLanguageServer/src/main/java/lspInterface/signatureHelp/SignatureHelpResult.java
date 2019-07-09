package ablecLanguageServer;

public class SignatureHelpResult extends NullableLSPResponse<SignatureHelp> {
  public SignatureHelpResult() {
    super();
  }

  public SignatureHelpResult(SignatureHelp result) {
    super(result);
  }
}

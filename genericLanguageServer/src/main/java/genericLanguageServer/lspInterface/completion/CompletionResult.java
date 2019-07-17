package genericLanguageServer.lspInterface.completion;

import genericLanguageServer.lspInterface.generics.NullableLSPResponse;

public class CompletionResult extends NullableLSPResponse<CompletionList> {
  public CompletionResult() {
    super();
  }

  public CompletionResult(CompletionList result) {
    super(result);
  }

  public CompletionResult(CompletionItem[] items) {
    super(new CompletionList(items));
  }
}

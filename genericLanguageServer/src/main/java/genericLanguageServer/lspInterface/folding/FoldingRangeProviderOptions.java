package genericLanguageServer.lspInterface.folding;

import genericLanguageServer.lspInterface.LSPObject;
/**
 * Folding range provider options.
 */
public class FoldingRangeProviderOptions implements LSPObject {

  public FoldingRangeProviderOptions() {

  }

  public String getJson() {
    return "{}";
  }
}

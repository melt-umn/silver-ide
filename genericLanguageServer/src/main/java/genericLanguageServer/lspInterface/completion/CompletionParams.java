package genericLanguageServer.lspInterface.completion;

import genericLanguageServer.lspInterface.document.TextDocumentPositionParams;

import org.json.*;

public class CompletionParams extends TextDocumentPositionParams {

	/**
	 * The completion context. This is only available if the client specifies
	 * to send this using `ClientCapabilities.textDocument.completion.contextSupport === true`
	 */
	CompletionContext context;

  public CompletionParams(JSONObject params) {
    super(params);
    if (params.has("context")) {
      context = new CompletionContext(params.getJSONObject("context"));
    }
  }

}

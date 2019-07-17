package genericLanguageServer;

import genericLanguageServer.lspInterface.*;
import genericLanguageServer.lspInterface.initialization.*;
import genericLanguageServer.lspInterface.messages.*;
import genericLanguageServer.lspInterface.shutdown.*;
import genericLanguageServer.lspInterface.diagnostics.*;
import genericLanguageServer.lspInterface.completion.*;
import genericLanguageServer.lspInterface.hover.*;
import genericLanguageServer.lspInterface.signatureHelp.*;
import genericLanguageServer.lspInterface.references.*;
import genericLanguageServer.lspInterface.highlighting.*;
import genericLanguageServer.lspInterface.files.*;
import genericLanguageServer.lspInterface.formatting.*;
import genericLanguageServer.lspInterface.goTo.*;
import genericLanguageServer.lspInterface.document.*;
import genericLanguageServer.lspInterface.document.synchronization.*;
import genericLanguageServer.lspInterface.document.save.*;

import genericLanguageServer.silverInterface.*;



import org.json.*;
import java.util.List;
import java.util.Arrays;

public class MethodDispatcher {
  private static Logger logger = Logger.getLogger();
  public static final String[] notificationMethods = new String[] {
    "$/cancelRequest",
    InitializedNotification.METHOD_NAME,
    ExitNotification.METHOD_NAME,
    ShowMessageNotification.METHOD_NAME,
    LogMessageNotification.METHOD_NAME,
    "telemetry/event",
    "workspace/didChangeWorkspaceFolders",
    "workspace/didChangeConfiguration",
    "workspace/didChangeWatchedFiles",
    DidOpenTextDocumentNotification.METHOD_NAME,
    DidChangeTextDocumentNotification.METHOD_NAME,
    WillSaveTextDocumentNotification.METHOD_NAME,
    DidSaveTextDocumentNotification.METHOD_NAME,
    DidCloseTextDocumentNotification.METHOD_NAME,
    PublishDiagnosticsNotification.METHOD_NAME
  };

  public static final List<String> notificationMethodList = Arrays.asList(notificationMethods);

  public static final String[] requestMethods = new String[] {
    InitializeRequest.METHOD_NAME,
    ShutdownRequest.METHOD_NAME,
    "window/showMessageRequest",
    "client/registerCapability",
    "client/unregisterCapability",
    "workspace/workspaceFolders",
    "workspace/configuration",
    "workspace/symbol",
    "workspace/executeCommand",
    "workspace/applyEdit",
    WillSaveWaitUntilTextDocumentRequest.METHOD_NAME,
    CompletionRequest.METHOD_NAME,
    "completionItem/resolve",
    HoverRequest.METHOD_NAME,
    SignatureHelpRequest.METHOD_NAME,
    GotoDeclarationRequest.METHOD_NAME,
    GotoDefinitionRequest.METHOD_NAME,
    GotoTypeDefinitionRequest.METHOD_NAME,
    GotoImplementationRequest.METHOD_NAME,
    FindReferencesRequest.METHOD_NAME,
    DocumentHighlightRequest.METHOD_NAME,
    "textDocument/documentSymbol",
    "textDocument/codeAction",
    "textDocument/codeLens",
    "codeLens/resolve",
    "textDocument/documentLink",
    "documentLink/resolve",
    "textDocument/documentColor",
    "textDocument/colorPresentation",
    DocumentFormattingRequest.METHOD_NAME,
    DocumentRangeFormattingRequest.METHOD_NAME,
    DocumentOnTypeFormattingRequest.METHOD_NAME,
    "textDocument/rename",
    "textDocument/prepareRename",
    "textDocument/foldingRange"
  };

  public static String dispatchMessage(String jsonMessage) {
    Server server = Server.getServer();
    String json = SilverInterface.handleMessage(
      server.getState(), jsonMessage, server.getSilverHandlerInterface());
    // was notification so no response
    if (json == null) {
      return null;
    } 
    // was request so construct response
    else {
      return ResponseHandler.constructResponse(json);
    }
  }

  public static boolean isNotificationMethod(String method) {
    logger.logMethodEntrance("isNotificationMethod");
    Boolean result = notificationMethodList.contains(method);
    logger.logMessage("Method: " + method + " is a notification method is: " +
result);
    logger.logMethodExit("isNotificationMethod");
    return result;
  }

  public static boolean isRequestMethod(String method) {
    logger.logMethodEntrance("isRequestMethod");
    Boolean result = !isNotificationMethod(method);
    logger.logMethodExit("isRequestMethod");
    return result;
  }
}

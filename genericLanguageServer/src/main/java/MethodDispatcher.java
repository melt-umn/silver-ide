package ablecLanguageServer;

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

  public static ResponseMessage dispatchRequestMethod(String method, RequestMessage message) throws InvalidLSPRequestException {
    logger.logMethodEntrance("dispatchRequestMethod");
    ResponseMessage response;
    LSPRequest request;
    if (method.equals(InitializeRequest.METHOD_NAME)) {
      request = new InitializeRequest(message.getParams());
    } 
    else if (method.equals(ShutdownRequest.METHOD_NAME)) {
      request = new ShutdownRequest();
    } 
    else if (method.equals(HoverRequest.METHOD_NAME)) {
      request = new HoverRequest(message.getParams());
    }
    else if (method.equals(DocumentFormattingRequest.METHOD_NAME)) {
      request = new DocumentFormattingRequest(message.getParams());
    }
    else if (method.equals(DocumentRangeFormattingRequest.METHOD_NAME)) {
      request = new DocumentRangeFormattingRequest(message.getParams());
    }
    else if (method.equals(DocumentOnTypeFormattingRequest.METHOD_NAME)) {
      request = new DocumentOnTypeFormattingRequest(message.getParams());
    } 
    else if (method.equals(WillSaveWaitUntilTextDocumentRequest.METHOD_NAME)) {
      request = new WillSaveWaitUntilTextDocumentRequest(message.getParams());
    }
    else if (method.equals(GotoDefinitionRequest.METHOD_NAME)) {
      request = new GotoDefinitionRequest(message.getParams());
    }
    else if (method.equals(GotoDeclarationRequest.METHOD_NAME)) {
      request = new GotoDeclarationRequest(message.getParams());
    }
    else if (method.equals(CompletionRequest.METHOD_NAME)) {
      request = new CompletionRequest(message.getParams());
    }
    else if (method.equals(SignatureHelpRequest.METHOD_NAME)) {
      request = new SignatureHelpRequest(message.getParams());
    }
    else if (method.equals(GotoTypeDefinitionRequest.METHOD_NAME)) {
      request = new GotoTypeDefinitionRequest(message.getParams());
    }
    else if (method.equals(GotoImplementationRequest.METHOD_NAME)) {
      request = new GotoImplementationRequest(message.getParams());
    }
    else if (method.equals(FindReferencesRequest.METHOD_NAME)) {
      request = new FindReferencesRequest(message.getParams());
    }
    else if (method.equals(DocumentHighlightRequest.METHOD_NAME)) {
      request = new DocumentHighlightRequest(message.getParams());
    }
    else {
      throw new InvalidLSPRequestException("Method: " + method + " not supported") ;
    }
    response = MethodHandler.handleRequest(message, request);
    logger.logMethodExit("dispatchRequestMethod");
    return response;
  }

  public static void dispatchNotificationMethod(String method, NotificationMessage notification) throws InvalidLSPRequestException {
    logger.logMethodEntrance("dispatchNotificationMethod");
    LSPNotification lspNotif;

    if (method.equals(InitializedNotification.METHOD_NAME)) {
      lspNotif = new InitializedNotification();
    } 
    else if (method.equals(ExitNotification.METHOD_NAME)) {
      lspNotif = new ExitNotification();
    }
    else if (method.equals(DidChangeWatchedFilesNotification.METHOD_NAME)) {
      lspNotif = new DidChangeWatchedFilesNotification(notification.getParams());
    } 
    else if (method.equals(DidOpenTextDocumentNotification.METHOD_NAME)) {
      lspNotif = new DidOpenTextDocumentNotification(notification.getParams());
    } 
    else if (method.equals(DidChangeTextDocumentNotification.METHOD_NAME)) {
      lspNotif = new DidChangeTextDocumentNotification(notification.getParams());
    }
    else if (method.equals(WillSaveTextDocumentNotification.METHOD_NAME)) {
      lspNotif = new WillSaveTextDocumentNotification(notification.getParams());
    }
    else if (method.equals(DidSaveTextDocumentNotification.METHOD_NAME)) {
      lspNotif = new DidSaveTextDocumentNotification(notification.getParams());
    }
    else if (method.equals(DidCloseTextDocumentNotification.METHOD_NAME)) {
      lspNotif = new DidCloseTextDocumentNotification(notification.getParams());
    } 
    else {
      throw new InvalidLSPRequestException("Method: " + method + " not supported") ;
    }
    MethodHandler.handleNotification(lspNotif);
    logger.logMethodExit("dispatchNotificationMethod");
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

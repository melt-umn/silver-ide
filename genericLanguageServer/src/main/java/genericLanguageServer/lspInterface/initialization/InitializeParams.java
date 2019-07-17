package genericLanguageServer.lspInterface.initialization;

import genericLanguageServer.Logger;

import genericLanguageServer.lspInterface.document.DocumentUri;
import genericLanguageServer.lspInterface.Trace;
import genericLanguageServer.lspInterface.WorkspaceFolder;


import org.json.*;

public class InitializeParams {
  private Integer processId;
  private String rootPath;
  private DocumentUri rootUri;
  private Trace trace; // default is off
  private ClientCapabilities capabilities;
  private WorkspaceFolder[] workspaceFolders;
  private Logger logger;
  public InitializeParams(JSONObject obj) {
    logger = Logger.getLogger();
    logger.logMethodEntrance("InitializeParams constructor");
    processId = obj.getInt("processId");
    if (obj.has("rootPath")) {
      rootPath = obj.getString("rootPath");
    }
    rootUri = new DocumentUri(obj.getString("rootUri"));
    capabilities = new ClientCapabilities(obj.getJSONObject("capabilities"));
    if (obj.has("trace")) {
      trace = new Trace(obj.getString("trace"));
    } else {
      trace = new Trace();
    }
    if (obj.has("workspaceFolders")) {
      workspaceFolders = null; //TODO: handle workspace folders
    }
    logger.logMethodExit("InitializeParams constructor");
  }

  public Integer getProcessId() {
    return processId;
  }

  public String getRootPath() {
    return rootPath;
  }

  public DocumentUri getRootUri() {
    return rootUri;
  }

  public Trace getTrace() {
    return trace;
  }

  public ClientCapabilities getClientCapabilities() {
   return capabilities;
  }

  public WorkspaceFolder[] getWorkspaceFolders() {
    return workspaceFolders;
  }
}

package ablecLanguageServer;

import org.json.*;

public class WorkspaceClientCapabilities {
  private Boolean applyEdit;
  private WorkspaceEdit workspaceEdit;
  private DidChangeConfiguration didChangeConfiguration;
  private DidChangeWatchedFiles didChangeWatchedFiles;
  private Symbol symbol;
  private ExecuteCommand executeCommand;
  private Boolean workspaceFolders;
  private Boolean configuration;

  class WorkspaceEdit {
    Boolean documentChanges;
    ResourceOperationKind[] resourceOperations;
    FailureHandlingKind failureHandling;

    public WorkspaceEdit(JSONObject obj) {
      if (obj.has("documentChanges")) {
        documentChanges = obj.getBoolean("documentChanges");
      }
      if (obj.has("resourceOperations")) {
        resourceOperations = ResourceOperationKind.parseResourceOperationKindJSONArray(obj.getJSONArray("resourceOperations"));
      }
      if (obj.has("failureHandling")) {
        failureHandling = new FailureHandlingKind(obj.getString("failureHandling"));
      }
    }
  }

  class DidChangeConfiguration {
    Boolean dynamicRegistration;

    public DidChangeConfiguration(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
    }
  }

  class DidChangeWatchedFiles {
    Boolean dynamicRegistration;
    
   public DidChangeWatchedFiles(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
    }
  }

  class Symbol {
    Boolean dynamicRegistration;
    SymbolKindCapabilities symbolKind;
    public Symbol(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
      if (obj.has("symbolKind")) {
        symbolKind = new SymbolKindCapabilities(obj.getJSONObject("symbolKind"));
      }
    }

    class SymbolKindCapabilities {
      SymbolKind[] valueSet;
      
      public SymbolKindCapabilities(JSONObject obj) {
        valueSet = SymbolKind.parseSymbolKindJSONArray(obj.getJSONArray("resourceOperations"));
      }
    }
  }
  class ExecuteCommand {
    Boolean dynamicRegistration;
   
    public ExecuteCommand(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
    }
  }

  public WorkspaceClientCapabilities(JSONObject obj) {
    if (obj.has("applyEdit")) {
      applyEdit = obj.getBoolean("applyEdit");
    }
    if (obj.has("workspaceEdit")) {
      workspaceEdit = new WorkspaceEdit(obj.getJSONObject("workspaceEdit"));
    }
    if (obj.has("didChangeConfiguration")) {
      didChangeConfiguration = new DidChangeConfiguration(obj.getJSONObject("didChangeConfiguration"));
    }
    if (obj.has("didChangeWatchedFiles")) {
      didChangeWatchedFiles = new DidChangeWatchedFiles(obj.getJSONObject("didChangeWatchedFiles"));
    }
    if (obj.has("symbol")) {
      symbol = new Symbol(obj.getJSONObject("symbol"));
    }
    if (obj.has("executeCommand")) {
      executeCommand = new ExecuteCommand(obj.getJSONObject("executeCommand"));
    }
    if (obj.has("workspaceFolders")) {
      workspaceFolders = obj.getBoolean("workspaceFolders");
    }
    if (obj.has("configuration")) {
      configuration = obj.getBoolean("configuration");
    }
  }
}


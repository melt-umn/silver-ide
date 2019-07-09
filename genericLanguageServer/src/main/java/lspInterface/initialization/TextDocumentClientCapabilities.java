package ablecLanguageServer;

import org.json.*;

public class TextDocumentClientCapabilities {
  private Synchronization synchronization;
  private Completion completion;
  private Hover hover;
  private SignatureHelp signatureHelp;
  private References references;
  private DocumentHighlight documentHighlight;
  private Formatting formatting;
  private RangeFormatting rangeFormatting;
  private OnTypeFormatting onTypeFormatting;
  
  private Declaration declaration;
  private Definition definition;
  private TypeDefinition typeDefinition;
  private Implementation implementation;
  
  private CodeAction codeAction;
  private CodeLens codeLens;
  private DocumentLink documentLink;
  private ColorProvider colorProvider;
  private Rename rename;
  private PublishDiagnostics publishDiagnostics;
  private FoldingRange foldingRange;

  public TextDocumentClientCapabilities(JSONObject obj) {
    if (obj.has("synchronization")) {
      synchronization = new Synchronization(obj.getJSONObject("synchronization"));
    }
    if (obj.has("completion")) {
      completion = new Completion(obj.getJSONObject("completion"));
    }
    if (obj.has("hover")) {
      hover = new Hover(obj.getJSONObject("hover"));
    }
    if (obj.has("signatureHelp")) {
      signatureHelp = new SignatureHelp(obj.getJSONObject("signatureHelp"));
    }
    if (obj.has("references")) {
      references = new References(obj.getJSONObject("references"));
    }
    if (obj.has("documentHiglight")) {
      documentHighlight = new DocumentHighlight(obj.getJSONObject("documentHighlight"));
    }
    if (obj.has("formatting")) {
      formatting = new Formatting(obj.getJSONObject("formatting"));
    }
    if (obj.has("rangeFormatting")) {
      rangeFormatting = new RangeFormatting(obj.getJSONObject("rangeFormatting"));
    }
    if (obj.has("onTypeFormatting")) {
      onTypeFormatting = new OnTypeFormatting(obj.getJSONObject("onTypeFormatting"));
    }
    if (obj.has("declaration")) {
      declaration = new Declaration(obj.getJSONObject("declaration"));
    }
    if (obj.has("definition")) {
      definition = new Definition(obj.getJSONObject("definition"));
    }
    if (obj.has("typeDefinition")) {
      typeDefinition = new TypeDefinition(obj.getJSONObject("typeDefinition"));
    }
    if (obj.has("implementation")) {
      implementation = new Implementation(obj.getJSONObject("implementation"));
    }
    if (obj.has("codeAction")) {
      codeAction = new CodeAction(obj.getJSONObject("codeAction"));
    }
    if (obj.has("codeLens")) {
      codeLens = new CodeLens(obj.getJSONObject("codeLens"));
    }
    if (obj.has("documentLink")) {
      documentLink = new DocumentLink(obj.getJSONObject("documentLink"));
    }
    if (obj.has("colorProvider")) {
      colorProvider = new ColorProvider(obj.getJSONObject("colorProvider"));
    }
    if (obj.has("rename")) {
      rename = new Rename(obj.getJSONObject("rename"));
    }
    if (obj.has("publishDiagnostics")) {
      synchronization = new Synchronization(obj.getJSONObject("synchronization"));
    }
    if (obj.has("synchronization")) {
      synchronization = new Synchronization(obj.getJSONObject("synchronization"));
    }
  }

  public boolean sendsWillSaveNotification() {
    return synchronization.getWillSave();
  }

  public boolean sendsWillSaveWaitUntilRequest() {
    return synchronization.getWillSaveWaitUntil();
  }

  public boolean sendsDidSaveNotification() {
    return synchronization.getDidSave();
  }

  class Synchronization {
    private Boolean dynamicRegistration;
    private Boolean willSave;
    private Boolean willSaveWaitUntil;
    private Boolean didSave;

    public Synchronization(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
      if (obj.has("willSave")) {
        willSave = obj.getBoolean("willSave");
      }
      if (obj.has("willSaveWaitUntil")) {
        willSaveWaitUntil = obj.getBoolean("willSaveWaitUntil");
      }
      if (obj.has("didSave")) {
        didSave = obj.getBoolean("didSave");
      }
    }

    public boolean getWillSave() {
      return Maybe.getBoolean(willSave, false);
    }

    public boolean getWillSaveWaitUntil() {
      return Maybe.getBoolean(willSaveWaitUntil, false);
    }

    public boolean getDidSave() {
      return Maybe.getBoolean(didSave, false);
    }
  }

  class Completion {
    Boolean dynamicRegistration;
    CompletionItem completionItem;
    CompletionItemKindCapabilities completionItemKind;
    Boolean contextSupport;

    public Completion(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
      if (obj.has("completionItem")) {
        completionItem = new CompletionItem(obj.getJSONObject("completionItem"));
      }
      if (obj.has("completionItemKind")) {
        completionItemKind = new CompletionItemKindCapabilities(obj.getJSONObject("completionItemKind"));
      }
      if (obj.has("contextSupport")) {
        contextSupport = obj.getBoolean("contextSupport");
      }
    }

    class CompletionItem {
      Boolean snippetSupport;
      Boolean commitCharactersSupport;
      MarkupKind[] documentationFormat;
      Boolean deprecatedSupport;
      Boolean preselectSupport;

      public CompletionItem(JSONObject obj) {
        if (obj.has("snippetSupport")) {
          snippetSupport = obj.getBoolean("snippetSupport");
        }
        if (obj.has("commitCharactersSupport")) {
          commitCharactersSupport = obj.getBoolean("commitCharactersSupport");
        }
        if (obj.has("deprecatedSupport")) {
          deprecatedSupport = obj.getBoolean("deprecatedSupport");
        }
        if (obj.has("preselectSupport")) {
          preselectSupport = obj.getBoolean("preselectSupport");
        }
        if (obj.has("documentationFormat")) {
          documentationFormat = MarkupKind.parseMarkupKindJSONArray(obj.getJSONArray("documentationFormat"));
        }
      }
    }
    class CompletionItemKindCapabilities {
      CompletionItemKind[] valueSet;

      public CompletionItemKindCapabilities (JSONObject obj) {
        if (obj.has("valueSet")) {
          valueSet = CompletionItemKind.parseCompletionItemKindJSONArray(obj.getJSONArray("valueSet"));
        }
      }
    }
  }

  class Hover {
    Boolean dynamicRegistration;
    MarkupKind[] contentFormat;
    public Hover(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
      if (obj.has("contentFormat")) {
        contentFormat = MarkupKind.parseMarkupKindJSONArray(obj.getJSONArray("contentFormat"));
      } 
    }
  }

  class SignatureHelp {
    Boolean dynamicRegistration;
    SignatureInformation signatureInformation;

    public SignatureHelp(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
      if (obj.has("signatureInformation")) {
        signatureInformation = new SignatureInformation(obj.getJSONObject("signatureInformation"));
      }
    }
    
    class SignatureInformation {
      MarkupKind[] documentationFormat;
      ParameterInformation parameterInformation;

      public SignatureInformation(JSONObject obj) {
        if (obj.has("documentationFormat")) {
          documentationFormat = MarkupKind.parseMarkupKindJSONArray(obj.getJSONArray("documentationFormat"));
        }
        if (obj.has("parameterInformation")) {
          parameterInformation = new ParameterInformation(obj.getJSONObject("parameterInformation"));
        }
      }

      class ParameterInformation {
        Boolean labelOffsetSupport;

        public ParameterInformation(JSONObject obj) {
          if (obj.has("labelOffsetSupport")) {
            labelOffsetSupport = obj.getBoolean("labelOffsetSupport");
          }
        }
      }
    }
  }

  class References {
    Boolean dynamicRegistration;

    public References(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
    }
  }

  class DocumentHighlight {
    Boolean dynamicRegistration;
    
    public DocumentHighlight(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
    }
  }

  class DocumentSymbol {
    Boolean dynamicRegistration;
    SymbolKindCapabilities symbolKind;
    Boolean hierarchicalDocumentSymbolSupport;
    
    public DocumentSymbol(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
      if (obj.has("symbolKind")) {
        symbolKind = new SymbolKindCapabilities(obj.getJSONObject("symbolKind"));
      }
      if (obj.has("hierarchicalDocumentSymbolSupport")) {
        hierarchicalDocumentSymbolSupport = obj.getBoolean("hierarchicalDocumentSymbolSupport");
      }
    }

    class SymbolKindCapabilities {
      SymbolKind[] valueSet;

      public SymbolKindCapabilities(JSONObject obj) {
        if (obj.has("valueSet")) {
          valueSet = SymbolKind.parseSymbolKindJSONArray(obj.getJSONArray("valueSet"));
        }
      }
    }
  }

  class Formatting {
    Boolean dynamicRegistration;
    
    public Formatting(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
    }
  }

  class RangeFormatting {
    Boolean dynamicRegistration;
    
    public RangeFormatting(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
    }
  }

  class OnTypeFormatting {
    Boolean dynamicRegistration;
    
    public OnTypeFormatting(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
    }
  }

  class Declaration {
    Boolean dynamicRegistration;
    Boolean linkSupport;
    
    public Declaration(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
      if (obj.has("linkSupport")) {
        linkSupport = obj.getBoolean("linkSupport");
      }
    }
  }

  class Definition {
    Boolean dynamicRegistration;
    Boolean linkSupport;
    
    public Definition(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
      if (obj.has("linkSupport")) {
        linkSupport = obj.getBoolean("linkSupport");
      }
    }
  }

  class TypeDefinition {
    Boolean dynamicRegistration;
    Boolean linkSupport;
    
    public TypeDefinition(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
      if (obj.has("linkSupport")) {
        linkSupport = obj.getBoolean("linkSupport");
      }
    }
  }

  class Implementation {
    Boolean dynamicRegistration;
    Boolean linkSupport;
    
    public Implementation(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
      if (obj.has("linkSupport")) {
        linkSupport = obj.getBoolean("linkSupport");
      }
    }
  }

  class CodeAction {
    Boolean dynamicRegistration;
    CodeActionLiteralSupport codeActionLiteralSupport;

    public CodeAction(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
      if (obj.has("codeActionLiteralSupport")) {
        codeActionLiteralSupport = new CodeActionLiteralSupport(obj.getJSONObject("codeActionLiteralSupport"));
      }
    }

    class CodeActionLiteralSupport {
      CodeActionKindCapabilities codeActionKind;

      public CodeActionLiteralSupport(JSONObject obj) {
        codeActionKind = new CodeActionKindCapabilities(obj.getJSONObject("codeActionKind"));
      }

      class CodeActionKindCapabilities {
        CodeActionKind[] valueSet;

        public CodeActionKindCapabilities(JSONObject obj) {
          valueSet = CodeActionKind.parseCodeActionKindJSONArray(obj.getJSONArray("valueSet"));
        }
      }
    }
  }
 
  class CodeLens {
    Boolean dynamicRegistration;
    
    public CodeLens(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
    }
  }
  class DocumentLink {
    Boolean dynamicRegistration;
    
    public DocumentLink(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
    }
  }
  class ColorProvider {
    Boolean dynamicRegistration;
    
    public ColorProvider(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
    }
  }
  class Rename {
    Boolean dynamicRegistration;
    Boolean prepareSupport;
    
    public Rename(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
      if (obj.has("prepareSupport")) {
        prepareSupport = obj.getBoolean("prepareSupport");
      }
    }
  }
  class PublishDiagnostics {
    Boolean relatedInformation;
    
    public PublishDiagnostics(JSONObject obj) {
      if (obj.has("relatedInformation")) {
        relatedInformation = obj.getBoolean("relatedInformation");
      }
    }
  }
  class FoldingRange {
    Boolean dynamicRegistration;
    Integer rangeLimit;
    Boolean lineFoldingOnly;
    
    public FoldingRange(JSONObject obj) {
      if (obj.has("dynamicRegistration")) {
        dynamicRegistration = obj.getBoolean("dynamicRegistration");
      }
      if (obj.has("rangeLimit")) {
        rangeLimit = obj.getInt("prepareSupport");
      }
      if (obj.has("lineFoldingOnly")) {
        lineFoldingOnly = obj.getBoolean("lineFoldingOnly");
      }
    }
  }
}

package ablecLanguageServer;

import java.util.PriorityQueue;

public class Server {
  // singleton instance of the server
  private static Server server = new Server();

  private static final boolean amLogging = true;
  private static Logger logger = Logger.getLogger();
  /**
  * The process Id of the parent process that started
  * the server. Is null if the process has not been started by another process.
  * If the parent process is not alive then the server should exit (see exit notification) its process.
  */

  private Boolean shutdown;
  private PriorityQueue<ServerInitiatedMessage> messagesToSend;

  private FileWatcher fileWatcher;
  private FileMatcher fileMatcher;

  private Integer processId;
  private String rootPath;
  private DocumentUri rootUri;
  private ClientCapabilities clientCapabilities;
  private WorkspaceFolder[] workspaceFolders;
  private Trace trace; 


  private String[] languageFileExtensions;
  private String firstLineRegex;
  // this code should be generated
  private void runStartupCode() {
    this.languageFileExtensions = new String[] {"xc"}; 
    this.firstLineRegex = null;
  }

  private Server() {
    runStartupCode();
    fileMatcher = new FileMatcher(languageFileExtensions, firstLineRegex);
    messagesToSend = new PriorityQueue<ServerInitiatedMessage>();
    lastOnTypeInsertion = System.currentTimeMillis(); 
    shutdown = false;
  }

  public static Server getServer() {
    return server;
  }

  public void setProcessId(Integer processId) {
    logger.logMethodEntrance("Server.setProcessId");
    this.processId = processId;
    logger.logMethodExit("Server.setProcessId");
  }

  public void setRootUri(DocumentUri rootUri) {
    logger.logMethodEntrance("Server.setRootUri");
    this.rootUri = rootUri;
    fileWatcher = new FileWatcher(rootUri, fileMatcher);
    logger.logMethodExit("Server.setRootUri");
  }

  public void setRootPath(String rootPath) {
    logger.logMethodEntrance("Server.setRootPath");
    this.rootPath = rootPath;
    if (rootUri == null) {
      fileWatcher = new FileWatcher(rootPath, fileMatcher);
    }
    logger.logMethodExit("Server.setRootPath");
  }

  public void setClientCapabilities(ClientCapabilities clientCapabilities) {
    logger.logMethodEntrance("Server.setClientCapabilities");
    this.clientCapabilities = clientCapabilities;
    logger.logMethodExit("Server.setClientCapabilities");
  }

  public void setWorkspaceFolders(WorkspaceFolder[] folders) {
    logger.logMethodEntrance("Server.setWorkspaceFolders");
    this.workspaceFolders = folders;
    logger.logMethodExit("Server.setWorkspaceFolders");
  }

  public void setTrace(Trace trace) {
    this.trace = trace;
  }

  public void shutdown() {
    shutdown = true;
    if (amLogging) {
      logger.logMessage("Server is shutdown\n");
    }
  }

  public boolean isShutdown() {
    return shutdown;
  }

  public void addMessageToSend(ServerInitiatedLSPNotification notif, int priority) {
    ServerInitiatedMessage msg = new ServerInitiatedMessage(notif, priority);
    messagesToSend.add(msg);
  }

  public void sendAllServerMessages() {
    while (!messagesToSend.isEmpty()) {
      ServerInitiatedMessage msg = messagesToSend.poll();
      String response = ResponseHandler.constructServerInitiatedMessage(msg);
      if (amLogging) {
        logger.logResponse(response);
      }
      System.out.print(response);
    }
  }

  public boolean amWatchingFile(DocumentUri file) {
    return fileWatcher.amWatchingFile(file);
  }

  public boolean amWatchingFile(String file) {
    return fileWatcher.amWatchingFile(file);
  }

  public Diagnostic[] getErrorsForFile(DocumentUri file) {
    Position errorStart = new Position(0, 1);
    Position errorEnd = new Position(0, 10);
    Range errorRange = new Range(errorStart, errorEnd);
    DiagnosticSeverity severity = new DiagnosticSeverity(DiagnosticSeverity.ERROR);
    Integer errorCode = 1;
    String errorMsg = "Test error message";
    String errorSource = "gcc";
    return new Diagnostic[] {new Diagnostic(errorRange, severity, errorCode, errorSource, errorMsg, null)};
  }

  public TextEdit[] formatDocument(DocumentUri file) {
    // same for insertion
    Position editStart = new Position(1,0);
    Position editEnd = new Position(1,0);
    Range editRange = new Range(editStart, editEnd);
    String newText = "\\n";
    return new TextEdit[] {new TextEdit(editRange, newText)};
  }

  public TextEdit[] formatDocumentRange(DocumentUri file, Range range) {
    Range editRange = new Range(range.getStart(), range.getStart());
    String newText = "formatted:";
    return new TextEdit[] {new TextEdit(editRange, newText)};
  }

  private long lastOnTypeInsertion;
  public TextEdit[] formatDocumentOnType(DocumentUri file, Position pos, String character) {
    long currentTime = System.currentTimeMillis();
    long difference = currentTime - lastOnTypeInsertion;
    if (difference > 250 && character.equals(":")) {
      Range insertSpot = new Range(pos, pos);
      String newText = ":"; // add another colon
      lastOnTypeInsertion = currentTime;
      return new TextEdit[] {new TextEdit(insertSpot, newText)};
    }
    return null;
  }

  public Reference[] findReferencesInDocumentForSymbolAt(DocumentUri doc, Position pos) {
    Position lineAboveStart = Position.getPositionLineAbove(Position.getPositionAtStartOfLine(pos));
    Range range1 = new Range(lineAboveStart, 10);
    Reference ref1 = new Reference(doc, range1, DocumentHighlightKind.READ);

    Position lineBelow = Position.getPositionLineBelow(pos);
    Range range2 = new Range(lineBelow, 4);
    Reference ref2 = new Reference(doc, range2, DocumentHighlightKind.WRITE);
    return new Reference[]{ref1, ref2};
  }

  public Reference[] findReferencesForSymbolAt(DocumentUri doc, Position pos, Boolean includeDecl) {
    return findReferencesInDocumentForSymbolAt(doc, pos);
  }

  public Location findDefinitionForSymbolAt(DocumentUri doc, Position pos) {
    Position lineAboveStart = Position.getPositionLineAbove(Position.getPositionAtStartOfLine(pos));
    Range range = new Range(lineAboveStart, 10);
    Location loc = new Location(doc, range);
    return loc;
  }

  public Location findDeclarationForSymbolAt(DocumentUri doc, Position pos) {
    return findDefinitionForSymbolAt(doc, pos);
  }

  public Location findTypeDefinitionForSymbolAt(DocumentUri doc, Position pos) {
    return findDefinitionForSymbolAt(doc, pos);
  }

  public Location findImplementationForSymbolAt(DocumentUri doc, Position pos) {
    return findDefinitionForSymbolAt(doc, pos);
  }

  public CompletionItem[] getCompletionItems(DocumentUri doc, Position pos) {
    CompletionItem item1 = new CompletionItem("aaaaa");
    CompletionItem item2 = new CompletionItem("aaabb");
    CompletionItem item3 = new CompletionItem("aaacc");
    CompletionItem item4 = new CompletionItem("aaadd");
    return new CompletionItem[] {item1, item2, item3, item4};
  }

  public SignatureHelp getSignatureHelp(DocumentUri doc, Position pos) {
    SignatureInformation[] signatures = getSignatures(doc, pos);
    Pair<Integer, Integer> activeInfo = getActiveSignatureInformation(signatures, doc, pos);
    return new SignatureHelp(signatures, activeInfo.fst(), activeInfo.snd());
  }

  public SignatureInformation[] getSignatures(DocumentUri doc, Position pos) {
    ParameterInformation param1 = new ParameterInformation("stuff: String");
    ParameterInformation param2 = new ParameterInformation("thing: Integer");
    ParameterInformation[] params = new ParameterInformation[] {param1, param2};

    SignatureInformation sig1 = new SignatureInformation("stuff: String, thing: Integer", "NO DOCUMENTATION", params);
    return new SignatureInformation[] {sig1};
  }

  public Pair<Integer, Integer> getActiveSignatureInformation(SignatureInformation[] sigs, DocumentUri doc, Position pos) {
    return new Pair(0,0);
  }
  
  public boolean clientSendsWillSaveNotification() {
    return clientCapabilities.sendsWillSaveNotification();
  }

  public boolean clientSendsWillSaveWaitUntilRequest() {
    return clientCapabilities.sendsWillSaveWaitUntilRequest();
  }
}

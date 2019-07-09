package ablecLanguageServer;
// A document selector is the combination of one or more document filters.
public class DocumentSelector {
  DocumentFilter[] filters;
  
  public DocumentSelector(DocumentFilter[] filters) {
    this.filters = filters;
  }
}

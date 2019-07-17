package genericLanguageServer.lspInterface.references;

import genericLanguageServer.lspInterface.highlighting.*;
import genericLanguageServer.lspInterface.document.*;

public class Reference {
  // the access kind
  private DocumentHighlightKind kind;

  private DocumentUri doc;
  private Range range;

  public Reference(DocumentUri doc, Range range, Integer kind) {
    this.kind = new DocumentHighlightKind(kind);
    this.doc = doc;
    this.range = range;
  }

  public DocumentHighlightKind getKind() {
    return kind;
  }

  public DocumentUri getDocument() {
    return doc;
  }

  public Range getRange() {
    return range;
  }

  public static Location[] convertToLocations(Reference[] references) {
    int entries = references.length;
    Location[] locations = new Location[entries];
    for (int i = 0; i < entries; i++) {
      Reference ref = references[i];
      locations[i] = new Location(ref.getDocument(), ref.getRange());
    }
    return locations;
  }

  public static DocumentHighlight[] convertToDocumentHighlights(Reference[] references) {
    int entries = references.length;
    DocumentHighlight[] highlights = new DocumentHighlight[entries];
    for (int i = 0; i < entries; i++) {
      Reference ref = references[i];
      highlights[i] = new DocumentHighlight(ref.getRange(), ref.getKind());
    }
    return highlights;

  }
}

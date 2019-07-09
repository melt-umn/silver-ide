package ablecLanguageServer;
/**
 * A document link is a range in a text document that links to an internal or external resource, like another
 * text document or a web site.
 */
public class DocumentLink {
	/**
	 * The range this link applies to.
	 */
	Range range;
	/**
	 * The uri this link points to. If missing a resolve request is sent later.
	 */
	DocumentUri target;
	/**
	 * A data entry field that is preserved on a document link between a
	 * DocumentLinkRequest and a DocumentLinkResolveRequest.
	 */
	Object data;
}

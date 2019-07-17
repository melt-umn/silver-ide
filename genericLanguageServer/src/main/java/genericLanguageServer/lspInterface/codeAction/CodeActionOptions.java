package genericLanguageServer.lspInterface.codeAction;
/**
 * Code Action options.
 */
public class CodeActionOptions {
	/**
	 * CodeActionKinds that this server may return.
	 *
	 * The list of kinds may be generic, such as `CodeActionKind.Refactor`, or the server
	 * may list out every specific kind they provide.
	 */
	CodeActionKind[] codeActionKinds;
}

package genericLanguageServer.lspInterface.registration;
/**
 * General parameters to unregister a capability.
 */
public class Unregistration {
	/**
	 * The id used to unregister the request or notification. Usually an id
	 * provided during the register request.
	 */
	String id;

	/**
	 * The method / capability to unregister for.
	 */
	String method;
}

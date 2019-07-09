package ablecLanguageServer;
/**
 * General parameters to register for a capability.
 */
public class Registration {
	/**
	 * The id used to register the request. The id can be used to deregister
	 * the request again.
	 */
	String id;

	/**
	 * The method / capability to register for.
	 */
	String method;

	/**
	 * Options necessary for the registration.
	 */
	Object registerOptions;
}

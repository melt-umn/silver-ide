package genericLanguageServer.lspInterface.registration;

import genericLanguageServer.lspInterface.files.FileSystemWatcher;

/**
 * Describe options to be used when registering for file system change events.
 */
public class DidChangeWatchedFilesRegistrationOptions {
	/**
	 * The watchers to register.
	 */
	FileSystemWatcher[] watchers;

}

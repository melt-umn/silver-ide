package genericLanguageServer;

import genericLanguageServer.lspInterface.ServerInitiatedLSPNotification;

public class ServerInitiatedNotification extends ServerInitiatedMessage {

  public ServerInitiatedNotification(ServerInitiatedLSPNotification notif, int priority) {
    super(notif, priority);
    this.modifyMessage(ResponseHandler.constructServerInitiatedMessage(this));
  }
}

package genericLanguageServer.silverInterface;

import lib.lsp.*;

public class SilverHandlerInterface {
  NLSP_Interface interfaceToPass;

  public SilverHandlerInterface() {
    interfaceToPass = PbuildInterface.invoke();
  }

  public NLSP_Interface getInterface() {
    return interfaceToPass;
  }
}

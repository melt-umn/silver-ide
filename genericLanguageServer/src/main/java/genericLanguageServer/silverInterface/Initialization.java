package genericLanguageServer.silverInterface;

import lib.lsp.*;
import ableC.lsp.*;

public class Initialization {
  public static void initSilver() {
    ableC.lsp.Init.initAllStatics();
    ableC.lsp.Init.init();
    ableC.lsp.Init.postInit();
  }
}

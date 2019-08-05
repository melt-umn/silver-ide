package genericLanguageServer.silverInterface;

import lib.lsp.*;
import YOUR-GRAMMAR.*;

public class Initialization {
  public static void initSilver() {
    YOUR-GRAMMAR.Init.initAllStatics();
    YOUR-GRAMMAR.Init.init();
    YOUR-GRAMMAR.Init.postInit();
  }
}

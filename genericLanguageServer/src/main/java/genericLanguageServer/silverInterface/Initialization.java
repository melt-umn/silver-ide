package genericLanguageServer.silverInterface;

import lib.lsp.*;
import ide.dummyTest.*;

public class Initialization {
  public static void initSilver() {
    lib.lsp.Init.initAllStatics();
    lib.lsp.Init.init();
    lib.lsp.Init.postInit();
    ide.dummyTest.Init.initAllStatics();
    ide.dummyTest.Init.init();
    ide.dummyTest.Init.postInit();
  }
}

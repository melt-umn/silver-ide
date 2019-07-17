package genericLanguageServer.silverInterface;

import genericLanguageServer.Logger;

import java.util.List;
import java.util.ArrayList;

import common.*;
import core.*;
import lib.lsp.*;

public class SilverInterface {
  // return json to send back
  public static String handleMessage(State state, String json, SilverHandlerInterface svInterface) {
    Logger logger = Logger.getLogger();
    // silver wants strings as string catters
    StringCatter jsonForSilver = new StringCatter(json);
    NPair stateReturnPair = PhandleMessage.invoke(state.getState(), jsonForSilver, svInterface.getInterface());
    logger.logMessage("Before fst");
    Object newState = Pfst.invoke(stateReturnPair);
    logger.logMessage("After fst");
    state.setState((NState) newState);
    logger.logMessage("Before snd");
    NMaybe responseMaybe = (NMaybe) Psnd.invoke(stateReturnPair);
    String jsonResponse = null;
    if (responseMaybe instanceof Pjust) {
      Pjust responseVal = (Pjust) responseMaybe;
      Object silverGenericJsonResponse = responseVal.getChild(0);
      // silver gives back strings as string catters
      StringCatter silverJsonResponse = (StringCatter) silverGenericJsonResponse;
      jsonResponse = silverJsonResponse.toString();
      logger.logMessage(jsonResponse);
    }
    return jsonResponse;
  }

  public static List<String> getServerInitiatedMessagesToSend(State state) {
    NPair stateReturnPair = PgetAndClearServerInitiatedMessages.invoke(state.getState());
    Object newState = Pfst.invoke(stateReturnPair);
    state.setState((NState) newState);

    List<String> messages = new ArrayList<String>();
    ConsCell headSilverMessageList = (ConsCell) Psnd.invoke(stateReturnPair);
    while (!headSilverMessageList.nil()) {
      StringCatter message = (StringCatter) headSilverMessageList.head();
      messages.add(message.toString());
      headSilverMessageList = headSilverMessageList.tail();
    }
    return messages;
  }
}

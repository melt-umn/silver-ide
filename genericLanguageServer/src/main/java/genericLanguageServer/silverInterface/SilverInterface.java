package genericLanguageServer.silverInterface;

import genericLanguageServer.Logger;
import genericLanguageServer.Pair;

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
    Object newState = Pfst.invoke(stateReturnPair);
    state.setState((NState) newState);
    NMaybe responseMaybe = (NMaybe) Psnd.invoke(stateReturnPair);
    String jsonResponse = null;
    if (responseMaybe instanceof Pjust) {
      Pjust responseVal = (Pjust) responseMaybe;
      Object silverGenericJsonResponse = responseVal.getChild(0);
      // silver gives back strings as string catters
      StringCatter silverJsonResponse = (StringCatter) silverGenericJsonResponse;
      jsonResponse = silverJsonResponse.toString();
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

  public static Pair<Boolean, Integer> needToExit(State state) {
    NPair exitAndCodePair = PneedToExit.invoke(state.getState());
    Boolean needToExit = (Boolean) Pfst.invoke(exitAndCodePair);
    if (needToExit) {
      NMaybe exitCodeMaybe = (NMaybe) Psnd.invoke(exitAndCodePair);
      if (exitCodeMaybe instanceof Pjust) {
        Pjust exitCodeVal = (Pjust) exitCodeMaybe;
        Integer exitCode = (Integer) exitCodeVal.getChild(0);
        return new Pair(true, exitCode);
      } else {
        return new Pair(true, 1);
      }
    } else {
      return new Pair(false, 0);
    }
  }
}

package ablecLanguageServer;

import org.json.*;

public class RequestMessage extends Message {
  private String stringId;
  private Integer numId;
  private Integer idType;
  private String method;
  private JSONObject params;
  private static Logger logger = Logger.getLogger();

  public static final int STRING_ID_TYPE = 1;
  public static final int INTEGER_ID_TYPE = 2;

  public RequestMessage(String id, String method, JSONObject params) {
    stringId = id;
    idType = STRING_ID_TYPE;
    this.method = method;
    this.params = params;
  }

  public RequestMessage(Integer id, String method, JSONObject params) {
    numId = id;
    idType = INTEGER_ID_TYPE;
    this.method = method;
    this.params = params;
  }

  public boolean hasIntegerId() {
    return idType == INTEGER_ID_TYPE;
  }

  public Integer getNumberId() {
    return numId;
  }

  public String getStringId() {
    return stringId;
  }

  public Integer getIdType() {
    return idType;
  }

  public JSONObject getParams() {
    logger.logMethodEntrance("RequestMessage.getParams");
    logger.logMethodExit("RequestMessage.getParams");
    return params;
  }

  public String getMethod() {
    return method;
  }
}

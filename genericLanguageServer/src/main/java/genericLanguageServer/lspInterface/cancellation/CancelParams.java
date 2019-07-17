package genericLanguageServer.lspInterface.cancellation;

import genericLanguageServer.lspInterface.messages.RequestMessage;

public class CancelParams {
  private String stringId;
  private Integer numId;
  private Integer idType;

  public CancelParams(String id) {
    stringId = id;
    idType = RequestMessage.STRING_ID_TYPE;
  }

  public CancelParams(Integer id) {
    numId = id;
    idType = RequestMessage.INTEGER_ID_TYPE;
  }

  public boolean hasIntegerId() {
    return idType == RequestMessage.INTEGER_ID_TYPE;
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
}

package genericLanguageServer;

import genericLanguageServer.lspInterface.LSPObject;

public class JSONBuilder {

  private StringBuilder jsonBuilder;
  private boolean insertCommaBeforeNextElem;
  private int openObjectCount;

  public JSONBuilder() {
    jsonBuilder = new StringBuilder();
    insertCommaBeforeNextElem = false;
    openObjectCount = 0;
  }

  public void startObject() {
    jsonBuilder.append("{");
    openObjectCount++;
  }

  public void endObject() {
    jsonBuilder.append("}");
    openObjectCount--;
  }

  public static String buildJSONArray(LSPObject[] arr) {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < arr.length - 1; i++) {
      sb.append(arr[i].getJson());
      sb.append(",");
    }
    if (arr.length > 0) {
      sb.append(arr[arr.length-1].getJson());
    }
    sb.append("]");
    return sb.toString();
  }

  public void addArrayValue(LSPObject[] arr) {
    if (arr != null) {
      jsonBuilder.append("[");
      for (int i = 0; i < arr.length - 1; i++) {
        jsonBuilder.append(arr[i].getJson());
        jsonBuilder.append(",");
      }
      if (arr.length > 0) {
        jsonBuilder.append(arr[arr.length-1].getJson());
      }
      jsonBuilder.append("]");
    }
  }

  public void addObjectValue(LSPObject obj) {
    if (obj != null) {
      jsonBuilder.append(obj.getJson());
    }
  }

  public void addStringValue(String val) {
    jsonBuilder.append("\"");
    jsonBuilder.append(val);
    jsonBuilder.append("\"");
  }

  public void addKeyValuePair(String key, LSPObject value) {
    if (value != null) {
      addKey(key);
      addObjectValue(value);
    }
  }

  public void addKeyValuePair(String key, LSPObject[] value) {
    if (value != null) {
      addKey(key);
      jsonBuilder.append(buildJSONArray(value));
    }
  }

  public void addKeyValuePair(String key, String value) {
    if (value != null) {
      addKey(key);
      addStringValue(value);
    }
  }

  public void addKeyValuePair(String key, String[] values) {
    if (values != null) {
      addKey(key);
      jsonBuilder.append("[");
      for (int i = 0; i < values.length - 1; i++) {
        addStringValue(values[i]);
        jsonBuilder.append(",");
      }
      if (values.length > 0) {
        addStringValue(values[values.length-1]);
      }
      jsonBuilder.append("]");
    }
  }

  public void addKeyValuePair(String key, Integer value) {
    if (value != null) {
      addKey(key);
      jsonBuilder.append(value);
    }
  }

  public void addKeyValuePair(String key, Boolean value) {
    if (value != null) {
      addKey(key);
      jsonBuilder.append(value);
    }
  }

  private void addKey(String key) {
    if (insertCommaBeforeNextElem) {
      jsonBuilder.append(",");
    }
    jsonBuilder.append("\"");
    jsonBuilder.append(key);
    jsonBuilder.append("\":");
    insertCommaBeforeNextElem = true;
  }

  public String getJson() {
    return jsonBuilder.toString();
  }
}

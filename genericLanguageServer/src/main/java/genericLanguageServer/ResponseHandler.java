package genericLanguageServer;

import genericLanguageServer.lspInterface.messages.*;

import java.io.UnsupportedEncodingException;

public class ResponseHandler {
  String responseHeader;
  String responseContent;
  public static final String headerSeparator = "\r\n";
  public static final String headerContentSeparator = "\r\n";

  public ResponseHandler(ResponseMessage msg) {
    responseContent = msg.getJson();
    int contentLength = getNumberOfBytes(responseContent, "UTF8");
    responseHeader = "Content-Length: " + contentLength + headerSeparator;
  }

  public ResponseHandler(String msg) {
    responseContent = msg;
    int contentLength = getNumberOfBytes(msg, "UTF8");
    responseHeader = "Content-Length: " + contentLength + headerSeparator;
  }

  public String getResponse() {
    return responseHeader + headerContentSeparator + responseContent;
  }

  public static String constructResponse(String msg) {
    String content = msg;
    int contentLength = getNumberOfBytes(content, "UTF8");
    String header = "Content-Length: " + contentLength + headerSeparator;
    return header + headerContentSeparator + content;
  }

  public static String constructResponse(ResponseMessage msg) {
    String content = msg.getJson();
    int contentLength = getNumberOfBytes(content, "UTF8");
    String header = "Content-Length: " + contentLength + headerSeparator;
    return header + headerContentSeparator + content;
  }

  public static String constructServerInitiatedMessage(String msg) {
    int contentLength = getNumberOfBytes(msg, "UTF8");
    String header = "Content-Length: " + contentLength + headerSeparator;
    return header + headerContentSeparator + msg;
  }

  public static String constructServerInitiatedMessage(ServerInitiatedMessage msg) {
    String content = msg.getMessage();
    int contentLength = getNumberOfBytes(content, "UTF8");
    String header = "Content-Length: " + contentLength + headerSeparator;
    return header + headerContentSeparator + content;
  }

  private static int getNumberOfBytes(String message, String encoding) {
    byte[] contentBytes;
    try {
      contentBytes = message.getBytes(encoding);
    } catch (UnsupportedEncodingException e) {
      contentBytes = new byte[0];
    }
    return contentBytes.length;
  }
}

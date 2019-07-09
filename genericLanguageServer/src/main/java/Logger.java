package ablecLanguageServer;

import java.io.*;
import org.json.*;
import java.util.List;

public class Logger {
  private static Logger logger;
  private File requestLogFile;
  private File responseLogFile;
  private File exceptionLogFile;
  private File messageLogFile;
  private File stackTraceLogFile;
  private BufferedWriter requestFileWriter;
  private BufferedWriter responseFileWriter;
  private BufferedWriter exceptionFileWriter;
  private BufferedWriter messageFileWriter;
  private BufferedWriter stackFileWriter;
  private int requestCount = 0;
  private int responseCount = 0;

  private static final long MAX_LOGFILE_SIZE = 20971520; // 20 MB

  private static final boolean amDebugging = true;

  private Logger() {

  }

  public static Logger getLogger() {
    if (logger == null) {
      logger = new Logger();
    }
    return logger;
  }

  public boolean setLogFiles(File[] logFiles) {
    try {
      requestLogFile = logFiles[0];
      responseLogFile = logFiles[1];
      exceptionLogFile = logFiles[2];
      messageLogFile = logFiles[3];
      stackTraceLogFile = logFiles[4];
      requestFileWriter = new BufferedWriter (new FileWriter(requestLogFile));
      responseFileWriter = new BufferedWriter (new FileWriter(responseLogFile));
      exceptionFileWriter = new BufferedWriter (new FileWriter(exceptionLogFile));
      messageFileWriter = new BufferedWriter (new FileWriter(messageLogFile));
      stackFileWriter = new BufferedWriter (new FileWriter(stackTraceLogFile));
      return true;
    } catch (IOException e) {
      return false;
    }

  }

  private boolean setLogFiles(File request, File response, File exception, File message, File stackTrace) {
    try {
      requestLogFile = request;
      responseLogFile = response;
      exceptionLogFile = exception;
      messageLogFile = message;
      stackTraceLogFile = stackTrace;
      requestFileWriter = new BufferedWriter (new FileWriter(requestLogFile));
      responseFileWriter = new BufferedWriter (new FileWriter(responseLogFile));
      exceptionFileWriter = new BufferedWriter (new FileWriter(exceptionLogFile));
      messageFileWriter = new BufferedWriter (new FileWriter(messageLogFile));
      stackFileWriter = new BufferedWriter (new FileWriter(stackTraceLogFile));
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  private boolean clearFileIfTooLarge(File f) {
    try {
      if (f.length() > MAX_LOGFILE_SIZE) {
        RandomAccessFile r = new RandomAccessFile(f, "rw");
        r.setLength(0);
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  public boolean clearLargeLogFiles() {
    clearFileIfTooLarge(requestLogFile);
    clearFileIfTooLarge(responseLogFile);
    clearFileIfTooLarge(exceptionLogFile);
    clearFileIfTooLarge(messageLogFile);
    clearFileIfTooLarge(stackTraceLogFile);
    return true;
  }

  public boolean logRequests(List<JSONObject> requests) {
    try {
      if (requests.size() > 1) {
        requestFileWriter.write("BATCH REQUEST\n");
      }
      for (JSONObject request : requests) {
        logRequest(request);
      }
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public boolean logRequest(JSONObject request) {
    try {
      StringBuilder requestTitle = new StringBuilder("Request ");
      requestCount += 1;
      requestTitle.append(requestCount);
      requestTitle.append('\n');
      requestFileWriter.write(requestTitle.toString());
      String requestText = request.toString();
      if (requestText != null) {
        requestFileWriter.write(requestText);
      } else {
        requestFileWriter.write("Error converting JSON to String\n");
      }
      requestFileWriter.write("\n");
      if (amDebugging) {
        flush();
      }
      return true;
    } catch (IOException e) {
      return false;
    }
  }


  public boolean logResponse(String response) {
    try {
      StringBuilder responseTitle = new StringBuilder("Response ");
      responseCount += 1;
      responseTitle.append(responseCount);
      responseTitle.append('\n');
      responseFileWriter.write(responseTitle.toString());
      responseFileWriter.write(response);
      responseFileWriter.write("\n");
      if (amDebugging) {
        flush();
      }
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public boolean close() {
    try {
      requestFileWriter.close();
      responseFileWriter.close();
      exceptionFileWriter.close();
      messageFileWriter.close();
      stackFileWriter.close();
      copyFile(requestLogFile);
      copyFile(responseLogFile);
      copyFile(exceptionLogFile);
      copyFile(messageLogFile);
      copyFile(stackTraceLogFile);
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public boolean flush() {
    try {
      requestFileWriter.flush();
      responseFileWriter.flush();
      exceptionFileWriter.flush();
      messageFileWriter.flush();
      stackFileWriter.flush();
      return true;
    } catch (IOException e) {
      return false;
    }
  }
  
  public boolean logException(Exception e) {
    try {
      String message = e.getMessage();
      if (message == null) {
        exceptionFileWriter.write("An exception of type " + e.getClass() + " occurred with a null message");
      } else {
        exceptionFileWriter.write(e.getMessage());
      }
      exceptionFileWriter.write('\n');
      if (amDebugging) {
        flush();
      }
      return true;
    } catch (IOException exp) {
      return false;
    }
  }

  public boolean logMessage(String message) {
    try {
      messageFileWriter.write(message);
      messageFileWriter.write('\n');
      if (amDebugging) {
        flush();
      }
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public boolean logMethodEntrance(String methodName) {
    try {
      stackFileWriter.write(methodName + " Entered");
      stackFileWriter.write('\n');
      if (amDebugging) {
        flush();
      }
      return true;
    } catch (IOException e) {
      return false;
    }

  }

  public boolean logMethodExit(String methodName) {
    try {
      stackFileWriter.write(methodName + " Exited");
      stackFileWriter.write('\n');
      if (amDebugging) {
        flush();
      }
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public void copyFile(File input) 
  throws IOException {
  
    File output = new File(input.getPath() + ".old");
      InputStream in = new BufferedInputStream(
        new FileInputStream(input));
      OutputStream out = new BufferedOutputStream(
        new FileOutputStream(output));
  
        byte[] buffer = new byte[1024];
        int lengthRead;
        while ((lengthRead = in.read(buffer)) > 0) {
            out.write(buffer, 0, lengthRead);
            out.flush();
        }
  }

  public void setupAndUseDefaultLogFiles() throws IOException {
    int numLogFiles = 5;
    File[] logFiles = new File[numLogFiles];
    logFiles[0] = new File("/Users/joeblanchard/serverlogs/RequestLog");
    logFiles[1] = new File("/Users/joeblanchard/serverlogs/ResponseLog");
    logFiles[2] = new File("/Users/joeblanchard/serverlogs/ExceptionLog");
    logFiles[3] = new File("/Users/joeblanchard/serverlogs/MessageLog");
    logFiles[4] = new File("/Users/joeblanchard/serverlogs/StackTraceLog");
    for (File file : logFiles) {
      file.getParentFile().mkdirs();
      file.createNewFile();
    }
    setLogFiles(logFiles);
  }
}

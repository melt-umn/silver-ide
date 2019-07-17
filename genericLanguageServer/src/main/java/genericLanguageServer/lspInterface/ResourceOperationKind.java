package genericLanguageServer.lspInterface;

import org.json.*;

public class ResourceOperationKind {
  private String kind;
  private static final String ILLEGAL_VALUE_ERROR_MSG_BASE =
    "ResourceOperationKind string must be 'create', 'rename' or 'delete' instead it was: ";
  public static final String CREATE = "create";
  public static final String RENAME = "rename";
  public static final String DELETE = "delete";
  public ResourceOperationKind(String kind) {
    if (!kind.equals(ResourceOperationKind.CREATE) &&
        !kind.equals(ResourceOperationKind.RENAME) &&
        !kind.equals(ResourceOperationKind.DELETE)) {
      throw new IllegalArgumentException(ILLEGAL_VALUE_ERROR_MSG_BASE + kind);
    }
    this.kind = kind;
  }

  public String getKind() {
    return kind;
  }

  public static ResourceOperationKind[] parseResourceOperationKindJSONArray(JSONArray ops) {
    int entries = ops.length();
    ResourceOperationKind[] kinds = new ResourceOperationKind[entries];
    for (int i = 0; i < entries; i++) {
      kinds[i] = new ResourceOperationKind(ops.getString(i));
    }
    return kinds;
  }
}

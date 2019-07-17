package genericLanguageServer.silverInterface;

import lib.lsp.*;

public class State {
  private NState state;

  public State() {
    this.state = new PinitialState();
  }

  public State(NState state) {
    this.state = state;
  }

  public void setState(NState state) {
    this.state = state;
  }

  public NState getState() {
    return state;
  }
}

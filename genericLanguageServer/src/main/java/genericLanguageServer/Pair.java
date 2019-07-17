package genericLanguageServer;

public class Pair<F,S> {
  private F fst;
  private S snd;

  public Pair(F fst, S snd) {
    this.fst = fst;
    this.snd = snd;
  }

  public F fst() {
    return fst;
  }

  public S snd() {
    return snd;
  }

  public String toString() {
    return "(" + fst.toString() + "," + snd.toString() + ")";
  }
}

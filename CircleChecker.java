import java.util.*;

public class CircleChecker {

  Cellworker cw = new Cellworker();
  ExpressionParser ep = new ExpressionParser();

  int d = 0;

  public CircleChecker() {
  }

  public boolean cCheck(String match, String exp, Table T) {
    StringTokenizer st1 = new StringTokenizer(exp);
    int i = 0;
    while (st1.hasMoreTokens()) {
      String tok = st1.nextToken();
      if (cw.isReference(tok)) { i++; }
      if (cw.isDate(tok)) { d++; }
    }
    StringTokenizer st2 = new StringTokenizer(exp);
    int j = 0;
    while (st2.hasMoreTokens()) {
      String tok = st2.nextToken();
      if (tok.equals(match)) {
        return false;
      } else if (cw.isReference(tok)) {
        String gool = "";
        try {
          gool = cw.getOneReference(tok, T);
        } catch (EmptyCellException e) { }
        if ( cCheck(match, gool, T) )
        j++;
      }
    }
    if (i == j) {
      return true;
    } else {
      return false;
    }
  }

  public int dCheck(String match, String exp, Table T) {
    d = 0;
    cCheck(match, exp, T);
    return d;
  }

}

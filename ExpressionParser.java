import java.util.*;
import java.math.*;

public class ExpressionParser {

  private Cellworker cw = new Cellworker();
  int tracer = 0;
  int pcounter = 0;
  String[] exp;

  public BigDecimal parseStart(String input, Table T) {
    StringTokenizer parts = new StringTokenizer(input);
    int length = parts.countTokens();
    String[] expression = new String[length];
    for (int i = 0; i < length; i ++) {
      expression[i] = parts.nextToken();
    }
    tracer = 0;
    pcounter = 0;
    exp = expression;
    BigDecimal answer = parseExpression(pcounter, T, "1");
    if (pcounter == 0) {
      return answer;
    } else {
      System.out.println("ERROR: Non-matching parens.");
    }
    return new BigDecimal("0");
  }

  public BigDecimal cCheckParseStart(String input, Table T) {
    StringTokenizer parts = new StringTokenizer(input);
    int length = parts.countTokens();
    String[] expression = new String[length];
    for (int i = 0; i < length; i ++) {
      expression[i] = parts.nextToken();
    }
    tracer = 0;
    pcounter = 0;
    exp = expression;
    return parseExpression(pcounter, T, "2");
  }

  private BigDecimal parseExpression(int pentrance, Table T, String code) {
    BigDecimal answer = new BigDecimal(0);
    Operator op = new Operator("+");

    while (tracer < exp.length) {

      if (cw.isNumber(exp[tracer])) {
        answer = op.operate(answer, new BigDecimal(exp[tracer]));
        tracer++;
      } else if (cw.isOperator(exp[tracer])) {
        op.contents = exp[tracer];
        tracer ++;
      } else if (exp[tracer].equals("(")) {
        tracer++;
        pcounter++;
        answer = op.operate(answer, parseExpression(pcounter, T, code));
      } else if (exp[tracer].equals(")")) {
        pcounter--;
        tracer++;
        return answer;
      } else if (cw.isDate(exp[tracer])) {

        StringTokenizer st = new StringTokenizer(exp[tracer], "-");
        String y = st.nextToken();
        String m = st.nextToken();
        String d = st.nextToken();
        Date crap = new Date(y, m, d);

        answer = op.operate(answer, new BigDecimal(Integer.toString(cw.DatetoInt(crap))));
        tracer++;

      } else if (exp[tracer].equals(")")) {
        System.out.println("YOU SHOULD NOT SEE THIS");

      } else if (cw.isReference(exp[tracer])) {


        String thing = "";
        if (code.equals("2")) {

          try {
            thing = cw.getOneReference(exp[tracer], T);
          } catch (EmptyCellException e) {
            System.out.println("ERROR: Cell cannot be referenced");
          }

        } else {

          try {
            thing = cw.getReference(exp[tracer], T);
          } catch (EmptyCellException e) {
            System.out.println("ERROR: Cell cannot be referenced");
          }

        }
        answer = op.operate(answer, (new ExpressionParser()).parseStart(thing, T));
        tracer++;

      } else {
        System.out.println("ERROR: Should have already been preparsed.");
        break;
      }
    }
    return answer;
  }


  public boolean preParser(String exp) { // must be at least 2 tokens long

    Cellworker cw = new Cellworker();
    StringTokenizer parts = new StringTokenizer(exp);
    String temp1 = parts.nextToken();
    String temp2 = parts.nextToken();
    if (cw.isOperator(temp1)) {
      return false;
    }

    if (! (parts.hasMoreTokens())) {
      return false;
    }

    int count = parts.countTokens();

    for (int i = 0; i <= count; i++) {

      if (cw.isText(temp1) || cw.isText(temp2)) {
        return false;
      } else if (cw.isNumber(temp1)) {
        if (cw.isNumber(temp2) ||
            cw.isReference(temp2) ||
            temp2.equals("(") ||
            cw.isDate(temp2)) {
          return false;
        }

      } else if (cw.isReference(temp1)) {
        if (cw.isNumber(temp2) ||
            cw.isReference(temp2) ||
            temp2.equals("(") ||
            cw.isDate(temp2)) {
          return false;
        }
      } else if (cw.isDate(temp1)) {
        if (cw.isNumber(temp2) ||
            cw.isReference(temp2) ||
            temp2.equals("(") ||
            cw.isDate(temp2)) {
          return false;
        }
      } else if (cw.isOperator(temp1)) {
        if (temp2.equals(")") ||
            cw.isOperator(temp2)) {
          return false;
        }
      } else if (temp1.equals (")")) {
        if (cw.isNumber(temp2) ||
            cw.isReference(temp2) ||
            temp2.equals("(") ||
            cw.isDate(temp2)) {
          return false;
        }
      } else if (temp1.equals ("(")) {
        if (temp2.equals(")") ||
            cw.isOperator(temp2))
          return false;
        }
      } else {
        return false;
      }

      temp1 = temp2;
      if (parts.countTokens() != 0) {
        temp2 = parts.nextToken();
      }
    }
    return true;
  }

}

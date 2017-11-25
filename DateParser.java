import java.math.*;
import java.util.*;

public class DateParser {

  private Cellworker cw = new Cellworker();
  int tracer = 0;
  int pcounter = 0;
  String[] exp;

  public Date parseStart(String input) {
    StringTokenizer parts = new StringTokenizer(input);
    int length = parts.countTokens();
    String[] expression = new String[length];
    for (int i = 0; i < length; i ++) {
      expression[i] = parts.nextToken();
    }
    tracer = 0;
    pcounter = 0;
    exp = expression;
    return parseExpression(pcounter);
  }

  private Date parseExpression(int pentrance) {
    BigDecimal answer = new BigDecimal("0");
    Date realanswer = new Date("1983", "03", "22");
    boolean dateyet = false;
    Operator op = new Operator("+");

    while (tracer < exp.length) {

      //	System.out.println(answer + "  " + exp[tracer]
      //			   + "  " + tracer + "  " + pcounter);

      if (cw.isNumber(exp[tracer])) {
        if (dateyet) {
          realanswer = op.doperate(realanswer.toString(), exp[tracer]);
        } else {
          answer = op.operate(answer, new BigDecimal(exp[tracer]));
        }
        tracer++;
      } else if (cw.isOperator(exp[tracer])) {
        op.contents = exp[tracer];
        tracer ++;
      } else if (exp[tracer].equals("(")) {
        tracer++;
        pcounter++;
        if (dateyet) {
          realanswer = op.doperate(realanswer.toString(), parseExpression(pcounter).toString());
        } else {
          answer = op.operate(answer, parseExpression(pcounter));
        }
      } else if (exp[tracer].equals(")") && pcounter == pentrance) {
        pcounter--;
        tracer++;

        if (dateyet) {
          return realanswer;
        } else {
          return answer;
        }
      } else if (cw.isDate(exp[tracer])) {
        dateyet = true;
        realanswer = op.doperate(answer, exp[tracer]);
        tracer++;
      } else if (exp[tracer].equals(")")) {
        System.out.println("YOU SHOULD NOT SEE THIS");
      } else
      System.out.println("YOU ALSO SHOULD NOT SEE THIS");
    }
    return realanswer;
  }

}

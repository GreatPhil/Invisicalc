import java.math.*;
import java.util.*;

public class Operator {

  String contents;

  public Operator(String contents) {
    this.contents = contents;
  }

  public BigDecimal operate(BigDecimal left, BigDecimal right) {
    if (contents.equals("+")) {
      return left.add(right);
    } else if (contents.equals("-")) {
      return left.subtract(right);
    } else if (contents.equals("*")) {
      return left.multiply(right);
    } else if (contents.equals("/")) {
      return left.divide(right, 500, 1); // Accurate to 500 digits
    } else {
      // ELSE THROW AN EXCEPTION ABOUT WRONG OPERATOR
      return new BigDecimal(0);
    }
  }

  // public Date doperate (String left, String right) {
  //   Cellworker cw = new Cellworker();
  //   if (cw.isDate(left)) {
  //     StringTokenizer st = new StringTokenizer(left, "-");
  //     String y = st.nextToken();
  //     String m = st.nextToken();
  //     String d = st.nextToken();
  //     Date temp = new Date(y, m, d);
  //     int change = (new BigInteger(right)).intValue();
  //     if (contents.equals("+"))
  //       temp.value.add(temp.value.DATE, change);
  //     if (contents.equals("-"))
  //       temp.value.add(temp.value.DATE, 0 - change);
  //     return temp;
  //
  //   } else if (cw.isDate(right)) {
  //     StringTokenizer st = new StringTokenizer(right, "-");
  //     String y = st.nextToken();
  //     String m = st.nextToken();
  //     String d = st.nextToken();
  //     Date temp = new Date(y, m, d);
  //     int change = (new BigInteger(left)).intValue();
  //     if (contents.equals("+"))
  //       temp.value.add(temp.value.DATE, change);
  //     return temp;
  //   }
  //   return new Date("1983", "03", "22"); // my Birthday.
  // }


  //
  // public static void main(String[] args) {
  //   Operator op = new Operator("-");
  //   System.out.println( "" + op.doperate("1998-03-22", "1"));
  // }


}

import java.util.*;
import java.math.*;

public class Cellworker {

  public Cellworker() {
  }

  public static int DatetoInt (Date d) {
    int answer = d.value.get(d.value.DAY_OF_YEAR);
    int y = d.value.get(d.value.YEAR);
    if (y > 1582) {
      answer = answer - 10; //Don't know why...
    }
    y--;
    while (y != 0) {
      if (d.value.isLeapYear(y)) {
        answer = answer + 366;
      } else {
        answer = answer + 365;
      }
      y--;
    }
    return answer;
  }

  public static Date InttoDate (int i) {
    Date d = new Date("1", "1", "1");
    d.value.add(d.value.DATE, i - 1);
    return d;
  }

  public static boolean isNumber(String str) {
    char[] strarr = str.toCharArray();
    int decim = 0;
    for (int i = 0; i < strarr.length; i++) {
      if (! (strarr[i] == '1' ||
             strarr[i] == '2' ||
             strarr[i] == '3'	||
             strarr[i] == '4' ||
             strarr[i] == '5' ||
             strarr[i] == '6' ||
             strarr[i] == '7' ||
             strarr[i] == '8' ||
             strarr[i] == '9' ||
             strarr[i] == '0' ||
             strarr[i] == '.' ||
            (strarr[i] == '-' && i == 0)))
      return false;
      if (strarr[i] == '.') {
        decim += 1;
      }
      if (strarr[i] == '-' && i == 0 && strarr.length == 1) {
        return false;
      }
    }
    if (decim < 2) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean isDigit(char i) {
    if (i == '1' ||
        i == '2' ||
        i == '3'||
        i == '4' ||
        i == '5' ||
        i == '6' ||
        i == '7' ||
        i == '8' ||
        i == '9' ||
        i == '0') {
      return true;
    } else {
      return false;
    }
  }

  public static boolean isInteger(String str) {
    char[] strarr = str.toCharArray();
    for (int i = 0; i < str.length(); i++) {
      if (! (strarr[i] == '1' ||
             strarr[i] == '2' ||
             strarr[i] == '3'	||
             strarr[i] == '4'	||
             strarr[i] == '5'	||
             strarr[i] == '6'	||
             strarr[i] == '7'	||
             strarr[i] == '8'	||
             strarr[i] == '9'	||
             strarr[i] == '0')) {
        return false;
      }
    }
    return true;
  }

  public static boolean isLetter(char i) {
    if (i == 'A' ||
        i == 'B' ||
        i == 'C' ||
        i == 'D' ||
        i == 'E' ||
        i == 'F' ||
        i == 'G' ||
        i == 'H' ||
        i == 'I' ||
        i == 'J' ||
        i == 'K' ||
        i == 'L' ||
        i == 'M' ||
        i == 'N' ||
        i == 'O' ||
        i == 'P' ||
        i == 'Q' ||
        i == 'R' ||
        i == 'S' ||
        i == 'T' ||
        i == 'U' ||
        i == 'V' ||
        i == 'W' ||
        i == 'X' ||
        i == 'Y' ||
        i == 'Z') {
      return true;
    } else {
      return false;
    }
  }


  public static boolean isText(String str) {
    return (str.charAt(0) == '"' && str.charAt(str.length() - 1) == '"');
  }


  public static boolean isDate(String str) {
    StringTokenizer dat = new StringTokenizer(str, "-");
    if (dat.countTokens() == 3) {
      String y = dat.nextToken();
      String m = dat.nextToken();
      String d = dat.nextToken();
      boolean tf = (isNumber (y) &&
                    isNumber(m)&&
                    isNumber(d) &&
                    // y.length() == 4 &&
                    m.length() == 2 &&
                    d.length() == 2 &&
                    Integer.parseInt(m) < 13);
      GregorianCalendar nerf = new GregorianCalendar();
      if ((Integer.parseInt(m) % 2 == 1 && Integer.parseInt(d) < 32) ||
          (Integer.parseInt(m) == 2 && nerf.isLeapYear(Integer.parseInt(y)) && Integer.parseInt(d) < 30) ||
          (Integer.parseInt(m) == 2 && !nerf.isLeapYear(Integer.parseInt(y)) &&bInteger.parseInt(d) < 29) ||
          (Integer.parseInt(m) % 2 == 0 && Integer.parseInt(d) < 31)) {
        return tf;
      }
      return false;
    }
    return false;
  }



  public static int DateCounter(String str) {
    StringTokenizer st = new StringTokenizer(str);
    int i = 0;
    while (st.hasMoreTokens()) {
      String k = st.nextToken();
      if (isDate(k))
      i++;
    }
    return i;
  }



  public static boolean noReferences(String str) {
    StringTokenizer st = new StringTokenizer(str);
    boolean b = true;
    while (st.hasMoreTokens()) {
      String k = st.nextToken();
      if (isReference(k))
      b = false;
    }
    return b;
  }



  public static boolean isOperator(String str) {
    if (str.equals("+") ||
        str.equals("-") ||
        str.equals("*") ||
        str.equals("/")) {
      return true;
    }
    return false;
  }

  public static String Cget(String arg) {
    String answer = "";
    for (int i = 0; i < arg.length(); i++) {
      if (isLetter(arg.charAt(i)))
      answer = answer + arg.charAt(i);
    }
    return answer;
  }

  public static String Rget(String arg) {
    String answer = "";
    for (int i = 0; i < arg.length(); i++) {
      if (isDigit(arg.charAt(i)))
      answer = answer + arg.charAt(i);
    }
    return answer;
  }


  //Helper for String Comparison
  public static boolean isGreater(String a, String b) {
    if (a.length() > b.length()) {
      return true;
    }
    if (a.length() < b.length()) {
      return false;
    }
    for (int i = 0; i < a.length(); i++) {
      if (a.charAt(i) > b.charAt(i)) {
        return true;
      }
      if (a.charAt(i) < b.charAt(i)) {
        return false;
      }
    }
    return false;
  }

  public static boolean isLegalCellName(String name) {
    boolean k = false;
    if (Cget(name).equals("")) {
      return false;
    }
    if (Rget(name).equals("")) {
      return false;
    }
    if ((new BigInteger((Rget(name)))).compareTo(new BigInteger("0")) < 1) {
      return false;
    }
    for (int i = 0; i < name.length(); i++) {
      if (isDigit(name.charAt(i))) {
        if (k == false) {
          k = true;
        }
      } else if (isLetter(name.charAt(i))) {
        if (k == true) {
          return false;
        }
      } else {
        return false;
      }
    }
    return true;
  }


  public static String getOneReference(String ref, Table T) throws EmptyCellException {

    if (isLegalCellName(ref)) {
      String ro = Rget(ref);
      String co = Cget(ref);
      Cell val = T.fetchItem(new BigInteger(ro), co);
      if (val.type.equals("Reference") || val.type.equals("ReferenceDate")) {
        return (String)val.value;
      }
      if (val.type.equals("Text")) {
        return (String)val.value;
      }
      if (val.type.equals("Real")) {
        return val.value.toString();
      }
      if (val.type.equals("Date")) {
        return val.value.toString();
      }
      if (val.type.equals("Empty")) {
        throw new EmptyCellException();
      }

    } else if (ref.substring(0,4).equals("sum(") || ref.substring(0,4).equals("avg(")) {
      StringTokenizer st = new StringTokenizer(ref.substring(4), "-");
      String s1 = st.nextToken();
      String s2 = st.nextToken();
      if (isLegalCellName(s1) &&
          isLegalCellName(s2.substring(0, s2.length() - 1)) &&
          s2.charAt(s2.length() - 1) == ')' &&
          (! (st.hasMoreTokens()))) {

        String r1 = Rget(s1);
        String c1 = Cget(s1);
        String r2 = Rget(s2);
        String c2 = Cget(s2);
        if (ColumnValue(c1).compareTo(ColumnValue(c2)) > 0) {
          String temp = c1;
          c1 = c2;
          c2 = temp;
        }
        if ((new BigInteger(r1)).compareTo(new BigInteger(r2)) > 0) {
          String temp = r1;
          r1 = r2;
          r2 = temp;
        }

        String answer = "0";
        BigInteger quotient = new BigInteger("0");

        BigInteger col1 = ColumnValue(c1);
        BigInteger col2 = ColumnValue(c2);
        BigInteger row1 = new BigInteger(r1);
        BigInteger row2 = new BigInteger(r2);
        BigInteger track1;
        BigInteger track2;

        for (track1 = row1; track1.compareTo(row2) <= 0; track1 = track1.add(new BigInteger("1"))) {
          for (track2 = col1; track2.compareTo(col2) <= 0; track2 = track2.add(new BigInteger("1"))) {
            Cell fetched = T.fetchItem(track1, ColumnName(track2));


            if (fetched.type.equals("Real")) {
              answer = answer + " + " + fetched.value.toString();
              quotient = quotient.add(new BigInteger("1"));
            } else if (fetched.type.equals("Reference")) {
              answer = answer + " + " + fetched.value.toString();
              quotient = quotient.add(new BigInteger("1"));
            } else {
              System.out.println("ERROR: Cells may be empty or not summable");
              throw new EmptyCellException();
            }

          }
        }
        if (ref.substring(0,3).equals("avg"))
        answer = answer + " / " + quotient.toString();

        return answer;
      }

      System.out.println("ERROR: Need to Specify what to sum(what-what)");
    }
    return (new ExpressionParser()).cCheckParseStart(ref, T).toString();
  }


  public static String getReference(String ref, Table T) throws EmptyCellException {

    if (isLegalCellName(ref)) {
      String ro = Rget(ref);
      String co = Cget(ref);
      Cell val = T.fetchItem(new BigInteger(ro), co);
      if (val.type.equals("Reference") || val.type.equals("ReferenceDate")) {
        return getReference((String)val.value, T);
      }
      if (val.type.equals("Text")) {
        return (String)val.value;
      }
      if (val.type.equals("Real")) {
        return val.value.toString();
      }
      if (val.type.equals("Date")) {
        return val.value.toString();
      }
      if (val.type.equals("Empty")) {
        throw new EmptyCellException();
      }

    } else if (ref.substring(0,4).equals("sum(") || ref.substring(0,4).equals("avg(")) {
      StringTokenizer st = new StringTokenizer(ref.substring(4), "-");
      String s1 = st.nextToken();
      String s2 = st.nextToken();
      if (isLegalCellName(s1) &&
          isLegalCellName(s2.substring(0, s2.length() - 1)) &&
          s2.charAt(s2.length() - 1) == ')' &&
          (! (st.hasMoreTokens()))) {

        String r1 = Rget(s1);
        String c1 = Cget(s1);
        String r2 = Rget(s2);
        String c2 = Cget(s2);
        if (ColumnValue(c1).compareTo(ColumnValue(c2)) > 0) {
          String temp = c1;
          c1 = c2;
          c2 = temp;
        }
        if ((new BigInteger(r1)).compareTo(new BigInteger(r2)) > 0) {
          String temp = r1;
          r1 = r2;
          r2 = temp;
        }

        String answer = "0";
        BigInteger quotient = new BigInteger("0");

        BigInteger col1 = ColumnValue(c1);
        BigInteger col2 = ColumnValue(c2);
        BigInteger row1 = new BigInteger(r1);
        BigInteger row2 = new BigInteger(r2);
        BigInteger track1;
        BigInteger track2;

        for (track1 = row1; track1.compareTo(row2) <= 0; track1 = track1.add(new BigInteger("1"))) {
          for (track2 = col1; track2.compareTo(col2) <= 0; track2 = track2.add(new BigInteger("1"))) {
            Cell fetched = T.fetchItem(track1, ColumnName(track2));

            if (fetched.type.equals("Real")) {
              answer = answer + " + " + fetched.value.toString();
              quotient = quotient.add(new BigInteger("1"));
            } else if (fetched.type.equals("Reference")) {
              answer = answer + " + " + getReference(fetched.value.toString(), T);
              quotient = quotient.add(new BigInteger("1"));
            } else {
              System.out.println("Can't SUM or AVG anything that is not a Real!!");
              throw new EmptyCellException();
            }

          }
        }
        if (ref.substring(0,3).equals("avg"))
        answer = answer + " / " + quotient.toString();

        return (new ExpressionParser()).parseStart(answer, T).toString();
      }

      System.out.println("ERROR: Need to Specify what to sum(C1-C2)");
    }
    return (new ExpressionParser()).parseStart(ref, T).toString();
  }


  public static boolean isReference(String name) {
    if (isLegalCellName(name))
    return true;
    if (name.length() < 10)
    return false;
    if (name.substring(0,4).equals("sum(") || name.substring(0,4).equals("avg(")) {
      StringTokenizer st = new StringTokenizer(name.substring(4), "-");
      String s1 = st.nextToken();
      String s2 = st.nextToken();
      if (isLegalCellName(s1) &&
          isLegalCellName(s2.substring(0, s2.length() - 1)) &&
          s2.charAt(s2.length() - 1) == ')' &&
          (! (st.hasMoreTokens()))) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }


  public static BigInteger ColumnValue(String name) {
    int len = name.length() - 1;
    BigInteger answer = new BigInteger("0");
    for (int i = len, j = 0; i >= 0; i --, j++) {
      int k = Character.getNumericValue(name.charAt(i)) - 9;
      String s = Integer.toString(k);
      BigInteger n26 = new BigInteger("26");
      answer = answer.add( (n26.pow(j)).multiply(new BigInteger(s)));
    }
    return answer;
  }

  public static String ColumnName(BigInteger bi) {
    String answer = "";
    BigInteger n26 = new BigInteger("26");
    String [] lett = {"A","B","C","D","E","F","G","H","I",
                      "J","K","L","M","N","O","P","Q","R",
                      "S","T","U","V","W","X","Y","Z"};
    while (bi.compareTo(new BigInteger("0")) == 1) { //greater than 0
      int rem = bi.remainder(n26).intValue();
      bi = bi.divide(n26);
      
      if (rem == 0 ) {
        answer = "Z" + answer;
        bi = bi.subtract(new BigInteger("1"));
      }
      else {
        String key = lett[rem - 1];
        answer = key + answer;
      }
    }
    return answer;
  }

}

class EmptyCellException extends Exception {
  EmptyCellException(){;}
}

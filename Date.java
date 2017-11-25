import java.util.*;

public class Date {

  public GregorianCalendar value;

  public Date (String year, String month, String date) {
    int y = Integer.parseInt(year);
    int m = Integer.parseInt(month) - 1;
    int d = Integer.parseInt(date);
    value = new GregorianCalendar(y, m, d);
  }

  public String toString() {

    String m = "" + (value.get(value.MONTH) + 1);
    if (m.length() == 1) {
      m = "0" + m;
    }

    String d = "" + value.get(value.DAY_OF_MONTH);
    if (d.length() == 1) {
      d = "0" + d;
    }

    String s = value.get(value.YEAR) + "-" + m + "-" + d;
    return s;
  }

  public static void main(String[] args) {
    Date d = new Date("7356", "12", "02");
    System.out.println(d);
  }

}

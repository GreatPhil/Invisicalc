import java.util.*;
import java.math.*;

public class Sorter {

  private Cellworker cw = new Cellworker();
  Cell[][] holder;
  Vector V;
  int [] compare;

  public Sorter() {
  }

  private int indexOfSmallest(Vector v) {
    BigDecimal ref = (BigDecimal)v.elementAt(0);
    int index = 0;
    for (int i = 0; i < v.size(); i++) {
      if (((BigDecimal)v.elementAt(i)).compareTo(ref) < 0) {
        ref = (BigDecimal)v.elementAt(i);
        index = i;
      }
    }
    return index;
  }


  private int indexOfLargest(Vector v) {
    BigDecimal ref = (BigDecimal)v.elementAt(0);
    int index = 0;
    for (int i = 0; i < v.size(); i++) {
      if (((BigDecimal)v.elementAt(i)).compareTo(ref) > 0) {
        ref = (BigDecimal)v.elementAt(i);
        index = i;
      }
    }
    return index;
  }


  private BigDecimal largestInVector(Vector v) {
    BigDecimal ref = (BigDecimal)v.elementAt(0);
    for (int i = 0; i < v.size(); i++) {
      if (((BigDecimal)v.elementAt(i)).compareTo(ref) > 0) {
        ref = (BigDecimal)v.elementAt(i);
      }
    }
    return ref.add(new BigDecimal("1"));
  }

  private BigDecimal smallestInVector(Vector v) {
    BigDecimal ref = (BigDecimal)v.elementAt(0);
    for (int i = 0; i < v.size(); i++) {
      if (((BigDecimal)v.elementAt(i)).compareTo(ref) < 0) {
        ref = (BigDecimal)v.elementAt(i);
      }
    }
    return ref.add(new BigDecimal("-1"));
  }

  public String isAllType(String window, Table T) {
    StringTokenizer wo = new StringTokenizer(window, "-");
    String cell1 = wo.nextToken();
    String r1 = cw.Rget(cell1);
    String c1 = cw.Cget(cell1);
    String cell2 = wo.nextToken();
    String r2 = cw.Rget(cell2);
    String c2 = cw.Cget(cell2);
    if (cw.ColumnValue(c1).compareTo(cw.ColumnValue(c2)) > 0) {
      String temp = c1;
      c1 = c2;
      c2 = temp;
    }
    if ((new BigInteger(r1)).compareTo(new BigInteger(r2)) > 0) {
      String temp = r1;
      r1 = r2;
      r2 = temp;
    }
    BigInteger startColumn = cw.ColumnValue(c1);
    BigInteger endColumn = cw.ColumnValue(c2);
    BigInteger startRow = new BigInteger(r1);
    BigInteger endRow = new BigInteger(r2);

    String currentType = "";

    for (BigInteger j = startRow;
    j.compareTo(endRow) <= 0;
    j = j.add(new BigInteger("1"))) {

      Cell current = new Cell();

      for (BigInteger k = startColumn;
      k.compareTo(endColumn) <= 0;
      k = k.add(new BigInteger("1"))) {

        current = T.fetchItem(j, cw.ColumnName(k));
        if (current.type.equals("Empty")) {
          return "Empty";
        }
        if (current.type.equals("Text") &&
            (!(currentType.equals("") ||
               currentType.equals("Empty") ||
               currentType.equals("Text")))) {
          return "Different";
        }
        if (current.type.equals("Date") &&
            (!(currentType.equals("") ||
              currentType.equals("Empty") ||
              currentType.equals("Date")))) {
          return "Different";
        }
        if (current.type.equals("Real") &&
            (!(currentType.equals("") ||
               currentType.equals("Empty") ||
               currentType.equals("Real")))) {
          return "Different";
        }
        if (current.type.equals("ReferenceDate") &&
            (!(currentType.equals("") ||
               currentType.equals("Empty") ||
               currentType.equals("Date")))) {
          return "Different";
        }
        if (current.type.equals("Text")) {
          currentType = "Text";
        }
        if (current.type.equals("Date")) {
          currentType = "Date";
        }
        if (current.type.equals("Real")) {
          currentType = "Real";
        }
        if (current.type.equals("Reference")) {
          String thing;
          try {
            thing =
            cw.getReference((String)current.value, T);
          } catch (EmptyCellException E) {
            return "Empty";
          }
          if (cw.isNumber(thing)) {
            currentType = "Real";
          } else {
            currentType = "Text";
          }
        }
        if (current.type.equals("ReferenceDate")) {
          currentType = "Date";
        }
      }
    }
    return currentType;
  }


  public int sort (String window, String range, Table T, String order) {

    String CorR = "";
    V = new Vector();

    StringTokenizer wo = new StringTokenizer(window, "-");
    String cell1 = wo.nextToken();
    String r1 = cw.Rget(cell1);
    String c1 = cw.Cget(cell1);
    String cell2 = wo.nextToken();
    String r2 = cw.Rget(cell2);
    String c2 = cw.Cget(cell2);
    if (cw.ColumnValue(c1).compareTo(cw.ColumnValue(c2)) > 0) {
      String temp = c1;
      c1 = c2;
      c2 = temp;
    }
    if ((new BigInteger(r1)).compareTo(new BigInteger(r2)) > 0) {
      String temp = r1;
      r1 = r2;
      r2 = temp;
    }
    if ((! cw.isLegalCellName(cell1)) || (! cw.isLegalCellName(cell2))) {
      System.out.println("ERROR: Not a proper window");
      return 1;
    }

    StringTokenizer ro = new StringTokenizer(range, "-");
    String cell3 = ro.nextToken();
    String r3 = cw.Rget(cell3);
    String c3 = cw.Cget(cell3);
    String cell4 = ro.nextToken();
    String r4 = cw.Rget(cell4);
    String c4 = cw.Cget(cell4);
    if (cw.ColumnValue(c3).compareTo(cw.ColumnValue(c3)) > 0) {
      String temp = c3;
      c3 = c4;
      c4 = temp;
    }
    if ((new BigInteger(r4)).compareTo(new BigInteger(r4)) > 0) {
      String temp = r3;
      r3 = r4;
      r4 = temp;
    }
    if (cw.isLegalCellName(cell3) && cw.isLegalCellName(cell4)) {
      if (r3.equals(r4) && c3.equals(c1) && c4.equals(c2)) {
        CorR = "R";
      } else if (c3.equals(c4) && r3.equals(r1) && r4.equals(r2)) {
        CorR = "C";
      } else {
        System.out.println("ERROR: Not a proper range");
        return 1;
      }
    }

    BigInteger colu1 = cw.ColumnValue(c1);
    BigInteger colu2 = cw.ColumnValue(c2);
    int width = colu2.subtract(colu1).intValue() + 1;
    BigInteger roww1 = new BigInteger(r1);
    BigInteger roww2 = new BigInteger(r2);
    int depth = roww2.subtract(roww1).intValue() + 1;
    holder = new Cell [width] [depth];

    if (CorR.equals("C")) {
      for (BigInteger r = roww1;
      r.compareTo(roww2) <= 0;
      r = r.add(new BigInteger("1"))) { V.add(T.fetchItem(r, c3).value); }
    } else if (CorR.equals("R")) {
      for (BigInteger c = colu1;
      c.compareTo(colu2) <= 0;
      c = c.add(new BigInteger("1"))) { V.add(T.fetchItem(new BigInteger(r3), cw.ColumnName(c)).value); }
    }

    compare = new int[V.size()];

    for (int i = 0; i < compare.length; i++) {
      if (order.equals("ascending")) {
        compare[i] = indexOfSmallest(V);
        V.setElementAt(largestInVector(V), indexOfSmallest(V));
      } else {
        compare[i] = indexOfLargest(V);
        V.setElementAt(smallestInVector(V), indexOfLargest(V));
      }
    }

    for (BigInteger r = roww1;
    r.compareTo(roww2) <= 0;
    r = r.add(new BigInteger("1"))) {
      for (BigInteger c = colu1;
      c.compareTo(colu2) <= 0;
      c = c.add(new BigInteger("1"))) {

        if (CorR.equals("C")) {

          int compareRef = r.subtract(roww1).intValue();
          int hjk = compare[compareRef];
          String s = Integer.toString(hjk);

          holder[c.subtract(colu1).intValue()][r.subtract(roww1).intValue()] =
            T.fetchItem(roww1.add(new BigInteger(s)), cw.ColumnName(c));

        } else if (CorR.equals("R")) {

          int compareRef = c.subtract(colu1).intValue();
          int hjk = compare[compareRef];
          String s = Integer.toString(hjk);

          holder[c.subtract(colu1).intValue()][r.subtract(roww1).intValue()] =
            T.fetchItem(r, cw.ColumnName(colu1.add(new BigInteger(s))));

        }
      }
    }


    for (int i = 0; i < width; i++) {
      for (int j = 0; j < depth; j++) {
        Cell current = holder[i][j];
        String ii = Integer.toString(i);
        String newc = cw.ColumnName(colu1.add(new BigInteger(ii)));
        String jj = Integer.toString(j);
        String newr = roww1.add(new BigInteger(jj)).toString();
        T.addCell(current.type, newr, newc, current.value);
      }
    }

    return 0;
  }

}

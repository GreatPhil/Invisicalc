import java.io.*;
import java.util.*;
import java.math.*;

public class invisicalc {

  public static void main(String[] args) {

    BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    String input;

    System.out.println("Welcome to Invisicalc, Type R version"); // REPLACED

    Table T = new Table();
    Cellworker cw = new Cellworker();
    ExpressionParser ep = new ExpressionParser();
    CircleChecker cc = new CircleChecker();
    Sorter ss = new Sorter();
    checkDisplay cdis = new checkDisplay();

    while (true) {
      try {
        System.out.print("> ");
        input = inp.readLine();
        if (input == null || input.equals("quit"))
        break;

        // FILLING IN...

        if (input.equals(""))
        System.out.print(""); // like carriage return

        else if (input.charAt(0) == '#')
        System.out.println(""); // do nothing.

        else if (input.length() < 5)
        System.out.println("ERROR: not a proper command");
        // Input too small

        else if (input.substring(0,5).equals("clear")) {
          if (input.length() == 5)
          T = new Table();
          else if (cw.isLegalCellName(input.substring(6))) {
            String r = cw.Rget(input.substring(6));
            String c = cw.Cget(input.substring(6));

            for(int i = 0; i < T.cells.size(); i++) {
              Cell current = (Cell)T.cells.elementAt(i);
              if (current.row.compareTo(new BigInteger(r)) == 0
              && current.column.equals(c))
              T.removeCell(current.row, current.column);
            }
          } else if (input.length() > 6) {
            String window = input.substring (6);
            StringTokenizer w = new StringTokenizer(window, "-");
            String cell1 = w.nextToken();
            String r1 = cw.Rget(cell1);
            String c1 = cw.Cget(cell1);
            String cell2 = w.nextToken();
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
            if ((! cw.isLegalCellName(cell1)) ||
                (! cw.isLegalCellName(cell2))) {
              System.out.println("ERROR: Not a proper window");
            } else {
              for(int i = 0; i < T.cells.size(); i++) {
                Cell current = (Cell)T.cells.elementAt(i);
                if (current.row.compareTo(new BigInteger(r1)) >= 0 &&
                    current.row.compareTo(new BigInteger(r2)) <= 0 &&
                    (cw.isGreater (current.column, c1) || (current.column.equals (c1))) &&
                    (cw.isGreater (c2, current.column) || (current.column.equals (c2)))) {
                  T.removeCell(current.row, current.column);
                }
              }
            }
          }
          else
          System.out.println("Clear what?!?");
        }

        else if (input.length() == 5) {
          System.out.println("ERROR: not a proper command");
          // Error Input: too small
        }

        else if (input.length() > 6 &&  input.substring(0,7).equals("display")) {
          if (input.length() == 7) {
            if (T.cells.isEmpty()) {
            } else {
              System.out.println();
              T.display((String)T.columns.elementAt(0),
              (String)T.columns.elementAt(T.columns.size()-1),
              (BigInteger)T.rows.elementAt(0),
              (BigInteger)T.rows.elementAt(T.rows.size()-1));
            }
          }
          else if (input.length() > 8) {

            if (cw.isReference(input.substring (8))) {
              String r = cw.Rget(input.substring(8));
              String c = cw.Cget(input.substring(8));
              if (cw.isLegalCellName(input.substring(8))) {
                System.out.println();
                T.display(c, c, new BigInteger(r), new BigInteger(r));
              }
            } else {
              String window = input.substring (8);
              StringTokenizer w = new StringTokenizer(window, "-");
              String cell1 = w.nextToken();
              String r1 = cw.Rget(cell1);
              String c1 = cw.Cget(cell1);
              String cell2 = w.nextToken();
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
              if ((! cw.isLegalCellName(cell1))
              || (! cw.isLegalCellName(cell2))) {
                System.out.println("ERROR: Not a proper window");

              } else {

                System.out.println();
                T.display(c1, c2, new BigInteger(r1), new BigInteger(r2));

              }
            }
          }
          else {
            System.out.println("ERROR: Display what?");
          }
        }

        else if (input.substring(0,4).equals("sort")) {
          StringTokenizer sortargs = new StringTokenizer(input);
          if (sortargs.countTokens() != 5)
          System.out.println("Sort ordering what by what?!?");
          else {
            sortargs.nextToken();
            String ordering = sortargs.nextToken();
            String window = sortargs.nextToken();
            if ( !(sortargs.nextToken().equals("by")) )
            System.out.println("Sort what BY what?!?");
            else {
              String range = sortargs.nextToken();

              String typr = ss.isAllType(range, T);
              String typw = ss.isAllType(window, T);
              if (typr.equals("Empty"))
              System.out.println("ERROR: Range is Empty");
              else if (typr.equals("Different"))
              System.out.println("ERROR: Cell Types in Range May vary");
              else {

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

                for (BigInteger j = startRow;
                j.compareTo(endRow) <= 0;
                j = j.add(new BigInteger("1"))) {

                  Cell current = new Cell();

                  for (BigInteger k = startColumn;
                  k.compareTo(endColumn) <= 0;
                  k = k.add(new BigInteger("1"))) {

                    current = T.fetchItem(j, cw.ColumnName(k));
                    if (typw == "Text") {
                      String tx = (String)current.value;
                      int l = tx.length();
                      while (l < 200) { // cheesy way to sort text.
                        tx = tx + "A";
                        l = l + 1;
                      }

                      BigInteger biggy1 = cw.ColumnValue(tx);
                      current.value = new BigDecimal(biggy1);

                    }
                    if (typw == "Date") {
                      BigInteger biggy2 = new BigInteger(Integer.toString(cw.DatetoInt((Date)current.value)));
                      current.value = new BigDecimal(biggy2);
                    }
                  }
                }

                int frag = ss.sort(window, range, T, ordering);
                // value of Frag is NOT important


                for (BigInteger j = startRow;
                j.compareTo(endRow) <= 0;
                j = j.add(new BigInteger("1"))) {

                  Cell current = new Cell();

                  for (BigInteger k = startColumn;
                  k.compareTo(endColumn) <= 0;
                  k = k.add(new BigInteger("1"))) {

                    current = T.fetchItem(j, cw.ColumnName(k));
                    if (typw == "Text") {
                      String stringy1 = ((BigDecimal)current.value).toString();
                      BigInteger biggy1 = new BigInteger(stringy1);
                      String nameyQ = cw.ColumnName(biggy1);
                      int FQ = 0;
                      while (! (nameyQ.charAt(FQ) == 'A'
                      && nameyQ.charAt(FQ+1) == 'A'
                      && nameyQ.charAt(FQ+2) == 'A')) {
                        FQ = FQ+1;
                      }
                      current.value = nameyQ.substring(0, FQ);
                    }
                    if (typw == "Date") {
                      String stringy2 = ((BigDecimal)current.value).toString();
                      BigInteger biggy2 = new BigInteger(stringy2);
                      int inty = biggy2.intValue();
                      current.value = cw.InttoDate(inty);
                    }
                  }
                }
              }
            }
          }
        }

        else {
          StringTokenizer com = new StringTokenizer(input);
          if (com.countTokens() < 3) {
            System.out.println ("ERROR: not a proper command");
          } else if (com.countTokens() == 3) {

            String holder = com.nextToken();

            if (! (cw.isLegalCellName(holder))) {
              System.out.println("ERROR : not a proper command");
            } else if (! (com.nextToken().equals("="))) {
              System.out.println ("ERROR: not a proper command");
            } else {
              String ro = cw.Rget(holder);
              String co = cw.Cget(holder);
              String val = com.nextToken();

              Cell adder = new Cell();
              if (cw.isNumber(val))
              T.addCell("Real", ro, co, new BigDecimal(val));
              else if (cw.isDate(val)) {
                StringTokenizer st = new StringTokenizer(val, "-");
                String y = st.nextToken();
                String m = st.nextToken();
                String d = st.nextToken();
                Date crap = new Date(y, m, d);
                T.addCell("Date", ro, co, crap);
              } else if (cw.isText(val))
              T.addCell("Text", ro, co, val.substring(1, val.length() - 1));
              else if (cw.isReference(val)) {
                if (cc.cCheck(holder, val, T)) {
                  if (cc.dCheck(holder, val, T) == 0)
                  T.addCell("Reference", ro, co, val);
                  else if (cc.dCheck(holder, val, T) == 1)
                  T.addCell("ReferenceDate", ro, co, val);
                  else
                  System.out.println("ERROR: Cannot operate on multiple Date types");
                } else
                System.out.println("ERROR: Circular references are not allowed");
              } else
              System.out.println("ERROR: not a proper command");
            }

          } else {
            String holder = com.nextToken();

            if (! (cw.isLegalCellName(holder))) {
              System.out.println("ERROR : not a proper command");
            } else if(! (com.nextToken().equals("="))) {
              System.out.println("ERROR: not a proper command");
            } else {
              String rest = com.nextToken();
              while (com.hasMoreTokens()) {
                rest = rest + " " + com.nextToken();
              }
              String ro = cw.Rget(holder);
              String co = cw.Cget(holder);

              if (rest.charAt(0) == '"'
              && rest.charAt(rest.length() - 1) == '"') {
                T.addCell("Text", ro, co, rest.substring(1, rest.length() - 1));
              } else {
                if (ep.preParser(rest)) {
                  if (cw.noReferences(rest)) {
                    if (cw.DateCounter(rest) == 0) {
                      BigDecimal FFX = ep.parseStart(rest, T);
                      T.addCell("Real", ro, co, FFX);
                    } else if (cw.DateCounter(rest) == 1) {
                      BigDecimal FFX = ep.parseStart(rest, T);
                      Date dat = cw.InttoDate(FFX.intValue());
                      T.addCell("Date", ro, co, dat);
                    }else
                    System.out.println("ERROR: not a valid command.");
                  } else {
                    if (cc.cCheck(holder, rest, T)) {
                      if (cc.dCheck(holder, rest, T) == 0)
                      T.addCell("Reference", ro, co, rest);
                      else if (cc.dCheck(holder, rest, T) == 1)
                      T.addCell("ReferenceDate", ro, co, rest);
                      else
                      System.out.println("ERROR: Cannot operate on multiple Date types");

                    } else
                    System.out.println("ERROR: Circular references are not allowed");
                  }
                } else {
                  System.out.println("ERROR: Input could not be parsed.");
                }
              }
            }
          }
        }
      } catch (IOException e) {
        System.out.println("ERROR: an IOException was Thrown!!!");
      }
    }
  }
}

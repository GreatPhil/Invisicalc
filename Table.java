import java.math.*;
import java.lang.*;
import java.util.*;

public class Table {

  Vector rows;
  Vector columns;
  Vector cells;
  int cellWidth = 10;

  public Table() {
    rows = new Vector();
    columns = new Vector();
    cells = new Vector();
  }

  //Helper to Create a Cell
  private void addColumn(String column) {
    Cellworker cw = new Cellworker();
    if (column.length() > cellWidth) {
      cellWidth = column.length();
    }
    if (columns.isEmpty()) {
      columns.insertElementAt(column, 0);
    } else if (cw.isGreater (column, (String) columns.lastElement())) {
      columns.insertElementAt(column, columns.size());
    } else if (!(columns.contains(column))) {
      for (int i = 0; i < columns.size(); i++) {
        if (cw.isGreater ((String) columns.elementAt(i), column)) {
          columns.insertElementAt(column, i);
          break;
        }
      }
    }
  }


  //Helper to Create a Cell
  private void addRow(BigInteger row) {
    String num = new String ("" + row);
    if (num.length() > cellWidth) {
      cellWidth = num.length();
    }
    if (rows.isEmpty()) {
      rows.insertElementAt(row, 0);
    } else if (row.compareTo((BigInteger)rows.lastElement()) > 0) {
      rows.insertElementAt(row, rows.size());
    } else if (!(rows.contains(row))) {
      for (int i = 0; i < rows.size(); i++) {
        if (row.compareTo((BigInteger)rows.elementAt(i)) < 0) {
          rows.insertElementAt(row, i);
          break;
        }
      }
    }
  }

  // also Overwrites...
  public void addCell(String type, String row, String column, Object value) {
    for(int i = 0; i < cells.size(); i++) {
      if (((Cell) this.cells.elementAt(i)).row.compareTo(new BigInteger(row)) == 0 &&
          ((Cell) this.cells.elementAt(i)).column.equals(column)) {
        this.cells.removeElementAt(i);
      }
    }
    Cell current = new Cell(type, row, column, value);
    cells.addElement(current);
    this.addRow(new BigInteger(row));
    this.addColumn(column);
  }


  public void removeCell(BigInteger row, String column) {
    int r = 0;
    int c = 0;
    for (int i = 0; i < cells.size(); i++) {
      if (((Cell) cells.elementAt(i)).row.compareTo(row) == 0) { r++; }
      if (((Cell) cells.elementAt(i)).column.equals(column)) { c++; }
      if (((Cell) cells.elementAt(i)).row.compareTo(row) == 0 &&
          ((Cell) cells.elementAt(i)).column.equals(column)) {
        cells.removeElementAt(i);
      }
    }
    if (r == 1) {
      rows.remove(row);
    }
    if (c == 1) {
      columns.remove(column);
    }
  }


  //Main Fetcher Method
  public Cell fetchItem(BigInteger row, String column) {
    for (int i = 0; i < cells.size(); i++) {
      Cell current = ((Cell)cells.elementAt(i));
      if (current.row.equals(row) && current.column.equals(column)) {
        return current;
      }
    }
    return new Cell("Empty", row.toString(), column, " ");
  }


  //Helper for Display
  private static String fillBuffer(StringBuffer buf, int start, int end) {
    for (int i = start; i <= end; i++) {
      buf.append(" ");
    }
    String answer = buf.toString();
    return answer;
  }


  public void display(String column1, String column2, BigInteger row1, BigInteger row2) {
    Cellworker cw = new Cellworker();
    BigInteger startColumn = cw.ColumnValue(column1);
    BigInteger endColumn = cw.ColumnValue(column2);
    BigInteger startRow = row1;
    BigInteger endRow = row2;

    int tempCellWidth = cellWidth;

    int celwid = 10;
    if (column2.length() > celwid) {
      celwid = column2.length();
    }
    if (row2.toString().length() > celwid) {
      celwid = row2.toString().length();
    }

    cellWidth = celwid;

    StringBuffer block = new StringBuffer(cellWidth);
    String blankspace = fillBuffer(block, 0, cellWidth - 1);
    System.out.print(blankspace + " ");


    for (BigInteger i = startColumn;
    i.compareTo(endColumn) <= 0;
    i = i.add(new BigInteger("1"))) {
      block = new StringBuffer(cellWidth);
      block.append(cw.ColumnName(i));
      fillBuffer(block, cw.ColumnName(i).length(), cellWidth - 1);
      System.out.print(block + " ");
    }

    System.out.println();

    for (BigInteger j = startRow;
    j.compareTo(endRow) <= 0;
    j = j.add(new BigInteger("1"))) {
      block = new StringBuffer(cellWidth);
      String rowName = j.toString();
      block.append(rowName);
      rowName = fillBuffer(block, rowName.length(), cellWidth - 1);
      System.out.print(rowName + " ");
      Cell current = new Cell();
      for (BigInteger k = startColumn;
      k.compareTo(endColumn) <= 0;
      k = k.add(new BigInteger("1"))) {
        block = new StringBuffer(cellWidth);
        current = fetchItem(j, cw.ColumnName(k));
        if (current.type.equals("Empty")) {
          System.out.print(blankspace + " ");
        } else if (current.type.equals("Real")) {
          String item = current.value.toString();
          if (item.length() > cellWidth)
          item = item.substring(0,cellWidth);
          block.append(item);
          fillBuffer(block, item.length(), cellWidth - 1);
          System.out.print(block + " ");
        }
        else if (current.type.equals("Date")) {
          String item = current.value.toString();
          block.append(item);
          fillBuffer(block, item.length(), cellWidth - 1);
          System.out.print(block + " ");
        }
        else if (current.type.equals("Text")) {
          String item = current.value.toString();
          if (item.length() > cellWidth) {
            item = item.substring(0,10);
          }
          block.append(item);
          fillBuffer(block, item.length(), cellWidth - 1);
          System.out.print(block + " ");
        }
        else if (current.type.equals("Reference") ||
                 current.type.equals("ReferenceDate")) {
          String item = "";
          try {
            item = cw.getReference((String)current.value, this);
          } catch (EmptyCellException e) {
            System.out.println("Empty Ref " + item);
          }

          if (current.type.equals("ReferenceDate")) {
            if (cw.isNumber(item)) {
              item = cw.InttoDate(Integer.parseInt(item)).toString();
            }
          }
          if (item.length() > cellWidth)
          item = item.substring(0,10);
          block.append(item);
          fillBuffer(block, item.length(), cellWidth - 1);
          System.out.print(block + " ");
        }
        else
        System.out.println ("ERROR...Table.java");
      }
      System.out.println();
    }
    cellWidth = tempCellWidth;
  }


  public static void main(String[] args) {
    Table t = new Table();
    String v1 = new String("2002-07-01");
    t.addCell("Date", "7", "A", v1);
    String v2 = new String("1983-03-83");
    t.addCell("Date", "4", "C", v2);
    String v3 = new String("1983-03-18");
    t.addCell("Date", "6", "B", v3);
    String v4 = new String("1985-06-10");
    t.addCell("Date", "5", "D", v4);
    String v5 = new String("1988-10-30");
    t.addCell("Date", "4", "A", v5);
    String v6 = new String("2001-09-11");
    t.addCell("Date", "5", "E", v6);
    String v7 = new String("FFFFFF");
    t.addCell("Date", "5", "A", v7);

    for (int i = 0; i < 6; i++) {
      //	System.out.println(t.rows.elementAt(i));
      //	System.out.println(t.columns.elementAt(i));
      //	System.out.println(t.cells.elementAt(i));
      System.out.println();
    }

    t.display("A", "E", new BigInteger("4"), new BigInteger("7"));
  
  }
}

import java.math.*;

public class Cell {
  
  String type;
  BigInteger row;
  String column;
  Object value;

  public Cell(String type, String row, String column, Object value) {
    this.type = type;
    this.row = new BigInteger(row);
    this.column = column;
    this.value = value;
  }

  public Cell() {
  }

  public String toString() {
    return new String ("[CELL] type: " + type + " " +
    "row: " + row + " " +
    "Column: " + column + " " +
    "Value: " + value + "]");
  }
}

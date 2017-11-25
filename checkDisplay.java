import java.util.*;
import java.math.*;

public class checkDisplay {

  public checkDisplay() {
  }

  public boolean checkD(String column1, String column2,
                        BigInteger row1, BigInteger row2, Table T) {
                          
    Cellworker cw = new Cellworker();
    BigInteger startColumn = cw.ColumnValue(column1);
    BigInteger endColumn = cw.ColumnValue(column2);
    BigInteger startRow = row1;
    BigInteger endRow = row2;

    for (BigInteger j = startRow;
    j.compareTo(endRow) <= 0;
    j = j.add(new BigInteger("1"))) {

      Cell current = new Cell();

      for (BigInteger k = startColumn;
           k.compareTo(endColumn) <= 0;
           k = k.add(new BigInteger("1"))) {

        current = T.fetchItem(j, cw.ColumnName(k));

        if (current.type.equals("Reference") ||
            current.type.equals("ReferenceDate")) {
          String item = "E";
          try {
            item = cw.getReference((String)current.value, T);
          } catch (Exception e) {
            return false;
          }
        } else if (current.type.equals("Date") ||
                   current.type.equals("Text") ||
                   current.type.equals("Real")) {
        } else
        return false;
      }
    }
    return true;
  }
}

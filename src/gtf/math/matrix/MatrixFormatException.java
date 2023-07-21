package gtf.math.matrix;

/**
 * Exception indicating a bad format when reading a matrix
 * from an input stream.
 *
 *@author   gtf
 */
public final class MatrixFormatException extends Exception {

  private static final long serialVersionUID = 1L;
  
  private int row;
  private boolean rowSet;
  private int col;
  private boolean colSet;

  /**
   *Constructor for the MatrixFormatException object.
   *
   *@param message  Description of the Parameter
   */
  public MatrixFormatException(String message) {
    super(message);
  }

  /**
   *Constructor for the MatrixFormatException object.
   *
   *@param message  Description of the Parameter
   *@param row      Description of the Parameter
   */
  public MatrixFormatException(String message, int row) {
    this(message);
    this.row = row;
    rowSet = true;
  }

  /**
   *Constructor for the MatrixFormatException object.
   *
   *@param message  Description of the Parameter
   *@param row      Description of the Parameter
   *@param col      Description of the Parameter
   */
  public MatrixFormatException(String message, int row, int col) {
    this(message, row);
    this.col = col;
    colSet = true;
  }

  /**
   * Gets the message attribute of the MatrixFormatException object
   *
   *@return   The message value
   */
  public String getMessage() {

    class Appender {
      StringBuffer buf = new StringBuffer();

      /**
       * Description of the Method.
       *
       *@param message  Description of the Parameter
       */
      void append(String message) {
        buf.append(message);
      }

      /**
       * Description of the Method.
       *
       *@param varName   Description of the Parameter
       *@param varValue  Description of the Parameter
       */
      void append(String varName, Object varValue) {
        append("[");
        append(varName);
        append("=");
        append(varValue == null ? null : varValue.toString());
        append("] ");
      }

      /**
       * Description of the Method.
       *
       *@return   Description of the Return Value
       */
      public String toString() {
        return buf.toString();
      }
    };

    Appender appender = new Appender();
    appender.append(super.getMessage());
    appender.append(": ");
    if (rowSet) {
      appender.append("row", row);
    }
    if (colSet) {
      appender.append("col", col);
    }
    return appender.toString();
  }
}


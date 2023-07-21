package gtf.math.matrix;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

import gtf.math.BigFraction;

/**
 * Reads matrices from an input stream.
 * The file format is
 * <pre>
 * rows cols
 * a00 a01 a02 ...
 * a10 a11 a12 000
 * ... ... ... ...
 * </pre>
 *
 *@author   gtf
 */
class MatrixReader {

  /**
   * Constructor for the MatrixReader object.
   */
  private MatrixReader() { }

  /**
   * Reads the matrix from the input stream.
   *
   *@param in                         Description of the Parameter
   *@return                           Description of the Return Value
   *@exception MatrixFormatException  Description of the Exception
   *@exception IOException            Description of the Exception
   */
  public static Matrix read(InputStream in) throws MatrixFormatException, IOException {
    Matrix matrix;
    int rows;
    int cols;
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    try {
      // read the dimensions from the first line
      String line = reader.readLine();
      if (line != null) {
        try {
          StringTokenizer tokenizer = new StringTokenizer(line);
          if (tokenizer.countTokens() != 2) {
            throw new MatrixFormatException("could not read the rows/columns spec");
          }
          String rowsStr = tokenizer.nextToken();
          rows = Integer.parseInt(rowsStr);
          String colsStr = tokenizer.nextToken();
          cols = Integer.parseInt(colsStr);
        } catch (NumberFormatException e) {
          throw new MatrixFormatException("Bad dimensions");
        }
      } else {
        throw new MatrixFormatException("Couldn't read dimensions");
      }
      if (rows <= 0 || cols <= 0) {
        throw new IllegalArgumentException("Bad dimensions");
      }
      matrix = new Matrix(rows, cols);
      int row = 0;
      line = reader.readLine();
      while (row < rows && line != null) {
        StringTokenizer tokenizer = new StringTokenizer(line);
        int col = 0;
        while (col < cols && tokenizer.hasMoreTokens()) {
          String token = tokenizer.nextToken();
          try {
            matrix.setCell(row, col, new BigFraction(token));
          } catch (NumberFormatException e) {
            throw new MatrixFormatException("Bad token", row, col);
          } catch (IllegalArgumentException e) {
            throw new MatrixFormatException(e.getMessage(), row, col);
          }
          col++;
        }
        if (col < cols) {
          throw new MatrixFormatException("Not enough values", row);
        }
        row++;
        line = reader.readLine();
      }
      if (row < rows) {
        throw new MatrixFormatException("Not enough rows", row);
      }
    } finally {
      reader.close();
    }
    return matrix;
  }
}


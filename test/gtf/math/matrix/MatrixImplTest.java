package gtf.math.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import gtf.math.BigFraction;
import gtf.math.algebra.field.Q;
import gtf.math.algebra.field.ZmodP;

/**
 * @author gtf
 */
public class MatrixImplTest {

  private static final Q Q_FIELD = new Q();
  private static final ZmodP F5 = new ZmodP(BigInteger.valueOf(5));
  private static final StorageFactory<BigFraction> STORAGE = new ArrayStorageFactory<BigFraction>();
  private static final StorageFactory<BigInteger> INTEGER_STORAGE = new ArrayStorageFactory<BigInteger>();

  @Test
  public void testTrace() {
    Matrix<BigFraction, Q> matrix = matrix(new long[][] {
        { 1, 2 },
        { 3, 4 }
    });

    assertEquals(fraction(5), matrix.trace());
  }

  @Test
  public void testDeterminant2x2() {
    Matrix<BigFraction, Q> matrix = matrix(new long[][] {
        { 1, 2 },
        { 3, 4 }
    });

    assertEquals(fraction(-2), matrix.determinant());
  }

  @Test
  public void testDeterminant3x3() {
    Matrix<BigFraction, Q> matrix = matrix(new long[][] {
        { 6, 1, 1 },
        { 4, -2, 5 },
        { 2, 8, 7 }
    });

    assertEquals(fraction(-306), matrix.determinant());
  }

  @Test
  public void testMultiply() {
    Matrix<BigFraction, Q> left = matrix(new long[][] {
        { 1, 2, 3 },
        { 4, 5, 6 }
    });
    Matrix<BigFraction, Q> right = matrix(new long[][] {
        { 7, 8 },
        { 9, 10 },
        { 11, 12 }
    });

    assertMatrixEquals(matrix(new long[][] {
        { 58, 64 },
        { 139, 154 }
    }), left.multiply(right));
  }

  @Test
  public void testInverse2x2() {
    Matrix<BigFraction, Q> matrix = matrix(new long[][] {
        { 1, 2 },
        { 3, 4 }
    });

    Matrix<BigFraction, Q> expected = emptyMatrix(2, 2);
    expected.setCell(0, 0, fraction(-2));
    expected.setCell(0, 1, fraction(1));
    expected.setCell(1, 0, fraction(3, 2));
    expected.setCell(1, 1, fraction(-1, 2));

    assertMatrixEquals(expected, matrix.inverse());
  }

  @Test
  public void testInverseWithRowSwap() {
    Matrix<BigFraction, Q> matrix = matrix(new long[][] {
        { 0, 1 },
        { 1, 0 }
    });

    assertMatrixEquals(matrix, matrix.inverse());
  }

  @Test
  public void testInverseProductIsIdentity() {
    Matrix<BigFraction, Q> matrix = matrix(new long[][] {
        { 2, 0, 1 },
        { 1, 1, 0 },
        { 0, 1, 1 }
    });

    assertTrue(matrix.multiply(matrix.inverse()).isIdentity());
    assertTrue(matrix.inverse().multiply(matrix).isIdentity());
  }

  @Test
  public void testFiniteFieldDeterminant() {
    Matrix<BigInteger, ZmodP> matrix = zmod5Matrix(new long[][] {
        { 1, 2 },
        { 3, 4 }
    });

    assertEquals(BigInteger.valueOf(3), matrix.determinant());
  }

  @Test
  public void testFiniteFieldInverse2x2() {
    Matrix<BigInteger, ZmodP> matrix = zmod5Matrix(new long[][] {
        { 1, 2 },
        { 3, 4 }
    });

    Matrix<BigInteger, ZmodP> expected = zmod5Matrix(new long[][] {
        { 3, 1 },
        { 4, 2 }
    });

    assertZmodPMatrixEquals(expected, matrix.inverse());
  }

  @Test
  public void testFiniteFieldInverseProductIsIdentity() {
    Matrix<BigInteger, ZmodP> matrix = zmod5Matrix(new long[][] {
        { 2, 0, 1 },
        { 1, 1, 0 },
        { 0, 1, 1 }
    });

    assertTrue(matrix.multiply(matrix.inverse()).isIdentity());
    assertTrue(matrix.inverse().multiply(matrix).isIdentity());
  }

  @Test(expected = ArithmeticException.class)
  public void testFiniteFieldInverseRejectsSingularMatrix() {
    zmod5Matrix(new long[][] {
        { 1, 2 },
        { 2, 4 }
    }).inverse();
  }

  @Test(expected = ArithmeticException.class)
  public void testInverseRejectsSingularMatrix() {
    matrix(new long[][] {
        { 1, 2 },
        { 2, 4 }
    }).inverse();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInverseRejectsNonSquareMatrix() {
    matrix(new long[][] {
        { 1, 2, 3 },
        { 4, 5, 6 }
    }).inverse();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testTraceRejectsNonSquareMatrix() {
    matrix(new long[][] {
        { 1, 2, 3 },
        { 4, 5, 6 }
    }).trace();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testDeterminantRejectsNonSquareMatrix() {
    matrix(new long[][] {
        { 1, 2, 3 },
        { 4, 5, 6 }
    }).determinant();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiplyRejectsIncompatibleDimensions() {
    matrix(new long[][] {
        { 1, 2 },
        { 3, 4 }
    }).multiply(matrix(new long[][] {
        { 1, 2 },
        { 3, 4 },
        { 5, 6 }
    }));
  }

  private Matrix<BigFraction, Q> matrix(long[][] values) {
    Matrix<BigFraction, Q> matrix = emptyMatrix(values.length, values[0].length);
    for (int row = 0; row < values.length; row++) {
      for (int col = 0; col < values[row].length; col++) {
        matrix.setCell(row, col, fraction(values[row][col]));
      }
    }
    return matrix;
  }

  private Matrix<BigInteger, ZmodP> zmod5Matrix(long[][] values) {
    Matrix<BigInteger, ZmodP> matrix = emptyZmod5Matrix(values.length, values[0].length);
    for (int row = 0; row < values.length; row++) {
      for (int col = 0; col < values[row].length; col++) {
        matrix.setCell(row, col, zmod5(values[row][col]));
      }
    }
    return matrix;
  }

  private Matrix<BigFraction, Q> emptyMatrix(int rows, int cols) {
    return new MatrixImpl<BigFraction, Q>(Q_FIELD, rows, cols, STORAGE);
  }

  private Matrix<BigInteger, ZmodP> emptyZmod5Matrix(int rows, int cols) {
    return new MatrixImpl<BigInteger, ZmodP>(F5, rows, cols, INTEGER_STORAGE);
  }

  private BigFraction fraction(long value) {
    return new BigFraction(value);
  }

  private BigFraction fraction(long numerator, long denominator) {
    return new BigFraction(numerator, denominator);
  }

  private BigInteger zmod5(long value) {
    return BigInteger.valueOf(value).mod(BigInteger.valueOf(5));
  }

  private void assertMatrixEquals(Matrix<BigFraction, Q> expected,
      Matrix<BigFraction, Q> actual) {
    assertEquals(expected.getRows(), actual.getRows());
    assertEquals(expected.getCols(), actual.getCols());
    for (int row = 0; row < expected.getRows(); row++) {
      for (int col = 0; col < expected.getCols(); col++) {
        assertEquals(expected.getCell(row, col), actual.getCell(row, col));
      }
    }
  }

  private void assertZmodPMatrixEquals(Matrix<BigInteger, ZmodP> expected,
      Matrix<BigInteger, ZmodP> actual) {
    assertEquals(expected.getRows(), actual.getRows());
    assertEquals(expected.getCols(), actual.getCols());
    for (int row = 0; row < expected.getRows(); row++) {
      for (int col = 0; col < expected.getCols(); col++) {
        assertEquals(expected.getCell(row, col), actual.getCell(row, col));
      }
    }
  }
}

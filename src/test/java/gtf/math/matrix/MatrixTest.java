package gtf.math.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import gtf.math.BigFraction;
import gtf.math.algebra.field.Q;
import gtf.math.algebra.field.ZmodP;

/**
 * Tests for the public {@link Matrix} API.
 *
 * @author gtf
 */
public class MatrixTest {

  private static final Q Q_FIELD = new Q();
  private static final ZmodP F5 = new ZmodP(BigInteger.valueOf(5));

  @Test
  public void testDenseFactory() {
    Matrix<BigFraction, Q> matrix = Matrix.dense(Q_FIELD, 2, 3);

    assertEquals(Q_FIELD, matrix.getRing());
    assertEquals(2, matrix.getRows());
    assertEquals(3, matrix.getCols());
    assertEquals(fraction(0), matrix.getCell(0, 0));
  }

  @Test
  public void testSparseFactory() {
    Matrix<BigFraction, Q> matrix = Matrix.sparse(Q_FIELD, 2, 3);

    matrix.setCell(1, 2, fraction(7));

    assertEquals(Q_FIELD, matrix.getRing());
    assertEquals(2, matrix.getRows());
    assertEquals(3, matrix.getCols());
    assertEquals(fraction(7), matrix.getCell(1, 2));
    assertEquals(fraction(0), matrix.getCell(0, 0));
  }

  @Test
  public void testTrace() {
    assertEquals(fraction(5), matrix(new long[][] {
        { 1, 2 },
        { 3, 4 }
    }).trace());
  }

  @Test
  public void testDeterminant2x2() {
    assertEquals(fraction(-2), matrix(new long[][] {
        { 1, 2 },
        { 3, 4 }
    }).determinant());
  }

  @Test
  public void testDeterminant3x3() {
    assertEquals(fraction(-306), matrix(new long[][] {
        { 6, 1, 1 },
        { 4, -2, 5 },
        { 2, 8, 7 }
    }).determinant());
  }

  @Test
  public void testAdd() {
    assertMatrixEquals(matrix(new long[][] {
        { 6, 8 },
        { 10, 12 }
    }), matrix(new long[][] {
        { 1, 2 },
        { 3, 4 }
    }).add(matrix(new long[][] {
        { 5, 6 },
        { 7, 8 }
    })));
  }

  @Test
  public void testTranspose() {
    assertMatrixEquals(matrix(new long[][] {
        { 1, 4 },
        { 2, 5 },
        { 3, 6 }
    }), matrix(new long[][] {
        { 1, 2, 3 },
        { 4, 5, 6 }
    }).transpose());
  }

  @Test
  public void testSubmatrix() {
    assertMatrixEquals(matrix(new long[][] {
        { 5, 6 },
        { 8, 9 }
    }), matrix(new long[][] {
        { 1, 2, 3 },
        { 4, 5, 6 },
        { 7, 8, 9 }
    }).submatrix(1, 2, 1, 2));
  }

  @Test
  public void testMultiply() {
    assertMatrixEquals(matrix(new long[][] {
        { 58, 64 },
        { 139, 154 }
    }), matrix(new long[][] {
        { 1, 2, 3 },
        { 4, 5, 6 }
    }).multiply(matrix(new long[][] {
        { 7, 8 },
        { 9, 10 },
        { 11, 12 }
    })));
  }

  @Test
  public void testInverse2x2() {
    Matrix<BigFraction, Q> expected = Matrix.dense(Q_FIELD, 2, 2);
    expected.setCell(0, 0, fraction(-2));
    expected.setCell(0, 1, fraction(1));
    expected.setCell(1, 0, fraction(3, 2));
    expected.setCell(1, 1, fraction(-1, 2));

    assertMatrixEquals(expected, matrix(new long[][] {
        { 1, 2 },
        { 3, 4 }
    }).inverse());
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
    assertEquals(BigInteger.valueOf(3), zmod5Matrix(new long[][] {
        { 1, 2 },
        { 3, 4 }
    }).determinant());
  }

  @Test
  public void testFiniteFieldInverse2x2() {
    assertZmodPMatrixEquals(zmod5Matrix(new long[][] {
        { 3, 1 },
        { 4, 2 }
    }), zmod5Matrix(new long[][] {
        { 1, 2 },
        { 3, 4 }
    }).inverse());
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
    Matrix<BigFraction, Q> matrix = Matrix.dense(
        Q_FIELD, values.length, values[0].length);
    for (int row = 0; row < values.length; row++) {
      for (int col = 0; col < values[row].length; col++) {
        matrix.setCell(row, col, fraction(values[row][col]));
      }
    }
    return matrix;
  }

  private Matrix<BigInteger, ZmodP> zmod5Matrix(long[][] values) {
    Matrix<BigInteger, ZmodP> matrix = Matrix.dense(
        F5, values.length, values[0].length);
    for (int row = 0; row < values.length; row++) {
      for (int col = 0; col < values[row].length; col++) {
        matrix.setCell(row, col, zmod5(values[row][col]));
      }
    }
    return matrix;
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

  private void assertMatrixEquals(
      Matrix<BigFraction, Q> expected,
      Matrix<BigFraction, Q> actual) {

    assertEquals(expected.getRows(), actual.getRows());
    assertEquals(expected.getCols(), actual.getCols());
    for (int row = 0; row < expected.getRows(); row++) {
      for (int col = 0; col < expected.getCols(); col++) {
        assertEquals(expected.getCell(row, col), actual.getCell(row, col));
      }
    }
  }

  private void assertZmodPMatrixEquals(
      Matrix<BigInteger, ZmodP> expected,
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

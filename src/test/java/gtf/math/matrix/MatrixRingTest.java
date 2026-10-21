package gtf.math.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gtf.math.BigFraction;
import gtf.math.algebra.field.Q;


/**
 * Tests for {@link MatrixRing}.
 *
 * @author gtf
 */
public class MatrixRingTest {

  private static final Q Q_FIELD = new Q();

  @Test
  public void testZero() {
    MatrixRing<BigFraction, Q> ring =
        MatrixRing.of(Q_FIELD, 2);

    Matrix<BigFraction, Q> zero = ring.zero();

    assertEquals(2, zero.getRows());
    assertEquals(2, zero.getCols());

    for (int row = 0; row < 2; row++) {
      for (int col = 0; col < 2; col++) {
        assertEquals(fraction(0), zero.getCell(row, col));
      }
    }
  }

  @Test
  public void testIdentity() {
    MatrixRing<BigFraction, Q> ring =
        MatrixRing.of(Q_FIELD, 3);

    assertTrue(ring.id().isIdentity());
  }

  @Test
  public void testAdd() {
    MatrixRing<BigFraction, Q> ring =
        MatrixRing.of(Q_FIELD, 2);

    Matrix<BigFraction, Q> left = matrix(new long[][] {
        { 1, 2 },
        { 3, 4 }
    });

    Matrix<BigFraction, Q> right = matrix(new long[][] {
        { 5, 6 },
        { 7, 8 }
    });

    Matrix<BigFraction, Q> expected = matrix(new long[][] {
        { 6, 8 },
        { 10, 12 }
    });

    assertMatrixEquals(expected, ring.add(left, right));
  }

  @Test
  public void testNeg() {
    MatrixRing<BigFraction, Q> ring =
        MatrixRing.of(Q_FIELD, 2);

    Matrix<BigFraction, Q> matrix = matrix(new long[][] {
        { 1, -2 },
        { 3, -4 }
    });

    Matrix<BigFraction, Q> expected = matrix(new long[][] {
        { -1, 2 },
        { -3, 4 }
    });

    assertMatrixEquals(expected, ring.neg(matrix));
  }

  @Test
  public void testMultiply() {
    MatrixRing<BigFraction, Q> ring =
        MatrixRing.of(Q_FIELD, 2);

    Matrix<BigFraction, Q> left = matrix(new long[][] {
        { 1, 2 },
        { 3, 4 }
    });

    Matrix<BigFraction, Q> right = matrix(new long[][] {
        { 5, 6 },
        { 7, 8 }
    });

    Matrix<BigFraction, Q> expected = matrix(new long[][] {
        { 19, 22 },
        { 43, 50 }
    });

    assertMatrixEquals(expected, ring.mul(left, right));
  }

  @Test
  public void testDivideByIdentity() {
    MatrixRing<BigFraction, Q> ring =
        MatrixRing.of(Q_FIELD, 2);

    Matrix<BigFraction, Q> matrix = matrix(new long[][] {
        { 1, 2 },
        { 3, 4 }
    });

    assertMatrixEquals(matrix, ring.divide(matrix, ring.id()));
  }

  @Test
  public void testDivideByInvertibleMatrix() {
    MatrixRing<BigFraction, Q> ring =
        MatrixRing.of(Q_FIELD, 2);

    Matrix<BigFraction, Q> numerator = matrix(new long[][] {
        { 2, 0 },
        { 0, 2 }
    });

    Matrix<BigFraction, Q> denominator = matrix(new long[][] {
        { 1, 2 },
        { 3, 4 }
    });

    Matrix<BigFraction, Q> quotient =
        ring.divide(numerator, denominator);

    assertMatrixEquals(
        numerator,
        quotient.multiply(denominator));
  }

  @Test(expected = ArithmeticException.class)
  public void testDivideRejectsSingularMatrix() {
    MatrixRing<BigFraction, Q> ring =
        MatrixRing.of(Q_FIELD, 2);

    ring.divide(
        matrix(new long[][] {
            { 1, 0 },
            { 0, 1 }
        }),
        matrix(new long[][] {
            { 1, 2 },
            { 2, 4 }
        }));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRejectsWrongSizeMatrix() {
    MatrixRing<BigFraction, Q> ring =
        MatrixRing.of(Q_FIELD, 2);

    ring.add(
        matrix(new long[][] {
            { 1, 2 },
            { 3, 4 }
        }),
        matrix3(new long[][] {
            { 1, 0, 0 },
            { 0, 1, 0 },
            { 0, 0, 1 }
        }));
  }

  private Matrix<BigFraction, Q> matrix(long[][] values) {
    Matrix<BigFraction, Q> matrix =
        Matrix.dense(
            Q_FIELD,
            values.length,
            values[0].length);

    for (int row = 0; row < values.length; row++) {
      for (int col = 0; col < values[row].length; col++) {
        matrix.setCell(row, col, fraction(values[row][col]));
      }
    }

    return matrix;
  }

  private Matrix<BigFraction, Q> matrix3(long[][] values) {
    return matrix(values);
  }

  private BigFraction fraction(long value) {
    return new BigFraction(value);
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
}

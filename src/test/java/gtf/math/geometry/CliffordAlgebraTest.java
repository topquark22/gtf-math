package gtf.math.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gtf.math.algebra.field.R;
import gtf.math.geometry.impl.ArrayMultivector;


/**
 * Tests for {@link CliffordAlgebra}.
 *
 * @author gtf
 */
public class CliffordAlgebraTest {

  private static final double EPSILON = 1.0e-12;

  @Test
  public void testConstructorRejectsNullVectorSpace() {
    try {
      new CliffordAlgebra<double[], Double, R>(null);
    } catch (NullPointerException expected) {
      assertEquals("vectorSpace", expected.getMessage());
      return;
    }

    throw new AssertionError("expected NullPointerException");
  }

  @Test
  public void testVectorSpaceAccessor() {
    EuclideanSpace space = EuclideanSpace.r3();
    CliffordAlgebra<double[], Double, R> algebra =
        new CliffordAlgebra<double[], Double, R>(space);

    assertEquals(space, algebra.vectorSpace());
  }

  @Test
  public void testGrade() {
    CliffordAlgebra<double[], Double, R> algebra = algebra3();

    assertEquals(0, algebra.grade(0));
    assertEquals(1, algebra.grade(1));
    assertEquals(1, algebra.grade(2));
    assertEquals(2, algebra.grade(3));
    assertEquals(1, algebra.grade(4));
    assertEquals(2, algebra.grade(5));
    assertEquals(2, algebra.grade(6));
    assertEquals(3, algebra.grade(7));
  }

  @Test
  public void testWedgeOfBasisVectors() {
    CliffordAlgebra<double[], Double, R> algebra = algebra3();

    Multivector<Double, R> result = algebra.wedge(e(3, 0), e(3, 1));

    assertBlade(result, 3, 1.0);
    assertOnlyBlade(result, 3);
  }

  @Test
  public void testWedgeOfBasisVectorsAnticommutes() {
    CliffordAlgebra<double[], Double, R> algebra = algebra3();

    Multivector<Double, R> result = algebra.wedge(e(3, 1), e(3, 0));

    assertBlade(result, 3, -1.0);
    assertOnlyBlade(result, 3);
  }

  @Test
  public void testWedgeOfRepeatedBasisVectorIsZero() {
    CliffordAlgebra<double[], Double, R> algebra = algebra3();

    Multivector<Double, R> result = algebra.wedge(e(3, 0), e(3, 0));

    assertZero(result);
  }

  @Test
  public void testWedgeIsBilinear() {
    CliffordAlgebra<double[], Double, R> algebra = algebra3();

    Multivector<Double, R> vector = vector3(2.0, 3.0, 0.0);
    Multivector<Double, R> result = algebra.wedge(vector, e(3, 2));

    assertBlade(result, 5, 2.0);
    assertBlade(result, 6, 3.0);
    assertOnlyBlades(result, 5, 6);
  }

  @Test
  public void testWedgeRejectsDimensionMismatch() {
    CliffordAlgebra<double[], Double, R> algebra = algebra3();

    try {
      algebra.wedge(e(3, 0), e(2, 0));
    } catch (IllegalArgumentException expected) {
      assertEquals("dimension mismatch", expected.getMessage());
      return;
    }

    throw new AssertionError("expected IllegalArgumentException");
  }

  @Test
  public void testHodgeDualOfBasisVectorsInThreeDimensions() {
    CliffordAlgebra<double[], Double, R> algebra = algebra3();

    Multivector<Double, R> dualE0 = algebra.hodgeDual(e(3, 0));
    Multivector<Double, R> dualE1 = algebra.hodgeDual(e(3, 1));
    Multivector<Double, R> dualE2 = algebra.hodgeDual(e(3, 2));

    assertBlade(dualE0, 6, 1.0);
    assertOnlyBlade(dualE0, 6);

    assertBlade(dualE1, 5, -1.0);
    assertOnlyBlade(dualE1, 5);

    assertBlade(dualE2, 3, 1.0);
    assertOnlyBlade(dualE2, 3);
  }

  @Test
  public void testHodgeDualOfBivectorsInThreeDimensions() {
    CliffordAlgebra<double[], Double, R> algebra = algebra3();

    Multivector<Double, R> dualE01 = algebra.hodgeDual(blade(3, 3, 1.0));
    Multivector<Double, R> dualE02 = algebra.hodgeDual(blade(3, 5, 1.0));
    Multivector<Double, R> dualE12 = algebra.hodgeDual(blade(3, 6, 1.0));

    assertBlade(dualE01, 4, 1.0);
    assertOnlyBlade(dualE01, 4);

    assertBlade(dualE02, 2, -1.0);
    assertOnlyBlade(dualE02, 2);

    assertBlade(dualE12, 1, 1.0);
    assertOnlyBlade(dualE12, 1);
  }

  @Test
  public void testCrossProductBasisVectors() {
    CliffordAlgebra<double[], Double, R> algebra = algebra3();

    Multivector<Double, R> result = algebra.crossProduct(e(3, 0), e(3, 1));

    assertBlade(result, 4, 1.0);
    assertOnlyBlade(result, 4);
  }

  @Test
  public void testCrossProductAnticommutes() {
    CliffordAlgebra<double[], Double, R> algebra = algebra3();

    Multivector<Double, R> result = algebra.crossProduct(e(3, 1), e(3, 0));

    assertBlade(result, 4, -1.0);
    assertOnlyBlade(result, 4);
  }

  @Test
  public void testCrossProductMatchesEuclideanSpaceCrossProduct() {
    EuclideanSpace space = EuclideanSpace.r3();
    CliffordAlgebra<double[], Double, R> algebra =
        new CliffordAlgebra<double[], Double, R>(space);

    Multivector<Double, R> a = vector3(1.0, 2.0, 3.0);
    Multivector<Double, R> b = vector3(4.0, -5.0, 6.0);

    double[] expected = space.crossProduct(
        new double[] { 1.0, 2.0, 3.0 },
        new double[] { 4.0, -5.0, 6.0 });
    double[] actual = space.fromMultivector(algebra.crossProduct(a, b));

    assertEquals(expected[0], actual[0], EPSILON);
    assertEquals(expected[1], actual[1], EPSILON);
    assertEquals(expected[2], actual[2], EPSILON);
  }

  @Test
  public void testCrossProductRejectsNonThreeDimensionalInputs() {
    CliffordAlgebra<double[], Double, R> algebra =
        new CliffordAlgebra<double[], Double, R>(EuclideanSpace.r2());

    try {
      algebra.crossProduct(e(2, 0), e(2, 1));
    } catch (IllegalArgumentException expected) {
      assertEquals(
          "cross product is only defined in dimension 3",
          expected.getMessage());
      return;
    }

    throw new AssertionError("expected IllegalArgumentException");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGeometricProductNotImplementedYet() {
    CliffordAlgebra<double[], Double, R> algebra = algebra3();

    algebra.geometricProduct(e(3, 0), e(3, 1));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInnerProductNotImplementedYet() {
    CliffordAlgebra<double[], Double, R> algebra = algebra3();

    algebra.innerProduct(e(3, 0), e(3, 1));
  }

  private CliffordAlgebra<double[], Double, R> algebra3() {
    return new CliffordAlgebra<double[], Double, R>(EuclideanSpace.r3());
  }

  private Multivector<Double, R> e(int dimension, int index) {
    return blade(dimension, 1 << index, 1.0);
  }

  private Multivector<Double, R> vector3(double x, double y, double z) {
    ArrayMultivector<Double, R> result =
        new ArrayMultivector<Double, R>(R.INSTANCE, 3);

    result.setCoefficient(1, x);
    result.setCoefficient(2, y);
    result.setCoefficient(4, z);

    return result;
  }

  private Multivector<Double, R> blade(
      int dimension,
      int blade,
      double coefficient) {

    ArrayMultivector<Double, R> result =
        new ArrayMultivector<Double, R>(R.INSTANCE, dimension);

    result.setCoefficient(blade, coefficient);
    return result;
  }

  private void assertZero(Multivector<Double, R> multivector) {
    assertOnlyBlades(multivector);
  }

  private void assertOnlyBlade(
      Multivector<Double, R> multivector,
      int blade) {

    assertOnlyBlades(multivector, blade);
  }

  private void assertOnlyBlades(
      Multivector<Double, R> multivector,
      int... nonzeroBlades) {

    for (int blade = 0; blade < multivector.bladeCount(); blade++) {
      boolean mayBeNonzero = false;

      for (int nonzeroBlade : nonzeroBlades) {
        if (blade == nonzeroBlade) {
          mayBeNonzero = true;
          break;
        }
      }

      if (!mayBeNonzero) {
        assertBlade(multivector, blade, 0.0);
      }
    }
  }

  private void assertBlade(
      Multivector<Double, R> multivector,
      int blade,
      double expected) {

    assertEquals(expected, multivector.coefficient(blade), EPSILON);
  }
}

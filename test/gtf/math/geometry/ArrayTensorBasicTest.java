package gtf.math.geometry;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import gtf.math.algebra.RealField;
import gtf.math.geometry.impl.ArrayTensor;
import gtf.math.geometry.impl.EuclideanSpace;

/**
 * Basic construction and indexing tests for {@link ArrayTensor}.
 *
 * @author gtf
 */
public class ArrayTensorBasicTest {

  @Test
  public void testScalarTensor() {
    Tensor<Double, RealField> tensor =
        new ArrayTensor<Double, RealField>(
            EuclideanSpace.R3,
            Arrays.asList(7.0));

    assertEquals(0, tensor.rank());
    assertEquals(0, tensor.contravariantRank());
    assertEquals(0, tensor.covariantRank());
    assertEquals(7.0, tensor.component(), 0.0);
  }

  @Test
  public void testContravariantVector() {
    Tensor<Double, RealField> tensor =
        new ArrayTensor<Double, RealField>(
            EuclideanSpace.R3,
            Arrays.asList(1.0, 2.0, 3.0),
            TensorVariance.CONTRAVARIANT);

    assertEquals(1, tensor.rank());
    assertEquals(1, tensor.contravariantRank());
    assertEquals(0, tensor.covariantRank());
    assertEquals(TensorVariance.CONTRAVARIANT, tensor.variance(0));

    assertEquals(1.0, tensor.component(0), 0.0);
    assertEquals(2.0, tensor.component(1), 0.0);
    assertEquals(3.0, tensor.component(2), 0.0);
  }

  @Test
  public void testCovariantVector() {
    Tensor<Double, RealField> tensor =
        new ArrayTensor<Double, RealField>(
            EuclideanSpace.R3,
            Arrays.asList(4.0, 5.0, 6.0),
            TensorVariance.COVARIANT);

    assertEquals(1, tensor.rank());
    assertEquals(0, tensor.contravariantRank());
    assertEquals(1, tensor.covariantRank());
    assertEquals(TensorVariance.COVARIANT, tensor.variance(0));

    assertEquals(4.0, tensor.component(0), 0.0);
    assertEquals(5.0, tensor.component(1), 0.0);
    assertEquals(6.0, tensor.component(2), 0.0);
  }
}

package gtf.math.geometry;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import gtf.math.algebra.RealField;
import gtf.math.geometry.impl.ArrayTensor;

/**
 * Tensor-product tests for {@link ArrayTensor}.
 *
 * @author gtf
 */
public class ArrayTensorTensorProductTest {

  @Test
  public void testVectorCovectorTensorProduct() {
    Tensor<Double, RealField> vector =
        new ArrayTensor<Double, RealField>(
            EuclideanSpace.r2(),
            Arrays.asList(1.0, 2.0),
            TensorVariance.CONTRAVARIANT);

    Tensor<Double, RealField> covector =
        new ArrayTensor<Double, RealField>(
            EuclideanSpace.r2(),
            Arrays.asList(3.0, 4.0),
            TensorVariance.COVARIANT);

    Tensor<Double, RealField> product = vector.tensorProduct(covector);

    assertEquals(2, product.rank());
    assertEquals(1, product.contravariantRank());
    assertEquals(1, product.covariantRank());
    assertEquals(TensorVariance.CONTRAVARIANT, product.variance(0));
    assertEquals(TensorVariance.COVARIANT, product.variance(1));

    assertEquals(3.0, product.component(0, 0), 0.0);
    assertEquals(4.0, product.component(0, 1), 0.0);
    assertEquals(6.0, product.component(1, 0), 0.0);
    assertEquals(8.0, product.component(1, 1), 0.0);
  }
}

package gtf.math.geometry;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import gtf.math.Permutation;
import gtf.math.algebra.RealField;
import gtf.math.geometry.impl.ArrayTensor;

/**
 * Tests for tensor index permutation.
 *
 * @author gtf
 */
public class ArrayTensorPermutationTest {

  @Test
  public void testRankTwoIndexSwap() {
    EuclideanSpace space = EuclideanSpace.r2();

    Tensor<Double, RealField> tensor =
        new ArrayTensor<Double, RealField>(
            space,
            Arrays.asList(
                1.0, 2.0,
                3.0, 4.0),
            TensorVariance.CONTRAVARIANT,
            TensorVariance.COVARIANT);

    Tensor<Double, RealField> swapped = tensor.permute(new Permutation(1, 0));

    assertEquals(2, swapped.rank());
    assertEquals(1, swapped.contravariantRank());
    assertEquals(1, swapped.covariantRank());
    assertEquals(TensorVariance.COVARIANT, swapped.variance(0));
    assertEquals(TensorVariance.CONTRAVARIANT, swapped.variance(1));

    assertEquals(1.0, swapped.component(0, 0), 0.0);
    assertEquals(3.0, swapped.component(0, 1), 0.0);
    assertEquals(2.0, swapped.component(1, 0), 0.0);
    assertEquals(4.0, swapped.component(1, 1), 0.0);
  }
}

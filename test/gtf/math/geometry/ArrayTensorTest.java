package gtf.math.geometry;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import gtf.math.Permutation;
import gtf.math.algebra.RealField;
import gtf.math.geometry.impl.ArrayTensor;

public class ArrayTensorTest {

  @Test
  public void testScalarTensor() {
    Tensor<Double, RealField> tensor = new ArrayTensor<Double, RealField>(
        EuclideanSpace.r3(), Arrays.asList(7.0));

    assertEquals(0, tensor.rank());
    assertEquals(0, tensor.contravariantRank());
    assertEquals(0, tensor.covariantRank());
    assertEquals(7.0, tensor.component(), 0.0);
  }

  @Test
  public void testContravariantVector() {
    Tensor<Double, RealField> tensor = new ArrayTensor<Double, RealField>(
        EuclideanSpace.r3(), Arrays.asList(1.0, 2.0, 3.0),
        TensorVariance.CONTRAVARIANT);

    assertEquals(1, tensor.rank());
    assertEquals(1, tensor.contravariantRank());
    assertEquals(0, tensor.covariantRank());
    assertEquals(TensorVariance.CONTRAVARIANT, tensor.variance(0));
    assertEquals(2.0, tensor.component(1), 0.0);
  }

  @Test
  public void testCovariantVector() {
    Tensor<Double, RealField> tensor = new ArrayTensor<Double, RealField>(
        EuclideanSpace.r3(), Arrays.asList(4.0, 5.0, 6.0),
        TensorVariance.COVARIANT);

    assertEquals(1, tensor.rank());
    assertEquals(0, tensor.contravariantRank());
    assertEquals(1, tensor.covariantRank());
    assertEquals(TensorVariance.COVARIANT, tensor.variance(0));
    assertEquals(5.0, tensor.component(1), 0.0);
  }

  @Test
  public void testTensorProduct() {
    EuclideanSpace space = EuclideanSpace.r2();
    Tensor<Double, RealField> vector = new ArrayTensor<Double, RealField>(
        space, Arrays.asList(1.0, 2.0), TensorVariance.CONTRAVARIANT);
    Tensor<Double, RealField> covector = new ArrayTensor<Double, RealField>(
        space, Arrays.asList(3.0, 4.0), TensorVariance.COVARIANT);

    Tensor<Double, RealField> product = vector.tensorProduct(covector);

    assertEquals(2, product.rank());
    assertEquals(TensorVariance.CONTRAVARIANT, product.variance(0));
    assertEquals(TensorVariance.COVARIANT, product.variance(1));
    assertEquals(3.0, product.component(0, 0), 0.0);
    assertEquals(4.0, product.component(0, 1), 0.0);
    assertEquals(6.0, product.component(1, 0), 0.0);
    assertEquals(8.0, product.component(1, 1), 0.0);
  }

  @Test
  public void testPermutation() {
    EuclideanSpace space = EuclideanSpace.r2();
    Tensor<Double, RealField> tensor = new ArrayTensor<Double, RealField>(
        space, Arrays.asList(1.0, 2.0, 3.0, 4.0),
        TensorVariance.CONTRAVARIANT, TensorVariance.COVARIANT);

    Tensor<Double, RealField> result = tensor.permute(new Permutation(1, 0));

    assertEquals(TensorVariance.COVARIANT, result.variance(0));
    assertEquals(TensorVariance.CONTRAVARIANT, result.variance(1));
    assertEquals(1.0, result.component(0, 0), 0.0);
    assertEquals(3.0, result.component(0, 1), 0.0);
    assertEquals(2.0, result.component(1, 0), 0.0);
    assertEquals(4.0, result.component(1, 1), 0.0);
  }
}

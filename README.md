# gtf-math

A lightweight Java mathematics, abstract algebra, linear algebra, and geometry framework.

## Overview

`gtf-math` is an experimental mathematics library for Java focused on:

- exact arithmetic
- abstract algebraic structures
- generic linear algebra
- finite-dimensional vector spaces
- inner-product and Euclidean geometry
- tensor algebra
- Clifford/geometric algebra foundations
- reusable mathematical abstractions

The goal of the project is not merely to provide numerical helper functions, but to build a coherent algebraic framework capable of representing and composing structures such as rings, fields, modules, vector spaces, matrices, tensors, and geometric algebras.

The library is intentionally designed around immutable mathematical objects wherever practical.

---

## Current Features

### Numeric Types

- `Fraction`
  - rational arithmetic using `long`
  - automatic reduction via gcd
  - immutable value semantics

- `BigFraction`
  - arbitrary-precision rational arithmetic using `BigInteger`
  - canonical normalization of signs
  - exact arithmetic without floating-point rounding

- `Complex`
  - complex arithmetic over double precision
  - polar and Cartesian representations

### Algebra

- `Ring<T>`
- `Field<T>`
- `Module<T, S, R>`
- `VectorSpace<T, S, F>`
- `FiniteDimensionalVectorSpace<T, S, F>`
- canonical real and complex fields:
  - `RealField`
  - `ComplexField`

### Linear Algebra

- generic matrices over abstract rings
- matrix implementations using pluggable storage models
- row reduction
- determinants
- matrix inverse support
- sparse vector infrastructure

### Geometry

- `InnerProductSpace`
- `EuclideanSpace`
- `Multivector`
- `CliffordAlgebra`
- wedge products
- Hodge duals
- cross products
- quaternion support

### Tensor Algebra

- `Tensor<S, F>` abstraction
- dense immutable `ArrayTensor`
- covariant and contravariant tensor indices via `TensorVariance`
- tensor products
- tensor index permutations using `Permutation`
- contraction of opposite-variance index pairs

### Utility Infrastructure

- general-purpose utility classes
- Apache Ant build system
- Javadoc generation
- JUnit 4 test support
- package-filtered test execution

---

## Design Philosophy

### Exact Arithmetic First

Where possible, exact arithmetic is preferred over floating-point approximation.

```java
BigFraction a = new BigFraction(1, 3);
BigFraction b = new BigFraction(1, 6);

BigFraction c = a.add(b);

System.out.println(c);
```

Output:

```text
1/2
```

---

### Algebraic Abstraction

The library is designed around reusable algebraic interfaces rather than only concrete numeric types.

For example, matrices and vector spaces are parameterized by the scalar domain, allowing the same structural code to work over different rings or fields where appropriate.

---

### Geometry as Structured Algebra

The `gtf.math.geometry` package builds on the algebra package to represent finite-dimensional geometric structures:

- inner-product spaces
- Euclidean spaces
- multivectors
- Clifford algebras
- tensors with index variance

This keeps the lower-level algebraic concepts separate from metric-dependent or basis-dependent geometric operations.

---

### Immutable Objects

Mathematical objects should behave like mathematical values.

Most core objects are therefore intended to be immutable, especially value-like objects such as fractions, permutations, and tensors.

---

## Tensor Example

```java
EuclideanSpace space = EuclideanSpace.r2();

Tensor<Double, RealField> vector = new ArrayTensor<Double, RealField>(
    space,
    Arrays.asList(1.0, 2.0),
    TensorVariance.CONTRAVARIANT);

Tensor<Double, RealField> covector = new ArrayTensor<Double, RealField>(
    space,
    Arrays.asList(3.0, 4.0),
    TensorVariance.COVARIANT);

Tensor<Double, RealField> product = vector.tensorProduct(covector);
Tensor<Double, RealField> contraction = product.contract(0, 1);
```

The contraction above computes:

```text
1 * 3 + 2 * 4 = 11
```

---

## Planned Areas of Expansion

### Linear Algebra

- sparse matrix support
- additional matrix decompositions
- improved matrix readers/writers
- optimized storage models

### Tensor and Geometry

- tensor spaces
- metric-based raising and lowering of indices
- symmetric and alternating tensors
- differential forms
- deeper Clifford/geometric algebra integration

### Abstract Algebra

- Euclidean domains
- finite fields
- quotient structures
- polynomial improvements

### Number Theory

- modular arithmetic enhancements
- primality testing
- continued fractions
- algebraic integers

---

## Building

The project currently uses Apache Ant.

### Compile

```bash
ant compile
```

### Run Tests

```bash
ant test
```

Tests use JUnit 4. Place JUnit jars in `lib/`, for example:

```text
lib/junit-4.13.2.jar
lib/hamcrest-core-1.3.jar
```

To run only tests under a specific package, use `test.package` with slash-separated package paths:

```bash
ant test -Dtest.package=gtf/math/geometry
```

### Build JAR

```bash
ant jar
```

### Generate Javadocs

```bash
ant javadoc
```

---

## Requirements

- Java 8 or later
- Apache Ant
- JUnit 4 for running tests

---

## License

MIT License.

See `LICENSE` for details.

---

## Author

Geoffrey T. Falk

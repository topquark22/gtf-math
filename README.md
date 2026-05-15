# gtf-math

A lightweight Java mathematics and abstract algebra framework.

## Overview

`gtf-math` is an experimental mathematics library for Java focused on:

- exact arithmetic
- abstract algebraic structures
- generic linear algebra
- symbolic mathematical manipulation
- reusable mathematical abstractions

The long-term goal of the project is not merely to provide numerical helper functions, but to build a coherent algebraic framework capable of representing:

- rings
- fields
- modules
- vector spaces
- matrices over arbitrary coefficient domains
- polynomial systems
- finite algebraic structures

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

### Utility Infrastructure

- general-purpose utility classes
- unit tests
- Ant build system
- Javadoc generation

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

The intended direction of the project is toward generic algebraic programming.

Future versions are expected to introduce abstractions such as:

```java
Ring<T>
Field<T>
Module<R, M>
Matrix<T>
Polynomial<T>
```

allowing mathematical structures to be composed generically.

---

### Immutable Objects

Mathematical objects should behave like mathematical values.

Most core objects are therefore intended to be immutable.

---

## Planned Areas of Expansion

### Abstract Algebra

- groups
- rings
- fields
- Euclidean domains
- modules
- vector spaces
- finite fields
- quotient structures

### Linear Algebra

- generic matrices
- vectors
- determinants
- LU decomposition
- sparse matrix support

### Symbolic Mathematics

- polynomial arithmetic
- symbolic simplification
- formal power series

### Number Theory

- modular arithmetic
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

### Build JAR

```bash
ant jar
```

### Generate Javadocs

```bash
ant javadocs
```

---

## Requirements

- Java 8 or later
- Apache Ant

---

## License

MIT License.

See `LICENSE` for details.

---

## Author

Geoffrey T. Falk

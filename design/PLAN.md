# gtf-math: Design and Development Plan

**Status:** Working design document  
**Baseline:** `main` after the Ant-to-Maven migration (October 21, 2026)  
**Scope:** The mathematical library, its public abstractions, tests, documentation, and build infrastructure

## 1. Purpose and direction

`gtf-math` is an experimental Java framework for exact arithmetic, abstract
algebra, linear algebra, geometry, tensors, combinatorics, and selected
statistical methods.

Its principal aim is **compositional mathematical programming**: express
mathematical structures explicitly, then construct more elaborate structures
from them. A matrix should be able to operate over an abstract ring rather
than only over `double`; polynomials should be defined over coefficient
fields; rational functions should be constructed from polynomial rings; and
tensor operations should respect the variance of their indices.

This is a mathematical framework first, not an attempt to compete on the
breadth or numerical performance of established numerical libraries.

The long-term direction is to make these abstractions **correct, coherent,
reusable, and testable**, while preserving exact arithmetic and immutable
value semantics where practical. Extensions should follow from clear
mathematical contracts, not simply from adding unrelated algorithms.

## 2. Current state (October 2026)

### 2.1 Repository and build

- 118 Java implementation files and 17 JUnit test classes.
- All 134 existing tests passed in the developer's local Maven build after
  the Ant-to-Maven migration. This is **not** a claim of complete test coverage.
- Maven coordinates: `io.github.topquark22:gtf-math:1.0-SNAPSHOT`.
- Standard Maven layout: `src/main/java` and `src/test/java`.
- Java compatibility target: release 8.
- Tests currently use JUnit 4.13.2.
- Maven builds the JAR and generates Javadocs; Eclipse is used for Java
  development.
- The repository does not yet include a Maven Wrapper or GitHub Actions
  workflows. Reproducibility and automated cross-JDK testing remain goals.

### 2.2 Implemented capabilities

| Area | Existing foundation |
| --- | --- |
| Exact arithmetic | `Fraction`, `BigFraction`, GCD/LCM, modular arithmetic, and number-theoretic utilities |
| Algebraic interfaces | Semigroup, monoid, group, abelian group, ring, field, and module abstractions |
| Concrete scalar structures | Rational, real, complex, integer, modular-ring, and prime-modulus field implementations |
| Polynomial algebra | Dense/sparse storage, polynomial arithmetic, division/remainder, GCDs, and rational functions |
| Group theory | Permutations, cyclic and symmetric groups, and Cartesian-product constructions |
| Linear algebra | Dense/sparse matrix storage, matrix rings, multiplication, row operations, determinants, and inverses |
| Geometry | Vector spaces, Euclidean spaces, norms, distances, and cross products |
| Tensors | Dense coordinate tensors with covariant/contravariant indices, products, permutations, and contractions |
| Geometric algebra | Multivectors, blade grades, wedge products, Hodge duals, and three-dimensional cross products |
| Graphs | Graph topologies, Cartesian-product topologies, colouring, and adjacency constraints |
| Statistics | Descriptive statistics, probability distributions, one-predictor linear regression, Markov-chain models, and entropy-related operations |
| Utilities | Combinatorial iterators, general mathematical helpers, and supporting data types |

These are **implemented components**, not a guarantee that every mathematical
case, public API, or invariant is fully tested.

### 2.3 Architectural strengths

1. **Structure-oriented APIs.** Operations are parameterized by algebraic
   domains instead of assuming a single numeric representation.
2. **Constructive composition.** For example, a coefficient field supports a
   polynomial ring, which supports a rational-function field; square matrices
   over a ring form a matrix ring.
3. **Exact arithmetic.** `BigFraction` and related types avoid rounding for
   computations naturally expressed over the rationals.
4. **Mathematical tensor semantics.** Covariant and contravariant indices are
   represented explicitly; contraction is restricted to opposite variance.
5. **Separate storage models.** Dense and sparse representations are available
   for matrices and polynomials, creating room for alternative implementations.
6. **Functional build baseline.** The Maven migration preserved the Java
   implementation and tests, and the developer verified 134 passing tests.

### 2.4 Known limits and design questions

- `CliffordAlgebra.geometricProduct()` and `innerProduct()` explicitly
  remain unimplemented. The available exterior operations do not yet
  constitute a complete geometric-algebra implementation.
- The `gtf.math.algebra` and `gtf.math.group` packages contain overlapping
  group-related interfaces. Their intended relationship needs documenting
  before further expansion.
- `Ring.divide()` permits unsupported division through a default
  `UnsupportedOperationException`. The domain assumptions of algorithms
  involving division, pivoting, or inversion should be explicit.
- Matrix implementations allow mutation, while fraction, polynomial, and
  tensor values tend toward immutability. Ownership, copying, equality, and
  aliasing contracts need consistent documentation.
- Tensor components and indices use a concrete coordinate representation.
  Broader coordinate/basis transformations, metric operations, and raising or
  lowering of indices are not yet complete.
- Statistical linear regression currently handles only one predictor.
  Advanced decompositions and numerically robust statistical procedures are
  future work.
- Several areas, especially graph algorithms and the more abstract algebraic
  interfaces, lack dedicated test classes. Existing tests are not a
  comprehensive verification of algebraic laws or corner cases.
- Sparse/dense behavior, large inputs, overflow, floating-point edge cases,
  and performance have not been systematically characterized.
- This document is a roadmap, not a declaration that the public API is frozen.

## 3. Design principles

### 3.1 Mathematical correctness before feature count

Every public mathematical operation should state its domain, preconditions,
postconditions, exceptional cases, and relevant laws. Do not silently treat a
ring as a field or assume division is always available.

### 3.2 Composition before duplication

Prefer algorithms expressed in terms of shared algebraic interfaces. Review
overlapping abstractions before adding new parallel hierarchies. Do not force
unification where the mathematical concepts or useful API contracts differ.

### 3.3 Exactness where natural; explicit approximation elsewhere

Retain exact rational, integer, polynomial, and finite-field calculations.
For floating-point algorithms, state precision limitations and numerical
stability requirements. Separate algebraic correctness from numerical
approximation guarantees.

### 3.4 Predictable object semantics

Prefer immutable mathematical values where feasible. Explicitly document
mutable types, deep versus shallow copies, equality, hash codes, and
storage-sharing behavior. Preserve backwards compatibility deliberately,
rather than changing semantics accidentally.

### 3.5 Small, reviewable changes

Develop each feature or refactoring in a topic branch with a focused test
suite and a narrow diff. Preserve existing formatting and unrelated code.
Keep build-system upgrades, API design changes, and mathematical algorithm
changes in distinct commits or pull requests.

## 4. Proposed roadmap

The phases are ordered by dependency and risk, not by calendar date.
Individual milestones should be reconsidered as design and testing reveal
new priorities.

### Phase A — Engineering baseline and reproducibility

**Goal:** Make the existing functionality reliably buildable, testable, and
maintainable before changing mathematical APIs.

- Add a Maven Wrapper and document supported JDK/Maven combinations.
- Introduce GitHub Actions for compilation, tests, and Javadocs across a
  selected Java compatibility matrix, including Java 8 where supported.
- Verify Eclipse/m2e setup and document clean import/update instructions.
- Consider a separate, reviewed migration from JUnit 4 to JUnit Jupiter;
  select the version based on the Java support policy.
- Improve build and test reporting without requiring code changes.
- Document how to produce the JAR, Javadocs, and package-filtered test runs.

**Exit criteria:** A fresh checkout builds with documented commands; CI
exercises the supported JDKs; all existing 134 tests remain passing; Eclipse
can import the Maven project cleanly.

### Phase B — Algebraic API and invariants audit

**Goal:** Establish reliable contracts before multiplying implementations.

- Map the interface relationships among semigroups, groups, rings, fields,
  modules, vector spaces, and the two group package families.
- Decide where operations requiring division or inverses belong and audit
  client algorithms for hidden field assumptions.
- Review the semantics of zero, identity, equality, and hashing for scalar
  elements, matrices, polynomials, permutations, and tensors.
- Specify scalar-domain compatibility, dimension checks, tensor-index
  variance rules, and cross-space operations.
- Review mutable versus immutable ownership rules and the meaning of copying.
- Record intentional API compatibility decisions before refactoring.

**Exit criteria:** A short architecture/design specification exists for the
core interfaces; documented invariants are reflected in focused tests; any
proposed breaking API changes have explicit rationale.

### Phase C — Strengthen correctness and test coverage

**Goal:** Make mathematical behavior verifiable rather than relying only on
example-based tests.

- Add law-based or property-based tests for associativity, identity,
  inverses, distributivity, permutation composition, and other applicable
  laws (with suitable restrictions for floating point).
- Cross-check dense and sparse implementations on equivalent inputs.
- Expand tests for graph colouring constraints, polynomial normalization,
  modular arithmetic, invalid arguments, and matrix singularities.
- Test tensor index permutations, contraction, rank-zero cases, and
  variance compatibility.
- Exercise edge cases: zero dimensions, empty structures where supported,
  large integer values, overflow, and numeric tolerances.
- Introduce regression tests for every corrected defect.

**Exit criteria:** A meaningful coverage report and a documented suite of
algebraic invariants exist; new changes run automatically in CI.

### Phase D — Complete the strongest mathematical abstractions

**Goal:** Deepen the reusable algebraic core rather than expanding into many
unrelated domains simultaneously.

1. **Groups and rings:** subgroup generation, cosets, homomorphisms,
   quotients, finite-field and polynomial constructions where contracts are
   clear and algorithms are tractable.
2. **Linear algebra:** reliable elimination over appropriate fields,
   decomposition APIs, and clear separation of exact and numerical methods.
3. **Tensors and Clifford algebras:** complete the geometric and inner
   products, define metric/basis conventions, and add raising/lowering and
   symmetric/alternating tensor operations as appropriate.
4. **Graph algorithms:** improve topology/colouring tests and reusable
   constraint-solving primitives.
5. **Statistics and probability:** pursue multivariate regression,
   numerical stability, additional distributions, and information-theoretic
   algorithms after the underlying linear algebra is trustworthy.

Each subproject should have its own design note, tests, and bounded scope.
These are candidate priorities, not commitments to implement everything.

**Exit criteria:** Each delivered feature has documented mathematical
semantics, representative usage examples, edge-case tests, and no
unexplained regressions in the core.

### Phase E — Documentation, releases, and consumers

**Goal:** Make the project understandable and usable outside its development
environment.

- Produce package-level conceptual guides, examples, and accurate Javadocs.
- Describe module structure, API stability, supported Java versions, and
  exceptions to immutability.
- Establish versioning and release practices, including migration notes for
  public API changes.
- Decide whether publication to Maven Central is useful; if so, supply
  required metadata, license details, and artifact signing.
- Identify a small real consumer/use case to validate usability of the public
  APIs rather than optimizing solely for internal tests.
- Measure and document performance only after correctness criteria are met.

**Exit criteria:** A new user can build the library, understand the major
abstractions, run examples, and rely on stated compatibility guarantees.

## 5. Immediate next steps

1. Keep the completed Ant-to-Maven migration as the initial engineering
   baseline. Confirm that Eclipse's Maven configuration remains usable.
2. Add the Maven Wrapper and CI in a small infrastructure-only change.
3. Record a concrete interface map for the two group-related packages and
   audit ring/field division assumptions.
4. Select one mathematically important but incomplete area—preferably
   Clifford products or rigorous property tests—and write a scoped design
   and test plan before implementation.
5. Keep future features and broad refactorings separate from infrastructure
   modernization.

## 6. Explicit non-goals for the immediate work

- No mass reformatting or rewriting of existing Java code.
- No change to Java package names merely to match Maven's `groupId`.
- No simultaneous change of build tooling, language level, testing
  framework, and public mathematical interfaces.
- No claim that 134 passing tests establish full mathematical correctness.
- No premature promise of a stable `1.0` API solely because the current
  Maven artifact uses `1.0-SNAPSHOT`.

## 7. Keeping this plan current

Update this document when a major mathematical design decision is made or a
roadmap milestone is completed. Keep **implemented**, **tested**, **planned**,
and **experimental** clearly distinct. Link substantial design decisions to
their implementation and test pull requests.

The guiding question for future development is:

> Does this change make the mathematical structures more correct, more
> composable, or more useful—without obscuring their algebraic meaning?

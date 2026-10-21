# TODO

## General

- Add README.md for the `gtf.math.group` package.
- Add more unit tests for closure, associativity, identity, and inverses.
- Add utility methods for generating Cayley tables.
- Add subgroup generation from a set of generators.
- Add support for left/right cosets.
- Add quotient groups.
- Add direct products of finite groups.
- Add support for group homomorphisms.
- Add support for automorphisms and isomorphism checks.

## Finite Groups

- Consider adding `Iterable<T>` support for lazy enumeration.
- Add support for finite subgroup generation.
- Add support for random element selection.
- Add support for element order computation.
- Add support for group exponent computation.

## Permutation Groups

- Add parity/sign methods directly to `Permutation`.
- Add cycle decomposition support.
- Add cycle notation formatting.
- Add transposition decomposition.
- Add permutation powers.
- Add orbit computation.
- Add stabilizer computation.
- Add generated permutation subgroups.
- Add Schreier-Sims style algorithms for larger groups.

## Factory Methods

- Add cyclic permutation groups.
- Add Klein four group factory.
- Add quaternion group factory.
- Add affine linear groups over finite fields.
- Add matrix groups such as GL(n, F).

## Performance

- Improve permutation generation efficiency.
- Replace factorial enumeration with lazy iterators where possible.
- Add caching for commonly used finite groups.
- Optimize closure validation for large groups.

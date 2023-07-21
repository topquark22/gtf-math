package gtf.math;

import java.math.BigInteger;

public class FractionTest {

  private static final long SIZE = 10000;

  public static void main(String[] args) {

    if (args.length >= 2) {
      long p1 = Long.parseLong(args[0]);
      long q1 = Long.parseLong(args[1]);
      Fraction f1 = new Fraction(p1, q1);
      doit(f1);
      if (args.length >= 4) {
        long p2 = Long.parseLong(args[2]);
        long q2 = Long.parseLong(args[3]);
        Fraction f2 = new Fraction(p2, q2);
        doit2(f1, f2);
      }
    } else {
      Fraction f1 = someFraction(SIZE);
      Fraction f2 = someFraction(SIZE);
      doit(f1);
      doit2(f1, f2);
    }

    try {
      Fraction f = new Fraction(someLong(SIZE), 0);
      System.out.println("Divide by zero test failed");
    } catch (ArithmeticException e) {
      System.out.println("Divide by zero test successful");
    }

    try {
      Fraction f = someFraction(SIZE);
      Fraction g = f.divide(Fraction.ZERO);
      System.out.println("Divide by zero test failed");
    } catch (ArithmeticException e) {
      System.out.println("Divide by zero test successful");
    }

    // test that fraction is reduced
    Fraction f = someFraction(SIZE);
    BigInteger p = new BigInteger(new Long(f.getNumerator()).toString());
    BigInteger q = new BigInteger(new Long(f.getDenominator()).toString());
    BigInteger gcd = p.gcd(q);
    if (gcd.equals(BigInteger.ONE)) {
      System.out.println("GCD test successful");
    } else {
      System.out.println("GCD test failed");
    }
  }

  private static long someLong(long size) {
    return (long) (Math.random() * 2 * size) - size;
  }

  private static Fraction someFraction(long size) {
    long p = someLong(size);
    long q = 0;
    while (q == 0) {
      q = someLong(size);
    }
    return new Fraction(p, q);
  }

  private static String comparisonOperator(Fraction f1, Fraction f2) {
    switch (f1.compareTo(f2)) {
      case -1:
        return "<";
      case 0:
        return "==";
      case 1:
        return ">";
    }
    return "!=";
  }

  private static void doit(Fraction f) {
    System.out.println("f = " + f);
    System.out.println();
    System.out.println("1/f = " + f.reciprocal());
    System.out.println("floor(f) = " + f.floor());
    System.out.println("ceil(f) = " + f.ceil());
    System.out.println("doubleValue(f) = " + f.doubleValue());
    System.out.println("floatValue(f) = " + f.floatValue());
  }

  private static void doit2(Fraction f1, Fraction f2) {
    System.out.println(f1 + " + " + f2 + " = " + f1.add(f2));
    System.out.println(f1 + " * " + f2 + " = " + f1.multiply(f2));
    System.out.println(f1 + " " + comparisonOperator(f1, f2) + " " + f2);
  }
}

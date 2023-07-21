package gtf.math;

import java.math.BigInteger;

public class BigFractionTest {

  public static void main(String[] args) {
    
    if (args.length >= 2) {
      BigInteger p1 = new BigInteger(args[0]);
      BigInteger q1 = new BigInteger(args[1]);
      BigFraction f1 = new BigFraction(p1, q1);
      doit(f1);
      if (args.length >= 4) {
        BigInteger p2 = new BigInteger(args[2]);
        BigInteger q2 = new BigInteger(args[3]);
        BigFraction f2 = new BigFraction(p2, q2);
        doit2(f1, f2);
      }
    }
  }

  private static void doit(BigFraction f1) {
    System.out.println("recip(" + f1 + ") = " + f1.reciprocal());
    System.out.println("floor(" + f1 + ") = " + f1.floor());
    System.out.println(" ceil(" + f1 + ") = " + f1.ceil());
    System.out.println("doubleValue(" + f1 + ") = " + f1.doubleValue());
  }

  private static void doit2(BigFraction f1, BigFraction f2) {    
    System.out.println(f1 + " + " + f2 + " = " + f1.add(f2));
    System.out.println(f1 + " * " + f2 + " = " + f1.multiply(f2));
    System.out.println(f1 + " compareTo " + f2 + " = " + f1.compareTo(f2));
  }
}

package gtf.math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;


public class MillerRabin32 {

  private static boolean miller_rabin_pass_32(int a, int n) {
    int d = n - 1;
    int s = Integer.numberOfTrailingZeros(d);
    d >>= s;
    int a_to_power = ModularArithmetic.exp32(a, d, n);
    if (a_to_power == 1)
      return true;
    for (int i = 0; i < s - 1; i++) {
      if (a_to_power == n - 1)
        return true;
      a_to_power = ModularArithmetic.exp32(a_to_power, 2, n);
    }
    if (a_to_power == n - 1)
      return true;
    return false;
  }

  public static boolean miller_rabin_32(int n) {
    if (n <= 1)
      return false;
    else if (n == 2)
      return true;
    else if (miller_rabin_pass_32(2, n)
        && (n <= 7 || miller_rabin_pass_32(7, n))
        && (n <= 61 || miller_rabin_pass_32(61, n)))
      return true;
    else
      return false;
  }

  public static void main(String[] args) throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    PrintStream out = System.out;
    String s = in.readLine();
    while (s != null) {
      try {
        int n = Integer.parseInt(s);
        out.println(miller_rabin_32(n) ? "PRIME" : "COMPOSITE");
      } catch (NumberFormatException e) {
        out.println("INVALID");
      }
      s = in.readLine();
    }
  }
}

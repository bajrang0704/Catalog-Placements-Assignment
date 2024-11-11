import java.math.BigInteger;
import java.util.*;

public class M{

    // Decode a string of a number in a given base
    public static BigInteger decodeBase(String value, int base) {
        return new BigInteger(value, base);
    }

    // Lagrange interpolation to find the polynomial's constant term (c)
    public static BigInteger findConstantTerm(List<BigInteger> xValues, List<BigInteger> yValues, int k) {
        BigInteger constantTerm = BigInteger.ZERO;
        
        // Iterate over each point (x_i, y_i)
        for (int i = 0; i < k; i++) {
            BigInteger xi = xValues.get(i);
            BigInteger yi = yValues.get(i);

            // Calculate the Lagrange basis polynomial L_i(x) at x = 0
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;

            // L_i(x) = (product over j != i) of (x - x_j) / (x_i - x_j)
            for (int j = 0; j < k; j++) {
                if (i != j) {
                    numerator = numerator.multiply(BigInteger.ZERO.subtract(xValues.get(j)));
                    denominator = denominator.multiply(xi.subtract(xValues.get(j)));
                }
            }

            // Add to the constant term: L_i(0) * y_i
            BigInteger liAtZero = numerator.divide(denominator);
            constantTerm = constantTerm.add(liAtZero.multiply(yi));
        }

        // The constant term is found by taking the result mod the prime
        return constantTerm.mod(BigInteger.TWO.pow(256)); // Modulo 256-bit
    }

    public static void main(String[] args) {
        // Test Case 1
        Map<String, Object> testCase1 = new HashMap<>();
        Map<String, Object> keys1 = new HashMap<>();
        keys1.put("n", 4);
        keys1.put("k", 3);
        testCase1.put("keys", keys1);
        testCase1.put("1", Map.of("base", "10", "value", "4"));
        testCase1.put("2", Map.of("base", "2", "value", "111"));
        testCase1.put("3", Map.of("base", "10", "value", "12"));
        testCase1.put("6", Map.of("base", "4", "value", "213"));

        // Test Case 2
        Map<String, Object> testCase2 = new HashMap<>();
        Map<String, Object> keys2 = new HashMap<>();
        keys2.put("n", 10);
        keys2.put("k", 7);
        testCase2.put("keys", keys2);
        testCase2.put("1", Map.of("base", "6", "value", "13444211440455345511"));
        testCase2.put("2", Map.of("base", "15", "value", "aed7015a346d63"));
        testCase2.put("3", Map.of("base", "15", "value", "6aeeb69631c227c"));
        testCase2.put("4", Map.of("base", "16", "value", "e1b5e05623d881f"));
        testCase2.put("5", Map.of("base", "8", "value", "316034514573652620673"));
        testCase2.put("6", Map.of("base", "3", "value", "2122212201122002221120200210011020220200"));
        testCase2.put("7", Map.of("base", "3", "value", "20120221122211000100210021102001201112121"));
        testCase2.put("8", Map.of("base", "6", "value", "20220554335330240002224253"));
        testCase2.put("9", Map.of("base", "12", "value", "45153788322a1255483"));
        testCase2.put("10", Map.of("base", "7", "value", "1101613130313526312514143"));

        // Process both test cases
        System.out.println("Output for TestCase 1: " + getConstantTerm(testCase1));
        System.out.println("Output for TestCase 2: " + getConstantTerm(testCase2));
    }

    // Process the given test case and return the constant term
    @SuppressWarnings("unchecked")
    public static BigInteger getConstantTerm(Map<String, Object> testCase) {
        // Retrieve the number of roots and minimum required roots
        Map<String, Object> keys = (Map<String, Object>) testCase.get("keys");
        int n = (int) keys.get("n");
        int k = (int) keys.get("k");

        List<BigInteger> xValues = new ArrayList<>();
        List<BigInteger> yValues = new ArrayList<>();

        // Parse the roots and decode the values
        for (int i = 1; i <= n; i++) {
            // Ensure the root data exists before trying to decode it
            Map<String, String> root = (Map<String, String>) testCase.get(String.valueOf(i));
            if (root != null) {
                int base = Integer.parseInt(root.get("base"));
                String value = root.get("value");

                BigInteger decodedValue = decodeBase(value, base);
                xValues.add(BigInteger.valueOf(i));
                yValues.add(decodedValue);
            } else {
                System.out.println("Missing root data for key: " + i);
            }
        }

        // Compute the constant term (c) using Lagrange interpolation
        return findConstantTerm(xValues, yValues, k);
    }
}
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Shamir2 {
        long q; // prime number q as modulus
        List<Long> polCoeff = new ArrayList<>(); // polynom coefficient
        long M; // message represented as number M
        long n; // number of participant
        long m; // number of min. participant to construct M
        long p; // prime number p as modulus where q|p-1, q is modulus of the polynomial function to generate shares
        long g; // generator g as base
        Map<Integer, Long> shares = new HashMap<Integer, Long>();
        Map<Integer, Long> checkСoefficient = new LinkedHashMap<>();

        public Shamir2(long q, long M, long n, long m, long p, long g){
            this.q = q;
            this.M = M;
            this.n = n;
            this.m = m;
            this.p = p;
            this.g = g;

            generatePolCoeff(M);
            for(int i=0; i<n; i++){
                int iCount = i + 1;
                long share = countShare(iCount);
//                long z = new Double(Math.pow(g, share) % p).longValue();
                shares.put(iCount, share);
            }
        }

    private void generatePolCoeff(long M){

        for(long i=0; i<n; i++){
            final byte[] p = GF256.generate( new SecureRandom(), 5, ByteUtils.longToBytes(M)[0]);
            polCoeff.add(new BigInteger(p).longValue());
        }
//        polCoeff.add((long)18);
//        polCoeff.add((long)13);
//        polCoeff.add((long)6);
//        polCoeff.add((long)20);
//        polCoeff.add((long)3);

        long rM = new Double(Math.pow(g, M) % p).longValue();
        checkСoefficient.put(0, rM);
        for (int i = 0; i < polCoeff.size(); i++) {
            Long aLong = polCoeff.get(i);
//            double r = Math.pow(g, aLong) % p;
            BigInteger r = BigInteger.valueOf(g).modPow(BigInteger.valueOf(aLong), BigInteger.valueOf(p));
            checkСoefficient.put(i + 1, r.longValue());
        }
    }

    private long countShare(int x){
        long res = 0;
        res += M;
        for(int i=0; i<polCoeff.size(); i++){
            long res1 = polCoeff.get(i) * (long) Math.pow(x, i + 1);
            res += res1;
        }
        res %= q;
        return res;
    }

    public boolean check(int i, long share){
        BigInteger rRez = BigInteger.valueOf(checkСoefficient.get(0));
            for (int ir = 1; ir < checkСoefficient.size(); ir++){
                int exponent = new Double(Math.pow(i, ir)).intValue();
                BigInteger a = BigInteger.valueOf(checkСoefficient.get(ir)).pow(exponent);
                rRez = rRez.multiply(a);
//                rRez *= Math.pow(checkСoefficient.get(ir), Math.pow(i, ir));
            }

            rRez = rRez.mod(BigInteger.valueOf(p));

        long secondCheck = BigInteger.valueOf(g).modPow(BigInteger.valueOf(share), BigInteger.valueOf(p)).longValue();

        return rRez.longValue() == secondCheck;
    }

    public long summ(Map<Integer, Long> shares) {
        long rez = 0;
        for (Map.Entry<Integer, Long> entry : shares.entrySet()) {
            Integer i = entry.getKey();
            Long share = entry.getValue();
            long l = calcL(i, share, new ArrayList<>(shares.keySet()));
            rez += l * share;
        }
        return rez % q;
    }

    private long calcL(Integer ind, Long share, List<Integer> keys) {
        BigInteger result = BigInteger.ZERO;
        BigInteger q2 = BigInteger.valueOf(q);
//        BigInteger q = BigInteger.valueOf((p.intValue()-1)/2);
        for (int i = 0; i < n; i++) {
            BigInteger lower = BigInteger.ONE;
            BigInteger upper = BigInteger.ONE;
            BigInteger term = BigInteger.valueOf(share);
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    upper = upper.multiply(BigInteger.valueOf(keys.get(j)).negate()).mod(q2);
                    BigInteger lowerpart = BigInteger.valueOf(keys.get(i)).subtract(BigInteger.valueOf(keys.get(j)));
                    lower = lower.multiply(lowerpart).mod(q2);
                }
            }
            BigInteger inverseLower = lower.mod(q2);
            term = term.multiply(inverseLower).multiply(upper).mod(q2);
            result = result.add(term).mod(q2);
        }
        return result.longValue();
    }
}

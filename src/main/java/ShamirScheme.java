
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import javafx.util.Pair;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Edwin
 */
public class ShamirScheme {
    long q; // prime number q as modulus
    ArrayList<Long> polCoeff; // polynom coefficient
    long M; // message represented as number M
    long w; // number of participant
    long t; // number of min. participant to construct M
    long p; // prime number p as modulus where q|p-1, q is modulus of the polynomial function to generate shares
    long g; // generator g as base
    ArrayList<Long> xVal; // x value for every participant
    ArrayList <Pair<Long, Long>> shares;
    ArrayList <Pair<Long, Double>> checkShare;
    ArrayList <Pair<Long, Double>> checkСoefficient;

    public ShamirScheme(long q, long M, long w, long t, long p, long g){
        this.q = q;
        this.M = M;
        this.w = w;
        this.t = t;
        this.p = p;
        this.g = g;
        polCoeff = new ArrayList<>();
        xVal = new ArrayList<>();
        shares = new ArrayList<>();
        checkShare = new ArrayList<>();
        checkСoefficient = new ArrayList<>();
    }
    
    public void generatePolCoeff(int secret){

        for(long i=0; i<t-1; i++){
//            final byte[] p = GF256.generate( new SecureRandom(), 5 - 1, ByteUtils.longToBytes(secret)[0]);
//            polCoeff.add(new BigInteger(p).longValue());

//            polCoeff.add((long)(Math.random()*(q-1)));
        }
        polCoeff.add((long)5);
        polCoeff.add((long)2);
        polCoeff.add((long)1);

        double rM = Math.pow(g, secret) % p;
        checkСoefficient.add(new Pair<>(0L, rM));
        for (int i = 0; i < polCoeff.size(); i++) {
            Long aLong = polCoeff.get(i);
            double r = Math.pow(g, aLong) % p;
            checkСoefficient.add(new Pair<>(i+1L, r));
        }
    }
    
    public void generateXVal(){
        for(long i=0; i<w; i++){
            xVal.add(i+1);
        }
    }
    
    public long countShare(long x){
        long res = 0;
        res += M;
        for(int i=0; i<polCoeff.size(); i++){
            res += (polCoeff.get(i)* (long)Math.pow(x, i+1));
        }
        res %= q;
        return res;
    }
    
    public void generateShares(){
        for(int i=0; i<w; i++){
            long share = countShare(xVal.get(i));
            double z = Math.pow(g, share) % p;
            checkShare.add(new Pair(xVal.get(i), z));
            shares.add(new Pair(xVal.get(i), share));
        }
    }
    
    // do full steps of Shamir Scheme to generate shares
    public void doShamirScheme(int i){
        generatePolCoeff(i);
        generateXVal();
        generateShares();
    }
    
    public void printShares(){
        System.out.print("S(X)= "+M+" + ");
        for(int i=0; i<polCoeff.size(); i++){
            System.out.print(polCoeff.get(i)+"X"+(i+1)+" + ");
        }
        System.out.println("");
        for(int i=0; i<shares.size(); i++){
            System.out.println("Share-"+ (i+1) + " : (" + shares.get(i).getKey() + " , " + shares.get(i).getValue() + " )");
        }
    }
}

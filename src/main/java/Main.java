
import javafx.util.Pair;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Edwin
 */
public class Main {

    public static final Long SECRET = 2L;

    public static void  main(String[] args){
        // share distribution

        Shamir2 s = new Shamir2(31, SECRET, 5, 5, 311, 47);
        Map<Integer, Long> shares = s.shares;
        s.check(2, shares.get(2));
//        long join = s.summ(shares);
//        System.out.println(join);
//        final byte[] secret = ByteUtils.longToBytes(SECRET);
//        ShamirScheme S = new ShamirScheme(22, SECRET, 5, 5, 24, 1);
//        S.doShamirScheme(SECRET.intValue());
//        S.printShares();

//
//        Scheme scheme = new Scheme(new SecureRandom(), 5, 5);
//        Pair<Map<Integer, BigInteger>, List<BigInteger>> mapListPair = scheme.splitByFeldman(secret);
//        Map<Integer, BigInteger> q = scheme.split(secret);
//
//        Map<Integer, BigInteger> spl = mapListPair.getKey();
//        System.out.println(spl);
//
//        List<Long> collect = mapListPair.getValue().stream().map(bigInteger -> bigInteger.longValue()).collect(Collectors.toList());

        
        //share verification
//        FeldmanVSS F = new FeldmanVSS(24, 1);
////        ArrayList<Long> polCoeff = new ArrayList<>();
//        Scanner reader = new Scanner(System.in);
//
//        System.out.println("ID partisipan: ");
//        long pid = reader.nextLong();
//
//        System.out.println("share yang diterima : ");
//        long share = reader.nextLong();
//
//        System.out.println("Commitments: ");
//        System.out.print("Enter secret: ");
//        long x = reader.nextLong();
//        polCoeff.add(x);
//        for(int i=0; i<S.polCoeff.size(); i++){
//            System.out.print("Enter coefficient-"+i+": ");
//            x = reader.nextLong();
//            polCoeff.add(x);
//        }
//        F.setCommitments(SECRET, S.checkСoefficient);
//        String result = F.verifyShare(pid, share, S.checkShare.get((int) pid));
//        System.out.println("hasil verifikasi: "+result);
    }
}


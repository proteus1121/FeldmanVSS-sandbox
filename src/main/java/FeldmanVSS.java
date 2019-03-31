
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Edwin
 */
public class FeldmanVSS {
    long p; // prime number p as modulus where q|p-1, q is modulus of the polynomial function to generate shares
    long g; // generator g as base
    ArrayList<Pair<Long, Double>> commitments; // commitments g^polCoeff;
    
    public FeldmanVSS(long p, long g){
        this.p = p;
        this.g = g;
        this.commitments = new ArrayList<>();
    }
    
    public void setCommitments(long M, ArrayList<Pair<Long, Double>> polCoeff){
        commitments = polCoeff;
    }
    
    public String verifyShare(long id, long s, Pair<Long, Double> longDoublePair){
        long shareRes = ((long) Math.pow(g, s)) % p;
        
        long commitmentsRes = 1;
        for(int i=0; i<this.commitments.size(); i++){
//            commitmentsRes *= ((long) Math.pow(commitments.get(i), Math.pow(id, i)) % p);
        }
        commitmentsRes %= p;
//        System.out.println("shareRes="+shareRes);
//        System.out.println("comRes="+commitmentsRes);
        return (shareRes == commitmentsRes)? "valid" : "invalid";
    }
    
}

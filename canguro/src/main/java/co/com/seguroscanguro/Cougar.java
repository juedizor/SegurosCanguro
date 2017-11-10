/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.seguroscanguro;

/**
 *
 * @author jeio
 */
interface PoseeCola {
    public int get();
}

abstract class Puma implements PoseeCola {
    protected int get(){
        
    }
}

public class Cougar {
    
    public static void main(String[] args) {
       String xyz = "xyz";
       String y = xyz.replace("Y", "y");
       y = y + "abc";
        System.out.println(y);
    }
    
}

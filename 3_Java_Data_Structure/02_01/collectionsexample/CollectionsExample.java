/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collectionsexample;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Producer
 */
public class CollectionsExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("Position of 21: " + Collections.binarySearch(list1, 5));
    }

}

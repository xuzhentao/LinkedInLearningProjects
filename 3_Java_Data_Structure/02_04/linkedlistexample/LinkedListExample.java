/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linkedlistexample;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author Producer
 */

public class LinkedListExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        LinkedList<String> states = new LinkedList<>();
        states.add("Alaska");
        states.add("Arizona");
        states.add("California");
        System.out.println(states);
        states.addFirst("Alabama");
        System.out.println(states);

        System.out.println("Last state in the list is " + states.getLast());
        System.out.println(states.size());
        ListIterator iterator = states.listIterator(states.size());
        while (iterator.hasPrevious()) {
            System.out.println(iterator.previous());
        }


    }


}



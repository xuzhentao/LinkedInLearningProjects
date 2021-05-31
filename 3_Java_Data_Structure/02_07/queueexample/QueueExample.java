/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queueexample;


import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Producer
 */
public class QueueExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i <= 10; i++) {
            queue.add(i);
        }

        System.out.println("Elements in the queue " + queue);

        Integer removed = queue.remove();
        System.out.println("Element is removed: " + removed);

        Integer top = queue.peek();
        System.out.println("Top element is: " + top);


    }

}

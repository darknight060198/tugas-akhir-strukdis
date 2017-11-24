package model;

import java.util.Scanner;

public class MSTProblem {    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Graph h = new Graph();
        int i = 0;
        while (true) {
            String nama = input.next();
            if (nama.equals("end")) break;
            h.addNode(new Node(i++, nama));
        }
        
        while (true) {
            int src = input.nextInt();
            int dest = input.nextInt();
            double weight = input.nextDouble();
            if (src == -1 && dest == -1 && weight == -1) break;
            if (!h.addEdges(src, dest, weight)) System.out.println("FAILED");;
        }
        
//        h.printNode();
//        
//        System.out.println();
//        
//        h.printEdge();
        
        h.printEdge(h.kruskalMST());
    }
}
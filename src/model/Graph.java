package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Graph {

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private DisjointSet set;
    
    public Graph(){
        nodes = new ArrayList();
        edges = new ArrayList();
    }
    
    public boolean addNode(Node n){
        return nodes.add(n);
    }
    
    public boolean addEdges(int srcCode, int destCode, double weight){
        if(srcCode > nodes.size()-1 || destCode > nodes.size()-1 || srcCode < 0 || destCode < 0) {
            return false;
        } else {
            edges.add(new Edge(weight, nodes.get(srcCode), nodes.get(destCode)));
        }
        
        return true;
    }
    
    public ArrayList<Edge> kruskalMST(){
        ArrayList<Edge> res = new ArrayList<>();
        
        Collections.sort(edges);
        
        set = new DisjointSet();
        set.initSet(nodes);
        
        Iterator<Edge> edgeIt = edges.iterator();
        
        while(set.getNumberofDisjointSet() < nodes.size()) {
            Edge nextEdge = edgeIt.next();
            
            int x = set.find_set(nextEdge.getSrc().number);
            int y = set.find_set(nextEdge.getDest().number);
            
            if (x != y) {
                res.add(nextEdge);
                set.union(x, y);
            }
        }
        
        return res;
    }
    
    public void printNode() {
        Iterator<Node> nodeit = nodes.iterator();
        while (nodeit.hasNext()) {
            Node temp = nodeit.next();
            System.out.println("Node : " + temp.getName() + ", No: " + temp.number);
        }
    }
    
    public void printEdge() {
        Iterator<Edge> edgeit = edges.iterator();
        while (edgeit.hasNext()) {
            Edge temp = edgeit.next();
            System.out.println(temp.getSrc().number + " -> " + temp.getDest().number + " : " + temp.getWeight());
        }
    }
    
    public void printEdge(ArrayList<Edge> edges) {
        Iterator<Edge> edgeit = edges.iterator();
        while (edgeit.hasNext()) {
            Edge temp = edgeit.next();
            System.out.println(temp.getSrc().number + " -> " + temp.getDest().number + " : " + temp.getWeight());
        }
    }
}

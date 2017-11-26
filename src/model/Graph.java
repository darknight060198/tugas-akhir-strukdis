package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    
    public boolean removeNode(int index) {
        return nodes.remove(nodes.get(index));
    }
    
    public boolean removeEdge(int index) {
        return edges.remove(edges.get(index));
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }
    
    public ObservableList<ModelNode> getModelNodesAsObservableList() {
        Iterator<Node> nodeIt = nodes.iterator();
        ArrayList<ModelNode> resNode = new ArrayList<>();
        while (nodeIt.hasNext()) {
            Node temp = nodeIt.next();
            resNode.add(new ModelNode(temp.getNumber(), temp.getName()));
        }
        
        return FXCollections.observableArrayList(resNode);
    }
    
    public ObservableList<ModelEdge> getModelEdgeAsObservableList(){
        Iterator<Edge> edgeIt = edges.iterator();
        ArrayList<ModelEdge> res = new ArrayList<>();
        while(edgeIt.hasNext()){
            Edge temp = edgeIt.next();
            res.add(new ModelEdge(temp.getWeight(), temp.getSrc(), temp.getDest()));
        }
        return FXCollections.observableArrayList(res);
    }
    
    public ArrayList<String> getNodeListAsString() {
        ArrayList<String> res = new ArrayList<>();
        Iterator<Node> nodeIt = nodes.iterator();
        while (nodeIt.hasNext()) {
            res.add(nodeIt.next().getName());
        }
        
        return res;
    }
    
    public ArrayList<Edge> kruskalMST(){
        ArrayList<Edge> res = new ArrayList<>();
        
        Collections.sort(edges);
        
        set = new DisjointSet();
        set.initSet(nodes);
        
        Iterator<Edge> edgeIt = edges.iterator();
        
        while(set.getNumberofDisjointSet() < nodes.size()) {
            Edge nextEdge = edgeIt.next();
            
            int x = set.find_set(nextEdge.getSrc().getNumber());
            int y = set.find_set(nextEdge.getDest().getNumber());
            
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
            System.out.println("Node : " + temp.getName() + ", No: " + temp.getNumber());
        }
    }
    
    public void printEdge() {
        Iterator<Edge> edgeit = edges.iterator();
        while (edgeit.hasNext()) {
            Edge temp = edgeit.next();
            System.out.println(temp.getSrc().getNumber() + " -> " + temp.getDest().getNumber() + " : " + temp.getWeight());
        }
    }
    
    public void printEdge(ArrayList<Edge> edges) {
        Iterator<Edge> edgeit = edges.iterator();
        while (edgeit.hasNext()) {
            Edge temp = edgeit.next();
            System.out.println(temp.getSrc().getNumber() + " -> " + temp.getDest().getNumber() + " : " + temp.getWeight());
        }
    }
    
    public void synchronizedNumberNode() {
        for (int i = 0 ; i < nodes.size(); i++) {
            nodes.get(i).setNumber(i);
        }
    }
    
    public void updateNodeName(int idx, String newName) {
        nodes.get(idx).setName(newName);
    }
}

package model;

public class Edge implements Comparable<Edge> {

    private double weight;
    private Node src, dest;

    public Edge(double weight, Node src, Node dest) {
        this.weight = weight;
        this.src = src;
        this.dest = dest;
    }
    
    public int compareTo(Edge compareEdge) {
        return (int)(this.weight - compareEdge.weight);
    }

    public double getWeight() {
        return weight;
    }

    public Node getSrc() {
        return src;
    }

    public Node getDest() {
        return dest;
    }
}

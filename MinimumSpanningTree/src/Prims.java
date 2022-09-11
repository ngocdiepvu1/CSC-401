import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Prims {

    // Create a class Edge for edges
    class Edge{

        // Each edge has source, destination, and weight
        private Node source;
        private Node destination;
        private int weight;

        // Helper functions (setters and getters)
        public void setSource(Node source) {
            this.source = source;
        }

        public void setDestination(Node destination) {
            this.destination = destination;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getWeight() {
            return this.weight;
        }

        public Node getSource() {
            return this.source;
        }

        public Node getDestination() {
            return this.destination;
        }

    };

    //  Create class Node for vertices
    class Node implements Comparable<Node> {
        private String label;
        private LinkedList<Edge> edges;
        private int key;
        private Node daddy;

        // Constructor to create a new ode
        Node() {
            this.edges = new LinkedList();
        }

        // Helper functions (setters and getters)
        public void setLabel(String label) {
            this.label = label;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public void setDaddy(Node daddy) {
            this.daddy = daddy;
        }

        public String getLabel() {
            return this.label;
        }

        public int getKey() {
            return this.key;
        }

        public LinkedList getEdges() {
            return this.edges;
        }

        // Function to compare the weight of 2 edges
        @Override
        public int compareTo(Node o) {
            if (this.getKey() > o.getKey()) {
                return 1;
            } else if (this.getKey() < o.getKey()) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    // Variables for number of nodes and the array of nodes for the graph
    private int numberOfNodes;
    private Node[] weightedGraph;

    // Constructor to create a graph
    public Prims(int graphV) {

        this.numberOfNodes = graphV;
        this.weightedGraph = new Node[this.numberOfNodes];

        // Create all the nodes
        for (int i = 0; i < this.numberOfNodes; i++) {
            this.weightedGraph[i] = new Node();
        }
    }

    // Add one edge to the final result array
    public void addEdge(String sourceLabel, String destinationLabel, int weight) {

        // Create the source and destination which pint to null
        Node sourceNode = null;
        Node destinationNode = null;

        // Set the source and destination according to the input
        for (Node node : this.weightedGraph) {
            if (node.getLabel().contains(sourceLabel)) {
                sourceNode = node;
            }
            if (node.getLabel().contains(destinationLabel)) {
                destinationNode = node;
            }
        }

        // Adjust the edge using setters functions
        Edge e = new Edge();
        e.setWeight(weight);
        e.setSource(sourceNode);
        e.setDestination(destinationNode);
        // Add the edge to source node
        sourceNode.getEdges().add(e);
    }

    // Main function to construct a minimum spanning tree using Prim's algorithm
    public void minimumSpanningTree(String root) {
        // Create the final result tree using ArrayList
        ArrayList<Node> msTree = new ArrayList<>();

        // Root node is initialized to be null
        Node rootNode = null;

        for (Node vertex: this.weightedGraph) {
            // Set all keys to infinity
            vertex.setKey(Integer.MAX_VALUE);
            vertex.setDaddy(null);

            // Set the root to the first vertex
            if (vertex.getLabel().contains(root)) {
                rootNode = vertex;
            }
        }

        // Set the key of the root to 0
        rootNode.setKey(0);

        // Create a Priority Queue
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<>();

        // Add every vertex in the weightedGraph to the queue
        for (Node vertex: this.weightedGraph) {
            nodePriorityQueue.add(vertex);
        }

        // Set min to 0
        int min = 0;

        // Loop until there is no vertex left
        while (!nodePriorityQueue.isEmpty()) {

            // Check the first node in the queue
            Node u = nodePriorityQueue.peek();

            // Create a LinkedList of currentEdges that contains the Node
            LinkedList<Edge> uEdges= u.getEdges();

            // For all the edges in the array of current edges
            for (Edge e: uEdges) {
                Node v = e.getDestination();
                // Get the weight of the node
                int u_vWeight = e.getWeight();
                // If the node has the smallest weight and does not create a circle then add it to the queue
                if (nodePriorityQueue.contains(e.getDestination()) && u_vWeight < v.getKey()) {
                    nodePriorityQueue.remove(v);
                    v.setDaddy(u);
                    v.setKey(u_vWeight);
                    nodePriorityQueue.add(v);
                }
            }

            msTree.add(u);
            // Print the next vertex connected the next smallest weight
            System.out.println(u.getLabel());
            // Remove that edge and vertices from the list
            nodePriorityQueue.remove(u);
        }
    }

    public static void main(String[] args) {

        // Create a graph
        Prims graph = new Prims(9);
        // Create a String array of nodes
        String[] nodes = new String[9];
        nodes[0] = "a";
        nodes[1] = "b";
        nodes[2] = "c";
        nodes[3] = "d";
        nodes[4] = "e";
        nodes[5] = "f";
        nodes[6] = "g";
        nodes[7] = "h";
        nodes[8] = "i";
        int pos = 0;

        // Creat the weighted graph
        for (String s : nodes) {
            graph.weightedGraph[pos].setLabel(s);
            pos += 1;
        }

        // User input for the vertices and edges
        graph.addEdge("a", "b", 4);
        graph.addEdge("a", "h", 9);
        graph.addEdge("b", "h", 11);
        graph.addEdge("b", "c", 8);
        graph.addEdge("h", "i", 7);
        graph.addEdge("i", "g", 6);
        graph.addEdge("c", "f", 4);
        graph.addEdge("c", "d", 7);
        graph.addEdge("d", "e", 9);
        graph.addEdge("d", "f", 14);
        graph.addEdge("e", "f", 10);
        graph.addEdge("h", "g", 1);
        graph.addEdge("c", "i", 2);
        graph.addEdge("g", "f", 2);

        // Call the function that implements the Prim's algorithm
        graph.minimumSpanningTree("a");
    }
}

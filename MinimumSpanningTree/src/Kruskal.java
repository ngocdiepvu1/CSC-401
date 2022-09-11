import java.util.*;

public class Kruskal {

    // Class Edge to create an edge of a class which implements Comparable
    class Edge implements Comparable<Edge> {
        int src, des, weight;

        // Compare the weight of 2 edges
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }
    };

    // Class Subset for union
    class Subset {
        int parent;
        int rank;
    };


    // Initialize number of vertices and edges
    int vertices, edges;
    // Initialize arrays of vertices and edges
    Edge edgeArray[];

    // Constructor to create a graph
    Kruskal(int vertices, int edges) {
        this.vertices = vertices;
        this.edges = edges;
        edgeArray = new Edge[this.edges];

        // Create all the edges
        for (int i = 0; i < this.edges; i++) {
            edgeArray[i] = new Edge();
        }
    }

    int find(Subset subsets[], int x) {
        // Find root and make root as parent of i
        if (subsets[x].parent != x)
            subsets[x].parent = find(subsets, subsets[x].parent);

        return subsets[x].parent;
    }

    void union(Subset subsets[], int x, int y) {
        int xParent = find(subsets, x);
        int yParent = find(subsets, y);

        // Attach tree with a smaller rank to the tree with the higher rank
        if (subsets[xParent].rank < subsets[yParent].rank)
            subsets[xParent].parent = yParent;
        else if (subsets[xParent].rank > subsets[yParent].rank)
            subsets[yParent].parent = xParent;
        // If they have the same rank, make one as a root and increment its rank by one
        else {
            subsets[yParent].parent = xParent;
            subsets[xParent].rank++;
        }
    }

    // Main function to construct a minimum spanning tree using Kruskal's algorithm
    void KruskalMST() {

        // An array of Edges to store the edges in the final answer
        Edge resultArr[] = new Edge[vertices];

        // Index variable for resultArr[]
        int e = 0;

        // Allocate space for the resultArr[]
        for (int i = 0; i < vertices; i++) {
            resultArr[i] = new Edge();
        }

        // Sort the edges in non decreasing order
        Arrays.sort(edgeArray);

        // An array of Subsets to store the subsets
        Subset subsets[] = new Subset[vertices];

        // Allocate memory for all the subsets for every vertex
        for (int i = 0; i < subsets.length; i++) {
            subsets[i] = new Subset();
        }

        // Make every vertex a subset
        for (int i = 0; i < vertices; i++) {
            subsets[i].parent = i;
            subsets[i].rank = 0;
        }

        // Index variable to choose the next edge\
        int i = 0;

        // The number of edges in the resultArray cannot be greater than or equal to the number of vertices
        while (e < vertices - 1) {
            // Pick the edge with the smallest weight
            Edge nextEdge = edgeArray[i++];

            // Find the parents of x and y (2 vertices of the chosen edge)
            int x = find(subsets, nextEdge.src);
            int y = find(subsets, nextEdge.des);

            // If this edge doesn't make a cycle, add it to resultArr[] and increment e by 1
            if (x != y) {
                resultArr[e++] = nextEdge;
                union(subsets, x, y);
            }
            // Else, move on to the next edge
        }

        // Display the final result
        System.out.println("These edges are in the constructed Minimum Spanning Tree: ");
        int minCost = 0;

        // Print all edges in resultArr[]
        for (i = 0; i < e; ++i){
            System.out.println(resultArr[i].src + " --- " + resultArr[i].des + " === " + resultArr[i].weight);
            minCost += resultArr[i].weight;
        }

        // Print the cost of the constructed tree
        System.out.println("The cost of the tree is: " + minCost);
    }
    public static void main(String args[]) {
        // Create a tree as an example
        //           0
        //        /    \
        //      3       12
        //    /          \
        //  1             2
        //  |  \          |
        //  |     \       |
        //  5      3      7
        //  |        \    |
        //  |           \ |
        //  3------2------4
        int vertices = 5;
        int edges = 6;
        Kruskal graph = new Kruskal(vertices, edges);

        // Add edge 0-1
        graph.edgeArray[0].src = 0;
        graph.edgeArray[0].des = 1;
        graph.edgeArray[0].weight = 3;

        // Add edge 0-2
        graph.edgeArray[1].src = 0;
        graph.edgeArray[1].des = 2;
        graph.edgeArray[1].weight = 12;

        // Add edge 1-3
        graph.edgeArray[2].src = 1;
        graph.edgeArray[2].des = 3;
        graph.edgeArray[2].weight = 5;

        // Add edge 1-4
        graph.edgeArray[3].src = 1;
        graph.edgeArray[3].des = 4;
        graph.edgeArray[3].weight = 3;

        // Add edge 2-4
        graph.edgeArray[4].src = 2;
        graph.edgeArray[4].des = 4;
        graph.edgeArray[4].weight = 7;

        // Add edge 3-4
        graph.edgeArray[5].src = 3;
        graph.edgeArray[5].des = 4;
        graph.edgeArray[5].weight = 2;

        // Call the function that implements the Kruskal's algorithm
        graph.KruskalMST();
    }
}

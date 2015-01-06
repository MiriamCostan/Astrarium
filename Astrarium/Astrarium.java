import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Reads a graph from "puzzle.txt" (unless a different file path is specified)
 * and attempts to find a path that vists each edge exactly once.
 *
 * @author Andrei Muntean
 * @author Miriam Costan
 */
public class Astrarium
{
    private static Graph graph;
    private static ArrayList<Edge> visitedEdges;
    private static ArrayList<Edge> solution;
    private static boolean solutionWasFound;

    private static void outputResult()
    {
        if (solutionWasFound)
        {
            System.out.println("Solution:");

            for (Edge edge : solution)
            {
                System.out.println(edge);
            }
        }
        else
        {
            System.out.println("No solution has been found.");
        }
    }

    private static boolean isSolution()
    {
        return visitedEdges.size() == graph.countEdges();
    }

    private static void visitNeighbors(Vertex vertex)
    {
        // Determines whether every edge has been visited exactly once.
        if (isSolution())
        {
            solutionWasFound = true;

            // Stores the solution.
            solution = new ArrayList<Edge>(visitedEdges);
        }

        if (solutionWasFound)
        {
            // Stops the program once a solution has been found.
            return;
        }

        // Visits every adjacent edge.
        for (Edge edge : graph.getPathsFrom(vertex))
        {
            // Only visits the edges that have not been visited.
            if (!visitedEdges.contains(edge))
            {
                // Gets the vertices that form the edge.
                Vertex[] vertices = edge.getVertices();

                // Adds this edge to the array of visited edges.
                visitedEdges.add(edge);

                // Goes to the vertex of this edge that is not this vertex.
                if (vertices[0].equals(vertex))
                {
                    visitNeighbors(vertices[1]);
                }
                else
                {
                    visitNeighbors(vertices[0]);
                }

                // Removes this edge from the array of visited edges.
                visitedEdges.remove(edge);
            }
        }
    }

    private static void solvePuzzle()
    {
        // Tries to solve the puzzle by starting from every vertex.
        for (int index = 0; index < graph.countVertices(); ++index)
        {
            if (solutionWasFound)
            {
                // Stop searching. One solution is sufficient.
                return;
            }
            else
            {
                // Initializes the array that keeps track of visited edges.
                visitedEdges = new ArrayList<Edge>();

                // Recursively visits every neighbor until every possibile
                // path has been visited or a solution has been found.
                visitNeighbors(graph.getVertex(index));
            }
        }
    }

    public static void main(String[] args)
    {
        // Gets the file path. Default is "puzzle.txt".
        String path = args.length > 0 ? args[0] : "puzzle.txt";

        try
        {
            // Initializes the graph.
            graph = new Graph(path);
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("\"" + path + "\" was not found.");

            // Stops the program.
            return;
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());

            // Stops the program.
            return;
        }

        // Tries to solve the puzzle.
        solvePuzzle();

        // Outputs the solution (if one exists).
        outputResult();
    }
}
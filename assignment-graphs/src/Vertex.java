/**
 * Vertex class represents a single node in the graph.
 * Each vertex has a unique integer identifier.
 */
public class Vertex {

    private int id;

    /**
     * Constructs a Vertex with the given id.
     * @param id unique identifier for this vertex
     */
    public Vertex(int id) {
        this.id = id;
    }

    /**
     * Returns the unique identifier of this vertex.
     * @return vertex id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a string representation of this vertex.
     * @return formatted string showing vertex id
     */
    @Override
    public String toString() {
        return "Vertex(" + id + ")";
    }
}

import com.mathsystem.graphapi.AbstractEdge;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.graphapi.Vertex;
import com.mathsystem.plugin.GraphProperty;


import java.util.ArrayList;

import java.util.List;

public class EulerGraph implements GraphProperty {
    public void dfs(AbstractGraph G, Vertex v, boolean[] used) {
        used[G.getVertices().indexOf(v)] = true;
        for (AbstractEdge e:
             v.getEdgeList()) {
            Vertex u = e.other(v);
            if (!used[G.getVertices().indexOf(u)]) {
                dfs(G, u, used);
            }

        }
    }
    @Override
    public boolean execute(AbstractGraph abstractGraph) {
        List<Vertex> vertices = abstractGraph.getVertices();
        int verticesCount = vertices.size();
        List<AbstractEdge> edges = new ArrayList<>();
        for (Vertex v: // множество всех ребер
             vertices) {
            for (AbstractEdge e:
                 v.getEdgeList()) {
                if (!edges.contains(e)) edges.add(e);
            }

        }
        int[] degrees = new int[verticesCount];
        for (AbstractEdge e:
             edges) {
            Vertex v = e.either();
            Vertex u = e.other(v);
            degrees[vertices.indexOf(v)]++;
            degrees[vertices.indexOf(u)]++;
        }
        for (int degree:
             degrees) { //нечетная степень
            if (degree % 2 == 1) return false;
        }


        boolean[] used = new boolean[verticesCount];
        Vertex start = vertices.get(0);
        dfs(abstractGraph, start, used);

        for (boolean use:
             used) {
            if (!use) return false; //граф несвязный
        }

        return true; //все ок
    }
}

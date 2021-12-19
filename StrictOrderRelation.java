import com.mathsystem.graphapi.AbstractEdge;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.graphapi.Vertex;
import com.mathsystem.plugin.GraphProperty;

import java.util.List;

public class StrictOrderRelation implements GraphProperty {


    @Override
    public boolean execute(AbstractGraph abstractGraph) {
        boolean res = true;

        List<Vertex> vertexes = abstractGraph.getVertices();

        Vertex v, u;

        for (int i = 0; i < abstractGraph.getVertexCount(); i++) {
            v = vertexes.get(i);
            List<AbstractEdge> E = v.getEdgeList();
            for (int j = 0; j < E.size(); j++) {
                u = E.get(j).other(v);
                List<AbstractEdge> u_edges = u.getEdgeList();
                for (int k = 0; k < u_edges.size(); k++) { // СОСЕДИ U
                    Vertex w = u_edges.get(k).other(u);
                    if (v.equals(w)) return false;
                    boolean trans = false;
                    for (int l = 0; l < E.size(); l++) {
                        Vertex maybe_w = E.get(l).other(v);
                        if (maybe_w.equals(w)) trans = true;
                    }
                    if (!trans) return false;
                }
            }
        }

        return res;
    }
}

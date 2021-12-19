import com.mathsystem.graphapi.AbstractEdge;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.graphapi.Vertex;
import com.mathsystem.plugin.GraphProperty;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BiconnectedGraph implements GraphProperty {

    private void build(Vertex start, ArrayList<Vertex> parents, ArrayList<Vertex> children, ArrayList<AbstractEdge> blocked){
        Vertex other = parents.get(children.indexOf(start));
        if(other != null) {
            for(var edge: other.getEdgeList()) {
                if (edge.other(other) == start) {
                    blocked.add(edge);
                    break;
                }
                build(other, parents, children, blocked);
            }
        }
    }

    private boolean search(Vertex start, Vertex end, ArrayList<AbstractEdge> blocked)
    {
        LinkedList<Vertex> queue = new LinkedList<>();
        ArrayList<Vertex> viewed = new ArrayList<>();
        ArrayList<Vertex> viewedParents = new ArrayList<>();

        queue.addLast(start);
        viewed.add(start);
        viewedParents.add(null);

        while(!queue.isEmpty()) {
            var vert = queue.pop();

            for(var edge: vert.getEdgeList()) {
                if (!blocked.contains(edge)) {
                    if (edge.other(vert) == end) {
                        blocked.add(edge);
                        build(vert, viewedParents, viewed, blocked);
                        return true;
                    } else {
                        if (!viewed.contains(edge.other(vert))) {
                            viewedParents.add(vert);
                            viewed.add(edge.other(vert));
                            queue.add(edge.other(vert));
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean execute(AbstractGraph abstractGraph) {
        List<Vertex> list = abstractGraph.getVertices();
        int count = abstractGraph.getVertexCount();
        ArrayList<AbstractEdge> blocked = new ArrayList<>();

        for(int i = 0; i < count; i++)
            for(int j = i+1; j < count; j++) {
                blocked.clear();
                for(int k = 0; k < 2; k++) {
                    if (!search(list.get(i), list.get(j), blocked))
                        return false;
                }
            }
        return true;
    }
}
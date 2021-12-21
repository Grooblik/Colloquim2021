import com.mathsystem.entity.graph.Edge;
import com.mathsystem.graphapi.AbstractEdge;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.graphapi.Vertex;
import com.mathsystem.plugin.GraphCharacteristic;

import java.util.List;

import static java.lang.System.out;

public class ChromaticNumber implements GraphCharacteristic {
    @Override
    public Integer execute(AbstractGraph G) {

        List<Vertex> vertices = G.getVertices();
        int i = 0, j = 0;

        /*
        out.println("List of vertices: ");
        for (Vertex v:
             vertices) {
            out.print("deg(");
            out.print(v.getIndex());
            out.print(") = ");
            out.println(v.getEdgeList().size());
        }
        */

        // сортировка списка вершин в порядке невозрастания степени вершины
        i = 0;
        int goodPairsCounter = 0;
        do {
            if (vertices.get(i).getEdgeList().size() < vertices.get(i + 1).getEdgeList().size()) {
                Vertex tmp = vertices.get(i);
                Vertex i1 = vertices.get(i + 1);
                vertices.remove(i);
                vertices.add(i, i1);
                vertices.remove(i + 1);
                vertices.add(i + 1, tmp);
                goodPairsCounter = 0;
            } else {
                goodPairsCounter++;
            }
            i++;
            if (i == G.getVertexCount() - 1) {
                i = 0;
            }
        } while (goodPairsCounter != G.getVertexCount() - 1);

        /*
        out.println("Sorted list of vertices: ");
        for (Vertex v:
                vertices) {
            out.print("deg(");
            out.print(v.getIndex());
            out.print(") = ");
            out.println(v.getEdgeList().size());
        }
        */

        // заполнение матрицы смежности
        int[][] adjacency_matrix = new int[G.getVertexCount()][G.getVertexCount()];
        for (Vertex v: vertices) {
            for (AbstractEdge e: v.getEdgeList()) {
                adjacency_matrix[v.getIndex()][e.other(v).getIndex()] = 1;
            }
        }

        /*
        out.println("Adjacency list: ");
        for (Vertex v:
                vertices) {
            out.print(v.getIndex());
            out.print(": ");
            for (AbstractEdge e:
                    v.getEdgeList()) {
                out.print(e.other(v).getIndex());
                out.print(" ");
            }
            out.println();
        }
        */

        /*
        out.println("Adjacency matrix: ");
        for (i = 0; i < G.getVertexCount(); i++) {
            for (j = 0; j < G.getVertexCount(); j++) {
                out.print(adjacency_matrix[i][j]);
                out.print(" ");
            }
            out.println();
        }
        */

        int chromatic_number = 0;
        int count_colored_vertices = 0;
        int[] color = new int[G.getVertexCount()];

        // непосредственно раскраска графа
        for (Vertex v : vertices) {
            i = v.getIndex();
            // берем неокрашенную вершину с наивысшей степенью
            if (color[i] == 0) {
                // красим её; увеличиваем хроматическое число
                chromatic_number++;
                color[i] = chromatic_number;
                count_colored_vertices++;

                // красим все неокрашенные вершины, которые не смежны текущей, в текущий свет
                // на самом деле можно и просто помечать факт окраски, но с раскраской нагляднее при отладке
                for (Vertex u : vertices) {
                    j = u.getIndex();
                    if (adjacency_matrix[i][j] == 0 && color[j] == 0) {
                        color[j] = chromatic_number;
                        count_colored_vertices++;
                    }
                    // каждый раз, окрашивая вершину, увеличиваем счетчик на 1
                    // когда все вершины окрашены, алгоритм закончил свою работу
                    if (count_colored_vertices == G.getVertexCount()) {
                        break;
                    }
                }
            }
        }

        return chromatic_number;
    }
}
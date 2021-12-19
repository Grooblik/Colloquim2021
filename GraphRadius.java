import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.graphapi.Vertex;
import com.mathsystem.plugin.GraphCharacteristic;

import java.util.LinkedList;
import java.util.List;

public class GraphRadius implements GraphCharacteristic {


    private int find_len(Vertex u, Vertex v, AbstractGraph G) {
        int len = 0;

        LinkedList<Vertex> Queue = new LinkedList<>();
        List<Vertex> verteces = G.getVertices();
        boolean[] used = new boolean[G.getVertexCount()];


        Queue.add(u);




        while (!Queue.isEmpty()) { // очередь не пуста
            int Qsize = Queue.size();
            for (int k = 0; k < Qsize; k++) { // обходим весь уровень очереди

                Vertex cur = Queue.remove();

                used[verteces.indexOf(cur)] = true; // добавляем в список осмотренных

                if (cur.equals(v)) return len; // если найден путь, вернем расстояние
                else {
                    int curEdgeCount = cur.getEdgeList().size();

                    for (int i = 0; i < curEdgeCount; i++) { // проходимся по всем соседям cur

                        Vertex curNeighbour = cur.getEdgeList().get(i).other(cur); // соседняя вершина

                        if (!used[verteces.indexOf(curNeighbour)]) Queue.add(curNeighbour); // Добавляем неотсмотренную вершину
                    }
                }
            }
            len++;
        }



        return -1;
    }


    @Override
    public Integer execute(AbstractGraph abstractGraph) {



        int radius = Integer.MAX_VALUE;

        List<Vertex> verteces = abstractGraph.getVertices();

        int cur_len, max_len;



        Vertex u, v;
        for (int i = 0; i < abstractGraph.getVertexCount(); ++i) {
            u = verteces.get(i);
            max_len = 0;
            for (int j = 0; j < abstractGraph.getVertexCount(); ++j) {
                v = verteces.get(j);
                cur_len = find_len(u, v, abstractGraph);

                if (cur_len != 0 && max_len < cur_len) {
                    max_len = cur_len;
                }
            }
            if (radius > max_len) radius = max_len;
        }





        return radius;
    }
}

import com.mathsystem.graphapi.AbstractEdge;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.graphapi.Vertex;
import com.mathsystem.plugin.GraphCharacteristic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArticulationPoints implements GraphCharacteristic{
    int count;
    int time;
    boolean[] visited;
    int[] up;
    int[] tin;
    List<ArrayList<Integer>> obj;
    ArrayList<Integer> res;

    @Override
    public Integer execute(AbstractGraph abstractGraph) {
        count = 0;
        int n = abstractGraph.getVertexCount();
        visited = new boolean[n];
        Arrays.fill(visited, false);
        up = new int[n];
        Arrays.fill(up, 0);
        tin = new int[n];
        Arrays.fill(tin, 0);
        res = new ArrayList<Integer>();
        adjList(abstractGraph);
        for (Vertex v: abstractGraph.getVertices()){
            if (!visited[v.getIndex()]){
                dfs(v.getIndex(), -1);
            }
        }
        return count;
    }
    private void dfs(int v, int p){
        tin[v] = up[v] = ++time;
        visited[v] = true;
        int k = 0;
        for (Integer i : obj.get(v)){
            if (i == p) continue;
            if (visited[i]) up[v] = Math.min(up[v], tin[i]);
            else{
                dfs(i, v);
                k++;
                up[v] = Math.min(up[v], up[i]);
                if (p != -1 && up[i] >= tin[v]) {
                    if (!res.contains(v)){
                        count++;
                        res.add(v);
                    }
                }
            }
        }
        if (p == -1 && k >= 2) {
            if (!res.contains(v)){
                count++;
                res.add(v);
            }
        }
    }

    private void adjList(AbstractGraph abstractGraph){
        int n = abstractGraph.getVertexCount();
        obj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            obj.add(new ArrayList<Integer>());
        }
        for (Vertex v: abstractGraph.getVertices()){
            for (AbstractEdge e: v.getEdgeList()){
                Vertex cur = e.other(v);
                int cur_i = cur.getIndex();
                int ver = v.getIndex();
                ArrayList<Integer> v1 = obj.get(ver);
                ArrayList<Integer> v2 = obj.get(cur_i);
                v1.add(cur_i);
                v2.add(ver);
            }
        }
    }
}
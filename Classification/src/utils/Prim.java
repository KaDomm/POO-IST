package utils;

import java.util.ArrayList;
import java.util.List;

import entities.Alpha;

/**
 * @author USER-Admin
 *
 */
public class Prim {

	// A utility function to find the vertex with maximum key
	// value, from the set of vertices not yet included in MST
	/**
	 * @param key
	 * @param mstSet
	 * @param size
	 * @return
	 */
	static int maxKey(double key[], Boolean mstSet[], int size) {
		// Initialize max value
		double max = Double.MAX_VALUE * (-1);
		int max_index = -1;

		for (int v = 0; v < size; v++)
			if (mstSet[v] == false && key[v] >= max) {
				max = key[v];
				max_index = v;
			}

		return max_index;
	}

	/**
	 * @param graph
	 * @return
	 */
	static int[] primMST(double[][] graph) {
		// Array to store constructed MST
		int parent[] = new int[graph.length];

		// Key values used to pick minimum weight edge in cut
		double key[] = new double[graph.length];

		// To represent set of vertices not yet included in MST
		Boolean mstSet[] = new Boolean[graph.length];

		// Initialize all keys as -INFINITE
		for (int i = 0; i < graph.length; i++) {
			key[i] = Double.MAX_VALUE * (-1);
			mstSet[i] = false;
		}

		// Always include first 1st vertex in MST.
		key[0] = 0.0; // Make key 0 so that this vertex is
		// picked as first vertex
		parent[0] = -1; // First node is always root of MST

		// The MST will have V vertices
		for (int count = 0; count < graph.length - 1; count++) {
			// Pick thd maximum key vertex from the set of vertices
			// not yet included in MST
			int u = maxKey(key, mstSet, graph.length);

			// Add the picked vertex to the MST Set
			mstSet[u] = true;

			// Update key value and parent index of the adjacent
			// vertices of the picked vertex. Consider only those
			// vertices which are not yet included in MST
			for (int v = 0; v < graph.length; v++)

				// graph[u][v] is non zero only for adjacent vertices of m
				// mstSet[v] is false for vertices not yet included in MST
				// Update the key only if graph[u][v] is greater than key[v]
				if (graph[u][v] != 0 && mstSet[v] == false && graph[u][v] >= key[v]) {
					parent[v] = u;
					key[v] = graph[u][v];
				}
		}

		return parent;
	}

	/**
	 * @param size       size
	 * @param listAlphas listAlphas
	 * @return parentList
	 */
	public static List<Alpha> generateTree(int size, List<Alpha> listAlphas) {

		double[][] graph = grafFromAlphas(size, listAlphas);

		// this will save information about the nodes that belong to our tree
		int[] parents = primMST(graph);

		listAlphas = new ArrayList<>();

		for (int i = 0; i < parents.length; i++) {
			Alpha alpha = new Alpha(i, parents[i]);
			listAlphas.add(alpha);
		}
		return listAlphas;
	}

	/**
	 * @param size
	 * @param listAlphas
	 * @return
	 */
	protected static double[][] grafFromAlphas(int size, List<Alpha> listAlphas) {

		double[][] graph = new double[size][size];

		for (Alpha alpha : listAlphas) {
			graph[alpha.getChildIndex()][alpha.getParentIndex()] = alpha.getAlpha();
			graph[alpha.getParentIndex()][alpha.getChildIndex()] = alpha.getAlpha();
		}

		return graph;
	}

}

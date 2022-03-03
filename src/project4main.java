import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class project4main {

	public static void main(String[] args) throws FileNotFoundException {
		
		// TODO Auto-generated method stub
		
		Scanner input = new Scanner(new File(args[0]));
		
		// Taking vehicle data
		int numGreenTrains = input.nextInt();
		LinkedList<Integer> greenTrainsList = new LinkedList<Integer>();
		for(int i = 0; i < numGreenTrains; i++) {
			int capacity = input.nextInt();
			if(capacity!=0){
				greenTrainsList.add(capacity);
			}
		}
		
		int numRedTrains = input.nextInt();
		LinkedList<Integer> redTrainsList = new LinkedList<Integer>();
		for(int i = 0; i < numRedTrains; i++) {
			int capacity = input.nextInt();
			if(capacity!=0){
				redTrainsList.add(capacity);
			}
		}
		
		int numGreenDeers = input.nextInt();
		LinkedList<Integer> greenDeersList = new LinkedList<Integer>();
		for(int i = 0; i < numGreenDeers; i++) {
			int capacity = input.nextInt();
			if(capacity!=0){
				greenDeersList.add(capacity);
			}
		}
		
		int numRedDeers = input.nextInt();
		LinkedList<Integer> redDeersList = new LinkedList<Integer>();
		for(int i = 0; i < numRedDeers; i++) {
			int capacity = input.nextInt();
			if(capacity!=0){
				redDeersList.add(capacity);
			}
		}
		
		// Taking bag data
		LinkedList<Bag> bags = new LinkedList<Bag>();
		bags.add(new Bag("b", 0));
		bags.add(new Bag("c", 0));
		bags.add(new Bag("d", 0));
		bags.add(new Bag("e", 0));
		bags.add(new Bag("bd", 0));
		bags.add(new Bag("be", 0));
		bags.add(new Bag("cd", 0));
		bags.add(new Bag("ce", 0));
		
		//int totalItems = 0;
		int numBags = input.nextInt();
		for(int i = 0; i< numBags; i++) {
			String type = input.next();
			int size = input.nextInt();
			//totalItems += size;
			if(size != 0) {
				if(!type.contains("a")) {
					Iterator<Bag> iter = bags.iterator();
					while(iter.hasNext()) {
						Bag bagFromList = iter.next();
						if(type.equals(bagFromList.type)) {
							bagFromList.size += size;
							break;
						}
					}
				} else {
					bags.add(new Bag(type, size));
				}
			}
		}
		
		// Removing 0 size bags (for first 8 combined bags)
		Iterator<Bag> zeroChecker = bags.iterator();
		for(int i = 0; i < 8; i++) {
			if(zeroChecker.next().size == 0)
				zeroChecker.remove();
		}
		
		// Constructing the graph
		int totalVertexNum = bags.size() + greenTrainsList.size() + redTrainsList.size() + greenDeersList.size() + redDeersList.size() + 2;
		
		int[][] residualGraph = new int[totalVertexNum][totalVertexNum];
		int greenTrainStart = bags.size() + 1;
		int redTrainStart = greenTrainStart + greenTrainsList.size();
		int greenDeerStart = redTrainStart + redTrainsList.size();
		int redDeerStart = greenDeerStart + greenDeersList.size();
		
		int verticeIndex = 1;
		int totalBagSize = 0;
		while(!bags.isEmpty()) {
			Bag current = bags.poll();
			String bagType = current.type;
			int bagSize = current.size;
			totalBagSize += bagSize;
			residualGraph[0][verticeIndex] = bagSize;
			
			if(bagType.equals("b")) {
				
				for(int i = greenTrainStart; i < redTrainStart; i++) {
					residualGraph[verticeIndex][i] = bagSize;
				}
				for(int i = greenDeerStart; i < redDeerStart; i++) {
					residualGraph[verticeIndex][i] = bagSize;
				}
				
			} else if(bagType.equals("c")) {
				
				for(int i = redTrainStart; i < greenDeerStart; i++) {
					residualGraph[verticeIndex][i] = bagSize;
				}
				for(int i = redDeerStart; i < totalVertexNum - 1; i++) {
					residualGraph[verticeIndex][i] = bagSize;
				}
				
			} else if(bagType.equals("d")) {

				for(int i = greenTrainStart; i < greenDeerStart; i++) {
					residualGraph[verticeIndex][i] = bagSize;
				}
				
			} else if(bagType.equals("e")) {

				for(int i = greenDeerStart; i < totalVertexNum - 1; i++) {
					residualGraph[verticeIndex][i] = bagSize;
				}
				
			} else if(bagType.equals("bd")) {
				
				for(int i = greenTrainStart; i < redTrainStart; i++) {
					residualGraph[verticeIndex][i] = bagSize;
				}
				
			} else if(bagType.equals("be")) {
				
				for(int i = greenDeerStart; i < redDeerStart; i++) {
					residualGraph[verticeIndex][i] = bagSize;
				}
				
			} else if(bagType.equals("cd")) {
				
				for(int i = redTrainStart; i < greenDeerStart; i++) {
					residualGraph[verticeIndex][i] = bagSize;
				}
				
			} else if(bagType.equals("ce")){

				for(int i = redDeerStart; i < totalVertexNum - 1; i++) {
					residualGraph[verticeIndex][i] = bagSize;
				}
				
			} else if(bagType.equals("a")) {
				
				for(int i = greenTrainStart; i < totalVertexNum - 1; i++) {
					residualGraph[verticeIndex][i] = 1;
				}
				
			} else if(bagType.equals("ab")) {

				for(int i = greenTrainStart; i < redTrainStart; i++) {
					residualGraph[verticeIndex][i] = 1;
				}
				for(int i = greenDeerStart; i < redDeerStart; i++) {
					residualGraph[verticeIndex][i] = 1;
				}
				
			} else if(bagType.equals("ac")) {

				for(int i = redTrainStart; i < greenDeerStart; i++) {
					residualGraph[verticeIndex][i] = 1;
				}
				for(int i = redDeerStart; i < totalVertexNum - 1; i++) {
					residualGraph[verticeIndex][i] = 1;
				}
				
			} else if(bagType.equals("ad")) {
				
				for(int i = greenTrainStart; i < greenDeerStart; i++) {
					residualGraph[verticeIndex][i] = 1;
				}
				
			} else if(bagType.equals("ae")) {
				
				for(int i = greenDeerStart; i < totalVertexNum - 1; i++) {
					residualGraph[verticeIndex][i] = 1;
				}
				
			} else if(bagType.equals("abd")) {
				
				for(int i = greenTrainStart; i < redTrainStart; i++) {
					residualGraph[verticeIndex][i] = 1;
				}
				
			} else if(bagType.equals("abe")) {

				for(int i = greenDeerStart; i < redDeerStart; i++) {
					residualGraph[verticeIndex][i] = 1;
				}
				
			} else if(bagType.equals("acd")) {

				for(int i = redTrainStart; i < greenDeerStart; i++) {
					residualGraph[verticeIndex][i] = 1;
				}
				
			} else {

				for(int i = redDeerStart; i < totalVertexNum - 1; i++) {
					residualGraph[verticeIndex][i] = 1;
				}
				
			}
			
			verticeIndex++;
		}
		
		for(int i = greenTrainStart; i < redTrainStart; i++) {
			residualGraph[i][totalVertexNum - 1] = greenTrainsList.poll();
		}
		
		for(int i = redTrainStart; i < greenDeerStart; i++) {
			residualGraph[i][totalVertexNum - 1] = redTrainsList.poll();
		}
		
		for(int i = greenDeerStart; i < redDeerStart; i++) {
			residualGraph[i][totalVertexNum - 1] = greenDeersList.poll();
		}
		
		for(int i = redDeerStart; i < totalVertexNum - 1; i++) {
			residualGraph[i][totalVertexNum - 1] = redDeersList.poll();
		}
		
		// Dinic's algorithm
		int[] levelGraph = BFS(residualGraph);
		while(levelGraph[totalVertexNum - 1] != 0) {
			int[] parents = DFS(residualGraph, levelGraph);
			
			while(parents[parents.length - 1] != -1) {
				
				// Find bottleNeck
				int bottleNeck = Integer.MAX_VALUE;
				int parent = parents[parents.length - 1];
				int self = parents.length - 1;
				while(parent != -1) {
					bottleNeck = Math.min(residualGraph[parent][self], bottleNeck);
					self = parent;
					parent = parents[self];
				}
				
				// Update the residual graph
				parent = parents[parents.length - 1];
				self = parents.length - 1;
				while(parent != -1) {
					residualGraph[parent][self] -= bottleNeck;
					residualGraph[self][parent] += bottleNeck;
					self = parent;
					parent = parents[self];
				}
				
				parents = DFS(residualGraph, levelGraph);
			}
			
			levelGraph = BFS(residualGraph);
		}
		
		int maxFlow2 = 0;
		for(int i = 0; i < residualGraph.length; i++)
			maxFlow2 += residualGraph[residualGraph.length - 1][i];
		
		PrintStream output = new PrintStream(new File(args[1]));
		
		output.print(totalBagSize - maxFlow2);
		
	}
	
	public static int[] BFS(int[][] graph) {
		
		
		int[] levelGraph = new int[graph.length];
		boolean[] isVisited = new boolean[graph.length];
		LinkedList<Integer> queue = new LinkedList<Integer>();
		
		queue.add(0);
		isVisited[0] = true;
		while(!queue.isEmpty()) {
			int current = queue.poll();
			for(int i = 0; i < graph.length; i++) {
				if(graph[current][i] != 0) {
					if(!isVisited[i]) {
						levelGraph[i] = levelGraph[current] + 1;
						queue.add(i);
						isVisited[i] = true;
					}
				}
			}
		}
		
		return levelGraph;
	}
	
	public static int[] DFS(int[][] graph, int[] levelGraph){ 
		
		int[] parents = new int[graph.length];
		for(int i = 0; i < parents.length; i++)
			parents[i] = -1;
		boolean[] isVisited = new boolean[graph.length];
		Stack<Integer> stack = new Stack<Integer>();
		
		stack.push(0);
		isVisited[0] = true;
		while(!stack.isEmpty()) {
			int current = stack.pop();
			for(int i = 0; i < graph.length; i++) {
				if(graph[current][i] != 0) {
					if(!isVisited[i]) {
						if(levelGraph[current] < levelGraph[i]) {
							stack.push(i);
							isVisited[i] = true;
							parents[i] = current;
							
							if(i == graph.length - 1) {
								return parents;
							}
							
						}
					}
				}
			}
		}
		
		return parents;
		
	}

}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

class Pos{
	int row;
	int col;
	
	Pos(int row,int col){
		this.row = row;
		this.col = col;
	}
}


class Node_SA{
	

	public Node_SA(char[][] zoo, ArrayList<Pos> lizardPositions, int n, int p,int currentConflicts) {
		super();
		this.zoo = zoo;
		this.lizardPositions = lizardPositions;
		this.n = n;
		this.p = p;
		this.currentConflicts = currentConflicts;
	}

	char [][] zoo;
	ArrayList<Pos>lizardPositions = new ArrayList<Pos>();
	int n;
	int p;
	int currentConflicts=0;
	
	public Node_SA(char[][] zoo, int n, int p) {
		super();
		this.zoo = zoo;
		this.n = n;
		this.p = p;
	}
	
	void placeLizardsRandomly(){
		
		// assuming it is possible to place the lizards -- this has been handled before calling this.
		int lizardsLeft = p;
		while(lizardsLeft > 0){
			
			// place a lizard randomly 
			int random_r = (int)Math.floor((Math.random()*n));
			int random_c = (int)Math.floor((Math.random()*n));
			
			if(random_r >=0 && random_r< n && random_c>=0 && random_c < n && 
					zoo[random_r][random_c]!='2' && zoo[random_r][random_c]!='1' ){
				// place the lizard in zoo and note its position
				zoo[random_r][random_c] = '1';
				Pos pos = new Pos(random_r,random_c);
				currentConflicts+=numOfConflictsAt(pos,zoo);
				this.lizardPositions.add(pos);
				lizardsLeft --;	
			}
		}
		
	}
	
	static int numOfConflictsAt(Pos pos,char[][] zoo){
		
		int n = zoo.length;
		int res = 0;
		if(pos.row>=0 && pos.col >=0 && pos.row<n && pos.col<n){
			
			int leftConflicts = 0;
			int j = pos.col-1;
			while(j>=0 && zoo[pos.row][j]!='2'){
				if(zoo[pos.row][j]=='1'){
					leftConflicts++;
				}
				j--;
			}
			
			int rightConflicts = 0;
			j=pos.col+1;
			while(j<n && zoo[pos.row][j]!='2'){
				if(zoo[pos.row][j]=='1'){
					rightConflicts++;
				}
				j++;
			}
			int upConflicts = 0;
			int i = pos.row-1;
			while(i>=0 && zoo[i][pos.col] !='2'){
				if(zoo[i][pos.col]=='1'){
					upConflicts++;
				}
				i--;
			}
			
			int downConflicts = 0;
			i = pos.row+1;
			while(i<n && zoo[i][pos.col] !='2'){
				if(zoo[i][pos.col]=='1'){
					downConflicts++;
				}
				i++;
			}
			
			int topLeftConflicts = 0;
			i = pos.row-1;
			j= pos.col -1;
			while(i>=0 && j>=0 && zoo[i][j] !='2' ){
				if(zoo[i][j]=='1'){
					topLeftConflicts++;
				}
				i--;
				j--;
			}
			
			int bottomLeftConflicts = 0;
			i = pos.row+1;
			j= pos.col -1;
			while(i<n && j>=0 && zoo[i][j] !='2' ){
				if(zoo[i][j]=='1'){
					topLeftConflicts++;
				}
				i++;
				j--;
			}
			
			int topRightConflicts = 0;
			i = pos.row-1;
			j= pos.col +1;
			while(i>=0 && j<n && zoo[i][j] !='2' ){
				if(zoo[i][j]=='1'){
					topLeftConflicts++;
				}
				i--;
				j++;
			}
			
			
			int bottomRightConflicts = 0;
			i = pos.row+1;
			j= pos.col +1;
			while(i<n && j<n && zoo[i][j] !='2' ){
				if(zoo[i][j]=='1'){
					topLeftConflicts++;
				}
				i++;
				j++;
			}
			
			res = upConflicts+downConflicts+leftConflicts+
					rightConflicts+topLeftConflicts+topRightConflicts+
					bottomLeftConflicts+bottomRightConflicts;
			
		}
		return res;
	}
	

	void display(){
		System.out.println("--------------------------------------------------");
		
			System.out.println("zoo state ");
			
			for(int i=0;i<zoo.length;i++){
				for(int j=0;j<zoo.length;j++){
						System.out.print(zoo[i][j]+" ");
				}
				System.out.println();
			}
		
			System.out.println("lizards placement");
			
			for(Pos pos : lizardPositions){
				System.out.print("("+pos.row+","+pos.col+") ");
			}
			
			System.out.println("current conflicts "+ currentConflicts);
		System.out.println("--------------------------------------------------");
	}
	
}


class Node{
	
	int lizardsLeft;
	int openPositionsNextCol;
	int nextCol;
	char [][] zoo;
	
	Node(int nextCol, int open,int lizardsLeft,char [][] zoo){
		this.nextCol = nextCol;
		this.lizardsLeft = lizardsLeft;
		this.openPositionsNextCol = open;
		this.zoo = zoo;
	}
	
	void display(){
		System.out.println("--------------------------------------------------");
		
			System.out.println("lizards left "+lizardsLeft);
			System.out.println("next col "+nextCol);
			System.out.println("openPositionsNextCol "+openPositionsNextCol);
			System.out.println("zoo state ");
			
			for(int i=0;i<zoo.length;i++){
				for(int j=0;j<zoo.length;j++){
						System.out.print(zoo[i][j]+" ");
				}
				System.out.println();
			}
		
		System.out.println("--------------------------------------------------");
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		    // self check
		    if (this == o)
		        return true;
		    // null check
		    if (o == null)
		        return false;
		    // type check and cast
		    if (!(o instanceof Node))
		        return false;
		    Node node = (Node) o;
		    // field comparison
		    return (lizardsLeft == node.lizardsLeft) && (openPositionsNextCol == node.openPositionsNextCol) && (nextCol == node.nextCol) && 
		            Arrays.deepEquals(zoo, node.zoo);
		
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int prime = 31;
		int result = 1;
		result = prime * result + lizardsLeft;
		result = prime * result + openPositionsNextCol;
		result = prime * result + nextCol;
	
		int hash = java.util.Arrays.deepHashCode( zoo );
		return result+hash;
	}
};

class Result {
	String result;
	char [][] solution;
	
	Result(String result, char[][] solution){
		this.result = result;
		this.solution= solution;
	}
	
	Result(String result){
		this.result = result;
	}
}


public class homework {
	
	static double schedule(int k){
		long T0 = 100;
		if(k == 0){
			return T0;
		} else {
			return (1 / Math.log(k));
		}
	}
	
	
	static Result SA(char[][] zoo,int n,int p,long endTime){
		
		Node_SA currNode= new Node_SA(zoo, n, p);
		currNode.placeLizardsRandomly();
		int k=0;
		while(true){
			
			double temperature = schedule(k);
			if(System.currentTimeMillis() >= endTime || temperature == 0 || currNode.currentConflicts == 0){
				// terminate and return the current state
				
				if(currNode.currentConflicts==0){
					// goal state return pass
					Result res = new Result("OK",currNode.zoo);
					return res;
					
				}else{
					// return failure
					Result res = new Result("FAIL");
					return res;
				}
			}
			
			int randomLizard = (int)Math.floor(Math.random()*p);
			Pos oldPos = currNode.lizardPositions.get(randomLizard); 
			int conflictsAtOldPosition = Node_SA.numOfConflictsAt(oldPos,currNode.zoo);
			
			
				int random_r = (int)Math.floor((Math.random()*n));
				int random_c = (int)Math.floor((Math.random()*n));
				while(System.currentTimeMillis() < endTime && 
						(zoo[random_r][random_c]=='2' || zoo[random_r][random_c]=='1' )){
				// while there is time try to find a successor and return it
					random_r = (int)Math.floor((Math.random()*n));
					random_c = (int)Math.floor((Math.random()*n));
				}
				
				if(System.currentTimeMillis() >= endTime){
					if(currNode.currentConflicts==0){
						// goal state return pass
						Result res = new Result("OK",currNode.zoo);
						return res;
						
					}else{
						// return failure
						Result res = new Result("FAIL");
						return res;
					}
				}
				
				
					// return the successor and calculate the enery diff
					Pos newPos = new Pos(random_r,random_c);
					// temporarily moving the lizard to new position. We will revert this if the
					// new state is not acceptable
					zoo[oldPos.row][oldPos.col] = '0';
					zoo[newPos.row][newPos.col] ='1';
					int newNumOfConflicts = currNode.currentConflicts-conflictsAtOldPosition+
							Node_SA.numOfConflictsAt(newPos,zoo);
					int eneryDiff =  newNumOfConflicts- currNode.currentConflicts;
					
					
					if(eneryDiff < 0){
						// accept the transition
						currNode.lizardPositions.set(randomLizard, newPos);
						currNode.currentConflicts = newNumOfConflicts;
						
					} else {
						double prob = Math.exp((-1*eneryDiff)/temperature);
						boolean accept = (Math.random() < prob)?true:false;
					if(accept){
						currNode.lizardPositions.set(randomLizard, newPos);
						currNode.currentConflicts = newNumOfConflicts;
					} else {
						// revert
						zoo[oldPos.row][oldPos.col] = '1';
						zoo[newPos.row][newPos.col] ='0';
					}
				}
			
			k++;
		}
	}
	
	
	static Result DFS(char [][] zoo,int n,int p,int [] numTrees){
		
		char[][] zooInitial = zoo.clone();
		Node initialNode = new Node(0,numTrees[0]+1,p,zooInitial);
		
		Stack<Node> stack = new Stack<Node>();
		stack.push(initialNode);
		
		
		
		
		while(true){
			if(stack.isEmpty()){
				Result res = new Result("FAIL");
				return res;
			}
			
			Node currNode = stack.pop();
			
			if(currNode.lizardsLeft == 0){
				// this is the goal state 
				
				Result res = new Result("OK",currNode.zoo);
				return res;
			}
			// find and enque nodes which by puttin lizards in the same level
			
			if(currNode.nextCol<n){
			
				
						int col=currNode.nextCol;
						
						// in case we dont fnd any valid row or we want to consider filling the column after and skipping this col
						
						char [][] zooNewNextLevel = new  char[n][];
						for(int i = 0; i < n; i++){
							zooNewNextLevel[i] = currNode.zoo[i].clone();
						}
					
						// creating a new node with this state and adding it to the stack;
						
						int nextColumnNew = currNode.nextCol+1;
						int openPositionsNextColumnNew = nextColumnNew < n ? numTrees[nextColumnNew]+1:0;
						Node childNextLevel = new Node( nextColumnNew,openPositionsNextColumnNew, currNode.lizardsLeft,
								zooNewNextLevel);
						
						stack.push(childNextLevel);

						// case 1 trying to fill this column
						for(int row =0; row< n ;row++){
							// check if isValid
							if(isValid(row,col,currNode.zoo,n) ){
													
									char [][] zooNew = new char[n][];
									for(int i = 0; i < n; i++){
										zooNew[i] = currNode.zoo[i].clone();
									}
									
									zooNew[row][col] = '1';
									
									//update next col
									int nextColNew;
									int openPositionsNextColNew;
									if(currNode.openPositionsNextCol == 1){
										nextColNew = currNode.nextCol+1;
										openPositionsNextColNew = nextColNew < n ?numTrees[nextColNew]+1:0;
									} else {
										nextColNew = currNode.nextCol;
										openPositionsNextColNew = currNode.openPositionsNextCol-1;
									}
									//update lizards remaining
									int lizardsLeftNew = currNode.lizardsLeft-1;
									
									// creating a new node with this state and adding it to the back of the queue;
									Node child = new Node( nextColNew, openPositionsNextColNew, lizardsLeftNew,
											 zooNew);
										
										stack.push(child);
										
							}
						}
			}
					
		}

}
	
	static Result BFS(char [][] zoo,int n,int p,int numTrees[]){
		
					char[][] zooInitial = zoo.clone();
					Node initialNode = new Node(0,numTrees[0]+1,p,zooInitial);
					
					LinkedList<Node> queue = new LinkedList<Node>();
					queue.add(initialNode);
					
					
					while(true){
						if(queue.isEmpty()){
							//System.out.println("queue is empty");
							Result res = new Result("FAIL");
							return res;
						}
						
						Node currNode = queue.removeFirst();
						
						if(currNode.lizardsLeft == 0){
							// this is the goal state 
							
							Result res = new Result("OK",currNode.zoo);
							return res;
						}
						// find and enque nodes which by puttin lizards in the same level
						
						if(currNode.nextCol<n){
							
									int col=currNode.nextCol;
										
									// case 1 trying to fill this column
									for(int row =0; row< n ;row++){
										// check if isValid
										if(isValid(row,col,currNode.zoo,n) ){
																
												char [][] zooNew = new char[n][];
												for(int i = 0; i < n; i++){
													zooNew[i] = currNode.zoo[i].clone();
												}
												
												zooNew[row][col] = '1';
												
												//update next col
												int nextColNew;
												int openPositionsNextColNew;
												if(currNode.openPositionsNextCol == 1){
													nextColNew = currNode.nextCol+1;
													openPositionsNextColNew = nextColNew < n ?numTrees[nextColNew]+1:0;
												} else {
													nextColNew = currNode.nextCol;
													openPositionsNextColNew = currNode.openPositionsNextCol-1;
												}
												//update lizards remaining
												int lizardsLeftNew = currNode.lizardsLeft-1;
												
												// creating a new node with this state and adding it to the back of the queue;
												Node child = new Node( nextColNew, openPositionsNextColNew, lizardsLeftNew,
														 zooNew);
													
													queue.add(child);
													
										}
									}
									
									// in case we dont fnd any valid row or we want to consider filling the column after and skipping this col
									
									char [][] zooNewNextLevel = new char[n][];
									for(int i = 0; i < n; i++){
										zooNewNextLevel[i] = currNode.zoo[i].clone();
									}
								
									// creating a new node with this state and adding it to the back of the queue;
									
									int nextColNew = currNode.nextCol+1;
									int openPositionsNextColNew = nextColNew < n ? numTrees[nextColNew]+1:0;
									Node childNextLevel = new Node( nextColNew,openPositionsNextColNew, currNode.lizardsLeft,
											zooNewNextLevel);
									queue.add(childNextLevel);
									
						}
								
					}
		
	}
	public static void main(String args[]){
		
		long start = System.currentTimeMillis();
		long end = start + 4*60*1000; // 60 seconds * 1000 ms/sec
		
	
		File dir = new File(".");
		File fin = null;
		int n = 0;
		int p = 0;
		String algo=null;
		char [][] zoo = null;
		
		
		long totalTrees = 0;
		int numTrees[]=null;
		try {
			fin = new File("input.txt");
			if(fin!=null){
				
				BufferedReader br;
				br = new BufferedReader(new FileReader(fin));
				String line = null;
				
				 algo = br.readLine().trim();
				 n = Integer.parseInt(br.readLine().trim());
				 p = Integer.parseInt(br.readLine().trim());
				
				 zoo = new char[n][n]; 
				// col wise number of trees
				 numTrees = new int[n]; 
			
				int i = 0;
				while ((line = br.readLine()) != null) {
					char[] row = (line.trim()).toCharArray();
					int j=0;
					for(char c : row){
						zoo[i][j] = c;
							if(c=='2'){
								// just placed a tree 
								numTrees[i]++;
								totalTrees++;
							}
						j++;
					}
					i++;
				}
				br.close();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		// check for trivial cases
		 
		Result res = null;
		
		long spaces = n*n;
		
		if(p>n+totalTrees || p>spaces-totalTrees){
			res =  new Result("FAIL");
		} else{
			// apply the algo and get the res;
			if("BFS".equals(algo)){
				res = BFS(zoo,n,p, numTrees);
			} else if("DFS".equals(algo)){
				res = DFS(zoo,n,p, numTrees);
			} else if("SA".equals(algo)){
				res = SA(zoo,n,p, end);
			} else{
				System.out.println("Invalid Algo input - Please enter either BFS/DFS/SA");
			}
		}
		
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			fw = new FileWriter("output.txt");
			bw = new BufferedWriter(fw);
			if(res!=null){
				bw.write(res.result);
				bw.newLine();
				if("OK".equals(res.result)){
					
					for(int i =0;i < n;i++){
						for(int j =0;j<n ;j++){
							bw.write(res.solution[i][j]+"");
						}
						bw.newLine();
					}
				} 
			}
		
			if (bw != null)
				bw.close();

			if (fw != null)
				fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		} 		
		
		long endTime = System.nanoTime();
		
		//System.out.println("Took "+(endTime - startTime) + " ns"); 
		
	
	}

	static boolean isValid(int row,int col, char [][] zoo,int n){
		
		// check if we are placing over a tree then not valid
		
			if(row<0 || col<0 || row>=n || col>=n){
				return false;
			}
			
			if(zoo[row][col] == '2' || zoo[row][col]== '1'){
				return false;
			}
		
			// check upper side is free
			
			boolean upFree = true;
			
			int i = row-1;
			while (i>=0 && zoo[i][col]!='1' && zoo[i][col]!='2'){
				i--;
			}
			if(i>=0 && zoo[i][col]=='1'){
				upFree = false;
			}
			
			boolean downFree = true;
			
			 i = row+1;
			while (i<n && zoo[i][col]!='1' && zoo[i][col]!='2'){
				i++;
			}
			if(i<n && zoo[i][col]=='1'){
				downFree = false;
			}
			
			boolean leftFree = true;
			int j= col-1;
			
			while (j>=0 && zoo[row][j]!='1' && zoo[row][j]!='2'){
				j--;
			}
			if(j>=0 && zoo[row][j]=='1'){
				leftFree = false;
			}
			
			
			boolean topLeftFree = true;
			j= col-1;
			i = row-1;
			while (j>=0 && i>=0  && zoo[i][j]!='1' && zoo[i][j]!='2'){
				i--;
				j--;
			}
			if(j>=0 && i>=0 && zoo[i][j]=='1'){
				topLeftFree = false;
			}
			
			
			boolean bottomLeftFree = true;
			j= col-1;
			i = row+1;
			while (j>=0 && i<n  && zoo[i][j]!='1' && zoo[i][j]!='2'){
				i++;
				j--;
			}
			if(j>=0 && i<n && zoo[i][j]=='1'){
				bottomLeftFree = false;
			}
			
		
		return (upFree && downFree && leftFree && topLeftFree && bottomLeftFree);
	}
}

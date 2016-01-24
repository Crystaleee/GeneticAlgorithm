package HamiltonianPath;

public class FitnessCalc {
	static double[][] pathCost;
	
	//计算个体原本适应度，即花费代价
	public static double calculateOriginalFitness(Individual individual){
		double fitness=0;
		int start;
		int end;
		
		for(int i=0;i<individual.size();i++){
			if(i==individual.size()-1){//回到起点的路径
				start=individual.getSingleGene(i);
				end=individual.getSingleGene(0);
			}
			else{
				start=individual.getSingleGene(i);
				end=individual.getSingleGene(i+1);
			}
			
			fitness+=pathCost[start][end];				
		}			
		return fitness;
	}

}

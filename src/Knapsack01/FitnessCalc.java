package Knapsack01;

public class FitnessCalc {
	static double Maxweight;
	static double[] values;//物品价值
	static double[] weights;//物品重量
	
	
	//计算个体原本适应度，即背包价值
	public static double calculateOriginalFitness(Individual individual){
		double fitness=0;
		double weight=0;
		for(int i=0;i<individual.size();i++){
			if(individual.getSingleGene(i)==1){
				fitness+=values[i];
				weight+=weights[i];
			}				
		}			
		individual.setWeight(weight);
			//若超出背包容量，适应度为0
			if(weight>Maxweight)
				fitness=0;
		return fitness;
	}


}

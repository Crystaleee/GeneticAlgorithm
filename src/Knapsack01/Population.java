package Knapsack01;

public class Population {
	Individual[] individuals;
	int fittest=-1;//population中最好的index
	double totalFitness;
	double averageFitness;
	
	public Population(int size, boolean initialize){
		this.individuals=new Individual[size];
		if(initialize==true){//需要初始化
			for(int i=0; i<size;i++){
				Individual individual=new Individual();
				individual.generateGenes();
				individuals[i]=individual;//将生成的个体放入population中
			}
		}		
	}
	
	public int size(){
		return individuals.length;
	}
	//计算总体适应度
	public double calculateTotalFitness(){
		double totalFitness=0;
		for(int i=0;i<individuals.length;i++){
			totalFitness+=individuals[i].getFitness();
		}
		this.totalFitness=totalFitness;
		this.averageFitness=totalFitness/(double)individuals.length;
		
		return totalFitness;
	}
	
	public void calculateProbability(){
		for(int i=0;i<individuals.length;i++){
			Individual individual=individuals[i];
			individual.setProbability(individual.getFitness()/this.totalFitness);
		}
	}
	
	//取得population中fitness最高的individual
	public Individual getFittest(){
		if(this.fittest==-1){
			int best=0;
			for(int i=1;i<individuals.length;i++){
				if(this.individuals[i].getFitness()>this.individuals[best].getFitness())
					best=i;
			}
			fittest=best;
		}	
		return this.individuals[fittest];
	}

	//将population中最差的替换
	public void setWorst(int[] best) {
		this.fittest=-1;//重置最优
		int worst=0;
		for(int i=1;i<individuals.length;i++){
			if(this.individuals[i].getFitness()<this.individuals[worst].getFitness())//worst为价值最小的
				worst=i;
		}
		this.individuals[worst].setGenes(best.clone());
	}
}

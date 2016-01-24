package HamiltonianPath;

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
	
	public double calculateProbability(){
		double sum=0;
		for(int i=0;i<individuals.length;i++){
			Individual individual=individuals[i];
			//double tmp=1/individual.getFitness();
			double tmp=this.averageFitness/individual.getFitness();
			if(tmp<1){//如果在平均值以下则不选择
				tmp=0;
			}
			else tmp=tmp;
			sum+=tmp;//因为轮盘赌中概率之和不是1了
			//选中概率=平均花费/个体花费，则花费少的概率大
			individual.setProbability(tmp);			
		}
		return sum;
	}
	
	//取得population中fitness最高的individual
	public Individual getFittest(){
		if(this.fittest==-1){
			int best=0;
			for(int i=1;i<individuals.length;i++){
				if(this.individuals[i].getFitness()<this.individuals[best].getFitness())//fittest为花费代价最小的
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
			if(this.individuals[i].getFitness()>this.individuals[worst].getFitness())//worst为花费代价最大的
				worst=i;
		}
		this.individuals[worst].setGenes(best.clone());
	}
}

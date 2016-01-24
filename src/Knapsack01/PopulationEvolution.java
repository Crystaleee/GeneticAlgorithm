package Knapsack01;

public class PopulationEvolution {
	private static final double PC=1;//交叉概率
	private static final double PM=0.05;//变异概率
	private static int[] best=new int[Individual.GeneLength];
	
	public static Population  evolution(Population pop){
		Population population=new Population(pop.size(), false);//不需要初始化
		evaluation(pop);
		best=pop.getFittest().getGenes().clone();
		crossover(population,pop);//交叉生成下一代
		mutation(population);	//下一代个体突变
		population.setWorst(best);//将下一代中最差的换成上一代中最好的
		return population;
	}
	
	//对当前populaition进行个体评价
	public static void evaluation(Population pop){
		//pop.calculateTotalFitness();//计算总体适应度
		pop.calculateProbability();//通过总体适应度计算个体选中概率
	}
		
	//对当前population进行交叉
	public static void crossover(Population newPop,Population pop){
		for(int n=0;n<pop.size()/2;n++){
			double random=Math.random();
			int i=roulette(pop);
			int j=roulette(pop);
			if(random<PC){//如果随机数小于交叉概率				
				crossover(newPop, pop.individuals[i],pop.individuals[j],n);
			}
			else{
				newPop.individuals[2*n]=pop.individuals[i];//不交叉就直接复制
				newPop.individuals[2*n+1]=pop.individuals[j];
			}		
		}
	}
	
	//对下一population进行变异
	public static void mutation(Population pop){
		for(int n=0;n<pop.size();n++){
			double random=Math.random();
			if(random<PM){//如果随机数小于变异概率
				mutate(pop.individuals[n]);
			}
		}
	}
	private static void mutate(Individual individual) {
		int length=individual.size();
		int pos= (int) Math.round(Math.random()*(length-1));//随机选择一个基因
		if(individual.getSingleGene(pos)==0)
			individual.setSingleGene(pos, 1);//本来是0，变为1
		else 
			individual.setSingleGene(pos, 0);		
	}

	//单点交叉，交叉点随机生成
	static void crossover(Population newPop,Individual father,Individual mother, int index){
		int length=father.size();
		int pos=(int) Math.round(Math.random()*(length-2));//length长的基因序列，0~length-2的截断位置
		Individual son=new Individual();
		Individual daughter=new Individual();
		
		//son前半段为father，后半段为mother，daughter反之
		for(int i=0;i<pos+1;i++){
			son.setSingleGene(i, father.getSingleGene(i));
			daughter.setSingleGene(i, mother.getSingleGene(i));
		}
		for(int i=pos+1;i<length;i++){
			son.setSingleGene(i, mother.getSingleGene(i));
			daughter.setSingleGene(i, father.getSingleGene(i));
		}
		//放到下一代中
		newPop.individuals[2*index]=son;
		newPop.individuals[2*index+1]=daughter;
	}
	
	//轮盘赌选择法，返回被选中的index
	static int roulette(Population pop){
		int index=0;
		double random=Math.random();
		double sum=0;
		while(sum<=random){//当累计概率>随机数时停止
			sum+=pop.individuals[index].getProbability();
			index++;
		}
		return index-1;
	}	
}

package HamiltonianPath;

public class PopulationEvolution {
	private static final double PC=0.5;//交叉概率
	private static final double PM=0.5;//变异概率
	private static double probabilitySum;
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
		probabilitySum=pop.calculateProbability();//通过总体适应度计算个体选中概率
	}
		
	//对当前population进行交叉
	public static void crossover(Population newPop,Population pop){
		for(int n=0;n<pop.size()/2;n++){
			double random=Math.random();
			int i=roulette(pop);
			int j=roulette(pop);
			if(random<PC){//如果随机数小于交叉概率				
				orderCrossover(newPop, pop.individuals[i],pop.individuals[j],n);
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
				inversionMutation(pop.individuals[n]);
			}
		}
		pop.fittest=-1;
	}
	
	//随机选择片段反写
	static void inversionMutation(Individual individual){
		individual.setFitness(0);
		int length=individual.size();
		int[] copy=new int[length];
		int start= (int) Math.round(Math.random()*(length));//随机选择起始位置
		int end= (int) Math.round(Math.random()*(length));//随机选择结束位置			
		if(end<start){//若end<start,交换
			int tmp=start;
			start=end;
			end=tmp;
		}
		for(int i=start;i<end;i++){
			copy[i]=individual.getSingleGene(i);
		}	
		int sum=start+end-1;
		for(int i=start;i<end;i++){
			individual.setSingleGene(i,copy[sum-i]);
		}
	}
	
	//替换变异（Displacement Mutation， DM）
	//DM先从父代个体中选择一个子位串，然后再随机在剩下的位串中选择一个为止，并插入该子位串
	static void displacementMutation(Individual individual) {
		int length=individual.size();
		int start= (int) Math.round(Math.random()*(length-1));//随机选择起始位置
		int end= (int) Math.round(Math.random()*(length-1));//随机选择结束位置
		if(end<start){//若end<start,交换
			int tmp=start;
			start=end;
			end=tmp;
		}
		int partLength=end-start+1;
		int[] tmp=new int[partLength];
		int[] genes=individual.getGenes();		
		System.arraycopy(genes, start, tmp, 0,partLength);//将选中片段移出		
		System.arraycopy(genes, end+1, genes, start,length-1-end);//将选中片段后的数组前移

		int pos= (int) Math.round(Math.random()*(length-partLength));//随机选择插入位置_0_1_2_3_4_
		System.arraycopy(genes, pos, genes, pos+partLength, length-partLength-pos);//后移插入位置后的
		System.arraycopy(tmp, 0, genes, pos, partLength);//插入片段
		
		individual.setGenes(genes);
	}

	//次序交叉法, eg: (1 9 2 |4 6 5 7| 8 3 )和 ( 4 5 9 |1 8 7 6| 2 3 ) start=2, end=6
	//				   生成( 2 3 9 |4 6 5 7| 1 8 )和 ( 3 9 2 |1 8 7 6| 4 5 )
	static void orderCrossover(Population newPop,Individual father,Individual mother, int index){
		int length=father.size();		
		Individual son=new Individual();
		Individual daughter=new Individual();
		
		//随机生成两个切点
		int start=(int) Math.round(Math.random()*(length-2));
		int end=(int) Math.round(Math.random()*(length-2));
		if(start>end){//确保start在end之前
			int tmp=end;
			end=start;
			start=tmp;
		}
		//复制随机片段
		for(int i=start+1;i<=end;i++){
			son.setSingleGene(i, father.getSingleGene(i));
			daughter.setSingleGene(i, mother.getSingleGene(i));
		}
		/////////////////son////////////////////
		{
		int num=0;//赋值位置指针
		int pos=end+1;//取值位置指针
		while(num<length){
			if(num==start+1)//跳过复制片段
				num=end+1;
			if(pos>=length)//到末尾了再从头
				pos=0;
			boolean flag=false;//是否已存在相同的数字
			for(int i=start+1;i<=end;i++){
				if(son.getSingleGene(i)==mother.getSingleGene(pos)){
					flag=true;
					break;
				}
			}
			if(flag==false){//不存在相同数字
				son.setSingleGene(num, mother.getSingleGene(pos));
				num++;
			}
				pos++;
		}
		}
		///////////////daughter/////////////////////
		{
			int num=0;
			int pos=end+1;
			while(num<length){
				if(num==start+1)//跳过复制片段
					num=end+1;
				if(pos>=length)
					pos=0;
				boolean flag=false;//是否已存在相同的数字
				for(int i=start+1;i<=end;i++){
					if(daughter.getSingleGene(i)==father.getSingleGene(pos)){
						flag=true;
						break;
					}
				}
				if(flag==false){//不存在相同数字
					daughter.setSingleGene(num, father.getSingleGene(pos));
					num++;
				}
					pos++;
			}
			}		
		
		//放到下一代中
				newPop.individuals[2*index]=son;
				newPop.individuals[2*index+1]=daughter;
	}
	
	//交替位置交叉法,eg:(1 2 3 4 5 6 7 8）和
	//                               （3 7 5 1 6 8 2 4)
	//生成						（1 3 2 7 5 4 6 8）和
	//								（3 1 7 2 5 4 6 8）
	static void alternativePositionCrossover(Population newPop,Individual father,Individual mother, int index){
		int length=father.size();		
		Individual son=new Individual();
		Individual daughter=new Individual();
		
		{/////////////////////////son////////////////////////
			int i=0;//正在更改的gene的index
		int pos=0;//遍历到parent的哪个位置
		boolean flag=false;//表示之前有否出现过同样基因
		boolean parent=true;//true代表son轮到father，false轮到mother,daughter反之
		int gene;//正取得的parent基因
		while(i<length&&pos<length){
			if(parent==true){//son轮到father
				gene=father.getSingleGene(pos);
				for(int j=0;j<i;j++){
					if(son.getSingleGene(j)==gene){//之前曾经出现过了						
						flag=true;
						break;
					}					
				}
			}
			else{//轮到mother
				gene=mother.getSingleGene(pos++);//移动取parent 基因的位置
				for(int j=0;j<i;j++){
					if(son.getSingleGene(j)==gene){
						flag=true;
						break;
					}				
				}
			}
			if(flag==false){//之前没有重复的				
					son.setSingleGene(i, gene);
					i++;	
			}	
			flag=false;//还原flag
			parent=!parent;
		}
	}
	{/////////////////////////daughter//////////////////////
		int i=0;//正在更改的gene的index
		int pos=0;//遍历到parent的哪个位置
		boolean flag=false;//表示之前有否出现过同样基因
		boolean parent=false;//true代表daughter轮到father，false轮到mother
		int gene;//正取得的parent基因
		while(i<length&&pos<length){
			if(parent==true){//daughter轮到father
				gene=father.getSingleGene(pos++);
				for(int j=0;j<i;j++){
					if(daughter.getSingleGene(j)==gene){//之前曾经出现过了
						flag=true;
						break;
					}					
				}
			}
			else{//轮到mother
				gene=mother.getSingleGene(pos);//移动取parent 基因的位置
				for(int j=0;j<i;j++){
					if(daughter.getSingleGene(j)==gene){
						flag=true;
						break;
					}				
				}
			}
			if(flag==false){//之前没有重复的
					daughter.setSingleGene(i, gene);
					i++;		
			}				
			flag=false;//还原flag
			parent=!parent;
		}
	}
		//放到下一代中
		newPop.individuals[2*index]=son;
		newPop.individuals[2*index+1]=daughter;
	}
	
	//轮盘赌选择法，返回被选中的index
	static int roulette(Population pop){
		int index=0;
		double random=Math.random()*probabilitySum;//随机数在0~probabilitySum
		double sum=0;
		while(sum<=random&&index<pop.individuals.length){//当累计概率>随机数时停止
			sum+=pop.individuals[index].getProbability();
			index++;
		}
		return index-1;
	}	
}

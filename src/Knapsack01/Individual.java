package Knapsack01;

public class Individual {
	public static int GeneLength;
	private int [] genes=new int[GeneLength];//基因序列
	private double weight;//背包重量
	private double fitness;//个体适应值
	private double probability;//个体被选择可能性
	
	//随机产生基因序列
	public void generateGenes(){
		int gene;
		for(int i=0;i<GeneLength;i++){
			if(Math.random()<0.3)
				gene=1;
			else gene=0;
			genes[i]=gene;
		}		
	}
	
	public int size(){
		return GeneLength;
	}

	public double getFitness() {
		if(this.fitness==0){
			this.fitness=FitnessCalc.calculateOriginalFitness(this);
		}
		return fitness;
	}
	
	public int[] getGenes() {
		return genes;
	}
	//取得特定位置pos的基因值
	public int getSingleGene(int pos){
		return genes[pos];
	}
	
	//设置特定位置pos的基因值
	public void setSingleGene(int pos, int value){
		this.genes[pos]=value;
		this.fitness=0;
		this.probability=0;
	}

	@Override
	public String toString() {
	 String geneString = "";
	 for (int i = 0; i < this.genes.length; i++) {
		 geneString += getSingleGene(i);
	 }
	return geneString;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setGenes(int[] g){
		this.genes=g;
		this.fitness=0;
	}

	
}

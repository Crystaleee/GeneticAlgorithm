package HamiltonianPath;

public class Individual {
	public static int GeneLength;
	private int [] genes=new int[GeneLength];//基因序列
	private double fitness;//个体适应值，即花费代价
	private double probability;//个体被选择可能性
	
	//随机产生基因序列
	public void generateGenes(){
		for(int i=0;i<GeneLength;i++){
			boolean flag=false;//是否与之前生成的基因有重合
			int gene=(int) Math.round(Math.random()*(GeneLength-1));//基因为0,1,2...n-1
			for(int j=0;j<i;j++){
				if(genes[j]==gene){
					flag=true;
					break;
				}
			}
			if(flag==false){
				genes[i]=gene;
			}
			else i--;
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
	public void setGenes(int[] g){
		this.genes=g;
		this.fitness=0;
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
		 geneString += getSingleGene(i)+" ";
	 }
	return geneString;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public void setFitness(double i) {
		this.fitness=i;		
	}

	
}


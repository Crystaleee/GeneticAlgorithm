package ThreeSAT;

public class Individual {
	static int GeneLength;
	private int [] genes=new int[GeneLength];//基因序列
	private double fitness;//个体适应值，即满足的子句的个数
	private double probability;//个体被选择可能性
	
	//随机产生01基因序列
	public void generateGenes(){
		for(int i=1;i<GeneLength;i++){
			int gene=(int) Math.round(Math.random());
			genes[i]=gene;
		}		
	}
	
	public int size(){
		return this.genes.length;
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

	public void setGenes(int[] g){
		this.genes=g;
		this.fitness=0;
	}

	
}

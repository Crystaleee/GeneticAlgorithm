package ThreeSAT;

public class FitnessCalc {
	static int m;//命题种数，即变量数量
	static int n;//命题组数，即子句个数
	static int[][] subSentence;//子句[子句数量][变量数+1]，0代表该变量未出现，1代表存在正，-1代表存在非
	
	//计算个体原本适应度
	public static double calculateOriginalFitness(Individual individual){
		double fitness=0;
		for(int i=0;i<n;i++){
			for(int j=1;j<m;j++){
				//若都为证（1+1）或都为非（-1+0），则子句成立
				if(individual.getSingleGene(j)+subSentence[i][j]==2||individual.getSingleGene(j)+subSentence[i][j]==-1){
					fitness++;
					break;
				}
			}
		}
		return fitness;
	}


}

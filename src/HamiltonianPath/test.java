package HamiltonianPath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class test {
	static final int POPULATIONSIZE =1000;
	static final int ITERATION=70000;
	static int NUM;
	static String fileName="TSP_2.txt";
	/**
	 * 读取单个文件
     */
    public static void readSingleFile(String fileName) { 	  
        File file = new File(fileName);
        if(!file.isFile()||!file.exists())
        	System.out.print("文件不存在！");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            String[] line;
            int lineNum=0;
            
            while ((tempString = reader.readLine()) != null) {
            	if(lineNum==0){
            		NUM=Integer.parseInt(tempString);//第一行为城市个数
            		Individual.GeneLength=NUM;
            		FitnessCalc.pathCost=new double[NUM][NUM];
            	}           	
            	else{
            		line=tempString.split(" ");
            		int a=Integer.parseInt(line[0]);
            		int b=Integer.parseInt(line[1]);
            		double value=Double.parseDouble(line[2]);
            		FitnessCalc.pathCost[a][b]=value;
            		FitnessCalc.pathCost[b][a]=value;//双向的值都要设置
            		}              
                lineNum++;
            }
            reader.close();           
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
    
    private static void print(String genes, double minFitness){   
    	String[] choice=genes.split(" ");
    	assert choice.length==NUM;
    	 
		File f=new File("2.txt");//新建一个文件对象
        FileWriter fw;
        try {
        	fw=new FileWriter(f);//新建一个FileWriter
        	for(int i=0;i<NUM;i++){    
        		fw.write(choice[i]+"\r\n");
        	}
        	fw.write(minFitness+"\r\n");
        			fw.close();      
        	} catch (IOException e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        }
}
    
	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("######0.0000"); 
		readSingleFile(fileName);
		for(int i=0;i<1;i++){
		Population myPop=new Population(POPULATIONSIZE,true);
		
		double minFitness=Integer.MAX_VALUE;
		String bestGene="";
		for(int generationCount=0;generationCount<ITERATION;generationCount++){
			myPop.calculateTotalFitness();//计算总体适应度			
			//与最佳结果比较
			if(myPop.getFittest().getFitness()<minFitness){
				minFitness=myPop.getFittest().getFitness();
				bestGene=myPop.getFittest().toString();
			}
			System.out.println("Generation: " + generationCount +"	Average Fitness: "+df.format(myPop.averageFitness)+ "	Fittest: "+ df.format(myPop.getFittest().getFitness()));
			//进化
			myPop = PopulationEvolution.evolution(myPop);
		}
		System.out.println("MinFitness: "+minFitness+"  BestGene: "+bestGene);
		//System.out.println(minFitness);
		print(bestGene,minFitness);
		}
	}
}

package Knapsack01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class test {
	static final int POPULATIONSIZE =1000;
	static final int ITERATION=100000;
	static int NUM;
	static String fileName="knapsack_2.txt";
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
            	if(lineNum==0)
            		FitnessCalc.Maxweight=Double.parseDouble(tempString);
            	else if(lineNum==1){
            		int num=Integer.parseInt(tempString);
            		NUM=num;
            		Individual.GeneLength=num;
            		FitnessCalc.weights=new double[num];
            		FitnessCalc.values=new double[num];
            	}            	
            	else{
            		line=tempString.split(" ");
            		FitnessCalc.weights[lineNum-2]=Double.parseDouble(line[0]);
            		FitnessCalc.values[lineNum-2]=Double.parseDouble(line[1]);
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
    
    private static void print(String genes, double maxFitness){   
    	int[] choice=new int[NUM];
    	for(int i=0;i<NUM;i++){
    		String character = genes.substring(i, i + 1);
    		choice[i]=Integer.parseInt(character);
    	}
    	 
		File f=new File("knapsack2.txt");//新建一个文件对象
        FileWriter fw;
        try {
        	fw=new FileWriter(f);//新建一个FileWriter
        	for(int i=0;i<NUM;i++){    
        		if(choice[i]==0)
        			fw.write("false\r\n");
        		else
        			fw.write("true\r\n");
        	}
        	fw.write(maxFitness+"\r\n");
        			fw.close();      
        	} catch (IOException e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        }
}
    
	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("######0.0000"); 
		readSingleFile(fileName);
		
		Population myPop=new Population(POPULATIONSIZE,true);
		
		double maxFitness=0;
		double bestWeight=0;
		String bestGene="";
		for(int generationCount=0;generationCount<ITERATION;generationCount++){
			myPop.calculateTotalFitness();//计算总体适应度
			System.out.println("Generation: " + generationCount +"	Average Fitness: "+df.format(myPop.averageFitness)+ "	Fittest: "+ myPop.getFittest().getFitness());			
			//与最佳结果比较
			if(myPop.getFittest().getFitness()>maxFitness){
				maxFitness=myPop.getFittest().getFitness();
				bestWeight=myPop.getFittest().getWeight();
				bestGene=myPop.getFittest().toString();
			}
			//进化
			myPop = PopulationEvolution.evolution(myPop);
		}
		System.out.println("MaxFitness: "+maxFitness+"		Weight: "+bestWeight+"  BestGene: "+bestGene);
		print(bestGene,maxFitness);
	}

}

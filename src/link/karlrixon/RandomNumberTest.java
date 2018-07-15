package link.karlrixon;

import java.util.Random;
import java.io.BufferedInputStream; 
import java.io.BufferedReader; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.InputStreamReader; 
import java.io.Reader; 

public class RandomNumberTest {
	
	public static void readTxtFile(String filePath){ 
	    try { 
	        String encoding="UTF-8"; 
	        File file=new File(filePath); 
	        if(file.isFile() && file.exists()){ //判断文件是否存在 
	          InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式 
	          BufferedReader bufferedReader = new BufferedReader(read); 
	          int num; 
	          for(int j=0; j<10; j++){
	        	  for(int i=0; i<10; i++){ 
		        	  if((num = bufferedReader.read()) != 0){ 
		  	            System.out.print(num+"  "); 
		  	          }
		          }System.out.println();
	          }
	          read.close(); 
	    }else{ 
	      System.out.println("找不到指定的文件"); 
	    } 
	    } catch (Exception e) { 
	      System.out.println("读取文件内容出错"); 
	      e.printStackTrace(); 
	    }
	  }
	
	public static double P_rand(double Lamda){      // 泊松分布
		 double x=0,b=1,c=Math.exp(-Lamda),u; 
		 do {
		  u=Math.random();
		  b *=u;
		  if(b>=c)
		   x++;
		  }while(b>=c);
		 return x;
	}
	
	public static double binomial(int N,int k,double p)
    {
        if (N < 0 || k < 0) return 0.0;
        double[][] ret = new double[N + 1][k + 1];
        //完成递归版第一步
        ret[0][0] = 1.0;
        //完成递归版第二步
        for (int i = 1; i < N + 1; ++i)
        {
            ret[i][0] = ret[i - 1][0] * (1.0 - p);
        }
        for (int j = 1; j < k + 1; ++j)
        {
            ret[0][j] = 0.0;
        }
        //得到序列ret[N][k]
        //完成递归版第三步
        for (int i = 1; i < N + 1; ++i)
            for (int j = 1; j < k + 1;++j)
            {
                ret[i][j] = (1.0 - p) * ret[i - 1][j] + p * ret[i - 1][j - 1];
            }
        return ret[N][k];
    }
	
	public static void main(String[] args) {
//		均匀分布随机数
//		for(int i = 0; i<50; i++){
//			System.out.println((Math.random()));
//		}
//		
//		for(int j=0;j<5;j++){
//			for(int i = 0; i<10; i++){
//				Random a = new Random();
//				System.out.print(a.nextInt(10));
//			}
//			System.out.println();
//		}
//		
//		for(int i = 0; i<10; i++){
//			Random a = new Random(10);
//			System.out.print(a.nextInt(10));
//		}
//		System.out.println();
//		
//		for(int i = 0; i<10; i++){
//			System.out.print((int)(int)(Math.random()*10*100%100+1)+" ");
//		}
		
//		正态分布随机数
//		Random r = new Random();
//		for(int i = 0; i<10; i++){
//			System.out.println((Math.sqrt(1)*r.nextGaussian()+0));
//		}
		
//		泊松分布随机数
//		for(int i=0; i<10; i++){
//			System.out.print(P_rand(10)+"\t");
//		}System.out.println();
		double sum = 0;
		double[] p = new double[4];
		for(int k=1; k<=4; k++){
			int factorial = 1;
			for(int i=1; i<=k; i++){
				factorial *= i;
			}
			p[k-1] = (Math.pow(2, k) * Math.exp(-2)) / factorial;
			sum += p[k-1];
			System.out.println(p[k-1]);
		}
		System.out.println("sum = "+sum);
		double sum2 = 0;
		for(int i=0; i<4; i++){
			System.out.println(p[i] / sum);
			sum2 += p[i]/sum;
		}
		System.out.println("sum2 = "+sum2);
		
//		二项分布随机数
//		String filePath = "bin/binrand.txt"; 
//	    readTxtFile(filePath);
//		
//		for(int j=0;j<10;j++){
//			double sum = 0;
//			double[] p = new double[4];
//			for(int i=0; i<4; i++){
//				p[i] = binomial(4, i+1, 0.5);
//				System.out.println(p[i]);
//				sum += p[i];
//			}
//			System.out.println("sum = "+sum);
//			double sum2 = 0;
//			for(int i=0; i<4; i++){
//				System.out.println(p[i] / sum);
//				sum2 += p[i] / sum;
//			}
//			System.out.println("sum2 = "+sum2);
//		}
	}
}

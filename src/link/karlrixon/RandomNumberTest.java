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
		
//		二项分布随机数
//		String filePath = "bin/binrand.txt"; 
//	    readTxtFile(filePath); 
	}
}

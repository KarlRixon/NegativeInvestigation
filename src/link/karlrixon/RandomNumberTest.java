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
	        if(file.isFile() && file.exists()){ //�ж��ļ��Ƿ���� 
	          InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//���ǵ������ʽ 
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
	      System.out.println("�Ҳ���ָ�����ļ�"); 
	    } 
	    } catch (Exception e) { 
	      System.out.println("��ȡ�ļ����ݳ���"); 
	      e.printStackTrace(); 
	    }
	  }
	
	public static double P_rand(double Lamda){      // ���ɷֲ�
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
//		���ȷֲ������
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
		
//		��̬�ֲ������
//		Random r = new Random();
//		for(int i = 0; i<10; i++){
//			System.out.println((Math.sqrt(1)*r.nextGaussian()+0));
//		}
		
//		���ɷֲ������
//		for(int i=0; i<10; i++){
//			System.out.print(P_rand(10)+"\t");
//		}System.out.println();
		
//		����ֲ������
//		String filePath = "bin/binrand.txt"; 
//	    readTxtFile(filePath); 
	}
}

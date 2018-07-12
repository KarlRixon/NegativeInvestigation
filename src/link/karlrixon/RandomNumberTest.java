package link.karlrixon;

import java.util.Random;

public class RandomNumberTest {
	public static void main(String[] args) {
		for(int i = 0; i<50; i++){
			System.out.print((int)(Math.random()*10));
		}
		System.out.println();
		
		for(int j=0;j<5;j++){
			for(int i = 0; i<10; i++){
				Random a = new Random();
				System.out.print(a.nextInt(10));
			}
			System.out.println();
		}
		
		for(int i = 0; i<10; i++){
			Random a = new Random(10);
			System.out.print(a.nextInt(10));
		}
		System.out.println();
		
		for(int i = 0; i<10; i++){
			System.out.print((int)(int)(Math.random()*10*100%100+1)+" ");
		}
	}
}

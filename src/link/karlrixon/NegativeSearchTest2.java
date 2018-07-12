package link.karlrixon;

import java.util.Scanner;

public class NegativeSearchTest2 {
	final static int C = 6;
	final static int M = 1000000;
	final static int k = 3;
	
	public static boolean keyIn(int key, int[] keys){
		for(int i = 0; i < k+1; i++){
			if(key == keys[i]){
				return true;
			}
		}
		return false;
	}
	
	public static void addKey(int key, int[] keys){
		for(int i = 0; i < C; i++){
			if(keys[i] == 0){
				keys[i] = key;
				return;
			}
		}
	}
	
	public static class Possibility{
		private double[] firstPossibility = new double[C+1];		//Ԥ��ٷֱ�
		private double[] secondPossibility = new double[C+1];	//�������ݰٷֱ�
		public double[] last2Possibility = new double[C+1];		//�������淨
		
		public void showFirstPossibility() {
			for(int i=1; i<=C; i++){
				System.out.print(firstPossibility[i]+"\t");
			}System.out.println();
		}
		public void setFirstPossibility(double[] firstPossibilty) {
			double sum = 0;
			for(int i = 1; i <=C; i++){
				sum += firstPossibilty[i];
			}
			if(sum != 1){
				System.out.println("Ԥ�����֮�Ͳ�Ϊһ");
				return;
			}
			this.firstPossibility = firstPossibilty;
			firstPossibilty[0] = 1;
		}
		public void setFirstPossibility(){		//�Ȳ����
			for(int i=1; i<=C; i++){
				firstPossibility[i]=2*(double)i/(double)(C*(C+1));
			}
			firstPossibility[0] = 1;
		}
		public void setFirstPossibility(int a){
			double sum=0;
			for (int i = 1; i <= C; i++)
			{
				firstPossibility[i] = (double)(int)(Math.random()*99+1);
				sum += firstPossibility[i];
			}
			for (int i = 1; i <= C; i++){
				firstPossibility[i] /= sum;
			}
			firstPossibility[0] = 1;
		}
		public void showSecondPossibilty() {
			for(int i=1; i<=C; i++){
				System.out.print(secondPossibility[i]+"\t");
			}System.out.println();
		}
		public void setSecondLast2Possibility() {
			if(firstPossibility[0] == 0){
				System.out.println("δ��ʼ��Ԥ�����");
				return;
			}
			double key1;	//ͨ���������ģ��һ���û�����ѡ��
			int key2=0;		//�û�����ѡ������
			int key3;		//������ɸ�ѡ������
			double[] usePossibility = new double[C+1];
			usePossibility[0] = 0;
			for(int i = 1; i <=C; i++){
				usePossibility[i] = firstPossibility[i] + usePossibility[i-1];
			}
			for(int i = 0; i < M; i++){
				key1 = (Math.random());
				for(int j = 1; j <= C; j++){
					if(key1 <= usePossibility[j]){
						key2 = j;
						secondPossibility[j] += (double)1;
						break;
					}
				}
				if(key2 == 0){
					System.out.println("error!");
					return;
				}
				//ģ��kѡ
				key3 = key2;
				int[] keys = new int[k+1];	//��Ȼ�û�ѡ����k����ѡ�������ȷ��key3ȡֵ��Χ��keys������Ҫ��һ����ѡ��
				keys[0] = key3;
				for(int m = 0; m < k; m++){
					while(keyIn(key3,keys)){
						key3 = (int)(Math.random()*10*C%C+1);
					}
					addKey(key3,keys);
					last2Possibility[key3] += 1;
				}
			}
			for(int i = 1; i <= C; i++){
				secondPossibility[i] /= (double)M;
				//��������
				last2Possibility[i] /= M;
				last2Possibility[i] = 1 - ((C-1)*last2Possibility[i])/k;
			}
		}
		public void showlast2Possibility(){
			for(int i=1; i<=C; i++){
				System.out.print(last2Possibility[i]+"\t");
			}System.out.println();
		}
		public void showDpossibility(){
			double Dpossibility = 0;
			for (int i = 1; i <= C; i++)
			{
				Dpossibility =Dpossibility + (secondPossibility[i] - last2Possibility[i])*(secondPossibility[i] - last2Possibility[i]);
			}
			System.out.println("�������淨���"+Dpossibility);
		}
	}
	
	public static void show(Possibility psb){
		System.out.println("ѡ�������"+C);
		System.out.println("����������"+M);
		System.out.println("��ѡ������"+k);
		System.out.println("������ٷֱȣ�");
		psb.showFirstPossibility();
		System.out.println("����������ٷֱȣ�");
		psb.showSecondPossibilty();
		System.out.println("���������ٷֱȣ��������淨����");
		psb.showlast2Possibility();
		psb.showDpossibility();
	}
	public static void main(String[] args) {
		if(k >= C || k < 1 || C < 2 || C > 20 || M < 1000 ){
			System.out.println("����C,M,k��ֵ�Ƿ�����");
			return;
		}
		while(true){
			Possibility psb = new Possibility();
			Scanner input = new Scanner(System.in);
			System.out.println("����Ԥ�����:");
			System.out.println("1.�Ȳ����");
			System.out.println("2.�������");
			System.out.println("3.�ֶ��������");
			System.out.println("4.�˳�����");
			switch(input.nextInt()){
				case 1: psb.setFirstPossibility();break;
				case 2: psb.setFirstPossibility(1);break;
				case 3: {
					double[] p = new double[C+1];
					System.out.println("���ֶ�����"+C+"������");
					for(int i=1; i<=C; i++){
						p[i] = input.nextDouble();
					}
					psb.setFirstPossibility(p);
					break;
				}
				case 4: input.close();return;
				default: continue;
			}
			psb.setSecondLast2Possibility();
			show(psb);
		}
	}
}

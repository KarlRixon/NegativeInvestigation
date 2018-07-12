package link.karlrixon;

import java.util.Scanner;

public class NegativeSearchTest1 {
	final static int C = 4;
	final static int M = 1000000;
	
	public static class Possibility{
		private double[] firstPossibility = new double[C+1];		//Ԥ��ٷֱ�
		private double[] secondPossibility = new double[C+1];	//�������ݰٷֱ�
		public double[] last1Possibility = new double[C+1];		//���ʵ��ӷ�
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
		public void setSecondLast1Last2Possibility() {
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
						secondPossibility[j] += 1;
						break;
					}
				}
				if(key2 == 0){
					System.out.println("error!");
					return;
				}
				key3 = key2;
				while(key3 == key2){
					key3 = (int)(Math.random()*10*C%C+1);
				}
				//���ʵ��ӷ�
				for(int j = 1; j <= C; j++){
					if(j!=key3){
						last1Possibility[j] = last1Possibility[j] + (double)1 / (double)(C - 1);;
					}
				}
				//�������淨
				last2Possibility[key3] += 1;
			}
			for(int i = 1; i <= C; i++){
				secondPossibility[i] /= M;
				//���ʵ���
				last1Possibility[i] /= M;
				last1Possibility[i] = last1Possibility[i] * (C-1)*(C-1) - (C-2);
				//��������
				last2Possibility[i] /= M;
				last2Possibility[i] = 1 - (C-1)*last2Possibility[i];
			}
		}
		public void showlast1Possibility(){
			for(int i=1; i<=C; i++){
				System.out.print(last1Possibility[i]+"\t");
			}System.out.println();
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
				Dpossibility =Dpossibility + (secondPossibility[i] - last1Possibility[i])*(secondPossibility[i] - last1Possibility[i]);
			}
			System.out.println("���ʵ��ӷ����"+Dpossibility);
			Dpossibility = 0;
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
		System.out.println("������ٷֱȣ�");
		psb.showFirstPossibility();
		System.out.println("����������ٷֱȣ�");
		psb.showSecondPossibilty();
		System.out.println("���������ٷֱȣ����ʵ��ӷ�����");
		psb.showlast1Possibility();
		System.out.println("���������ٷֱȣ��������淨����");
		psb.showlast2Possibility();
		psb.showDpossibility();
	}
	
	public static void main(String[] args) {
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
			psb.setSecondLast1Last2Possibility();
			show(psb);
		}
	}
}

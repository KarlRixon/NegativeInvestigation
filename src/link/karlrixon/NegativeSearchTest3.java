package link.karlrixon;

import java.util.Scanner;
import java.io.BufferedReader; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.InputStreamReader; 

public class NegativeSearchTest3 {
	final static int C = 6;
	final static int M = 1000000;
	static int k = 3;
	
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
		private double[] firstPossibility = new double[C+1];		//Ԥ����ʷֲ�  ��һ��Ԫ�����ڱ��k�ĸ��ʷֲ���ʽ��0(δ����Ԥ�����),1(�Ȳ�ֲ�),2(����ֲ�),3(�ֶ��ֲ�),4(),,,
		private double[] secondPossibility = new double[C+1];	//�������ݰٷֱ�
		private double[] last2Possibility = new double[C+1];		//�������淨
		private double[] lambda_j = new double[C-2];	//���ɵ�ѡ��������ʷֲ�=N_j / N
		private double[] Dpossibility = new double[C+1];	//ÿ��ѡ��ķ���
		
		private double[] kPossibility = new double[C-2];		//��¼kȡֵ�ĸ��ʷֲ�
		private double[] kUsePossibility = new double[C-2];		//��������k
		private int[][] R_ij = new int[C][C-2];		//ѡ��j����ѡ��ͬʱѡ�˵�i�Ÿ�ѡ�������
		private int[] sum_R_ij = new int[C-2];		//R_ij��i���
		
		public void refresh(){ 
			for(int i=0; i<C+1; i++){
				firstPossibility[i] = 0;
				secondPossibility[i] = 0;
				last2Possibility[i] = 0;
				Dpossibility[i] = 0;
			}
			for(int i=0; i<C-2; i++){
				lambda_j[i] = 0;
				kPossibility[i] = 0;
				kUsePossibility[i] = 0;
				sum_R_ij[i] = 0;
			}
			for(int i=0; i<C; i++){
				for(int j=0; j<C-2; j++){
					R_ij[i][j] = 0;
				}
			}
		}
		public void showFirstPossibility() {
			for(int i=1; i<=C; i++){
				System.out.print(firstPossibility[i]+"\t");
			}System.out.println();
		}
		public void setFirstPossibility(double[] firstPossibilty) {		//�ֶ�����
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
		public void setFirstPossibility(int a){	//�������
			double sum=0;
			for(int i = 1; i <= C; i++){
				firstPossibility[i] = (double)(int)(Math.random()*99+1);
				sum += firstPossibility[i];
			}
			for (int i = 1; i <= C; i++){
				firstPossibility[i] /= sum;
			}
			firstPossibility[0] = 1;
		}
		public void setFirstPossibility(int a, int b){	//���ȷֲ�
			for(int i = 1; i <=C; i++){
				firstPossibility[i] = 1/(double)C;
			}
			firstPossibility[0] = 1;
		}
		public void setFirstPossibility(int a, int b, int c){	//���ɷֲ�
			
			firstPossibility[0] = 1;
		}
		public void setFirstPossibility(int a, int b, int c, int d){	//����ֲ�
			
			firstPossibility[0] = 1;
		}
		public void setFirstPossibility(int a, int b, int c, int d, int e){	//��̬�ֲ�
			
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
//			�ռ�����
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
				//�����ʷֲ�ѡȡkֵ
				k = generate_k();
				lambda_j[k-1] += 1;
				//ģ��kѡ
				key3 = key2;
				int[] keys = new int[k+1];	//��Ȼ�û�ѡ����k����ѡ�������ȷ��key3ȡֵ��Χ��keys������Ҫ��һ����ѡ��
				keys[0] = key3;
				for(int m = 0; m < k; m++){
					while(keyIn(key3,keys)){
						key3 = (int)(Math.random()*10*C%C+1);
					}
					addKey(key3,keys);
					R_ij[key3-1][k-1] += 1;
				}
			}
//			��������ݴ���
			for(int i = 1; i <= C; i++){
				secondPossibility[i] /= (double)M;
			}
			for(int i = 0; i < C-2; i++){
				lambda_j[i] /= (double)M;
			}
			for(int j=0; j<C-2; j++){
				for(int i=0; i<C; i++){
					sum_R_ij[j] += R_ij[i][j];
				}
			}
			for(int i=1; i<=C; i++){
				for(int j=0; j<C-2; j++){
					last2Possibility[i] += lambda_j[j]*(1-(C-1)*((double)R_ij[i-1][j]/(double)sum_R_ij[j]));
				}
			}
		}
		public void showlast2Possibility(){
			for(int i=1; i<=C; i++){
				System.out.print(last2Possibility[i]+"\t");
			}System.out.println();
		}
		public void showDpossibility(){		//���㷽��
			double sum_lambda_j_2 = 0;
			double sum_lambda_j_frac_j = 0;
			for(int i=0; i<C-2; i++){
				sum_lambda_j_2 += lambda_j[i] * lambda_j[i];
				sum_lambda_j_frac_j += lambda_j[i] / (double)(i+1);
			}
			System.out.println("�������淨���");
			for (int i = 1; i <= C; i++)
			{
				Dpossibility[i] = (1-sum_lambda_j_2+(1-last2Possibility[i])*((C-1)*sum_lambda_j_frac_j+sum_lambda_j_2-2));
				System.out.print(Dpossibility[i]+"\t");
			}System.out.println();
		}
		public void set_k_mode(int mode){
			firstPossibility[0] = mode;
		}
		public void show_k_possibility(){
			for(int i=0; i<C-2; i++){
				System.out.print(kPossibility[i]+"\t");
			}System.out.println();
		}
		public void showLambdaJ(){
			for(int i=0; i<C-2; i++){
				System.out.print(lambda_j[i]+"\t");
			}System.out.println();
		}
		public int generate_k(){	//�����ʷֲ�ѡȡkֵ
			if(firstPossibility[0] == 1){		//�Ȳ�
				for(int i=0; i<C-2; i++){
					kPossibility[i]=2*(double)(i+1)/(double)((C-2)*(C-1));
				}
			}
			else if(firstPossibility[0] == 2){	//���
				double sum=0;
				for(int i = 0; i < C-2; i++){
					kPossibility[i] = (double)(int)(Math.random()*99+1);
					sum += kPossibility[i];
				}
				for(int i = 0; i < C-2; i++){
					kPossibility[i] /= sum;
				}
			}
			else if(firstPossibility[0] == 3){	//���ȷֲ�
				for(int i = 0; i <C-2; i++){
					kPossibility[i] = 1/(double)(C-2);
				}
			}
			else if(firstPossibility[0] == 4){	//���ɷֲ�
				
			}
			else if(firstPossibility[0] == 5){	//����ֲ�
				
			}
			else if(firstPossibility[0] == 6){	//��̬�ֲ�
				
			}
			else {System.out.println("error");return 1;}		//shouldn't reach here
			kUsePossibility[0] = kPossibility[0];
			for(int i=1; i<C-2; i++){
				kUsePossibility[i] += kPossibility[i] + kUsePossibility[i-1];
			}
			double r = Math.random();
			for(int i=0; i<C-2; i++){
				if(r <= kUsePossibility[i]){
					for(int j=0; j<C-2; j++){
						kUsePossibility[j] = 0;
					}
					return i+1;
				}
			}
			System.out.println("error");
			return 1;		//shouldn't reach here
		}
	}
	
	public static void show(Possibility psb){
		System.out.println("ѡ�������"+C);
		System.out.println("����������"+M);
		System.out.println("Ԥ���ѡ�������ʣ�");
		psb.show_k_possibility();
		System.out.println("���ɶ�ѡ�����ٷֱȣ�");
		psb.showLambdaJ();
		System.out.println("Ԥ����������ʣ�");
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
			System.out.println("----����Ԥ�����:");
			System.out.println("1.�Ȳ�ֲ�");
			System.out.println("2.����ֲ�");
			System.out.println("3.�ֶ��������");
			System.out.println("4.���ȷֲ�");
			System.out.println("5.���ɷֲ�");
			System.out.println("6.����ֲ�");
			System.out.println("7.��̬�ֲ�");
			System.out.println("8.�˳�����");
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
				case 4: psb.setFirstPossibility(1,1);break;
				case 5: psb.setFirstPossibility(1, 1, 1);break;
				case 6: psb.setFirstPossibility(1, 1, 1, 1);break;
				case 7: psb.setFirstPossibility(1, 1, 1, 1, 1);break;
				case 8: input.close();return;
				default: continue;
			}
			while(true){
				System.out.println("----��ѡ����k�ĸ��ʷֲ���");
				System.out.println("1.�Ȳ�ֲ�");
				System.out.println("2.����ֲ�");
				System.out.println("3.���ȷֲ�");
				System.out.println("4.���ɷֲ�");
				System.out.println("5.����ֲ�");
				System.out.println("6.��̬�ֲ�");
				System.out.println("7.�˳�����");
				int i = input.nextInt();
				if(i == 8){
					input.close();
					return;
				}else if(i < 1 || i > 7){
					System.out.println("�������");
					continue;
				}
				psb.set_k_mode(i);
				psb.setSecondLast2Possibility();
				show(psb);
				System.out.println();
				System.out.println();
				break;
			}
			psb.refresh();
		}
	}
}

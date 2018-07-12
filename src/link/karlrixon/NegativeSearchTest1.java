package link.karlrixon;

import java.util.Scanner;

public class NegativeSearchTest1 {
	final static int C = 4;
	final static int M = 1000000;
	
	public static class Possibility{
		private double[] firstPossibility = new double[C+1];		//预设百分比
		private double[] secondPossibility = new double[C+1];	//生成数据百分比
		public double[] last1Possibility = new double[C+1];		//概率叠加法
		public double[] last2Possibility = new double[C+1];		//矩阵求逆法
		
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
				System.out.println("预设概率之和不为一");
				return;
			}
			this.firstPossibility = firstPossibilty;
			firstPossibilty[0] = 1;
		}
		public void setFirstPossibility(){		//等差概率
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
				System.out.println("未初始化预设概率");
				return;
			}
			double key1;	//通过此随机数模拟一个用户的正选项
			int key2=0;		//用户的正选项索引
			int key3;		//随机生成负选项索引
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
				//概率叠加法
				for(int j = 1; j <= C; j++){
					if(j!=key3){
						last1Possibility[j] = last1Possibility[j] + (double)1 / (double)(C - 1);;
					}
				}
				//矩阵求逆法
				last2Possibility[key3] += 1;
			}
			for(int i = 1; i <= C; i++){
				secondPossibility[i] /= M;
				//概率叠加
				last1Possibility[i] /= M;
				last1Possibility[i] = last1Possibility[i] * (C-1)*(C-1) - (C-2);
				//矩阵求逆
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
			System.out.println("概率叠加法方差："+Dpossibility);
			Dpossibility = 0;
			for (int i = 1; i <= C; i++)
			{
				Dpossibility =Dpossibility + (secondPossibility[i] - last2Possibility[i])*(secondPossibility[i] - last2Possibility[i]);
			}
			System.out.println("矩阵求逆法方差："+Dpossibility);
		}
	}
	
	public static void show(Possibility psb){
		System.out.println("选项个数："+C);
		System.out.println("调查人数："+M);
		System.out.println("正调查百分比：");
		psb.showFirstPossibility();
		System.out.println("生成正调查百分比：");
		psb.showSecondPossibilty();
		System.out.println("算得正调查百分比（概率叠加法）：");
		psb.showlast1Possibility();
		System.out.println("算得正调查百分比（矩阵求逆法）：");
		psb.showlast2Possibility();
		psb.showDpossibility();
	}
	
	public static void main(String[] args) {
		while(true){
			Possibility psb = new Possibility();
			Scanner input = new Scanner(System.in);
			System.out.println("生成预设概率:");
			System.out.println("1.等差概率");
			System.out.println("2.随机概率");
			System.out.println("3.手动输入概率");
			System.out.println("4.退出程序");
			switch(input.nextInt()){
				case 1: psb.setFirstPossibility();break;
				case 2: psb.setFirstPossibility(1);break;
				case 3: {
					double[] p = new double[C+1];
					System.out.println("请手动输入"+C+"个概率");
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

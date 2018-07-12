package link.karlrixon;

import java.util.Scanner;

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
		private double[] firstPossibility = new double[C+1];		//预设百分比  第一个元素用于标记k的概率分布方式：0(未设置预设概率),1(等差分布),2(随机分布),3(手动分布),4(),,,
		private double[] secondPossibility = new double[C+1];	//生成数据百分比
		private double[] last2Possibility = new double[C+1];		//矩阵求逆法
		
		private double[] kPossibility = new double[C-2];		//记录k取值的概率分布
		private double[] kUsePossibility = new double[C-2];		//用于生成k
		
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
		public void setFirstPossibility(int a){	//随机概率
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
						secondPossibility[j] += (double)1;
						break;
					}
				}
				if(key2 == 0){
					System.out.println("error!");
					return;
				}
				//依概率分布选取k值
				k = generate_k();
				//模拟k选
				key3 = key2;
				int[] keys = new int[k+1];	//虽然用户选择了k个负选项，但用于确定key3取值范围的keys容量还要加一个正选项
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
				//概率求逆
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
			System.out.println("矩阵求逆法方差："+Dpossibility);
		}
		public void set_k_mode(int mode){
			firstPossibility[0] = mode;
		}
		public void show_k_possibility(){
			for(int i=0; i<C-2; i++){
				System.out.print(kPossibility[i]+"\t");
			}System.out.println();
		}
		public int generate_k(){	//依概率分布选取k值
			if(firstPossibility[0] == 1){		//等差
				for(int i=0; i<C-2; i++){
					kPossibility[i]=2*(double)i/(double)((C-2)*(C-1));
				}
			}
			else if(firstPossibility[0] == 2){	//随机
				double sum=0;
				for (int i = 0; i < C-2; i++)
				{
					kPossibility[i] = (double)(int)(Math.random()*99+1);
					sum += kPossibility[i];
				}
				for (int i = 0; i < C-2; i++){
					kPossibility[i] /= sum;
				}
			}
			else if(firstPossibility[0] == 3){
				
			}
			else return 1;		//shouldn't reach here
			for(int i=1; i<C-2; i++){
				kUsePossibility[i] += kUsePossibility[i-1];
			}
			double r = Math.random();
			for(int i=0; i<C-2; i++){
				if(r < kUsePossibility[i]){
					return i+1;
				}
			}
			return 1;		//shouldn't reach here
		}
	}
	
	public static void show(Possibility psb){
		System.out.println("选项个数："+C);
		System.out.println("调查人数："+M);
		System.out.println("多选数量百分比：");
		psb.show_k_possibility();
		System.out.println("正调查百分比：");
		psb.showFirstPossibility();
		System.out.println("生成正调查百分比：");
		psb.showSecondPossibilty();
		System.out.println("算得正调查百分比（矩阵求逆法）：");
		psb.showlast2Possibility();
		psb.showDpossibility();
	}
	public static void main(String[] args) {
		if(k >= C || k < 1 || C < 2 || C > 20 || M < 1000 ){
			System.out.println("请检查C,M,k的值是否有误");
			return;
		}
		while(true){
			Possibility psb = new Possibility();
			Scanner input = new Scanner(System.in);
			System.out.println("----生成预设概率:");
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
			while(true){
				System.out.println("----多选数量k的概率分布：");
				System.out.println("1.等差概率");
				System.out.println("2.随机概率");
				System.out.println("3.手动输入概率");
				System.out.println("4.退出程序");
				int i = input.nextInt();
				if(i == 4){
					input.close();
					return;
				}else if(i < 1 || i > 4){
					System.out.println("输入错误");
					continue;
				}
				psb.set_k_mode(i);
				psb.setSecondLast2Possibility();
				show(psb);
				System.out.println();
				System.out.println();
				break;
			}
		}
	}
}

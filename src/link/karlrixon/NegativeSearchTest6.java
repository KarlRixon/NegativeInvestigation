package link.karlrixon;

import java.util.Scanner;

public class NegativeSearchTest6 {
	final static int C = 10;			//选项个数
	final static int M = 1000000;	//调查人数
	static int n;					//不定项负调查选择个数
	static double[] firstPossibility = new double[C+1];//两种调查使用相同的正选项预设概率
	static double[] firstPossibilityN = new double[C-2];//不定项负调查选择个数预设概率
	//不同分布的选项索引随机数
	static int[] randomC;
	//不同分布的选择个数随机数
	static int[] randomN;
	
	public static double binomial(int N,int k,double p)	//计算泊松分布
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
	
	public static void setFirstPossibility(int j, PossibilityN psbn){
		if(j == 1){		//等差概率
			for(int i=1; i<=C; i++){
				firstPossibility[i]=2*(double)i/(double)(C*(C+1));
			}
			firstPossibility[0] = 1;
			psbn.setFirstPossibility(firstPossibility);
		}
		else if(j == 2){	//随机概率
			double sum=0;
			for(int i = 1; i <= C; i++){
				firstPossibility[i] = (double)(int)(Math.random()*99+1);
				sum += firstPossibility[i];
			}
			for (int i = 1; i <= C; i++){
				firstPossibility[i] /= sum;
			}
			firstPossibility[0] = 1;
			psbn.setFirstPossibility(firstPossibility);
		}
		else if(j == 3){	//手动输入
			System.out.print("输入1-C个正选项的概率：");
			Scanner input = new Scanner(System.in);
			double sum = 0;
			for(int i = 1; i <=C; i++){
				firstPossibility[i] = input.nextDouble();
				sum += firstPossibility[i];
			}
			if(sum != 1){
				System.out.println("预设概率之和不为一");
				input.close();
				return;
			}
			input.close();
			firstPossibility[0] = 1;
			psbn.setFirstPossibility(firstPossibility);
		}
		else if(j == 4){	//均匀分布
			for(int i = 1; i <=C; i++){
				firstPossibility[i] = 1/(double)C;
			}
			firstPossibility[0] = 1;
			psbn.setFirstPossibility(firstPossibility);
		}
		else if(j == 5){	//泊松分布
			double sum = 0;
			for(int t=1; t<=C; t++){
				int factorial = 1;
				for(int i=1; i<=t; i++){
					factorial *= i;
				}
				firstPossibility[t] = ((Math.pow(3, t) * Math.exp(-3)) / factorial);
				sum += firstPossibility[t];
			}
			for(int i=0; i<C; i++){
				firstPossibility[i+1] /= sum;
			}
			firstPossibility[0] = 1;
			psbn.setFirstPossibility(firstPossibility);
		}
		else if(j == 6){	//二项分布
			double sum = 0;
			for(int i=0; i<C; i++){
				firstPossibility[i+1] = binomial(C, i, 0.5);	//p = 0.5
				sum += firstPossibility[i+1];
			}
			for(int i=0; i<C; i++){
				firstPossibility[i+1] /= sum;
			}
			firstPossibility[0] = 1;
			psbn.setFirstPossibility(firstPossibility);
		}
		else if(j == 7){	//正态分布
			
			firstPossibility[0] = 1;
			psbn.setFirstPossibility(firstPossibility);
		}
	}
	
	public static void setFirstPossibilityN(int j, PossibilityN psbn){
		if(j == 1){		//等差
			for(int i=0; i<C-2; i++){
				firstPossibilityN[i]=2*(double)(i+1)/(double)((C-2)*(C-1));
			}
			psbn.setFirstPossibilityN(firstPossibilityN);
		}
		else if(j == 2){	//随机
			double sum=0;
			for(int i = 0; i < C-2; i++){
				firstPossibilityN[i] = (double)(int)(Math.random()*99+1);
				sum += firstPossibilityN[i];
			}
			for(int i = 0; i < C-2; i++){
				firstPossibilityN[i] /= sum;
			}
			psbn.setFirstPossibilityN(firstPossibilityN);
		}
		else if(j == 3){	//均匀分布
			for(int i = 0; i <C-2; i++){
				firstPossibilityN[i] = 1/(double)(C-2);
			}
			psbn.setFirstPossibilityN(firstPossibilityN);
		}
		else if(j == 4){	//泊松分布
			double sum = 0;
			for(int t=1; t<=C-2; t++){
				int factorial = 1;
				for(int i=1; i<=t; i++){
					factorial *= i;
				}
				firstPossibilityN[t-1] = ((Math.pow(2, t) * Math.exp(-2)) / factorial);
				sum += firstPossibilityN[t-1];
			}
			for(int i=0; i<C-2; i++){
				firstPossibilityN[i] /= sum;
			}
			psbn.setFirstPossibilityN(firstPossibilityN);
		}
		else if(j == 5){	//二项分布
			double sum = 0;
			for(int i=0; i<C-2; i++){
				firstPossibilityN[i] = binomial(C-2, i, 0.5);	//p = 0.5
				sum += firstPossibilityN[i];
			}
			for(int i=0; i<C-2; i++){
				firstPossibilityN[i] /= sum;
			}
			psbn.setFirstPossibilityN(firstPossibilityN);
		}
		else if(j == 6){	//正态分布
			double sum = 0;
//			double test = 0;
			for(int i=0; i<C-2; i++){
				firstPossibilityN[i] = normal((double)(i+1)/9, 0.5, 0.2);
				sum += firstPossibilityN[i];
			}
			for(int i=0; i<C-2; i++){
				firstPossibilityN[i] /= sum;
//				test += firstPossibilityN[i];
			}
//			if(test != 1)System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+test);
			psbn.setFirstPossibilityN(firstPossibilityN);
		}
	}

	public static void setOptionRandomNum(){
		double key1;	//通过此随机数模拟一个用户的正选项
		int key2=0;		//用户的正选项索引
		randomC = new int[M];
		double[] usePossibility = new double[C+1];
		usePossibility[0] = 0;
		for(int i = 1; i <=C; i++){
			usePossibility[i] = firstPossibility[i] + usePossibility[i-1];
		}
		for(int i = 0; i < M; i++){
			key1 = Math.random();
			for(int j = 1; j <= C; j++){
				if(key1 <= usePossibility[j]){	//不需要刷新usePossibility
					key2 = j;
					randomC[i] = j;
					break;
				}
			}
			if(key2 == 0){
				System.out.println("error!选项随机数未生成");
				return;
			}
		}
	}
	
	public static void setOptionCountRandomNum(){
		double key1;	//通过此随机数模拟一个用户的正选项
		int key2=0;		//用户的正选项索引
		randomN = new int[M];
		double[] usePossibility = new double[C-2];
		usePossibility[0] = firstPossibilityN[0];
		for(int i=1; i<C-2; i++){
			usePossibility[i] += firstPossibilityN[i] + usePossibility[i-1];
		}
		for(int t=0; t<M; t++){
			key1 = Math.random();
			for(int i=0; i<C-2; i++){
				if(key1 <= usePossibility[i]){
					randomN[t] = (i+1);
					key2 = (i+1);
					break;
				}
			}
			if(key2 == 0){
				System.out.println("error!选择个数随机数未生成");
				return;
			}
		}
	}
	
	public static double normal(double x, double e, double s){
		double a = 1/(s*Math.sqrt(2*Math.PI));
		double b = Math.exp(((x-e)*(e-x))/(2*s*s));
		return a*b;
	}
	
	public static boolean n_keyIn(int key, int[] keys){
		for(int i = 0; i < n+1; i++){
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
	
	public static class PossibilityN{
		private double[] firstPossibility = new double[C+1];		//预设概率分布  第一个元素用于标记k的概率分布方式：0(未设置预设概率),1(等差分布),2(随机分布),3(手动分布),4(),,,
		private double[] secondPossibility = new double[C+1];	//生成数据百分比
		private double[] last2Possibility = new double[C+1];		//矩阵求逆法
		private double[] lambda_j = new double[C-2];	//生成的选择个数概率分布=N_j / N
		private double[] Dpossibility = new double[C+1];	//每个选项的方差
		
		private double[] nPossibility = new double[C-2];		//记录k取值的概率分布
		private int[][] R_ij = new int[C][C-2];		//选了j个负选项同时选了第i号负选项的人数
		private int[] sum_R_ij = new int[C-2];		//R_ij对i求和
		
		public void refresh(){ 
			for(int i=0; i<C+1; i++){
				firstPossibility[i] = 0;
				secondPossibility[i] = 0;
				last2Possibility[i] = 0;
				Dpossibility[i] = 0;
			}
			for(int i=0; i<C-2; i++){
				lambda_j[i] = 0;
				nPossibility[i] = 0;
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
		public void setFirstPossibility(double[] p){
			for(int i=0; i<C+1; i++){
				firstPossibility[i] = p[i];
			}
		}
		public void setFirstPossibilityN(double[] p){
			for(int i=0; i<C-2; i++){
				nPossibility[i] = p[i];
			}
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
			int key2=0;		//用户的正选项索引
			int key3;		//随机生成负选项索引
//			收集数据
			for(int i = 0; i < M; i++){
				key2 = randomC[i];
				secondPossibility[key2] += 1;
				if(key2 == 0){
					System.out.println("error!");
					return;
				}
				//依概率分布随机选取n值
				n = randomN[i];
//				int spy = n;
				lambda_j[n-1] += 1;
				//模拟不定项选
				key3 = key2;
				int[] keys = new int[n+1];	//虽然用户选择了n个负选项，但用于确定key3取值范围的keys容量还要加一个正选项
				keys[0] = key3;
				for(int m = 0; m < n; m++){
					while(n_keyIn(key3,keys)){
						key3 = (int)(Math.random()*10*C%C+1);
					}
					addKey(key3,keys);
					R_ij[key3-1][n-1] += 1;
				}
			}
//			服务端数据处理
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
		public void showDpossibility(){		//计算方差
			double sum_lambda_j_2 = 0;
			double sum_lambda_j_frac_j = 0;
			for(int i=0; i<C-2; i++){
				sum_lambda_j_2 += lambda_j[i] * lambda_j[i];
				sum_lambda_j_frac_j += lambda_j[i] / (double)(i+1);
			}
			System.out.println("方差（理论值）：");
			for (int i = 1; i <= C; i++)
			{
				Dpossibility[i] = (1-sum_lambda_j_2+(1-last2Possibility[i])*((C-1)*sum_lambda_j_frac_j+sum_lambda_j_2-2)) / M;
				System.out.print(Dpossibility[i]+"\t");
			}System.out.println();
			double DpossibilityN[] = new double[C+1];
			System.out.println("方差（实验值）：");
			for (int i = 1; i <= C; i++)
			{
				DpossibilityN[i] = (last2Possibility[i] - secondPossibility[i]) * (last2Possibility[i] - secondPossibility[i]);
				System.out.print(DpossibilityN[i]+"\t");
			}System.out.println();
		}
		public void show_n_possibility(){
			for(int i=0; i<C-2; i++){
				System.out.print(nPossibility[i]+"\t");
			}System.out.println();
		}
		public void showLambdaJ(){
			for(int i=0; i<C-2; i++){
				System.out.print(lambda_j[i]+"\t");
			}System.out.println();
		}
		public void show(){
			System.out.println("选项个数："+C);
			System.out.println("调查人数："+M);
//			System.out.println("预设多选数量概率：");
//			show_n_possibility();
			System.out.println("生成多选数量概率分布：");
			showLambdaJ();
//			System.out.println("预设正调查概率：");
//			showFirstPossibility();
			System.out.println("生成正调查概率分布：");
			showSecondPossibilty();
			System.out.println("算得正调查百分比：");
			showlast2Possibility();
//			showDpossibility();
		}
	}
	
	public static void fun(int option, int i){
		PossibilityN psbn = new PossibilityN();
		{
			setFirstPossibility(option, psbn);
			setOptionRandomNum();
			{
				setFirstPossibilityN(i, psbn);
				setOptionCountRandomNum();
				//开始调查和统计
				psbn.setSecondLast2Possibility();
				psbn.show();
				System.out.println();
				System.out.println();
				psbn.refresh();
			}
		}
	}
	
	public static void main(String[] args) {
		if(C < 2 || C > 20 || M < 1000 ){
			System.out.println("请检查C,M,k的值是否有误");
			return;
		}
		System.out.println("均匀\t均匀---------------------------------------------------------------------------------------------");
		fun(4, 3);
		System.out.println("均匀\t正态---------------------------------------------------------------------------------------------");
		fun(4, 6);
		System.out.println("泊松\t均匀---------------------------------------------------------------------------------------------");
		fun(5, 3);
		System.out.println("泊松\t正态---------------------------------------------------------------------------------------------");
		fun(5, 6);
		System.out.println("二项\t均匀---------------------------------------------------------------------------------------------");
		fun(6, 3);
		System.out.println("二项\t正态---------------------------------------------------------------------------------------------");
		fun(6, 6);
//		Scanner input = new Scanner(System.in);
//		System.out.println("----生成预设概率:");
//		System.out.print("1.等差分布"+"  ");
//		System.out.print("2.随机分布"+"  ");
//		System.out.print("3.手动输入概率"+"  ");
//		System.out.print("4.均匀分布"+"  ");
//		System.out.print("5.泊松分布"+"  ");
//		System.out.print("6.二项分布"+"  ");
//		System.out.print("7.正态分布"+"  ");
//		System.out.println("8.退出程序"+"  ");
		
//		System.out.println("----多选数量k的概率分布：");
//		System.out.print("1.等差分布"+"  ");
//		System.out.print("2.随机分布"+"  ");
//		System.out.print("3.均匀分布"+"  ");
//		System.out.print("4.泊松分布"+"  ");
//		System.out.print("5.二项分布"+"  ");
//		System.out.print("6.正态分布"+"  ");
//		System.out.println("7.退出程序");
		
	}
}

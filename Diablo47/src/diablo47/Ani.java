package diablo47;
public class Ani{
	int					row		= 15;
	int					col		= 15;
	public int[][]		canvas	= new int[row][col];
	public int			userMaxHp;
	public int			userHpGauge;
	public int			userHp;
	public int			monMaxHp;
	public int			monHpGauge;
	public int			monHp;
	public static Ani	ani		= null;
	
	private Ani(){}
	private static class Singleton {
		private static final Ani instance = new Ani();
	}
	
	public static Ani getInstance () {
		return Singleton.instance;
	}
	
//	public static void main(String[] args){
//		new Ani().main2();
//	}
	
	public void main2(){
		// rndMonAndHp();
		adventure();
	}
	
	public void adventure(){
		System.out.print(
			"　　　　　　　　　　　　　　　　"
				+"\n　■■■■　■　　　■■　　　　"
				+"\n　■　　　　■　　■■■■　　　"
				+"\n　■■■■　■■■　　　　　■　"
				+"\n　■　　　　■　　　■■　　■　"
				+"\n　■■■■　■　　■　　■■■　"
				+"\n　　　　　　　　　■　　■　■　"
				+"\n　　■■■■　　　　■■　　■　"
				+"\n　　■　　■　　　　　　　　　　"
				+"\n　　■■■■　　　　　■■■■　"
				+"\n　　　　　　　　　　　■　　■　"
				+"\n　　　■　　　　　　　■■■■　"
				+"\n　■■■■■　　　　　　　　　　"
				+"\n　■　■　■　　　　　　　　　　"
				+"\n　■　■　■　　　　　　　　　　"
				+"\n　■■■■■　　　　　　　　　　"
				+"\n　　　■　　　"
			);
		
		int count = (int)(Math.random() * 4) + 2;
		while(--count > 0){
			System.out.print("■　");
			sleep(1000);
		}
		System.out.println();
	}
	
	public void rest(){
		System.out.print(
			"　　　　　　　　　　　　　　　　"
				+"\n　　■■　　　　　　　　　　　　"
				+"\n　■■■■　　　　■　　■　　　"
				+"\n　　■■　　　　■　　　■　　　"
				+"\n　■　　■　　■　■　　■　　　"
				+"\n　■　　■　■　　　■　■　　　"
				+"\n　　■■　　　　　　　　■　　　"
				+"\n　■■■■■　■■■■■　　　　"
				+"\n　　■　■　　　　　　■　　　　"
				+"\n　　■　■　　　　　　■　　　　"
				+"\n　　　　　　　　　　　■　　　　"
				+"\n　　　　　　　　　　　　　　　　"
				+"\n　　　　　■■■■■　　　　　　"
				+"\n　■■■■　　　■　　　　　　　"
				+"\n　　　■　　　■　　　　　　　　"
				+"\n　　■　　　■　　　　　　　　　"
				+"\n　■■■■■■■■■　"
			);
		int count = (int)(Math.random() * 4) + 2;
		while(--count > 0){
			System.out.print("■　");
			sleep(1000);
		}
		System.out.println();
	}
	
	public void sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void rndMonAndHp(){
		int count = 5;
		while (--count > 0) {
			for (int i = 2; i <= 12; i++) {
				for (int j = 2; j <= 12; j++) {
					if (i == 2 || i == 12 || j == 2 || j == 12)
						canvas[i + (int) (Math.random() * 2) - 1][j + (int) (Math.random() * 2) - 1] = 1;
				}
			}
			int rowCen = row / 2;
			int colCen = col / 2;
			canvas[rowCen - 1][colCen - 1] = 1;
			canvas[rowCen - 1][colCen + 1] = 1;
			canvas[rowCen][colCen - 1] = 1;
			canvas[rowCen][colCen + 1] = 1;
			canvas[rowCen + 1][colCen - 1] = 1;
			canvas[rowCen + 1][colCen + 1] = 1;
			clearConsole();
			printCanvas();
			printHp("ＭＯＮ＿ＨＰ", monHp, monMaxHp);
			System.out.println();
			printHp("ＭＹ＿＿ＨＰ", userHp, userMaxHp);
			sleep(500);
			clearCanvas();
		}
	}
	
	public void setMonHp(int hp, int maxhp){
		this.monHp = hp;
		this.monMaxHp = maxhp;
		float calcHp = ((float)hp / maxhp * 10);
		monHpGauge = (int)(calcHp);
		monHpGauge = (monHpGauge <= 0 && 0 < calcHp) ? 1 : monHpGauge; 
	}
	
	public int getMonHp(){
		return monHpGauge;
	}
	
	public void setUserHp(int hp, int maxhp){
		this.userHp = hp;
		this.userMaxHp = maxhp;
		float calcHp = ((float)hp / maxhp * 10);
		userHpGauge = (int)(calcHp);
		userHpGauge = (userHpGauge <= 0 && 0 < calcHp) ? 1 : userHpGauge; 
	}

	public int calcHpGauge(int hp, int maxHp){
		float calcHp = ((float)hp / maxHp * 10);
		int hpGauge = (int)(calcHp);
		hpGauge = (hpGauge <= 0 && 0 < calcHp) ? 1 : hpGauge;
		return hpGauge;
	}
	
	public void printHp(String label, int hp, int maxHp){
		printGauge(label, hp, maxHp, '■');
	}
	
	public void printSp(String label, int sp, int maxSp){
		printGauge(label, sp, maxSp, '▦');
	}
	
	public void printGauge(String label, int hp, int maxHp, char box){
		int hpGauge = calcHpGauge(hp, maxHp);
		String gaugePrint = "";
		for (int i = 0; i < 10; i++) {
			if (i < hpGauge)
				gaugePrint += box;
			else
				gaugePrint += '□';
		}
		System.out.println(label + gaugePrint);
		System.out.println("　　　　　　　" + hp + "/" + maxHp);
	}
	
	public void cross(){
		int count = 7;
		int count2 = 8;
		while (true) {
			for (int i = 0; i < canvas.length; i++) {
				canvas[count][i] = 1;
				canvas[i][count] = 1;
			}
			if (--count < 0) count = canvas.length - 1;
			for (int i = 0; i < canvas.length; i++) {
				canvas[count2][i] = 1;
				canvas[i][count2] = 1;
			}
			if (++count2 >= canvas.length) count2 = 0;
			clearConsole();
			printCanvas();
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {}
			clearCanvas();
		}
	}
	
	public void moveCanvas(int x, int y){}
	
	public void clearCanvas(){
		canvas = new int[row][col];
	}
	
	public void clearConsole(){
		for (int i = 0; i < 10; i++)
			System.out.println();
	}
	
	public void printCanvas(){
		for (int[] bb : canvas) {
			for (int b : bb) {
				char c = '　';
				if (b == 1)
					c = '■';
				else if (b == 2) c = '□';
				System.out.print(c);
			}
			System.out.println();
		}
	}
}

package diablo47.characters;

public class Boss extends Char {
	
	public String[] monList = {"보스"};
	
	public Boss(){
		init();
	}

	@Override
	public void init(){
		super.init();
		cls = "보스";
		id = monList[(int)(Math.random() * monList.length)];
		
		hp = defaultMaxHp = maxHp = 800;
		sp = defaultMaxSp = maxSp = 300;
		lvUpHp = 10;
		lvUpSp = 10;
		
		needExp = 10;
		addNeedExp = 10;
		
		atk = defaultAtk = 50;
		lvUpAtk = 5;
		
		def = defaultDef = 20;
		lvUpDef = 2;
		
		skillNames = new String[]{"다리걸기改", "묶기"};
		skillNeedSps = new int[]{10, 20};
		afterInit();
	}

	@Override
	public void start() {
		
	}

	@Override
	public void skill1(Char target){
		target.buffDef(-10);
	}

	@Override
	public void skill2(Char target){
		target.buffAtk(-10);
	}

	@Override
	public void skill3(Char target){}

	@Override
	public void skill4(Char target){}

	@Override
	public void skill5(Char target){}
	
}
package diablo47.characters;

public class Mon extends Char {
	
	public String[] monList = {"슬라임", "오크", "트롤", "픽시", "커플", "솔로", "컴퓨터"};
	
	public Mon(){
		init();
	}

	@Override
	public void init(){
		super.init();
		cls = "몬스터";
		id = monList[(int)(Math.random() * monList.length)];
		
		hp = defaultMaxHp = maxHp = 100;
		sp = defaultMaxSp = maxSp = 20;
		lvUpHp = 10;
		lvUpSp = 10;
		
		atk = defaultAtk = 20;
		lvUpAtk = 5;
		
		def = defaultDef = 5;
		lvUpDef = 2;
		
		skillNames = new String[]{"다리걸기"};
		skillNeedSps = new int[]{10};
		afterInit();
	}

	@Override
	public void start() {
		
	}

	@Override
	public void skill1(Char target){
		target.buffDef(-2);
	}

	@Override
	public void skill2(Char target){}

	@Override
	public void skill3(Char target){}

	@Override
	public void skill4(Char target){}

	@Override
	public void skill5(Char target){}
	
}
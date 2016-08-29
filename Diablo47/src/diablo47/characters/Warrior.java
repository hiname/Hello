package diablo47.characters;


public class Warrior extends Char {
	
	public Warrior() {
		init();
	}

	@Override
	public void init() {
		super.init();
		cls = "전사";
		idList = new String[]{"스파르타쿠스", "리스"};
		id = idList[(int)(Math.random() * idList.length)];
		
		hp = defaultMaxHp = maxHp = 300;
		sp = defaultMaxSp = maxSp = 100;
		lvUpHp = 100;
		lvUpSp = 10;
		
		atk = defaultAtk = 20;
		lvUpAtk = 10;
		
		def = defaultDef = 6;
		lvUpDef = 3;
		
		skillNames = new String[]{"함성", "HOT강타", "초콜렛복근", "옷벗기기", "버서커모드"};
		skillNeedSps = new int[]{10, 15, 25, 30, 80};
		afterInit();
	}

	@Override // 함성
	public void skill1(Char target){
		buffMaxHp(500);
		healHp(500);
	}

	@Override // HOT강타
	public void skill2(Char target){
		int tmpAtk = atk;
		atk *= 1.5;
		doAttack(target);
		atk = tmpAtk;
	}

	@Override // 초콜렛복근
	public void skill3(Char target){
		buffDef(100);
	}

	@Override // 옷벗기기
	public void skill4(Char target){
		target.buffDef(-100);
	}

	@Override // 버서커모드
	public void skill5(Char target){
		buffAtk(500);
		buffDef(-500);
		buffMaxHp(1000);
	}
	
	@Override
	public int autoSelection(Char target) {
		int selection = 1;
		if(chkSkillPossible()) {
			for(int i = (skillNeedSps.length - 1); i >= 0; i--){
				// System.out.println("sp>=skillNeeds[" + i + "] : " + sp + ">=" + skillNeedSps[i]);
				if(sp >= skillNeedSps[i]){
					selection = i + 4;
					break;
				}
			}

		}
		return selection;
	}
}

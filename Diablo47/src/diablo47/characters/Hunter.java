package diablo47.characters;


public class Hunter extends Char {
	
	public Hunter() {
		init();
	}

	@Override
	public void init() {
		super.init();
		cls = "사냥꾼";
		idList = new String[]{"헌트리스", "로빈후드"};
		id = idList[(int)(Math.random() * idList.length)];
		
		hp = defaultMaxHp = maxHp = 240;
		sp = defaultMaxSp = maxSp = 120;
		lvUpHp = 50;
		lvUpSp = 50;
		
		atk = defaultAtk = 40;
		lvUpAtk = 20;
		
		def = defaultDef = 5;
		lvUpDef = 2;
		
		skillNames = new String[] { "적 공격력 낮추기", "적 방어력 낮추기", "회복", "적 공업 방다", "확률 하르마게돈" };
		skillNeedSps = new int[]{10, 10, 30, 20, 80};
		afterInit();
		// 공격 / 방어 / 스킬
		// 				 -> 스킬1 - 스킬5
	}

	// ()안에 양수를 넣을 경우 증가, 음수를 넣을 경우 감소
	// buffAtk(1); 은 내 공격력을 1 증가 시키는 것이며,
	// target.buffAtk(1); 은 상대의 공격력을 1 증가시키게 됨.

	// buffAtk(1); // 공격력 증가 / 감소
	// buffDef(1); // 방어력 증가 / 감소
	// buffHp(1); // 최대HP 증가 / 감소 (최대량을 늘려도 HP는 회복 되지 않음-그냥 최대량만 늘어난 상태)
	// buffSp(1); // 최대SP 증가 / 감소
	// healCurse(); // 디버프 해제(공격력, 방어력, 최대HP, 최대SP)
	// healHp(1); // HP 회복 / 감소
	// healSp(1); // SP 회복 / 감소

	@Override
	public void skill1(Char target){
		target.buffAtk(-10);
	}

	@Override
	public void skill2(Char target){
		target.buffDef(-50);
	}

	@Override
	public void skill3(Char target){
		healHp(100);
	}

	@Override
	public void skill4(Char target){
		target.buffDef(-50);
		target.buffAtk(30);
	}

	@Override
	public void skill5(Char target){
		int death = (int) (Math.random() * 10 + 1);
		if (death < 3) {
			target.diedInstantly();
		} else {
			System.out.println("실패");
		}
	}
	
	@Override
	public int autoSelection(Char target) {
		// return super.autoSelection(target);
		int selection = 1;
		if (sp < 80 && hp > (target.atk - def)) {
			selection = 2;
		}

		if (chkSkillPossible()) {
			if (sp >= 80 && hp > (target.atk - def)) {
				selection = 8;
			} else if ((target.atk - def) > hp && sp >= 10) {
				selection = 4;
			} else if ((atk - target.def) < 100 && sp >= 10) {
				selection = 5;
			} else if (target.isDie != true && sp >= 80)
				selection = 1;
		}
		return selection;
	}

}

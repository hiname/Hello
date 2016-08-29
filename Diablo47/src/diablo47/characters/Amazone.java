package diablo47.characters;


public class Amazone extends Char {
	
	public Amazone() {
		init();
	}

	@Override
	public void init() {
		super.init();
		cls = "아마존";
		idList = new String[]{"안다리엘", "메피스토"};
		id = idList[(int)(Math.random() * idList.length)];
		
		hp = defaultMaxHp = maxHp = 150;
		sp = defaultMaxSp = maxSp = 60;
		lvUpHp = 10;
		lvUpSp = 10;
		
		atk = defaultAtk = 25;
		lvUpAtk = 15;
		
		def = defaultDef = 2;
		lvUpDef = 1;
		
		skillNames = new String[]{"멀티샷", "포이즌샷", "파이어샷"};
		skillNeedSps = new int[]{20,20,30};
	}

	@Override // 멀티샷
	public void skill1(Char target){
		int tmpAtk = atk;
		atk *= 0.5;
		doAttack(target);
		doAttack(target);
		doAttack(target);
		atk = tmpAtk;
	}

	@Override // 포이즌샷
	public void skill2(Char target){
		int tmpAtk = atk;
		atk += (int)(Math.random() * atk);
		doAttack(target);
		doAttack(target);
		atk = tmpAtk;
	}

	@Override // 파이어샷
	public void skill3(Char target){
		int tmpAtk = atk;
		atk = (int)(Math.random() * (atk * 3));
		doAttack(target);
		atk = tmpAtk;
	}

	@Override
	public void skill4(Char target){
	}

	@Override 
	public void skill5(Char target){
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

package diablo47.characters;


public class Mage extends Char {
	
	public Mage() {
		init();
	}

	@Override
	public void init() {
		super.init();
		cls = "마법사";
		idList = new String[]{"수리수리마수리", "아브라카타브라"};
		id = idList[(int)(Math.random() * idList.length)];
		
		hp = defaultMaxHp = maxHp = 150;
		sp = defaultMaxSp = maxSp = 300;
		lvUpHp = 30;
		lvUpSp = 80;
		
		atk = defaultAtk = 30;
		lvUpAtk = 20;
		
		def = defaultDef = 2;
		lvUpDef = 1;
		
		skillNames = new String[]{"파이어볼", "아이스볼", "라이트닝", "퍼지", "메테오"};
		skillNeedSps = new int[]{10, 15, 20, 15, 40};
	}

	@Override // 파이어볼 : 80%로 2회 타격
	public void skill1(Char target){
		int tmpAtk = atk;
		atk *= 0.8;
		doAttack(target);
		doAttack(target);
		atk = tmpAtk;
	}

	@Override // 아이스볼 : -10의 방어 감소 후 공격
	public void skill2(Char target){
		target.buffDef(-10);
		doAttack(target);
	}

	@Override // 라이트닝 : 0~10까지 랜덤하게 방어를 낮추고 0~atk 3배의 임의 데미지로 공격
	public void skill3(Char target){
		target.buffDef((int)(-Math.random() * 10));
		int tmpAtk = atk;
		atk = (int)(Math.random() * (atk * 3));
		doAttack(target);
		atk = tmpAtk;
	}

	@Override // 퍼지 : 상대와 나의 모든 상태를 해제
	public void skill4(Char target){
		target.purge();
		purge();
	}

	@Override // 메테오 : 공격력을 50% 증가시키고 상대 방어를 공격력의 50%만큼 감소 후 공격
	public void skill5(Char target){
		int tmpAtk = atk;
		int tmpDef = target.def;
		buffAtk((int)(atk * 0.5));
		target.buffDef((int)(atk * 0.5));
		doAttack(target);
		atk = tmpAtk;
		target.def = tmpDef;
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

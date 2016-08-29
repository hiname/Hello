package diablo47.characters;


public class Healer extends Char {
	
	public Healer() {
		init();
	}

	@Override
	public void init() {
		super.init();
		cls = "힐러";
		idList = new String[]{"김창식씨", "이봉석씨"};
		id = idList[(int)(Math.random() * idList.length)];

		hp = defaultMaxHp = maxHp = 400;
		sp = defaultMaxSp = maxSp = 200;
		lvUpHp = 120;
		lvUpSp = 20;
		
		atk = defaultAtk = 15;
		lvUpAtk = 8;
		
		def = defaultDef = 10;
		lvUpDef = 5;
		
		skillNames = new String[]{"메스힐", "마인드컨트롤", "큐어", "수호", "신의은총"};
		skillNeedSps = new int[]{10, 15, 20, 15, 40};
	}
	
	@Override // 메스힐
	public void skill1(Char target){
		healHp(maxHp);
	}

	@Override // 마인드컨트롤
	public void skill2(Char target){
		healSp(maxSp);
	}

	@Override // 큐어
	public void skill3(Char target){
		healCurse();
	}

	@Override // 수호
	public void skill4(Char target){
		buffDef(50);
	}

	@Override // 신의은총
	public void skill5(Char target){
		if(hp < (maxHp * 0.1)){
			healHp(maxHp);
			healSp(-maxSp);
		} else {
			System.out.println("스킬 실패(HP가 10% 미만일때만 사용 가능)");
		}
	}
	
	@Override
	public int autoSelection(Char target) {
		int selection = 1;
		if(chkSkillPossible()) {
			for(int i = (skillNeedSps.length - 1); i >= 0; i--){
				if(sp >= skillNeedSps[i]){
					selection = i + 4;
					break;
				}
			}

		}
		return selection;
	}

}

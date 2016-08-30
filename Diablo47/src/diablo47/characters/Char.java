package diablo47.characters;
import java.util.InputMismatchException;
import java.util.Scanner;

import diablo47.Ani;

interface CharAction {
	public void adventure();
	public void breakTime();
	public void start();
	public void init();
}


public abstract class Char implements CharAction {
	public String	id				= null;
	public String	cls				= null;
	public String[]	idList			= null;

	public int		minHp;
	public int		defaultMaxHp;
	public int		maxHp;
	public int		hp;
	public int		defaultTurnReHp;
	public int		turnReHp;
	public int		lvUpHp;

	public int		minSp;
	public int		defaultMaxSp;
	public int		maxSp;
	public int		sp;
	public int		defaultTurnReSp;
	public int		turnReSp;
	public int		lvUpSp;

	public int		atk;
	public int		defaultAtk;
	public int		atkRng;
	public int		lvUpAtk;

	public int		def;
	public int		defaultDef;
	public int		defRng;
	public int		lvUpDef;
	public int		addDoDef;
	public int 		addDoSp;

	public int		level;
	public int		exp;
	public int		needExp;
	public int		addNeedExp;
	public int		inflationAddNeedExp;
	public boolean	isSkillRestraint	= false;
	public boolean autoBattle = false;

	public Ani		ani				= Ani.getInstance();

	boolean			isDie;
	boolean			battling;

	public static final Scanner sc = new Scanner(System.in);
	
	@Override
	public void init(){
		cls = "무직";
		id = "무명";
		
		level = 1;
		exp = 5;
		needExp = 10;
		addNeedExp = 10;
		inflationAddNeedExp = 5;
		
	}
	
	public void afterInit(){
		atkRng = (int) (defaultAtk * 0.3);
		defRng = (int) (defaultDef * 0.3);
		addDoDef = (int) (defaultDef * 0.3);
		addDoSp = (int) (defaultMaxSp * 0.3);
	}
	
	public void sleep(long mills) {
		try {
			Thread.sleep(mills + 200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void printStat(String[] a) {
		System.out.print(
			"Class : " + cls + a[0] + "\n"
			+ "LV : " + level + a[1] + "\n"
			+ "ATK : " + atk + a[2] + "\n"
			+ "DEF : " + def + a[3] + "\n"
			+ "HP : " + hp + a[4] + "\n"
			+ "SP : " + sp + a[5] + "\n"
			+ "EXP : " + exp + a[6] + "\n"
			);
	}
	private String[] nulls = new String[]{"","","","","","",""};
	
	private String[] statAdd = new String[]{"","","","","","",""};
	
	public void printStat() {
		printStat(statAdd);
		if(statAdd[2] != "")
			System.arraycopy(nulls, 0, statAdd, 0, nulls.length);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCls() {
		return cls;
	}

	public String getId() {
		return id;
	}
	
	public int getSkillLen() {
		return (skillNames == null) ? 0 : skillNames.length;
	}
	
	public void initLevelUpStat(int level){
		this.level = level;
		hp = maxHp = (defaultMaxHp += (lvUpHp * level));
		sp = maxSp = (defaultMaxSp += (lvUpSp * level));
		
		atk = (defaultAtk += (lvUpAtk * level));
		def = (defaultDef += (lvUpDef * level));
		
		addDoDef = (int)(def * 0.1);
	}

	@Override
	public void start() {
		printStat();
		while (true) {
			System.out.println("행동\n1:모험 2: 휴식");
			int selAction = sc.nextInt();
			if (selAction == 1) {
				adventure();
				if (isDie)
					break;

			} else if (selAction == 2) {
				breakTime();
			}
		}
	}

	@Override
	public void adventure() {
		ani.adventure();
		int disc = (int) (Math.random() * 10 + 1);

		Char mon = new Mon();
		
		int rndMonLevel = level + (int)(Math.random() * 5 - 2);
		if(rndMonLevel <= 0) rndMonLevel = 1;
		mon.initLevelUpStat(rndMonLevel);
		
		if (disc >= 2)
			battle(mon);
		else {
			System.out.println(id + "은(는) " + "아무것도 발견하지 못했다.");
			sleep(500);
		}
	}

	@Override
	public void breakTime() {
		ani.rest();
		int restSp = (int) (Math.random()*50+5);
		int restHp = (int) (Math.random() * 500 + 50);
		hp += restHp;
		if (hp > maxHp)
			hp = maxHp;
		sp += restSp;
		if (sp > maxSp)
			sp=maxSp;
		System.out.println(id + "은(는) " + restHp + "의 HP를 회복하였다.");
		System.out.println(id + "은(는) " + restSp + "의 Sp를 회복하였다.");
		printStat();
		ani.printHp(id + "HP : ", hp, maxHp);
	}

	public void battle(Char target) {
		ani.setMonHp(target.hp, target.maxHp);
		ani.setUserHp(hp, maxHp);
		ani.rndMonAndHp();
		//
		System.out.println();
		System.out.println(target.id + "와(과) 전투가 시작됐다.");
		sleep(300);
		target.printStat();
		sleep(1000);
		battling = true;
		target.battling = true;
		while (true) {
			int userIsSel = -1;
			if(autoBattle) {
				userIsSel = autoSelection(target);
			} else {
				System.out.println("1.공격 2.방어 3.스킬 4.자동");
				userIsSel = sc.nextInt();
			}
			
			// System.out.println("userIsSel : " + userIsSel);			
			
			if (!autoBattle && userIsSel == 4) {
				autoBattle = true;
				userIsSel = autoSelection(target);
			} 
			
			if (userIsSel == 1) {
				doAttack(target);
			} else if (userIsSel == 2) {
				doDefend();
			} else if (userIsSel == 3) {
				doSkill(target, -1);
			} else if (userIsSel > 3) {
				doSkill(target, userIsSel - 3);
			}

			sleep(1000);
			if (!target.battling || target.isDie)
				break;

			// 몬스터의 공격
			int monIsSel = (int)(Math.random() * 3) + 1;
			if(monIsSel == 1)
				target.doAttack(this);
			else if(monIsSel == 2)
				target.doDefend();
			else if(monIsSel == 3)
				target.doSkill(this, 1);
			
			sleep(1000);
			if (!battling || isDie)
				break;
		}

		// 전투 종료 / 승리 메세지, 경험치 체크
		if (target.isDie)
			addExp(target.exp);
		printStat();
		initBattle();
	}
	
	public void initBattle(){
		atk = defaultAtk;
		def = defaultDef;
		initHp();
		initSp();
		
		isSkillRestraint = false;
		autoBattle = false;
	}
	
	public void initHp(){
		maxHp = defaultMaxHp;
		if(hp > maxHp) 
			hp = maxHp;
	}
	
	public void initSp(){
		maxSp = defaultMaxSp;
		if(sp > maxSp) 
			sp = maxSp;
	}
	

	public void addExp(int value) {
		exp += value;
		System.out.println(id + "은(는) " + value + "의 경험치를 얻었다!");
		sleep(250);
		while (exp >= needExp) {
			levelUp();
			addNeedExp += inflationAddNeedExp;
			needExp += addNeedExp;
		}
	}

	public void levelUp() {
		level++;
		atk = (defaultAtk += lvUpAtk);
		def = (defaultDef += lvUpDef);
		hp = maxHp = (defaultMaxHp += lvUpHp);
		sp = maxSp = (defaultMaxSp += lvUpSp);
		System.out.println("레벨업!");
		statAdd[2] = "(+" + lvUpAtk + ")";
		statAdd[3] = "(+" + lvUpDef + ")";
		statAdd[4] = "(+" + lvUpHp + ")";
		statAdd[5] = "(+" + lvUpSp + ")";
		
		sleep(3000);
	}

	public void diedInstantly(){
		isDie = true;
		hp = 0;
		chkThisDie();
	}
	
	public boolean chkThisDie() {
		if (hp <= 0) {
			System.out.println(id + "은(는) 영원한 잠에 빠졌다.");
			sleep(500);
			battling = false;
			isDie = true;
		}
		return isDie;
	}

	public int doAttack(Char target) {
		// 공격을 함
		int rndAtk = atk + (int) (Math.random() * atkRng - (atkRng / 2));
		System.out.println(id + "은(는) " + rndAtk + "의 공격을 가했다!");
		sleep(500);
		target.beAttack(rndAtk);
		
		return rndAtk;
	}

	public int beAttack(int attackerAtk) {
		// 공격을 받음
		int dmg = attackerAtk - def;
		if (dmg <= 0) dmg = 1;
		hp -= dmg;
		if (hp <= 0) hp = 0;
		
		System.out.println(id + "은(는) " + def + "의 방어로 " + dmg + "의 데미지를 입었다!");
		ani.printHp(id + "HP : ", hp, maxHp);
		System.out.println();
		sleep(500);
		chkThisDie();
		return dmg;
	}//d옵티말 

	public void doDefend() {
		def += addDoDef;
		sp += addDoSp;
		if(sp>maxSp){
			sp=maxSp;
		}
		System.out.println(id + "의 방어력 증가(+" + addDoDef + ") => 현재 : " + def);
		System.out.println(id + "의 SP 회복(+" + addDoSp + ") => 현재 : " + sp);
		System.out.println(id + "의 체력 : " + hp);
		System.out.println();
	}
	
	public void buffAtk(int value){
		atk += value;
		System.out.println(id + "의 공격력 " 
			+ ((value < 0) ? "감소(" : "증가(+") 
			+ value + ") = > 현재 : " + atk);
		
		sleep(250);
	}
	
	public void buffDef(int value){
		def += value;
		System.out.println(id + "의 방어력 " 
			+ ((value < 0) ? "감소(" : "증가(+") 
			+ value + ") = > 현재 : " + def);
		
		sleep(250);
	}
	
	public void buffMaxHp(int value){
		maxHp += value;
		System.out.println(id + "의 최대체력 " 
			+ ((value < 0) ? "감소(" : "증가(+") 
			+ value + ") = > 현재 : " + maxHp);
		
		sleep(250);
	}
	
	public void buffMaxSp(int value){
		maxSp += value;
		System.out.println(id + "의 최대마나 " 
			+ ((value < 0) ? "감소(" : "증가(+") 
			+ value + ") = > 현재 : " + maxSp);
		
		sleep(250);
	}
	
	public void healHp(int value){
		hp += value;
		if(hp > maxHp) {
			hp = maxHp;
			value -= (hp - maxHp); 
		}
		
		System.out.println(id + "의 HP " 
			+ ((value < 0) ? "감소(" : "회복(+") 
			+ value + ") = > 현재 : " + hp);
		
		sleep(250);
	}
	
	public void healSp(int value){
		sp += value;
		
		if(sp > maxSp) {
			sp = maxSp;
			value -= (sp - maxSp); 
		}
		
		System.out.println(id + "의 SP " 
			+ ((value < 0) ? "감소" : "회복") 
			+ "(" + value + ") = > 현재 : " + sp);
		
		sleep(250);
	}
	
	public void healCurse(){
		System.out.println(id + "의 상태이상 회복(공격력,방어력,최대HP,SP)");
		
		if(atk < defaultAtk){
			System.out.println("공격력 : " + atk + " => " + defaultAtk);
			atk = defaultAtk;
		}
		
		if(def < defaultDef){
			System.out.println("방어력 : " + def + " => " + defaultDef);
			def = defaultDef;
		}
		
		if(maxHp < defaultMaxHp){
			System.out.println("HP : " + maxHp + " => " + defaultMaxHp);
			initHp();
		}
		
		if(maxSp < defaultMaxSp){
			System.out.println("SP : " + maxSp + " => " + defaultMaxSp);
			initSp();
		}
		
		sleep(250);
	}
	
	public void purge(){
		System.out.println(id + "의 상태를 원래대로 돌림");
		System.out.println("공격력 : " + atk + " => " + defaultAtk);
		System.out.println("방어력 : " + def + " => " + defaultDef);
		System.out.println("HP : " + maxHp + " => " + defaultMaxHp);
		System.out.println("SP : " + maxSp + " => " + defaultMaxSp);
		
		atk = defaultAtk;
		def = defaultDef;
		
		initHp();
		initSp();
	}
	
	public String[]	skillNames		= null;
	public int[]	skillNeedSps	= null;
	
	public void doSkill(Char target, int selection) {
		if(isSkillRestraint) {
			System.out.println("스킬 사용 불가 상태입니다.");
			return;
		}
		
		while (true) {
			try {
				if(selection == -1){ // 사용자에게 입력 받기
					for(int i = 0; i < skillNames.length; i++)
						System.out.print((i + 1) + "." + skillNames[i] + " ");
					selection = Integer.parseInt(sc.next());
				}
				System.out.println(id + "은(는) " + skillNames[selection - 1] + "! 을(를) 사용했다.");
				sleep(1000);
				
				if(sp < skillNeedSps[selection - 1]) {
					System.out.println("SP 부족, " + skillNeedSps[selection - 1] + "의 SP가 필요합니다.");
				} else {
					sp -= skillNeedSps[selection - 1];
					if (selection == 1) {
						skill1(target);
					} else if (selection == 2) {
						skill2(target);
					} else if (selection == 3) {
						skill3(target);
					} else if (selection == 4) {
						skill4(target);
					} else if (selection == 5) {
						skill5(target);
					} else {
						throw new InputMismatchException();
					}
				}
				ani.printSp(id + "SP : ", sp, maxSp);
				sleep(500);
				break;
			} catch (Exception e) {
				System.out.println("잘못 입력 하였습니다.");
				e.printStackTrace();
			}
			
		}
	}
	
	public void skill1(Char target){}
	public void skill2(Char target){}
	public void skill3(Char target){}
	public void skill4(Char target){}
	public void skill5(Char target){}
	
	public int autoSelection(Char target){
		return -1;
	}
	
	public boolean chkSkillPossible(){
		if(isSkillRestraint) return false;
		
		for(int needSp : skillNeedSps){
			if(sp >= needSp) return true;
		}
		return false;
	}
}
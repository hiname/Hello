package diablo47;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import diablo47.characters.Amazon;
import diablo47.characters.Char;
import diablo47.characters.Healer;
import diablo47.characters.Hunter;
import diablo47.characters.Mage;
import diablo47.characters.Warrior;

public class Main{
	Char	selChar	= null;
	public Ani	ani	= Ani.getInstance();
	
	public static void main(String[] args){
		new Main().main2();
	}
	
	public void main2(){
		ArrayList<Char> charList = new ArrayList<Char>();
		charList.add(new Hunter());
		charList.add(new Healer());
		charList.add(new Warrior());
		charList.add(new Mage());
		charList.add(new Amazon());
		Scanner sc = new Scanner(System.in);
		int selNum = -1;
		while (true) {
			System.out.println("캐릭터를 선택하세요.");
			System.out.print("1." + charList.get(0).getCls());
			for (int i = 1; i < charList.size(); i++) {
				System.out.print(" " + (i + 1) + "." + charList.get(i).getCls());
			}
			System.out.println();
			try {
				selNum = Integer.parseInt(sc.next());
				if (selNum < 1 || charList.size() < selNum) 
					throw new InputMismatchException();
				selChar = charList.get(selNum - 1);
				break;
			} catch (Exception e) {
				System.out.println("잘못 입력하였습니다.");
			}
		}
		System.out.println("아이디를 입력하세요.");
		selChar.setId(sc.next());
		System.out.println();
		selChar.start();
		sc.close();
		Char.sc.close();
	}
}

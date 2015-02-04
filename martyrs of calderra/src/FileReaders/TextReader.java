package FileReaders;

import model.AbstractHero;
import model.AbstractCard;
import model.AbstractVillain;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextReader {
	
	
	public TextReader() {
		super();
	}
	
	public ArrayList<ArrayList<AbstractCard>> readShop(String fileName, AbstractHero hero) {
		try {
			Scanner fileScan = new Scanner(new File(fileName));
			ArrayList<ArrayList<AbstractCard>> myLists = new ArrayList<ArrayList<AbstractCard>>();
			ArrayList<AbstractCard> cardList = new ArrayList<AbstractCard>();
			while (fileScan.hasNextLine()) {
				String line = fileScan.nextLine();
				if ((!line.contains("Offensive Items;") && !line.contains("Defensive Items;")
						&& !line.contains("Consumables;") && !line.contains("Other;") && !line.contains("Attacks;")
						&& !line.contains("EndShop;"))) {
					Scanner lineScan = new Scanner(line);
					lineScan.useDelimiter(", ");
					
					while (lineScan.hasNext()) {
						String cardName = lineScan.next();						
						Class<?> card = Class.forName("model." + cardName);
						Constructor<?> con = card.getConstructor(AbstractHero.class);
						Object real = con.newInstance(hero);
						
						cardList.add((AbstractCard) real);
						
					}
					lineScan.close();
				} else {
					if (cardList.size() > 0) myLists.add(cardList); cardList = new ArrayList<AbstractCard>();
				}				
			}
			fileScan.close();
			
			
			return myLists;
			
			
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
		} catch (InstantiationException e) {
//			e.printStackTrace();
		} catch (IllegalAccessException e) {
//			e.printStackTrace();
		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
		} catch (SecurityException e) {
//			e.printStackTrace();
		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
		} catch (InvocationTargetException e) {
//			e.printStackTrace();
		}
		return new ArrayList<ArrayList<AbstractCard>>();
	}
	
	
	public ArrayList<AbstractCard> readAttacks(AbstractVillain him) {
		
		try {
			Scanner fileScan = new Scanner(new File("src/resources/attacks.txt"));
			ArrayList<AbstractCard> cardList = new ArrayList<>();
			while (fileScan.hasNextLine()) {
				Scanner lineScan = new Scanner(fileScan.nextLine());
				lineScan.useDelimiter(", ");
				while (lineScan.hasNext()) {
					String cardName = lineScan.next();						
					Class<?> card = Class.forName("model." + cardName);
					Constructor<?> con = card.getConstructor(AbstractHero.class);
					Object real = con.newInstance(him);
//					him.addObserver((AbstractCard)real);
					cardList.add((AbstractCard) real);
				}
				lineScan.close();
			}
			fileScan.close();
			return cardList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return new ArrayList<AbstractCard>();
	}
}

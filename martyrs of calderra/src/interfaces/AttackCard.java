package interfaces;

import java.util.ArrayList;

public interface AttackCard {

	void getUpgrade();
	void getDowngrade();
	
	int getCruxCost();
	
	ArrayList<String> getAttackGif();
}

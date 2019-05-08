package AI;

import AI.AIhelpers.*;
import Components.*;
import java.util.LinkedList;

public class TreeState {
	State state;
	int playeri;
	int visitn;
	int winn;
	
	public TreeState(State st, int vn, int wn) {
		state = st;
		playeri = state.get_current_player_int();
		visitn = vn;
		winn = wn;
	}
	
	public TreeState(State st) {
		state = st;
		playeri = state.get_current_player_int();
		visitn = 0;
		winn = 0;
	}
	public State getState() {
		return state;
	}
	public int getPlayeri() {
		return playeri;
	}
	public int getvisitn() {
		return visitn;
	}
	public int getwinn() {
		return winn;
	}
	
	public void setvisitn(int vn) {
		visitn = vn;
	}
	public void setwinn(int wn) {
		winn = wn;
	}
	public void setState(State st) {
		state = st;
		playeri = state.get_current_player_int();
	}
		
	/*
	 * Starting from current state, obtain the list of states that the game could
	 * end up in. Then, updates each players' bank using expected profit.
	 * Each state will be in phase 3.
	 */
	public LinkedList<TreeState> childStates() {
		Player currplayer = state.get_current_player();
		
		state = State.nextTurn(state);
		LinkedList<Landmark> landops = AIhelpers.landmarksUnownedPurchasable(state,currplayer);
		LinkedList<State> children = AIhelpers.childStatesL(state, landops);
		
		LinkedList<Establishment> estops = AIhelpers.estOpsPurchasable(state, currplayer);
		estops = AIhelpers.uniqueEst(estops);
		LinkedList<State> childrenL = AIhelpers.childStatesE(state, estops);
		children.addAll(childrenL);
		children.add(state);
		
		LinkedList<TreeState> childTS = new LinkedList <TreeState>();
		for(State s: children) {
			childTS.add(new TreeState(s));
		}
		
		return childTS;
	}
	
	public void increment_visitn() {
		visitn = getvisitn() + 1; 
	}
	
	public void add_winn(int n) {
		winn = getwinn() + n; 
	}
}

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suraj on 11/11/2017.
 */
public class Dilemma {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Problem Statement:");
        System.out.println("The prisoner's dilemma is a standard example of a \n" +
                "game analyzed in game theory that shows why two completely \"rational\" \n" +
                "individuals might not cooperate, even if it appears that it is in their \n" +
                "best interests to do so. It was originally framed by Merrill Flood \n" +
                "and Melvin Dresher working at RAND in 1950.");

        HashMap<State, JailTime> stateMap = new HashMap<State, JailTime>();
        HashMap<Integer, Boolean> confessionMap = new HashMap<Integer, Boolean>();
        confessionMap.put(0, false);
        confessionMap.put(1, true);
        HashMap<Integer, State> indexMap = new HashMap<Integer, State>();
        System.out.println("====================================================================================\n");
        in.readLine();
        State state0 = new State(true,true);
        State state1 = new State(true,false);
        State state2 = new State(false,true);
        State state3 = new State(false,false);

        stateMap.put(state0,new JailTime(5,5));
        indexMap.put(0, state0);
        stateMap.put(state1,new JailTime(0,8));
        indexMap.put(1, state1);
        stateMap.put(state2,new JailTime(8,0));
        indexMap.put(2, state2);
        stateMap.put(state3,new JailTime(2,2));
        indexMap.put(3, state3);

        System.out.println("\n The state matrix is as follows: \n");

        System.out.println(" --------------------");
        System.out.println("|  |    C   |   D    |");
        System.out.println("|--------------------|");
        System.out.print("|C |");
        for (int i = 0; i< stateMap.size();i++){
            State entry = indexMap.get(i);
            if(i%2 == 0 && i>0){
                System.out.println("\n|--------------------|");
                System.out.print("|D |");
            }
            JailTime tempJailTime = stateMap.get(entry);
            System.out.print("  ");
            tempJailTime.printJailTime();
            System.out.print("   |");

        }
        System.out.println("\n --------------------");
        in.readLine();
        System.out.println("\nNash Equilibrium states : ");
        System.out.println("Adam and Eve are in Nash equilibrium if Adam is making \n" +
                "the best decision he can, taking into account Eve's decision \n" +
                "while Eve's decision remains unchanged, and Eve is making the \n" +
                "best decision she can, taking into account Adam's decision while\n" +
                " Adam's decision remains unchanged.");
        in.readLine();
        System.out.println("\nTo find Nash Equilibrium State:");
        Dilemma dilemma = new Dilemma();
        for (int j=0; j<=1; j++){
            for(int k=0; k<=1; k++){
                State s = new State(confessionMap.get(k), confessionMap.get(j));
                boolean changeAdam = (confessionMap.get(k))? false: true;
                boolean changeEve = (confessionMap.get(j))? false: true;
                State changeAdamState = new State(changeAdam, confessionMap.get(j));
                State changeEveState = new State(confessionMap.get(k), changeEve);

                System.out.println("Keeping Eve's decision constant: ");
                System.out.print("Comparing states ");
                stateMap.get(s).printJailTime();
                System.out.print(" and ");
                stateMap.get(changeAdamState).printJailTime();
                boolean res1 = dilemma.compareStates(stateMap, s, changeAdamState, 'A');
                System.out.print(": "+res1);

                System.out.println("\nKeeping Adam's decision constant: ");
                System.out.print("Comparing states ");
                stateMap.get(s).printJailTime();
                System.out.print(" and ");
                stateMap.get(changeEveState).printJailTime();
                boolean res2 = dilemma.compareStates(stateMap, s, changeEveState, 'E');
                System.out.println(": "+res2+"\n======================================================");
                in.readLine();
                if (res1 && res2){
                    stateMap.get(s).printJailTime();
                    System.out.println(" is the Nash Equilibrium State!");
                }
            }
        }
    }

    public boolean compareStates(HashMap<State, JailTime> stateMap, State s1, State s2, char playerToBeChecked){
        JailTime j1 = stateMap.get(s1);
        JailTime j2 = stateMap.get(s2);
        switch (playerToBeChecked){
            case 'A':
                if(j1.aJailTime > j2.aJailTime)
                    return false;
                break;
            case 'E':
                if(j1.eJailTime > j2.eJailTime )
                    return false;
                break;
        }
        return true;
    }
}

class State{
    boolean aConfess;
    boolean eConfess;

    public State(boolean aConfess, boolean eConfess){
        this.aConfess = aConfess;
        this.eConfess = eConfess;
    }

    @Override
    public int hashCode(){
        int a = (aConfess)? 0: 1;
        int b = (eConfess)? 0: 1;
        int result = 31 * a + 17 * b;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        State other = (State) obj;
        if (aConfess != other.aConfess || eConfess != other.eConfess)
            return false;
        return true;
    }
}

class JailTime{
    int aJailTime;
    int eJailTime;

    public JailTime(int aJailTime, int eJailTime){
        this.aJailTime = aJailTime;
        this.eJailTime = eJailTime;
    }

    public void printJailTime(){
        System.out.print(aJailTime+","+eJailTime);
    }
}

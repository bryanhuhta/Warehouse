import java.util.List;

public abstract class WarehouseContext {
    private static final int ERROR_CODE = -2,
                             EXIT_CODE  = -1;

    private List<WarehouseState> states;
    private int[][] transitions;
    private int currentState;

    protected WarehouseContext(int size) {
        transitions = new int[size][size];
        currentState = 0;
    }

    public boolean addState(WarehouseState state) {
         return this.states.add(state);
    }

    public boolean addTransition(int initialState, int finalState, int exitCode) {
        boolean canAdd = ((initialState >= 0) &&
                (initialState < transitions.length) &&
                (finalState >= 0) &&
                (finalState < transitions.length));

        if  (canAdd) {
            transitions[initialState][finalState] = exitCode;
        }

        return canAdd;
    }

    public void changeState(int transition) {
        currentState = transitions[currentState][transition];

        if (currentState < ERROR_CODE) {
            System.out.println("Error: invalid state");
            terminate();
        }
        if (currentState < EXIT_CODE) {
            terminate();
        }

        states.get(currentState).run();
    }

    public void start() {
        currentState = 0;
        states.get(currentState).run();
    }

    protected abstract void terminate();
}

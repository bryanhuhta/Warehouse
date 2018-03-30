public abstract class WarehouseContext {
    private static final int ERROR_CODE = -2,
                             EXIT_CODE  = -1;

    private WarehouseState[] states;
    private int[][] transitions;
    private int currentState;
    private int stateCount;

    protected WarehouseContext(int size) {
        transitions = new int[size][size];
        states = new WarehouseState[size];
        currentState = 0;
        stateCount = 0;
    }

    public void addState(WarehouseState state) {
        states[stateCount++] = state;
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

        if (currentState == ERROR_CODE) {
            System.out.println("Error: invalid state");
            terminate();
        }
        if (currentState == EXIT_CODE) {
            terminate();
        }

        states[currentState].run();
    }

    public void start() {
        currentState = 0;
        states[currentState].run();
    }

    protected abstract void terminate();
}

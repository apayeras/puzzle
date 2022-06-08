package p6.puzzle.Control;

public enum Heuristic {
    WRONG_PLACED,
    MANHATTAN,
    EUCLIDEAN;

    public static Heuristic getHeuristic(String s){
        return switch (s) {
            case "WRONG_PLACED" -> WRONG_PLACED;
            case "MANHATTAN" -> MANHATTAN;
            case "EUCLIDEAN" -> EUCLIDEAN;
            default -> null;
        };
    }

    public static String[] toString(Heuristic[] heu){
        String[] s = new String[heu.length];
        for(int i=0; i < heu.length;i++){
            s[i] = heu[i].name();
        }
        return s;
    }
}

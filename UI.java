public interface UI {
    int askInt(String prompt);
    String askString(String prompt);
    boolean askYesNo(String prompt);// yes - true, no - false
    void println(String msg);
}
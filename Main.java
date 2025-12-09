public class Main {
    
    public static void main(String[] args) {

        // Ο πρωθυπουργός αλλάζει προϋπολογισμό
        Budget oasa = new Budget("Greece", 2025, "OASA", 200000000.0);

        oasa.updateAmount(400000000.0, "Elena");
        oasa.updateAmount(900000000.0, "Eirini");

        oasa.printHistory();
    }
}



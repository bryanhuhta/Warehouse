public class Security {
    private static Warehouse warehouse;

    public Security() {
        warehouse = Warehouse.instance();
    }

    public boolean verify(String user, String password) {
        boolean isValid = false;

        if (user.equals("manager")) {
            isValid = user.equals(password);
        }
        else if (user.equals("salesclerk")) {
            isValid = user.equals(password);
        }
        else {
            if (warehouse.getClient(user) != null) {
                isValid = user.equals(password);
            }
        }

        return isValid;
    }
}

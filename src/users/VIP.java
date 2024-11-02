package users;

import logistics.Cart;

public final class VIP extends Customer {
    public VIP(String username, String password, String address, Cart cart) {
        super(username, password, address, cart);
    }

    @Override
    public void checkout(String address, boolean isPaid) {
        this.cart.setAddress(address);
        this.cart.setPaid(isPaid);
        this.cart.placeOrder(true);
    }

    @Override
    public void repeatOrder(int index) {
        this.cart.placeOrder(true, index);
    }
}

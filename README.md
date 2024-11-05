# ByteMe
A CLI based Java Application for ordering food and managing orders

To run the code, you can try any of the following:

- use any IDE like IntelliJ or Eclipse and run the Main file
- open the terminal, go into the src directory of the project, and run
```
javac *.java
```
After the files have been compiled, run
```
java Main.java
```

## Java Collections used

### List
Used for storing
- Completed orders, i.e. orders with the status `Delivered`
- Unsuccessful Orders, i.e. orders with the status `Cancelled` or `Denied`
- Item Catalogue
- Cart items which contains the customer's present selection of items
- Customers' previously placed orders and currently in-progress order
- Reviews made on a certain item

### HashMap
Used for storing
- Customer login credentials and data
- Items in an order, which contains the item itself as the key and the quantity as the value

### PriorityQueue
Used for storing the pending orders in the order of priority.

An order is given higher priority if it has been ordered from a VIP customer or if it has been ordered before other orders.

## Assumptions

### Admin
- There exists only one login for an admin: 
> username: admin
>
> password: admin
- Whenever an order is placed, it is by default given the status `OrderReceived` and only the admin can change the order's status, except when it is cancelled.
- Orders whose refunds are yet to be processed must have paid the amount upfront (using UPI or net banking) and cash on delivery orders are not sent to the refund list
- Each item must have at least one difference (either the name or the category)
- The availability of an item is the same as its inventory present

### Customers
- Customers must sign themselves up before logging in to the app.
- Each customer must have a unique username
- Each customer has a single cart which gets reset after they place their order or remove all the items from the cart
- Each customer can only order one order at a time. If they try to order multiple orders together, they might not be able to see the order status of remaining orders
- A customer's order is valid only if their required quantity is within the bounds of the inventory of the item
- A customer can cancel their order only if it hasn't been `Delivered`, `Cancelled` or `Denied`
- A customer can easily upgrade their status to VIP via the customer menu, it is assumed that the intricacies (payment,  time period, etc.) are not required to be handled in this version

### VIP
- A VIP once upgraded forever have their status updated to VIP, i.e. they cannot go back to being a regular customer after they have upgraded their account
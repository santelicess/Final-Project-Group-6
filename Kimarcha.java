package kimarchaFinalRev;
import java.util.Scanner;

public class Kimarcha{
	
    static String[] drinks = {"Caramel Macchiato", "Strawberry Latte", "Iced Latte", "Iced Mocha", "Affogato"};
    static double[] drinkPrices = {49.0, 69.0, 49.0, 59.0, 59.0};
    static String[] pastries = {"Honey Cookies", "Bungeoppang", "Madeleines", "Tiramisu", "Hotteok"};
    static double[] pastryPrices = {49.0, 34.0, 39.0, 69.0, 39.0};

    static String[] orderItems = new String[50];
    static int[] orderQuantities = new int[50];
    static double[] orderPrices = new double[50];
    static double[] orderUnitPrices = new double[50];
    static int orderCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to Kimarcha!");
       

        while (running) {
           
            displayMainMenu();
            String choice = scanner.next();

            if (choice.equals("1")) {
		    orderDrinks(scanner);
			} else if (choice.equals("2")) {
			    orderPastries(scanner);
			} else if (choice.equals("3")) {
			    cancelOrder(scanner);
			} else if (choice.equals("4")) {
			    running = checkout(scanner);
			} else if (choice.equals("5")) {
			    clearOrders();
			    System.out.println("Thank you for visiting Kimarcha!");
			    System.out.println("\nSuccesfully returned to the main menu.");
			    continue;
			} else if (choice.equals("0")) {  
			    System.out.println("Thank you for visiting Kimarcha!");
			    running = false;
			} else {
			    System.out.println("Invalid option. Please try again.");
			}
	
	    }
        scanner.close();
    }
       
    
   public static void displayMainMenu() {
	   System.out.println("\nYour Cart:");
	   if (orderCount == 0) {
		   System.out.println("Your cart is empty.");
	   } else {
		   for (int i = 0; i < orderCount; i++) {
			   System.out.println((i+1) + ". " + orderItems[i] + " (x" + orderQuantities[i] + ") - PHP " + String.format("%.2f", orderPrices[i]));
	        }
	    }
    System.out.println("\nMain Menu:");
    System.out.println("1 - Order Drinks");
    System.out.println("2 - Order Pastries");
    System.out.println("3 - Cancel Order");
    System.out.println("4 - Checkout");
    System.out.println("5 - Start Over");
    System.out.println("0 - Exit");  
    System.out.print("Choose an option: ");
   }

   
   public static void orderDrinks(Scanner scanner) {
	   while (true) {
		   System.out.println("\nAvailable Drinks:");
		   System.out.println("PRODUCT                REGULAR 12OZ      MEDIUM 16OZ        LARGE 22OZ");
        
		   for (int i = 0; i < drinks.length; i++) {
			   double mediumPrice = drinkPrices[i] + 10;
			   double largePrice = drinkPrices[i] + 20;
			   System.out.println("D" + (i + 1) + " " + drinks[i] + " ".repeat(25 - ("D" + (i + 1) + " " + drinks[i]).length()) 
                + drinkPrices[i] + " ".repeat(18 - String.valueOf(drinkPrices[i]).length()) 
                + mediumPrice + " ".repeat(18 - String.valueOf(mediumPrice).length()) 
                + largePrice);
		   }
        
        System.out.println("0 - Back to Main Menu");

        System.out.print("\nEnter drink codes (e.g., D1,D2,D3 or d1,d2,d3): ");
        String input = scanner.next().toUpperCase();
        
        if (input.equals("0")) {
            System.out.println("Succesfully returned to the main menu.");
            return; 
        }

        String[] drinkChoices = input.split(",");
        boolean hasValidInput = false;

        for (String drinkChoice : drinkChoices) {
            if (drinkChoice.startsWith("D") && drinkChoice.length() == 2) {
                int index = Character.getNumericValue(drinkChoice.charAt(1)) - 1;
                if (index >= 0 && index < drinks.length) {
                    hasValidInput = true;
                    System.out.println("\nChoose size for " + drinks[index] + ":");
                    System.out.println("1. Small (12oz) - Base Price");
                    System.out.println("2. Medium (16oz) - PHP 10 extra");
                    System.out.println("3. Large (22oz) - PHP 20 extra");

                    int sizeChoice = 0;
                    boolean validSize = false;

                    while (!validSize) {
                        System.out.print("Select size: ");
                        if (scanner.hasNextInt()) {
                            sizeChoice = scanner.nextInt();
                            if (sizeChoice == 1 || sizeChoice == 2 || sizeChoice == 3) {
                                validSize = true;
                            } else {
                                System.out.println("Invalid size choice. Please select 1, 2, or 3.");
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a number (1, 2, or 3).");
                            scanner.next();
                        }
                    }

                    double sizePrice = 0;
                    if (sizeChoice == 2) {
                        sizePrice = 10;
                    } else if (sizeChoice == 3) {
                        sizePrice = 20;
                    }

                    String sizeName;
                    if (sizeChoice == 2) {
                        sizeName = "Medium";
                    } else if (sizeChoice == 3) {
                        sizeName = "Large";
                    } else {
                        sizeName = "Small";
                    }

                    int quantity = 0;
                    boolean validQuantity = false;

                    while (!validQuantity) {
                        System.out.print("Enter quantity: ");
                        if (scanner.hasNextInt()) {
                            quantity = scanner.nextInt();
                            if (quantity > 0) {
                                validQuantity = true;
                            } else {
                                System.out.println("Invalid quantity. Please enter a number greater than 0.");
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a valid quantity.");
                            scanner.next();
                        }
                    }

                    addToOrder(drinks[index] + " (" + sizeName + ")", quantity, drinkPrices[index] + sizePrice);
                } else {
                    System.out.println("Invalid drink code: " + drinkChoice);
                }
            } else {
                System.out.println("Invalid drink code: " + drinkChoice);
            }
        }

        if (hasValidInput) {
        	break;
        }else {
        	System.out.println("No valid drinks selected. Please try again.");
        }
	    }
	}

   
   public static void clearOrders() {
	   for (int i = 0; i < orderCount; i++) {
			orderItems[i] = null;
			orderQuantities[i] = 0;
			orderPrices[i] = 0;
			orderUnitPrices[i] = 0;
	   }
	   orderCount = 0;
	   System.out.println(" ");
   }

   
   public static void orderPastries(Scanner scanner) {
	   while (true) {
		   System.out.println("\nAvailable Pastries:");
		   System.out.println(String.format("%-20s%-20s", "PRODUCT", "PRICE"));
		   for (int i = 0; i < pastries.length; i++) {
			   System.out.println(String.format("%-20s%-20.2f", "P" + (i + 1) + " " + pastries[i], pastryPrices[i]));
		   }
		   System.out.println("0 - Back to Main Menu");

		   System.out.print("\nEnter pastry codes (e.g., P1,P3,P5 or p1,p3,p5): ");
		   String input = scanner.next().toUpperCase();
        
		   if (input.equals("0")) {
			   System.out.println("Succesfully returned to the main menu.");
			   return;
		   }

        String[] pastryChoices = input.split(",");
        boolean hasValidInput = false;

        for (String pastryChoice : pastryChoices) {
            if (pastryChoice.startsWith("P") && pastryChoice.length() == 2) {
                int index = Character.getNumericValue(pastryChoice.charAt(1)) - 1;
                if (index >= 0 && index < pastries.length) {
                    hasValidInput = true;
                    System.out.println("\nYou selected: " + pastries[index]);
                    System.out.print("Enter quantity for " + pastries[index] + ": ");
                    int quantity = 0;

                    while (true) {
                        if (scanner.hasNextInt()) {
                            quantity = scanner.nextInt();
                           if (quantity <= 0) {
                                System.out.print("Quantity must be greater than 0. Please enter again: ");
                            } else {
                                break;
                            }
                        } else {
                            System.out.print("Invalid input. Please enter a valid number: ");
                            scanner.next();                         
                        }
                    }

                    addToOrder(pastries[index], quantity, pastryPrices[index]);
                } else {
                    System.out.println("Invalid pastry code: " + pastryChoice + ". Please enter a valid code (P1 to P5).");
                }
            } else {
                System.out.println("Invalid pastry code: " + pastryChoice + ". Please enter a valid code (P1 to P5).");
            }
        }

        if (!hasValidInput) {
            System.out.println("No valid pastries selected. Please try again.");
        } else {
            break; 
        }
	   }
   }


   public static void addToOrder(String itemName, int quantity, double pricePerUnit) {
	   boolean itemExists = false;

	   for (int i = 0; i < orderCount; i++) {
		   if (orderItems[i].equals(itemName)) {
			   orderQuantities[i] += quantity; 
			   orderPrices[i] += quantity * pricePerUnit;
			   System.out.println(String.format("Added %d more of %s to your cart. Total quantity: %d.", quantity, itemName, orderQuantities[i]));                  
			   itemExists = true;
			   break;
        }
    }
    if (!itemExists) {
    	orderItems[orderCount] = itemName;
        orderQuantities[orderCount] = quantity;
        orderPrices[orderCount] = quantity * pricePerUnit;
        orderUnitPrices[orderCount] = pricePerUnit;
        System.out.println(String.format("Added %d of %s to your cart.", quantity, itemName));
        orderCount++;
    }
   }

   
   public static void cancelOrder(Scanner scanner) {
	   if (orderCount == 0) {
		   System.out.println("No items to cancel.");
		   return;
	   }
	   
	   System.out.println("\nYour Current Order:");
	   for (int i = 0; i < orderCount; i++) {
		   System.out.println(String.format("%d. %s (x%d) - PHP %.2f", i + 1, orderItems[i], orderQuantities[i], orderPrices[i]));
	   }
	   System.out.println("0. Back to Main Menu");

	   System.out.print("Enter the number of the item you want to cancel or to reduce the quantity: ");
	   int cancelChoice;
	   while (true) {
		   if (scanner.hasNextInt()) {
			   cancelChoice = scanner.nextInt();
			   if (cancelChoice >= 0 && cancelChoice <= orderCount) {
				   break;
			   } else {
				   System.out.print("Invalid choice. Enter a valid number (0 to " + orderCount + "): ");
			   }
		   } else {
			   System.out.print("Invalid input. Please enter a valid number: ");
			   scanner.next(); 
		   }
	   }

	   if (cancelChoice == 0) {
		   System.out.println("Returning to the main menu");
		   return;
	   }

	   int index = cancelChoice - 1;

	   System.out.print("Enter quantity to cancel (currently ordered: " + orderQuantities[index] + "): ");
	   int quantityToCancel;
	   while (true) {
		   if (scanner.hasNextInt()) {
			   quantityToCancel = scanner.nextInt();
			   if (quantityToCancel > 0 && quantityToCancel <= orderQuantities[index]) {
				   break;
	            } else {
	                System.out.print("Invalid quantity. Enter a number between 1 and " + orderQuantities[index] + ": ");
	            }
	        } else {
	            System.out.print("Invalid input. Please enter a valid number: ");
	            scanner.next(); 
	        }
	   }

    orderQuantities[index] -= quantityToCancel;
    orderPrices[index] -= quantityToCancel * orderUnitPrices[index];

    System.out.println("Updated quantity of " + orderItems[index] + ": " + orderQuantities[index]);

    if (orderQuantities[index] == 0) {
        for (int i = index; i < orderCount - 1; i++) {
            orderItems[i] = orderItems[i + 1];
            orderQuantities[i] = orderQuantities[i + 1];
            orderPrices[i] = orderPrices[i + 1];
            orderUnitPrices[i] = orderUnitPrices[i + 1];
        }
        orderCount--;
        System.out.println("Item removed from the order.");
    }
   }

   
   public static boolean checkout(Scanner scanner) {
	   if (orderCount == 0) {
		   System.out.println("No items to checkout.");
		   return true; 
	   }

	   System.out.println("\nYour Current Order:");
	   for (int i = 0; i < orderCount; i++) {
		   System.out.println(String.format("%d. %s (x%d) - PHP %.2f", i + 1, orderItems[i], orderQuantities[i], orderPrices[i]));
	   }

	   System.out.println("\nPROCEED TO CHECKOUT?");
	   System.out.println("1. Proceed to Checkout");
	   System.out.println("0 - Cancel and Back to Main Menu");

	   while (true) {
		   System.out.print("Choose an option: ");
		   if (scanner.hasNextInt()) {
			   int choice = scanner.nextInt();
			   if (choice == 1) {
				   finalizeCheckout(scanner);
				   return false; 
			   } else if (choice == 0) {
				   System.out.println("Succesfully returned to the main menu. Your orders are saved.");
				   return true; 
			   } else {
				   System.out.println("Invalid choice. Please select 1 or 2.");
			   }
		   } else {
			   System.out.println("Invalid input. Please enter 1 or 2.");
			   scanner.next(); 
		   }
	   }
   }


   public static boolean finalizeCheckout(Scanner scanner) {
	    double subtotal = 0;
	    
	    for (int i = 0; i < orderCount; i++) {
	        subtotal += orderPrices[i];
	    }

	    String isPWD;
	    while (true) {
	        System.out.print("\nAre you a PWD? (yes/no): ");
	        isPWD = scanner.next();
	        if (isPWD.equalsIgnoreCase("yes") || isPWD.equalsIgnoreCase("no")) {
	            break;
	        } else {
	            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
	        }
	    }

	    String isSenior;
	    while (true) {
	        System.out.print("Are you a Senior Citizen? (yes/no): ");
	        isSenior = scanner.next();
	        if (isSenior.equalsIgnoreCase("yes") || isSenior.equalsIgnoreCase("no")) {
	            break;
	        } else {
	            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
	        }
	    }

	    double discount = 0;
	    if (isPWD.equalsIgnoreCase("yes") || isSenior.equalsIgnoreCase("yes")) {
	        discount = subtotal * 0.20;
	    }

	    String discountType;
	    if (isPWD.equalsIgnoreCase("yes") && isSenior.equalsIgnoreCase("yes")) {
	        discountType = "PWD & Senior";
	    } else if (isPWD.equalsIgnoreCase("yes")) {
	        discountType = "PWD";
	    } else if (isSenior.equalsIgnoreCase("yes")) {
	        discountType = "Senior";
	    } else {
	        discountType = "None";
	    }

	    discount = Math.round(discount); 
	    double finalAmount = Math.round(subtotal - discount);
	    double vat = Math.round(finalAmount * 0.20); 
	    double total = Math.round(finalAmount + vat);

	    System.out.println("\nSUBTOTAL: PHP " + (int) subtotal + ".00");
	    System.out.println("\nDiscount (20%): PHP " + (int) discount + ".00 (" + discountType + ")");
	    System.out.println("DISCOUNTED AMOUNT (after discount): PHP " + (int) finalAmount + ".00");
	    System.out.println("VAT (20%): PHP " + (int) vat + ".00");
	    System.out.println("\nTOTAL AMOUNT(incl. VAT): PHP " + (int) total + ".00");

	    double payment = 0;
	    while (true) {
	        System.out.print("\nEnter CASH amount: ");
	        if (scanner.hasNextDouble()) {
	            payment = scanner.nextDouble();
	            if (payment >= total) {
	                double change = Math.round((payment - total) * 100.0) / 100.0;  
	                System.out.println("\nCHANGE: PHP " + (int) change + ".00\n");
	                printFinalReceipt(subtotal, discount, discountType, finalAmount, vat, total, payment);
	                System.exit(0); // Terminate the program
	            } else {
	                System.out.println("Insufficient cash. Please make sure you enter enough to cover the total.");
	            }
	        } else {
	            System.out.println("Invalid input. Please enter a valid payment amount.");
	            scanner.next(); 
	        }
	        
	    }
	}


   public static void printFinalReceipt(double subtotal, double discount, String discountType, double finalAmount, double vat, double total, double payment) {
    
	   System.out.println("======================== CAFE RECEIPT ========================");
	   System.out.println("                        KIMARCHA CAFE");
	   System.out.println("      MAHUSAY ST, MENZILAN SUBD, MAULAD HOMES, MOJON");
	   System.out.println("          CITY OF MALOLOS CAPITAL OF BULACAN");
	   System.out.println("                 TEL.# +9423757794");
	   System.out.println("==============================================================");
	   System.out.println("RECIPT NO. :0001                             DATE: 12/05/24"             );
	   System.out.println("==============================================================");
	   System.out.println(String.format("%-25s %-15s %-10s %-15s", "Product", "Unit Price", "Qty", "Total Price"));
	   System.out.println("--------------------------------------------------------------");
	    
		for (int i = 0; i < orderCount; i++) {
		    System.out.println(String.format("%-25s PHP %-10.2f %-10d PHP %-15.2f", orderItems[i], orderUnitPrices[i], orderQuantities[i], orderPrices[i]));
		}
		
		System.out.println("-------------------------------------------------------------");
		System.out.println(String.format("Subtotal: PHP %.2f", subtotal));
		System.out.println(String.format("Discount (20%%): PHP %.2f (%s)", discount, discountType));
		System.out.println(String.format("DISCOUNTED AMOUNT (20%%): PHP %.2f", finalAmount));
		System.out.println("---------------------------------------");
		System.out.println(String.format("VAT (20%%): PHP %.2f", vat));
		System.out.println("---------------------------------------");
		System.out.println(String.format("TOTAL AMOUNT(incl. VAT): PHP %.2f", total));
		System.out.println(String.format("CASH: PHP %.2f", payment));
		System.out.println(String.format("CHANGE: PHP %.2f", payment - total));
		System.out.println("==============================================================");
		System.out.println("            CUSTOMER INFORMATION");
		System.out.println("Name:__________________");
		System.out.println("Address:_______________");
		System.out.println("TIN:___________________");
		System.out.println("==============================================================");
		System.out.println("     Thank you for visiting Kimarcha! Please come again!");
		System.out.println("                 mapapa-KIMARCHA ka sa sarap!");
		System.out.println("==============================================================");
		
	}
}

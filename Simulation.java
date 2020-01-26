import java.util.Scanner;
import java.util.ArrayList;
class Simulation{
    //initialises a new PortfolioDisplay for the session so that when add stock is called, the stock details can then be added to the portfolio array
   static PortfolioDisplay session1 = new PortfolioDisplay(); 
   static Scanner scan = new Scanner(System.in);
   static Scanner scan2 = new Scanner(System.in);
   //class variables (static) as we are going to use these in the class methods within this Simulation class
   private static double profit = 0;
   private static double sellPrice;
   public static void main(String [] args){ 
       
       System.out.println("Ok to start stock simulation");
   }
   //the below method is used when the array of portfolios is returned to the user and then printed to the screen
   public static void iterateAndPrint(){
       Stock [] arrayOfPortfolios = session1.getPortfolioNames();
       ArrayList<ProtectedAttributes> arrayOfProtection = session1.getProtectedStock();
       //ProtectedAttributes[] arrayOfProtection = session1.getProtectedStock();
       int protection; 
       for(int i = 0 ; i < arrayOfPortfolios.length; i++)
       {          
           System.out.println(arrayOfPortfolios[i].shareName);
           System.out.println(arrayOfPortfolios[i].sharePrice);
           System.out.println(arrayOfPortfolios[i].amountOfShare);
           protection = session1.getProtectedStockIndex(arrayOfPortfolios[i].shareName);
           if(protection != -1)
                System.out.println(arrayOfProtection.get(protection).protectionSellPrice);
                
                //System.out.println(arrayOfProtection.[protection].protectionSellPrice);
           else
                System.out.println("No protection on this stock");
           System.out.println("Next stock follows");
       }
   }
   
   public static void addStock()
   {
       System.out.println("Ready To Enter Stock");
       System.out.println("Name");
       String stockName = scan2.nextLine();
       System.out.println("Price");
       double price = scan.nextDouble();
       System.out.println("Amount");
       int amount = scan.nextInt();
       UnProtectedStock newStock = new UnProtectedStock(stockName, price, amount);
       session1.AddStock(newStock);
   }
  
   public static void addProtectedProfitStock()
   {
       System.out.println("Ready To Enter Stock");
       System.out.println("Name");
       String stockName = scan2.nextLine();
       System.out.println("Price");
       double price = scan.nextDouble();
       System.out.println("Amount");
       int amount = scan.nextInt();
       System.out.println("Set protected profit to sell at if stock reaches this amount");
       double protectedProfit = scan.nextDouble();
       ProfitProtectedStock newStock = new ProfitProtectedStock(stockName, price, amount, protectedProfit);
       //below we cast using the properties of inheritance being able to call a method that accepts a type Stock with a type ProfitProtectedStock that is a subclass of type Stock
       session1.AddStock(newStock);
       //ProtectedAttributes is a subclass of ProfitProtectedStock
       ProtectedAttributes newStock2 = new ProtectedAttributes(stockName, price, amount, protectedProfit);
       session1.ProtectionAttributes(newStock2);
   }
   //for a given stock this instance method will either buy or sell the stock
   public static void buySellStock()
   {
       System.out.println("Enter Stock Name To Buy / Sell");
       String searchName = scan2.nextLine();
       int indexOfShare = session1.getStockIndex(searchName);
       int amount = 1;
       if(indexOfShare == -1) //this corresponds to our error value if stock isnt found
       {     
            System.out.println("Not Found");
            return;
       }     
       else
       {
            System.out.println("Stock " + session1.getStockName(indexOfShare));
            System.out.println("Price Is " + session1.getStockPrice(indexOfShare));
            System.out.println("Amount Is " + session1.getStockAmount(indexOfShare));
       }    
       System.out.println("What Do You Want To Do, Type BUY or SELL");
       String actionToTake = scan2.nextLine();
       if(actionToTake.equals("BUY"))
       {
            System.out.println("How Many More? You Currently Have " + session1.getStockAmount(indexOfShare));
            amount = scan.nextInt();
            session1.Buy(indexOfShare, amount);
       } 
       else if(actionToTake.equals("SELL"))
       {
            System.out.println("How Many To Sell? You Currently Have " + session1.getStockAmount(indexOfShare));
            amount = scan.nextInt();
            if(amount > session1.getStockAmount(indexOfShare) && amount > 0)
                System.out.println("Action disallowed, you want to sell more of the stock than you actually have or you have entered an invalid amount to sell, you naughty trader, TRANSACTION CANCELLED");
            else
            {
                //in the below we check to see if the stock is protected or not - if not protected return value will be -1 (or error value / not found)
                if(session1.getProtectedStockIndex(session1.getStockName(indexOfShare)) == -1)
                {    
                    System.out.println("Enter SELL PRICE");
                    sellPrice = scan.nextDouble();
                    profit += session1.Sell(indexOfShare, amount, sellPrice);
                    //in the below we want to destory the stock from our portfolio if none left
                    if(session1.getStockAmount(indexOfShare) == 0)
                         session1.shufflePortfolioDisplay(indexOfShare);   
                }
                //the below would mean that the stock has been found in the protectedattributes array
                else
                    System.out.println("Action disallowed, you want to sell a protected stock, you naughty trader, TRANSACTION CANCELLED");
            }
       }    
   }

   public static void getSessionProfit() {
       
       System.out.println("Session PROFIT is: " + profit); 
   }
}
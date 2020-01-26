import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
public class GUI extends Frame implements ActionListener, SaveLoad
{
    //Here we declare private global variables so the code can re-write to these after an event
    private BufferedImage image;
    private TextField nameT;
    private TextField priceT;
    private TextField quantT;
    private TextField protectedStockT;
    //Static variables that remain constant for the whole class
    private static List stockSpecs = new List();
    private static PortfolioDisplay session1 = new PortfolioDisplay();
    //We make the variable below static as we access it from the static class method that reads the profit variable, we could not access this variable if it wasnt static in the static method of profit as we would get an error of non static variable being accessed from static context, however we can access the static variable in the method buySellStock even though this method isnt static
    private static double profit = 0;
    private double sellPrice;
    private GUI popup;
    //Below is the constructor for the main GUI
    public GUI()
    {
        super("Trading 21-SP");
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Button buy = new Button("Buy More Stock");
        buy.addActionListener(this);
        Button sell = new Button("Sell Stock");
        sell.addActionListener(this);
        
        Button showAll = new Button("Show Portfolio");
        showAll.addActionListener(this);
        
        c.weightx = 0.01;
        c.ipady = 20;
        c.weighty = 0.1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(buy, c);
        
        c.gridx = 4;
        add(sell, c);
        
        c.gridy = 2;
        add(showAll, c);
        
        Button addProtected = new Button("Add Protected Stock");
        Button addUnprotected = new Button("Add Unprotected Stock");
        addProtected.addActionListener(this);
        addUnprotected.addActionListener(this);
        
        c.ipady = 0;
        c.ipadx = 100;
        c.gridx = 2;
        c.gridy = 2;
        add(addProtected, c);

        c.gridx = 3;
        add(addUnprotected, c);

        Button profitButton = new Button("Show Session Profit");
        profitButton.addActionListener(this);
        
        Button Write = new Button("Write");
        Write.addActionListener(this);
        
        Button Load = new Button("Load");
        Load.addActionListener(this);

        c.gridx = 2;
        c.gridy = 0;
        add(profitButton, c);
        
        c.gridx = 3;
        add(Write, c);
        
        c.gridx = 4;
        add(Load, c);
        
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 3;
        c.ipady = 200;
        c.ipadx = 200;
        add(stockSpecs, c);

        try
        {
            
            image = ImageIO.read(new File("background.jpg"));
        }
        catch(IOException e)
        {
        };
        
        setSize(800, 800);
        setResizable(true);
        setVisible(true);

    }
    //Below is the constructor for the popup window to enter the sotck details
    public GUI(String type)
    {
        super("Add Stock");
        setSize(400,400);
        setVisible(true);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        
        Label name = new Label("Name: ");
        nameT = new TextField("", 10);
        Label price = new Label("Price: ");
        priceT = new TextField("", 10);
        Label quant = new Label("Amount: ");
        quantT = new TextField("", 10);
        
        add(name, c);
        
        c.gridx = 1;
        c.gridy = 0;
        
        add(nameT, c);
        
        c.gridx = 0;
        c.gridy = 1;
        
        add(price, c);
        
        c.gridx = 1;
        c.gridy = 1;
        
        add(priceT, c);
        
        c.gridx = 0;
        c.gridy = 2;        
        
        add(quant, c);
        
        c.gridx = 1;
        c.gridy = 2; 
        
        add(quantT,c);
                
        if(type == "protected")
        {
            c.gridx = 0;
            c.gridy = 4; 
            
            Button protectedButton = new Button("Save Protected Stock");
            add(protectedButton, c);
            protectedButton.addActionListener(this);
            
            c.gridx = 0;
            c.gridy = 3; 
            
            Label protectedStock = new Label("Protection Price: ");
            add(protectedStock, c);
            
            c.gridx = 1;
            c.gridy = 3; 
            
            protectedStockT = new TextField("", 10);
            add(protectedStockT, c);
        }
        
        else
        {
            c.gridx = 0;
            c.gridy = 3; 
            Button unProtectedButton = new Button("Save Stock");
            add(unProtectedButton, c);
            unProtectedButton.addActionListener(this);
        }
    }  
    
    @Override
    public void paint(Graphics background)
    {
       super.paint(background);
       background.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
    
    @Override
    public void stockLoadSave(String keyword)
    {
       File file = new File("/homes/spp30/Mini Project NEW/SaveLoad.txt"); 
       if(keyword.equals("Load"))
       {
           String read = "";
           try (BufferedReader br = new BufferedReader(new FileReader(file))) 
           {
               String line;
               while ((line = br.readLine()) != null)
                    stockSpecs.add(line);
               br.close();
           }
           catch(Exception E)
           {
               read = "error occured reading file";
               printMessageToGui(read);
           };
           //at the moment only shows in the awt ui list - in future this could actually import to the portfolioDisplay instance;
           
       }
       else
       {
           String write = "";
           try (BufferedWriter bw = new BufferedWriter(new FileWriter("SaveLoad.txt"))) 
           {
               String text = getPortfolioNames("FileWrite");
               bw.write(text);
               bw.newLine();
               bw.close();
           }
           catch(Exception E)
           {
               write = "error occured writing to file";
           };
           printMessageToGui("file written ok");
       }
    }
    
    public void actionPerformed(ActionEvent event)
    {
        String ButtonLabel = event.getActionCommand();
        switch(ButtonLabel) 
        {
            case "Show Portfolio": getPortfolioNames("Display");
        break;
        
            case "Add Unprotected Stock": addStock(ButtonLabel);
                break;
                
            case "Add Protected Stock": addStock(ButtonLabel);
                break;
            
            case "Save Protected Stock": addPStock(nameT.getText(), priceT.getText(), quantT.getText(), protectedStockT.getText());
                break;  
            
            case "Save Stock": addUpStock(nameT.getText(), priceT.getText(), quantT.getText()); 
                break;
        
            case "Buy More Stock": buySellStock("BUY");
                break;
            
            case "Sell Stock": buySellStock("SELL");
                break;
                
            case "Show Session Profit": getSessionProfit();
                break;  
        
            case "Write": stockLoadSave("Write");
                break;  
                
            case "Load": stockLoadSave("Load");
                break;   
                
        }   
    }
    
    public static void main(String [] args)
    {
        Frame GUI = new GUI();
        GUI.setVisible(true);
    }
    
    public void addStock(String ButtonLabel)
    {
        if(ButtonLabel == "Add Protected Stock")
            popup = new GUI("protected");
        else    
            popup = new GUI("unprotected");
    }
    
    public void addUpStock(String name, String price, String quant)
    {
        int quantD;
        double priceD;
        try
        {
            quantD = Integer.parseInt(quant);
            priceD = Double.parseDouble(price);
        }
        catch(Exception e)
        {
            printMessageToGui("unsupported operation, action cancelled");
            //System.out.println("unsupported operation, action cancelled");
            return;
        }
        UnProtectedStock newStock = new UnProtectedStock(name, priceD, quantD);
        session1.AddStock(newStock);
        //The below is so the popup gets removed after the stock is added
        this.setVisible(false);
    }
    
    public void addPStock(String name, String price, String quant, String protection)
    {
        int quantD;
        double priceD;
        double protectionD;
        try
        {
            quantD = Integer.parseInt(quant);
            priceD = Double.parseDouble(price);
            protectionD = Double.parseDouble(protection);
        }
        catch(Exception e)
        {
            printMessageToGui("unsupported operation, action cancelled");
            return;
        }
        ProfitProtectedStock newStock = new ProfitProtectedStock(name, priceD, quantD, protectionD);
        int code = newStock.checker(newStock);
        if(code != -1)
        {   session1.AddStock(newStock);
            ProtectedAttributes newStock2 = new ProtectedAttributes(name, priceD, quantD, protectionD);
            session1.ProtectionAttributes(newStock2);
        }
        //The below is so the popup gets removed after the stock is added
        this.setVisible(false);
    }
    
    public void buySellStock(String action)
    {
       //System.out.println("Enter Stock Name To Buy / Sell");
       //Below we get the selected item from the list, i.e. the stock name
       String searchNameUntrimmed = stockSpecs.getSelectedItem();
       String searchName = null;
       //We now trim the string to remove the "Name: " prefix
       try {
            searchName = searchNameUntrimmed.substring(6);
        }
       catch (Exception e) {
            printMessageToGui("No Stock Selected");
            return;
       };
       int indexOfShare = session1.getStockIndex(searchName);
       int amount;
       if(indexOfShare == -1) //this corresponds to our error value if stock isnt found
       {     
            //System.out.println("Not Found");
            printMessageToGui("Stock Not Found");
            return;
       }     
       //System.out.println("What Do You Want To Do, Type BUY or SELL");
       String actionToTake = action;
       String amountS = JOptionPane.showInputDialog(null, actionToTake + " How Many? You Currently Have " + session1.getStockAmount(indexOfShare));
       amount = parserInt(amountS);
       if(amount == -1)
            return;
       if(actionToTake.equals("BUY"))
       {
            
            session1.Buy(indexOfShare, amount);
       } 
       else if(actionToTake.equals("SELL"))
       {
            if(amount > session1.getStockAmount(indexOfShare) || amount < 0)
                printMessageToGui("Action disallowed, you want to sell more of the stock than you actually have or you have entered an invalid amount to sell, you naughty trader, TRANSACTION CANCELLED");
            else
            {
                //in the below we check to see if the stock is protected or not - if not protected return value will be -1 (or error value / not found)
                if(session1.getProtectedStockIndex(session1.getStockName(indexOfShare)) == -1)
                {    
                    String sellPriceS = JOptionPane.showInputDialog(null, "Enter SELL Price");
                    sellPrice = parserDouble(sellPriceS);
                    if(sellPrice == -1.0)
                        return;
                    profit += session1.Sell(indexOfShare, amount, sellPrice);
                    //in the below we want to destory the stock from our portfolio if none left
                    if(session1.getStockAmount(indexOfShare) == 0)
                         session1.shufflePortfolioDisplay(indexOfShare);   
                }
                //the below would mean that the stock has been found in the protectedattributes array
                else
                    printMessageToGui("Action disallowed, you want to sell a protected stock, you naughty trader, TRANSACTION CANCELLED");
            }
       }    
    }
   
    public static int parserInt(String toParse)
    {
       int parsed;
       try
       {
            parsed = Integer.parseInt(toParse);
       }
       catch(Exception e)
       {
            printMessageToGui("Action disallowed, bad input");
            //Below we return the error value which then goes on to cancel the 
            return(-1);
       }
       return(parsed);
    }
   
    public static double parserDouble(String toParse)
    {
       double parsed;
       try
       {
            parsed = Double.parseDouble(toParse);
       }
       catch(Exception e)
       {
           printMessageToGui("Action disallowed, bad input");
            //Below we return the error value which then goes on to cancel the 
            return(-1.0);
       }
       return(parsed);
    }
   
    public static void getSessionProfit() {
       
       JOptionPane.showMessageDialog(null, "Session PROFIT is: " + profit); 
    }
    
    public static void printMessageToGui(String message) {
       stockSpecs.clear();   
       stockSpecs.add(message); 
    }
    
    public String getPortfolioNames(String action) {
        String text = "";
        Stock [] portfolios = session1.getPortfolioNames();
        ArrayList<ProtectedAttributes> arrayOfProtection = session1.getProtectedStock();
        int protection;
        if(action.equals("Display"))
            stockSpecs.clear();
        try 
        {
            for(Stock stockToRead : portfolios)
            {
                String name = stockToRead.shareName;
                double price = stockToRead.sharePrice;
                int amount = stockToRead.amountOfShare;
                double protectionPrice = 0;
                
                protection = session1.getProtectedStockIndex(name);
                
                if(protection != - 1)
                    protectionPrice = arrayOfProtection.get(protection).protectionSellPrice;
                
                if(action.equals("Display"))
                {
                    stockSpecs.add("Name: " + name);               
                    stockSpecs.add("Price: " + price);
                    stockSpecs.add("Amount: " + amount);
                    stockSpecs.add("Protection?: " + protectionPrice);
                    stockSpecs.add("....");
                    stockSpecs.add("Next stock follows");
                    stockSpecs.add("....");
                    text = "ok";
                }
                if(action.equals("FileWrite"))
                {
                    text += "Name: " + name + "\r\n" + "Price: " + price + "\r\n" + "Amount: " + amount + "\r\n" + "Protection?: " + protectionPrice + "\r\n" + "...." + "\r\n" + "Next stock follows" + "\r\n" + "...." +"\r\n";
                }
            }
        }
        catch(Exception e)
        {
            text = "error occured in looping through your stock library";
        }
        return(text);
    }
}

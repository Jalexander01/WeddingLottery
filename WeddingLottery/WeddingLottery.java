package WeddingLottery; /**
   Programming Challenge 20-5.
   Wedding Lottery I.
*/
import java.util.*;

/**
   The LotteryList class represents the list of suitors, and the 
   operations that can be performed on that list.
*/

class LotteryList
{    
    private LinkedList<String> hopefuls;    // Suitors still under consideration
    private LinkedList<String> eliminated;  // Suitors already rejected
    private int position; // Current position of the princess
    int [ ] steps;        // Rotate steps
    
    /**
        getHopefuls.
        @return The list of suitors still under consideration.
    */    
    public List<String> getHopefuls()
    {
        return hopefuls;
    }
    
    /**
     * alias for the above method
     * @return 
     */
    public List<String> getRemainingSuitors()
    {
        return getHopefuls();
    }
    
    /**
       getEliminated.
       @return  The list of suitors already eliminated.
    */     
    public List<String> getEliminated()
    {
       return eliminated;
    }
    
    /**
       rotate.
       Performs a rotation of the suitors remaining through a single step.
       @param step The size  of  the step to rotate.
    */
    
    public void rotateSingleStep(int step)
    {
        position = (position + step) % hopefuls.size();
    }
    
    /**
       Reject the suitor at the current position
     */    
    public void reject()
    {
        if (hopefuls.size() == 0)
            throw new IllegalStateException("There are no suitors to reject");
        String sadGuy = hopefuls.remove(position);
        eliminated.add(sadGuy);
        
        //
        for (int k = 0; k < hopefuls.size(); k++)
            System.out.print(hopefuls.get(k) + " ");
        System.out.println();
    } 
    
    public void rotateThroughAllSteps()
    {
         // See if there are  steps to rotate.
        if (steps != null)        
        {
           for (int k = 0; k < steps.length; k++) 
           {
               rotateSingleStep(steps[k]);
               reject();
           }            
        }
    }
    
    public LotteryList(Collection<String> suitors, int [ ] steps)
    {
        hopefuls = new LinkedList<String>(suitors);
        eliminated = new LinkedList<String>();
        position = 0;
        this.steps = steps;      
    }   
}


public class WeddingLottery 
{
    
    /** Creates a new instance of WeddingLottery 
        and demonstrates its operation.
    */
    
    public static void  main(String [] arg) 
    {
        // Create list of suitors
        List<String> suitors = new LinkedList<String>();
        String [ ] bachelors = {"A", "B", "C", "D"};
        for (int k = 0; k < bachelors.length; k++)
            suitors.add(bachelors[k]);
        System.out.printf("List of suitors is %s\n", Arrays.toString(bachelors));
		
        // Elimination steps
        int [ ] steps = {0, 2, 1};
        System.out.printf("List of elimination steps is %s\n\n", Arrays.toString(steps));
		
        // Create the lottery list and have it work
        LotteryList ll = new LotteryList(suitors, steps);
        ll.rotateThroughAllSteps();
        
        // Enjoy the results        
        List<String> rejected = ll.getEliminated();
		System.out.printf("\nThe rejected suitors are %s\n", rejected);
        
        System.out.println();
     
        List<String> hopefuls = ll.getHopefuls();
		System.out.printf("The remaining suitors are %s\n", hopefuls);        
             
    }   
}

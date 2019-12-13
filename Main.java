import java.util.*;
import java.lang.*;

class MultithreadingDemo extends Thread
{
  int r1, t1;
  static int p1=0;
  public int newer()
  {
    Random rand = new Random();
    r1 = rand.nextInt(10);
    Random rant = new Random();
    Paging.pid++;
    t1= 1000;
    t1 = t1 + rant.nextInt(3)*1000;
      // System.out.println("Tiemr Delay : " + t1);
      // try{
      // Thread.sleep(t1);}
      // catch (Exception e) {
      //       System.out.println(e);
      // }
      if (r1 == 0)
        r1=1;
    return r1;
  }
  public int timer()
  {
      return t1;
  }
  public void run()
  {
      try
      {
  			System.out.println ("Thread " + Thread.currentThread().getId() + " is running");
  		}
  		catch (Exception e)
      {
  			System.out.println ("Exception is caught");
  		}
  }
}

class Paging
{
  public static int pid;
	public void Paging()
	{
    Scanner sc = new Scanner(System.in);
    System.out.println("*********Enter the time for Simulation:**********");
    int time = sc.nextInt();
    int Main_memory[] = new int[32];
    int proccess_size,no_of_pages,proccess_time;
    System.out.println("=>Enter Page Size : ");
    int PageSize = sc.nextInt();
    if(PageSize >= 32)  PageSize=32;
    int availablePages;
    int NumberofBlocks = Main_memory.length/PageSize;
    int Block[] = new int[NumberofBlocks];
    for(int i=0;i<NumberofBlocks;i++) Block[i] = -1;
    //System.out.println("NumberofBlocks : " + NumberofBlocks );
    MultithreadingDemo object = new MultithreadingDemo();
    int index_of_process=0,block_count = 0;
    int y=0;
    pid =0;
    object.start();
    Queue<Integer> q= new LinkedList<Integer>();
    for(int i=0;i<time;i++)
    {
      try{
      Thread.sleep(1000);}
      catch (Exception e) {
            System.out.println(e);
      }
      HashMap<Integer, Integer> storesize = new HashMap<Integer,Integer>();
      HashMap<Integer, Integer> storetime = new HashMap<Integer,Integer>();
      HashMap<Integer, Integer> PageTable = new HashMap<Integer,Integer>();
      HashMap<Integer, Integer> FreePageTable = new HashMap<Integer,Integer>();
      List<HashMap<Integer, Integer>> Proccess = new ArrayList<HashMap<Integer, Integer>>();
      List<Integer> Proc = new ArrayList<Integer>();
      proccess_size = object.newer();
      proccess_time = object.timer();
      System.out.println("\n********************Process Details :************************** ");
      System.out.println("Pid :" + pid + " | ProcessSize :" + proccess_size + " |  ProcessTime  : " + proccess_time );
      System.out.println("**********************End Process Details: ***********************\n");
      if(proccess_size%PageSize == 0)  no_of_pages = proccess_size/PageSize;
      else  no_of_pages = proccess_size/PageSize + 1;
      storesize.put(pid, proccess_size);
      storetime.put(pid, proccess_time);
      int flag = 0;
      if(pid == i+1)
      {
        for(int j=0;j<NumberofBlocks;j++)
        {
            if(no_of_pages <= 0 )
            {
              break;
            }
            else if(no_of_pages > 0 )
            {
              no_of_pages--;
              block_count++;
            }
            if(NumberofBlocks - block_count < proccess_size)
            {
              flag = -1;
            }
            PageTable.put(j,block_count);
            Proccess.add(PageTable);
        }
      }
      if(flag == -1)
      {
        q.add(pid);
      }
      availablePages = NumberofBlocks - block_count;
      for(int k=1;k<=i;k++)
      {
        Integer strtime=0;
        System.out.println("key :: " + storetime.containsKey(k+1));
        if(storetime.containsKey(k+1))
          strtime = storetime.get(k+1);
        System.out.println("strtime : " + strtime/1000);
        if(i == k+1 + strtime)
        {
          for(int x=0;x<Proccess.get(0).size();x++ )
          {
            if(Proccess.get(0).containsKey(x));
              Proccess.get(0).put(x,-1);
          }
          for (Map.Entry<Integer,Integer> entry : PageTable.entrySet())
          {
            System.out.println("Key : " + entry.getKey()+ "\tValue : " + entry.getValue());
          }
        }
      }
        if(NumberofBlocks - block_count < proccess_size)
            {
              flag = -1;
            }
        System.out.println("\n\t***********Page Table of Process PID : "+ pid+"**************** \t");
        for (Map.Entry<Integer,Integer> entry : PageTable.entrySet())
        {
          System.out.println("Key : " + entry.getKey()+ "\tValue : " + entry.getValue());
        }
        System.out.println("\t***********End of Page Table of Process with PID :"+ pid+"**************** \t\n");
        System.out.println("\n\t***********Free Page Table : **************** \t" );
        for(int k=block_count+1;k<NumberofBlocks;k++)
        {
          if(Block[k]==-1)
          {
            System.out.println("Block : " + k );// "block_count" + block_count);
          }
        }
        System.out.println("\t***********End of Free Page Table : **************** \t\n" );
    }
	}
}

public class Main
{
  public static void main(String[] args)
  {
    Paging pg = new Paging();
    Memory sg = new Memory();
    Scanner scan = new Scanner(System.in);
    int choice=0;
    do
    {
      System.out.println("=================Please enter your choice.================");
      System.out.println("===>1.Paging");
      System.out.println("===>2.Segmentation");
      System.out.println("===>3.Exit");
      choice = scan.nextInt();
      switch(choice)
        {
          case 1:
            pg.Paging();
            break;
          case 2:
            sg.Segmentation();
            break;
          case 3:
            System.out.println("Exited Successfully");
            break;
          }
     }while(choice!=3);
  }
}

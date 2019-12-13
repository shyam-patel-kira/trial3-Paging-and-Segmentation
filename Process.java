public class Process
{
  public int proc_id; 
  public int proc_size;
  public int text;
  public int data;
  public int heap;
  public int pages;
  public int Ext;
  public int Frag;
  public void setid(int val)
  {
    proc_id=val;
    System.out.println("Process ID:"+proc_id);
  }
  public Process(int low,int high)
  {
    int lower,higher;
    double db = Math.random()*(high-low)+low;
    int k=1,sum=0;
    proc_size=(int) db;
  
    if(proc_size%32==0)
    {                 //Process fits into the frame or multiple frames correctly
        pages=proc_size/32; 
    }
    else
    {
	      Ext=proc_size%32;
        pages=(proc_size/32)+1;
        Frag=32-Ext;
    }
        lower=(proc_size/3)/2;
        higher=(proc_size/3)*2;                   
        int total=proc_size;
    while(k==1) 
    {
        double a = Math.random()*(higher-lower)+lower;  
        text=(int) a;
        total =total-text;     
        double b = Math.random()*(higher-lower)+lower;  
        data=(int) b;
        total =total- data;     
        if(total<=higher && total>=lower)
        {
            heap=total;
            k=0;
        }
        else
        {
            total=proc_size;    
        }
                  //repeating the procedure until random no.s generated satisfies the condition.
   }
        System.out.println("Process size:"+proc_size);
        System.out.println("Text size:"+text);
        System.out.println("Data Size:"+data);
        System.out.println("Heap Size:"+heap);
  }
}
import java.lang.reflect.Array;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Scanner;
class Memory
{
	static int memory_size=1024,total,low,high,process_no;
	static int first_fit_flag[]=new int[20];
  static int best_fit_flag[]=new int[20];
  static int Pframe=memory_size/32;       //Pframe : No of frames-- Page Size :32
	static int worst_fit_flag[]= new int[20];
	static int FreeBlock[] = new int[20];

	public void Segmentation()
	{
		int k=1;
		System.out.println("Enter number of free blocks:");
		Scanner in = new Scanner(System.in);
		int free_block= in.nextInt();                     //ENTERING NUMBER OF HOLES
	    if(free_block==0)
	    {
	    	System.out.println("Re-enter the number of free blocks:");
	    	free_block = in.nextInt();
	    }
		  total=memory_size;
	    low=(total/free_block)/2;
	    high=(total/free_block)*2;
      System.out.println("low = "+low);
      System.out.println("high = "+high);

	    while(k==1)
	    {
	        for(int i = 0;i<(free_block-1);i++)
            {
                double db = Math.random()*(high-low)+low;  //RANDOMLY GENERATING FreeBlock SIZES-----low is  inclusive---high is exlusive
                FreeBlock[i]=(int) db;
                total -= FreeBlock[i];
            }
	        FreeBlock[free_block-1]=total;
	        if(total>=low&&total<=high)
	        	k=0;
	        else
	        	total=memory_size;
	    }
	    for(int i=0;i<free_block;i++)
		  {
			   System.out.print("Size of free block-"+(i)+" - "); //DISPLAYING GENERATED FREE BLOCK SIZES
				 System.out.println(FreeBlock[i]);
		  }

        int maxsize = maxValue(FreeBlock);
        int minsize = minValue(FreeBlock);

      System.out.println("Free block with Maximum size= "+maxsize);
	    System.out.println("Enter number of processes:");  //ENTER NUMBER OF PROCESSES
    	Scanner inc = new Scanner(System.in);
	    process_no = inc.nextInt();
  		Process[] P=new Process[process_no];

	    for(int i=0;i<process_no;i++)
	      {
	         System.out.println("Process - "+(i+1));
	         P[i]=new Process(minsize,maxsize);
	      }
	    System.out.println("No. of processes = "+process_no);
		int ch;			//ASKING FOR THE KIND OF ALLOCATION
		do
		{
			System.out.println("*********************Select the method of your choice*********************");
	    	  System.out.print("\n\t1.Segmentation\n\t2.Exit\n");
	    		Scanner chk= new Scanner(System.in);
	    		   ch=chk.nextInt();
              switch (ch)
              {
								case 1:
			          int choice;
			      do {
			    	   System.out.println("Select the allocation method of your choice");
			    	   System.out.print("\n1.First fit\n2.Best fit\n3.Worst Fit\n4.exit\n");
			    	   Scanner cho= new Scanner(System.in);
			    	   choice=cho.nextInt();
                       switch (choice)
                        {
					        case 1:
						        firstfit(P,FreeBlock,process_no,free_block,first_fit_flag);
						        break;
					        case 2:
						        bestfit(P, FreeBlock, process_no, free_block, best_fit_flag);
						        break;
					        case 3:
						        worstfit(P, FreeBlock, process_no, free_block, worst_fit_flag);
						        break;
					        case 4:
						        System.out.println("\nExited Successfully...\n");
						        break;
					        default:
						        System.out.println("Invalid choice\n");
						        break;
					}
				} while (choice!=4);
				break;
			case 2:
				System.out.println("\n Exited Successfully...\n");
				break;
			default:
				System.out.println("Invalid choice...");
				break;
			}
		}while(ch!=2);
	}

    static int maxValue(int[] FreeBlock)
    {
		    int max = FreeBlock[0];
        for (int ktr = 0; ktr < FreeBlock.length; ktr++)
        {
            if (FreeBlock[ktr] > max)
            {                         //FINDING MAX VALUE OF FreeBlock ARRAY
				      max = FreeBlock[ktr];
			      }
		    }
		  return max;
	  }
    static int minValue(int[] FreeBlock)
    {
		    int min = FreeBlock[0];
        for (int ktr = 0; ktr < FreeBlock.length; ktr++)
        {
            if (FreeBlock[ktr] < min)
            {                         //FINDING MIN VALUE OF FreeBlock ARRAY
				      min = FreeBlock[ktr];
			      }
		    }
		   return min;
	}

    static void firstfit(Process P[],int H[],int nproc,int nholes,int flag[])
    {
		int i,j=0,frag=0;
        int count=0;
		for(i=0;i<nproc;i++)
		{
			while(j<nholes)
			{
				if(P[i].text<=H[j] && flag[j]==0)
				{
					frag+=H[j]-P[i].text;
					flag[j]=1;
					count++;
					System.out.println("First Fit: Process- "+(i+1)+" text allocated in free block-"+ j);
					break;
				}
				else
				{
					j++;               // going to the next hole
				}
			}
		        j=0;
				while(j<nholes)
				{
					if(P[i].data<=H[j] && flag[j]==0)
				 	{
						frag+=H[j]-P[i].data;
						flag[j]=1;
						count++;
						System.out.println("First Fit: Process- "+(i+1)+"data allocated in free block-"+ j);
						break;
				 	}
				    else
				    {
				        j++;
				    }
				}
				j=0;
				while(j<nholes)
				{	//ALLOCATING HEAP SECTION
					 if(P[i].heap<=H[j] && flag[j]==0)
					{
						frag+=H[j]-P[i].heap;
						flag[j]=1;
						count++;
						System.out.println("First Fit: Process- "+(i+1)+"heap allocated in free block-"+ j);
						break;
					}
             else
             {
				    	 j++;
				     }
				}
             if(j==nholes)
             {
			    			while(i<nproc)
			    			{
			        		System.out.println("Process"+(i+1)+" cannot be allocated");
			    				i++;
			    			}
			    				break;
		     		}
		   }
		System.out.println("The resulting Fragmentation is : "+frag);
	}


    static void bestfit(Process P[],int H[],int nproc,int nholes,int flag[])
    {
		int min=1000,loc=0,j,i,frag=0,temp, k=1,count=0;
		for(i=0;i<nproc;i++)
		{					//FINDING THE BEST FIT FOR THE TEXT SECTION OF PROCESS
		    for( j=0;j<nholes;j++)
		    {
		    	temp=H[j];
		    	if(flag[j]==0 && P[i].text<=temp)
		    	{
		    		if(min>=temp)
		    		{
		    			min=temp;
		    			loc=j;
		    		}
		    	}
		    }
		  	if(k==1 && count<nholes && flag[loc]==0)
		    {

		  		flag[loc]=1;
		  		count++;
		  		frag=frag+(H[loc]-P[i].text);
		         	System.out.println("Best Fit: Process- "+(i+1)+"text allocated in free block-"+(loc));
		         	k=0;
		    }

		  	min=1000;
		  	loc=0;
		  	k=1;
		  //FINDING THE BEST FIT FOR THE DATA SECTION OF PROCESS
			    for( j=0;j<nholes;j++)
			    {
			    	temp=H[j];
			    	if(flag[j]==0 && P[i].data<=temp)
			    	{
			    		if(min>=temp)
			    		{
			    			min=temp;
			    			loc=j;
			    		}
			    	}
			       // k=1;
			    }
			  	if(k==1&& count<nholes && flag[loc]==0)
			    {
			    		flag[loc]=1;
	         			frag=frag+(H[loc]-P[i].data);
	         			count++;
			         	System.out.println("Best Fit: Process- "+(i+1)+"data allocated in free block-"+(loc));
			         	k=0;
			    }

				  min=1000;
			  	loc=0;
			  	k=1;
			  //FINDING THE BEST FIT FOR THE HEAP SECTION OF PROCESS
				    for(j=0;j<nholes;j++)
				    {
				    	temp=H[j];
				    	if(flag[j]==0 && P[i].heap<=temp)
				    	{
				    		if(min>=temp)
				    		{
				    			min=temp;
				    			loc=j;
				    		}
				    	}
				    }
				  	if(k==1&& count<nholes && flag[loc]==0)
				    {
				    		flag[loc]=1;
		         			frag=frag+(H[loc]-P[i].heap);
		         			count++;
				         	System.out.println("Best Fit : Process- "+(i+1)+"heap allocated in free block-"+(loc));
				         	k=0;
				    }
				  	min=1000;
				  	loc=0;
				  	k=1;
				  	if(count==nholes)
				  	{
				  		System.out.println("Process: "+(i+1)+ "Cannot be allocated as memory is full.");
				  	}
		}
		System.out.println("The resulting Fragmentation is : "+frag);
	}

    static void worstfit(Process P[],int H[],int nproc,int nholes,int flag[])
    {
		int max=0,loc=0,j,i,frag=0,temp, k=1,count=0;
		for(i=0;i<nproc;i++)
		{			//FINDING THE WORST FIT FOR THE TEXT SECTION OF PROCESS
		    for( j=0;j<nholes;j++)
		    {
		    	temp=H[j];
		    	if(flag[j]==0 && P[i].text<=temp)
		    	{
		    		if(max<=temp)
		    		{
		    			max=temp;
		    			loc=j;
		    		}
		    	}
		       // k=1;
		    }
		  	if(k==1 && count<nholes && flag[loc]==0)
		    {

		  		    flag[loc]=1;
		  			  count++;
		  		    frag=frag+(H[loc]-P[i].text);
		         	System.out.println("Worst Fit: Process- "+(i+1)+"text Allocated in free block-"+(loc));
		         	k=0;
		    }

		  	max=0;
		  	loc=0;
		  	k=1;
		  //FINDING THE WORST FIT FOR THE DATA SECTION OF PROCESS
			    for( j=0;j<nholes;j++)
			    {
			    	temp=H[j];
			    	if(flag[j]==0 && P[i].data<=temp)
			    	{
			    		if(max<=temp)
			    		{
			    			max=temp;
			    			loc=j;
			    		}
			    	}
			    }
			  	if(k==1&& count<nholes && flag[loc]==0)
			    {
			    		flag[loc]=1;
	         			frag=frag+(H[loc]-P[i].data);
	         			count++;
			         	System.out.println("Worst Fit: Process- "+(i+1)+"data allocated in free block-"+(loc));
			         	k=0;
			    }

				  max=0;
			  	loc=0;
			  	k=1;

			  //FINDING THE WORST FIT FOR THE HEAP SECTION OF PROCESS
				    for( j=0;j<nholes;j++)
				    {
				    	temp=H[j];
				    	if(flag[j]==0 && P[i].heap<=temp)
				    	{
				    		if(max<=temp)
				    		{
				    			max=temp;
				    			loc=j;
				    		}
				    	}
				    }
				  	if(k==1&& count<nholes && flag[loc]==0)
				    {
				    		flag[loc]=1;
		         			frag=frag+(H[loc]-P[i].heap);
		         			count++;
				         	System.out.println("Worst Fit: Process-"+(i+1)+"heap allocated in free block-"+(loc));
				         	k=0;
				    }
				  	max=0;
				  	loc=0;
				  	k=1;
				  	if(count==nholes)
				  	{
				  		System.out.println("Process: "+(i+1)+"cannot be allocated due to insufficient memory.");
				  	}
		}
		System.out.println("The resulting Fragmentation is : "+frag);
	}
}

import java.io.*;
import java.util.*;

public class Solution {
	
	static int win = 0;
	static int Gyu[];
	static boolean visit_permutaion[];

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for(int ii=1; ii<=T; ii++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			Gyu = new int[9];
			boolean visit[] = new boolean[19];
			
			for(int i=0; i<9; i++)
			{
				Gyu[i] = Integer.parseInt(st.nextToken());
				visit[Gyu[i]] = true;
			}
			
			int Select[] = new int[9];	//이 안에서 숫자 고름
			int count = 0;
			
			for(int i=1; i<19; i++)
			{
				if(visit[i] == false)
				{
					Select[count] = i;
					count++;
				}
				if(count == 9)
					break;
			}
			
			win = 0;
			int In[] = new int[9];		//최종 배열
			visit_permutaion = new boolean[9];
			
			DFS(In, Select, 0);
			
			System.out.println("#"+ii+" "+win+" "+(362880-win));
		}
		br.close();
	}

	static void DFS(int In[], int Select[], int count)
	{
		if(count == 9)
		{
			int gy = 0;
			int in = 0;
			
			for(int i=0; i<9; i++)
			{
				int temp = Gyu[i]+In[i];
				if(Gyu[i] > In[i])
					gy += temp;
				else
					in += temp;
			}
			
			if(gy > in)
				win++;
		}
		
		for(int i=0; i<9; i++)
		{
			if(visit_permutaion[i] == true)
				continue;
			
			visit_permutaion[i] = true;
			In[count] = Select[i];
			DFS(In, Select, count+1);
			visit_permutaion[i] = false;
		}
	}
}

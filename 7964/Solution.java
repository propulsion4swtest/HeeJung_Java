import java.io.*;
import java.util.*;

public class Solution {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int ii=1; ii<=T; ii++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int D = Integer.parseInt(st.nextToken());
			
			int map[] = new int[N];
			st = new StringTokenizer(br.readLine());
			
			for(int i=0; i<N; i++)
				map[i] = Integer.parseInt(st.nextToken());
			///입력 완료
			
			int idx = 0;
			int result = 0;		//세울 차원관문
			int dist = 0;		//거리
			
			while(idx < N)
			{
				if(map[idx] == 0)
				{
					dist++;
					if(dist == D)
					{
						map[idx] = 1;
						dist = 0;
						result++;
					}
				}
				else
					dist = 0;
				
				idx++;
			}
			
			//N+1 처리하기
			idx = N-1;
			dist = 0;
			while(idx >= 0)
			{
				if(map[idx] == 0)
				{
					dist++;
					if(dist == D)
					{
						map[idx] = 1;
						result++;
						break;
					}
				}
				else
					break;
				idx--;
			}
			
			System.out.println("#"+ii+" "+result);
		}
		
		br.close();
	}

}

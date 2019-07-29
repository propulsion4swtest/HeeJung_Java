import java.util.*;
import java.io.*;

public class Solution {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine().trim());
		for(int ii=1; ii<=T; ii++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int N = Integer.parseInt(st.nextToken());
			int L = Integer.parseInt(st.nextToken());
			
			int map[][] = new int[N][N];
			
			for(int i=0; i<N; i++)
			{
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<N; j++)
					map[i][j] = Integer.parseInt(st.nextToken());
			}
			
			//---------------초기화 완료
			
			int total = 0;		//길의 수
			
			//-----가로방향으로 탐색
			for(int i=0; i<N; i++)
			{
				int count = 1;				//평지가 얼마나 연속되어있나
				boolean flag = true;
				boolean downflag = false;

				int prev = map[i][0];
				int cur;
				
				//가로줄 판단
				for(int j=1; j<N; j++)
				{
					cur = map[i][j];
					if(Math.abs(prev-cur) > 1)	//차이가 2 이상이면 탈출
					{
						flag = false;
						break;
					}
					else if(prev-cur == -1)	//올라가는 경우
					{
						if(count < L || downflag == true)	//평지가 경사로보다 작다면 + 다운플래그있으면 꽝
						{
							flag = false;					//거짓 플래그 세우고 탈출
							break;
						}
						count = 1;		//평지 세는수 초기화
						prev = cur;
						continue;
					}
						
					if(prev-cur == 1)	//내려가는 경우
					{		
						if(downflag == true)
						{
							flag = false;
							break;
						}
						count = 0;
						downflag = true;
					}

					count++;
					if(downflag == true && count == L)
					{
						downflag = false;
						count = 0;
					}
					
					prev = cur;
				}
				
				if(downflag == true)	flag = false;
				if(flag == true)	total++;				//이번층에 있는지 판단
			}//---가로방향 끝
			
			//-----세로방향으로 탐색
			for(int i=0; i<N; i++)
			{
				int count = 1;				//평지가 얼마나 연속되어있나
				boolean flag = true;
				boolean downflag = false;

				int prev = map[0][i];
				int cur;
				
				//세로줄 판단
				for(int j=1; j<N; j++)
				{
					cur = map[j][i];
					if(Math.abs(prev-cur) > 1)	//차이가 2 이상이면 탈출
					{
						flag = false;
						break;
					}
					else if(prev-cur == -1)	//올라가는 경우
					{
						if(count < L || downflag == true)	//평지가 경사로보다 작다면 + 다운플래그있으면 꽝
						{
							flag = false;					//거짓 플래그 세우고 탈출
							break;
						}
						count = 1;		//평지 세는수 초기화
						prev = cur;
						continue;
					}
						
					if(prev-cur == 1)	//내려가는 경우
					{	
						if(downflag == true)
						{
							flag = false;
							break;
						}
						count = 0;
						downflag = true;
					}

					count++;
					if(downflag == true && count == L)
					{
						downflag = false;
						count = 0;
					}
					
					prev = cur;
				}
				
				if(downflag == true)	flag = false;
				if(flag == true)	total++;				//이번층에 있는지 판단
			}//---세로방향 끝
			
			System.out.println("#"+ii+" "+total);
		}
		
	}

}
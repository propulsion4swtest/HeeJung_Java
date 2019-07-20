import java.io.*;
import java.util.*;

public class Solution {
	
	static final int in_dr[] = {1, -1, 0, 0};	//상하좌우가 쓸 것 - 이동방향은 하상우좌
	static final int in_dc[] = {0, 0, 1, -1};
	static final int out_dr[] = {0, 0, 1, 1};	//외부 루프: 상하는 col 이동, 좌우는 row 이동
	static final int out_dc[] = {1, 1, 0, 0};

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int ii=1; ii<=T; ii++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			String dir_str = st.nextToken();
			int dir, row, col;		//방향, 시작지점 행렬
			
			if(dir_str.equals("up"))		{dir = 0;	row = 0;	col = 0;}
			else if(dir_str.equals("down"))	{dir = 1;	row = N-1;	col = 0;}
			else if(dir_str.equals("left"))	{dir = 2;	row = 0;	col = 0;}
			else							{dir = 3;	row = 0;	col = N-1;}
			
			int map[][] = new int[N][N];
			
			for(int i=0; i<N; i++)
			{
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<N; j++)
					map[i][j] = Integer.parseInt(st.nextToken());
			}
			
			pull(dir, map, N);
			
			//합치기
			for(int i=0; i<N; i++)
			{
				int in_row = row;
				int in_col = col;
				
				for(int j=1; j<N; j++)
				{
					int prev = map[in_row][in_col];
					int cur = map[in_row+in_dr[dir]][in_col+in_dc[dir]]; 
					
					if(prev == cur)
					{
						map[in_row][in_col] *= 2;
						map[in_row+in_dr[dir]][in_col+in_dc[dir]] = 0;
					}
					
					in_row += in_dr[dir];
					in_col += in_dc[dir];
				}
				
				row += out_dr[dir];
				col += out_dc[dir];
			}
			
			//땡기기
			pull(dir, map, N);
			
			System.out.println("#"+ii);
			
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<N; i++)
			{
				for(int j=0; j<N; j++)
					sb.append(map[i][j]+" ");
				sb.append("\n");
			}
			
			System.out.print(sb);
		}
		
		br.close();
	}

	static void pull(int dir, int map[][], int N)		//해당 방향으로 0이 없게 땡기는 메소드
	{
		int row, col;
		if(dir == 0)		{row = 0;	col = 0;}
		else if(dir == 1)	{row = N-1;	col = 0;}
		else if(dir == 2)	{row = 0;	col = 0;}
		else				{row = 0;	col = N-1;}
		
		for(int i=0; i<N; i++)
		{
			int in_row = row;
			int in_col = col;
			
			for(int j=0; j<N; j++)
			{
				int cur = map[in_row][in_col];
				if(cur != 0)
				{
					in_row += in_dr[dir];
					in_col += in_dc[dir];
					continue;
				}
				else
				{
					int temp_row = in_row + in_dr[dir];
					int temp_col = in_col + in_dc[dir];
					
					while(temp_row < N && temp_col < N && temp_row >= 0 && temp_col >= 0)
					{
						if(map[temp_row][temp_col] != 0)
						{
							map[in_row][in_col] = map[temp_row][temp_col];
							map[temp_row][temp_col] = 0;
							break;
						}
						
						temp_row += in_dr[dir];
						temp_col += in_dc[dir];
						
					}
					
					in_row += in_dr[dir];
					in_col += in_dc[dir];
				}
			}
			
			row += out_dr[dir];
			col += out_dc[dir];
		}
	}
}

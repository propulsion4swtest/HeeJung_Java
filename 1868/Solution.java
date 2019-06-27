import java.io.*;
import java.util.*;

public class Solution {

	static int[][] map;
	static final int dr[] = {-1, -1, -1, 0, 0, 1, 1, 1};
	static final int dc[] = {-1, 0, 1, -1, 1, -1, 0, 1};
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int ii=1; ii<=T; ii++)
		{
			int N = Integer.parseInt(br.readLine());
			map = new int[N][N];		//지뢰는 -1, 아닌건 -2
			int total = N*N;	//지뢰 아닌 개수 카운트
			
			for(int i=0; i<N; i++)
			{
				String temp = br.readLine();
				for(int j=0; j<N; j++)
				{
					char ch = temp.charAt(j);
					int value = 0;
					
					if(ch == '*')
					{
						value = -1;
						total--;
					}
					
					map[i][j] = value;
				}
			}

			//입력 완료
			
			//숫자 다 찾아놓기
			for(int i=0; i<N; i++)
			{
				for(int j=0; j<N; j++)
				{
					if(map[i][j] == -1)
						continue;
					
					for(int k=0; k<8; k++)
					{
						int nr = i+dr[k];
						int nc = j+dc[k];
						
						if(nr >= N || nr < 0 || nc >= N || nc < 0)
							continue;
						
						if(map[nr][nc] == -1)
							map[i][j]++;
					}
				}
			}
			
			int result = 0;
			boolean visit[][] = new boolean[N][N];
			
			for(int i=0; i<N; i++)
			{
				for(int j=0; j<N; j++)
				{
					//0 주변만 탐색
					if(map[i][j] == 0 && visit[i][j] == false)
					{
						result++;
						//BFS 시작
						
						Queue<Point> q = new LinkedList<Point>();
						q.add(new Point(i, j));
						visit[i][j] = true;
						total--;
						
						while(!q.isEmpty())
						{
							Point temp = q.poll();
							
							for(int k=0; k<8; k++)
							{
								int nr = temp.r+dr[k];
								int nc = temp.c+dc[k];
								
								if(nr >= N || nc >= N || nr < 0 || nc < 0)
									continue;
								
								if(visit[nr][nc] == true || map[nr][nc] == -1)
									continue;
									
								visit[nr][nc] = true;
								total--;
								
								if(map[nr][nc] == 0)
									q.add(new Point(nr, nc));
							}
						}
					}//if문 끝
				}
			}
			
			//total은 0에 의해 탐색이 안된 개수가 남음
			//0으로 인해 탐색한 수(result) + 탐색 안된 0이 아닌 수(total)
			
			System.out.println("#"+ii+" "+(result+total));
		}
		
		br.close();
	}

}

class Point{
	int r;int c;
	Point(int r, int c){this.r = r; this.c = c;}
}
import java.util.*;
import java.io.*;
import java.awt.Point;

public class Solution {

	static int max;
	static final int dr[] = {-1, 1, 0, 0};
	static final int dc[] = {0, 0, -1, 1};
	static int map[][];
	static int dp[][];
	static int N, K;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int testcase = 1; testcase <= T; testcase++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			map = new int[N][N];
			dp = new int[N][N];
			int highest = 0;
			ArrayList<Point> list = new ArrayList<Point>();
			
			for(int i=0; i<N; i++)
			{
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<N; j++)
				{
					map[i][j] = Integer.parseInt(st.nextToken());
					if(highest < map[i][j])
					{
						highest = map[i][j];
						list = new ArrayList<Point>();
						list.add(new Point(i, j));
					}
					else if(highest == map[i][j])
					{
						list.add(new Point(i, j));
					}
				}
			}
			
			max = Integer.MIN_VALUE;
			
			for(Point p : list)
			{
				int r = p.x;
				int c = p.y;
				
				boolean visit[][] = new boolean[N][N];
				visit[r][c] = true;
				DFS(visit, true, r, c, 1);
				visit[r][c] = false;
			}
			
			System.out.println("#"+testcase+" "+max);
		}
		
		br.close();
	}

	static void DFS(boolean visit[][], boolean token, int r, int c, int count) {
		boolean flag = false;
		
		for(int i=0; i<4; i++)
		{
			int nr = r + dr[i];
			int nc = c + dc[i];
			
			if(nr >= N || nc >= N || nr < 0 || nc < 0)
				continue;
			if(visit[nr][nc] == true)
				continue;
		
			int cur = map[r][c];
			int next = map[nr][nc];
			
			if(cur > next)				//높이가 낮을 때
			{
				visit[nr][nc] = true;
				DFS(visit, token, nr, nc, count+1);
				visit[nr][nc] = false;
				flag = true;
			}
			else if(cur-1 >= next-K && token == true)		//깎을 때
			{
				visit[nr][nc] = true;
				int temp = map[nr][nc];	
				map[nr][nc] = cur-1;		//현재보다 1 낮게 깎는게 이득
				DFS(visit, false, nr, nc, count+1);
				map[nr][nc] = temp;
				visit[nr][nc] = false;
				flag = true;
			}		
		}
		
		if(flag == false)	//마지막 지점일 때
		{
			max = Math.max(max, count);
		}
	}
}

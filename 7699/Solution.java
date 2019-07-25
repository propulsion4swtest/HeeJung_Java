import java.util.*;
import java.io.*;

public class Solution {

	static boolean visit[];
	static int max;
	
	static final int dr[] = {-1, 1, 0, 0};
	static final int dc[] = {0, 0, -1, 1};
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine().trim());
		
		for(int ii=1; ii<=T; ii++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			int R = Integer.parseInt(st.nextToken());
			int C = Integer.parseInt(st.nextToken());
			
			int map[][] = new int[R][C];
			
			for(int i=0; i<R; i++)
			{
				String str = br.readLine();
				for(int j=0; j<C; j++)
				{
					map[i][j] = str.charAt(j) - 'A';	//'A'가 0을 가리키도록 세팅
				}
			}
			
			visit = new boolean[26];	//a to z
			max = 0;
			
			visit[map[0][0]] = true;
			DFS(map, 0, 0, R, C, 1);
			
			System.out.println("#"+ii+" "+max);
		}
		
		br.close();
	}

	static void DFS(int map[][], int r, int c, int R, int C, int sum)
	{
		max = Math.max(max, sum);
	    if(max == 26)	return;
		
	    boolean flag = false;
		for(int i=0; i<4; i++)
		{
			int nr = r + dr[i];
			int nc = c + dc[i];
			
			if(nr >= R || nc >= C || nr < 0 || nc < 0)
				continue;
			
			if(visit[map[nr][nc]] == true)
				continue;
			
			visit[map[nr][nc]] = true;
			DFS(map, nr, nc, R, C, sum+1);
			flag = true;
			visit[map[nr][nc]] = false;
		}
		
		if(flag == false)
			max = Math.max(max, sum);
	}
}

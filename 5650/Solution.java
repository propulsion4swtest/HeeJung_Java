import java.util.*;
import java.io.*;
import java.awt.Point;

public class Solution {

	static final int dr[] = {-1, 1, 0, 0};	//상하좌우
	static final int dc[] = {0, 0, -1, 1};
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T= Integer.parseInt(br.readLine().trim());
		
		for(int ii=1; ii<=T; ii++)
		{
			int N = Integer.parseInt(br.readLine().trim());
			int map[][] = new int[N][N];
			Point wormhole[][] = new Point[11][2];
						
			for(int i=0; i<N; i++)
			{
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int j=0; j<N; j++)
				{
					int cur = Integer.parseInt(st.nextToken());
					map[i][j] = cur;
					
					if(cur >= 6 && cur <= 10)		//블랙홀 배열에 넣기
					{
						if(wormhole[cur][0] == null)	wormhole[cur][0] = new Point(i, j);
						else							wormhole[cur][1] = new Point(i, j);
					}
				}
			}
			
			int max = Integer.MIN_VALUE;			//점수의 최대값
			int dp[][][] = new int[N][N][4];		//상하좌우에 따른 점수 저장
			boolean visit[][][] = new boolean[N][N][4];
			
			for(int i=0; i<N; i++)
			{
				for(int j=0; j<N; j++)
				{
					if(map[i][j] != 0)		continue;	//0일때만 탐색 시작
					
					for(int k=0; k<4; k++)
					{
						int temp = Simulation(map, visit, dp, N, wormhole, i, j, k);
						max = Math.max(max, temp);
					}	
				}
			}
			
			System.out.println("#"+ii+" "+max);
		}
		
		br.close();
	}

	static int Simulation(int map[][], boolean visit[][][], int dp[][][], int N, Point hole[][], int r, int c, int dir)
	{
		int sum = 0;
		int start_r = r;	int start_c = c;
		r = r + dr[dir];		c = c + dc[dir];	//방향값 더한 값부터 시작
		
		while(true)
		{	
			if(r < 0 || c < 0 || r >= N || c >= N)		//벽에 부딪히면 방향 바꾸기
			{
				if(dir%2 == 0)		dir++;		//0->1, 2->3
				else				dir--;		//1->0, 3->2
				
				sum++;					//점수 증가
				
				//좌표 변경
				if(r < 0)		r++;
				else if(c < 0)	c++;
				else if(r >= N)	r--;
				else			c--;
			}
			else if(map[r][c] >= 6 && map[r][c] <= 10)		//웜홀 타기
			{
				Point one = hole[map[r][c]][0];		//해당 번호의 웜홀 꺼내기
				Point two = hole[map[r][c]][1];
				
				if(one.x == r && one.y == c)	//해당하는 웜홀의 반대쪽 웜홀로 이동
				{
					r = two.x+dr[dir];		c = two.y+dc[dir];	//방향값 더하기
				}
				else
				{
					r = one.x+dr[dir];		c = one.y+dc[dir];	//방향값 더하기
				}
			}
			else if(map[r][c] >=1 && map[r][c] <= 5)	//블럭에 부딪히기 : 들어간 방향에 따라 나오는 방향이 다름 / 좌표 설정
			{
				switch(map[r][c])
				{
				case 1:	//1번블록
					if(dir == 0)
					{	dir = 1;	r++;	}
					else if(dir == 1)
					{	dir = 3;	c++;	}
					else if(dir == 2)
					{	dir = 0;	r--;	}
					else if(dir == 3)
					{	dir = 2;	c--;	}
					break;
					
				case 2:	//2번블록
					if(dir == 0)
					{	dir = 3;	c++;	}
					else if(dir == 1)
					{	dir = 0;	r--;	}
					else if(dir == 2)
					{	dir = 1;	r++;	}
					else if(dir == 3)
					{	dir = 2;	c--;	}
					break;
				
				case 3:	//3번블록
					if(dir == 0)
					{	dir = 2;	c--;	}
					else if(dir == 1)
					{	dir = 0;	r--;	}
					else if(dir == 2)
					{	dir = 3;	c++;	}
					else if(dir == 3)
					{	dir = 1;	r++;	}
					break;
				
				case 4:	//4번블록
					if(dir == 0)
					{	dir = 1;	r++;	}
					else if(dir == 1)
					{	dir = 2;	c--;	}
					else if(dir == 2)
					{	dir = 3;	c++;	}
					else if(dir == 3)
					{	dir = 0;	r--;	}
					break;
					
				case 5: //전방향이 반대방향으로 이동
					if(dir == 0)
					{	dir = 1;	r++;	}
					else if(dir == 1)
					{	dir = 0;	r--;	}
					else if(dir == 2)
					{	dir = 3;	c++;	}
					else if(dir == 3)
					{	dir = 2;	c--;	}
					break;
				}
				sum++;
			}
			else if(map[r][c] == -1 || (r==start_r && c==start_c))	//탈출 조건
			{
				break;
			}
			else		//0일때 이동
			{
				//다음 좌표 설정
				int nr = r + dr[dir];
				int nc = c + dc[dir];
				
				r = nr;
				c = nc;
				
				if(r >= N || c >= N || r < 0 || c < 0)		//바운더리를 넘어가는 경우 배열 체크는 패스
					continue;
//				
//				if(visit[r][c][dir] == true && dp[r][c][dir] >= sum)		//방문했을 경우 -> dp값이 현재값보다 더 크면 갈 필요 X
//					break;
//				
//				visit[r][c][dir] = true;	//방문 체크
//				dp[r][c][dir] = sum;
			}
		}
		
		return sum;
	}
}

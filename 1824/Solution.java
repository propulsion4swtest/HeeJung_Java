import java.util.*;
import java.io.*;

public class Solution {

	static final int dr[] = {-1, 1, 0, 0};	//상, 하, 좌, 우
	static final int dc[] = {0, 0, -1, 1};	//0, 1, 2, 3
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int ii=1; ii<=T; ii++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			int R = Integer.parseInt(st.nextToken());
			int C = Integer.parseInt(st.nextToken());
			
			char map[][] = new char[R][C];
			int visit[][] = new int[R][C];
			
			for(int i=0; i<R; i++)
			{
				String str = br.readLine();
				for(int j=0; j<C; j++)
					map[i][j] = str.charAt(j);
			}
			//입력 완료
			int memory = 0;
			boolean result = false;
			
			Queue<Node> q = new LinkedList<Node>();
			q.offer(new Node(0, 0, 3));
			visit[0][0] = 1;
			
			while(!q.isEmpty())
			{
				Node cur = q.poll();
				int r = cur.r;
				int c = cur.c;
				char op = map[r][c];		//현재 옵션
				int dir = cur.dir;
				
				if(op == '<')			//이동 방향을 왼쪽으로 바꾼다
					dir = 2;
				else if(op == '>')		//이동 방향을 오른쪽으로 바꾼다
					dir = 3;
				else if(op == '^')		//이동 방향을 위쪽으로 바꾼다
					dir = 0;
				else if(op == 'v')		//이동 방향을 아래쪽으로 바꾼다
					dir = 1;
				else if(op == '_')		//메모리에 0이 저장되어 있으면 이동 방향을 오른쪽으로 바꾸고, 아니면 왼쪽으로 바꾼다
				{
					if(memory == 0)	dir = 3;
					else			dir = 2;
				}
				else if(op == '|')		//메모리에 0이 저장되어 있으면 이동 방향을 아래쪽으로 바꾸고, 아니면 위쪽으로 바꾼다
				{
					if(memory == 0)	dir = 1;
					else			dir = 0;
				}
				else if(op == '?')		//이동 방향을 상하좌우 중 하나로 무작위로 바꾼다. 방향이 바뀔 확률은 네 방향 동일하다
				{
					//현재 방향을 제외한 모든 방향을 큐에 넣기
					for(int i= 0; i<4; i++)
					{
						if(i == dir)	continue;
						
						//다음 갈 곳 정하기
						int nr = r + dr[i];
						int nc = c + dc[i];
						
						//바운더리를 넘어갈 때
						if(nr >= R)	nr = 0;
						if(nc >= C) nc = 0;
						if(nr < 0)	nr = R-1;
						if(nc < 0)	nc = C-1;
						
						if(visit[nr][nc] > 16)
							continue;
						
						visit[nr][nc]++;
						q.offer(new Node(nr, nc, i));
					}
				}
				else if(op == '.')		//아무 것도 하지 않는다
				{}
				else if(op == '@')		//프로그램의 실행을 정지한다
				{
					result = true;
					break;
				}
				else if(op >= '0' && op <= '9')		//메모리에 문자가 나타내는 값을 저장한다
				{
					memory = op - '0';
				}
				else if(op == '+')		//메모리에 저장된 값에 1을 더한다. 만약 더하기 전 값이 15이라면 0으로 바꾼다
				{
					memory++;
					if(memory == 16)	memory = 0;
				}
				else if(op == '-')		//메모리에 저장된 값에 1을 뺀다. 만약 빼기 전 값이 0이라면 15로 바꾼다
				{
					memory--;
					if(memory == -1)	memory = 15;
				}
				
				//다음 갈 곳 정하기
				int nr = r + dr[dir];
				int nc = c + dc[dir];
				
				//바운더리를 넘어갈 때
				if(nr >= R)	nr = 0;
				if(nc >= C) nc = 0;
				if(nr < 0)	nr = R-1;
				if(nc < 0)	nc = C-1;
				
				if(visit[nr][nc] > 16)
					continue;
				
				visit[nr][nc]++;
				q.offer(new Node(nr, nc, dir));
			}
			
			//결과 출력
			StringBuilder sb = new StringBuilder();
			sb.append("#"+ii+" ");
			if(result == true)
				sb.append("YES");
			else
				sb.append("NO");
			
			System.out.println(sb);
		}
		
		br.close();
	}

}

class Node{
	int r;	int c;	int dir;
	Node(int r, int c, int dir)
	{
		this.r = r;	this.c = c;	this.dir = dir;
	}
}
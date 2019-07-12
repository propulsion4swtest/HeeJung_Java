import java.io.*;
import java.util.*;

public class Solution {

	static final int[] dr = {-1, 1, 0, 0};
	static final int[] dc = {0, 0, -1, 1};	//상하좌우
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int ii=1; ii<=T; ii++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			int H = Integer.parseInt(st.nextToken());
			int W = Integer.parseInt(st.nextToken());
			
			char map[][] = new char[H][W];
			int tank[] = new int[2];
			
			for(int i=0; i<H; i++)
			{
				String str = br.readLine();
				for(int j=0; j<W; j++)
				{
					map[i][j] = str.charAt(j);
					if(map[i][j] == '^' || map[i][j] == 'v' || map[i][j] == '<' || map[i][j] == '>')
					{
						tank[0] = i;	tank[1] = j;
					}
				}
			}
			//맵 입력 끝
			
			int N = Integer.parseInt(br.readLine());
			Queue<Character> q = new LinkedList<Character>();
			String str = br.readLine();
			for(int i=0; i<N; i++)
				q.add(str.charAt(i));
			//명령 입력 끝
			
			while(!q.isEmpty())
			{
				char command = q.poll();
//				System.out.println(command);
				
				if(command == 'S')
				{
					int dir = -1;
					switch(map[tank[0]][tank[1]])
					{
					case '^':	dir = 0;	break;
					case 'v': 	dir = 1;	break;
					case '<':	dir = 2;	break;
					case '>':	dir = 3;	break;
					}
					
					int r = tank[0];	int c = tank[1];
					
					while(true)
					{
						int nr = r + dr[dir];	int nc = c + dc[dir];
						if(nr >= H || nc >= W || nr < 0 || nc < 0)
							break;
						
						if(map[nr][nc] == '*')
						{
							map[nr][nc] = '.';
							break;
						}
						if(map[nr][nc] == '#')
							break;
						
						r = nr;	c = nc;
					}
				}
				else
				{
					int dir = -1;
					switch(command)
					{
					case 'U':	dir = 0;	map[tank[0]][tank[1]] = '^';	break;
					case 'D': 	dir = 1;	map[tank[0]][tank[1]] = 'v';	break;
					case 'L':	dir = 2;	map[tank[0]][tank[1]] = '<';	break;
					case 'R':	dir = 3;	map[tank[0]][tank[1]] = '>';	break;
					}
					
					int nr = tank[0] + dr[dir];	int nc = tank[1] + dc[dir];
					
					if(nr >= H || nc >= W || nr < 0 || nc < 0)
						continue;
					
					if(map[nr][nc] != '.')	continue;
					
					map[nr][nc] = map[tank[0]][tank[1]];
					map[tank[0]][tank[1]] = '.';
					
					tank[0] = nr;	tank[1] = nc;
					
				}
			}
			
			System.out.print("#"+ii+" ");
			for(int i=0; i<H; i++)
			{
				for(int j=0; j<W; j++)
					System.out.print(map[i][j]);
				System.out.println();
			}
		}
		
		br.close();
	}

}

import java.io.*;
import java.util.*;
import java.awt.Point;

public class Solution {

	static final int dr[] = {-1, -1, -1, 0, 0, 1, 1, 1};
	static final int dc[] = {-1, 0, 1, -1, 1, -1, 0, 1};
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int ii=1; ii<=T; ii++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());

			int map[][] = new int[N+1][N+1];
			map[N/2][N/2] = map[N/2+1][N/2+1] = 2;	//백돌 초기화
			map[N/2][N/2+1] = map[N/2+1][N/2] = 1;	//흑돌 초기화
			
			int count[] = {0, 2, 2};	//흑돌 카운트 인덱스는 1, 백돌 카운트 인덱스는 2. 초기 개수는 2로 세팅
			
			for(int jj=0; jj<M; jj++)
			{
				st = new StringTokenizer(br.readLine());
				int r = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				int color = Integer.parseInt(st.nextToken());	//흑돌이 1, 백돌이 2
				
				map[r][c] = color;
				count[color]++;
				
				ArrayList<Point> change_list[] = new ArrayList[8];
				for(int i=0; i<8; i++)
					change_list[i] = new ArrayList<Point>();	//변경할 점 찾아서 리스트에 담기
				
				//색깔 바꿀 돌들 찾기
				for(int i=0; i<8; i++)		//대각선 방향으로 탐색
				{
					int nr = r + dr[i];
					int nc = c + dc[i];
					
					if(nr > N || nc > N || nr <= 0 || nc <= 0)	//바운더리 체크
						continue;
					
					if(map[nr][nc] == 0 || map[nr][nc] == color)	//색이 같거나 0이면 체크 안함
						continue;
					
					//유효한 방향이면 계속 찾아 나가기
					int temp_r = nr;
					int temp_c = nc;
					change_list[i].add(new Point(temp_r, temp_c));
					
					while(true)
					{
						temp_r = temp_r + dr[i];
						temp_c = temp_c + dc[i];
						
						//다음 지점이 바운더리를 넘어가면 탈출
						if(temp_r > N || temp_c > N || temp_r <= 0 || temp_c <= 0)
						{
							change_list[i] = null;
							break;
						}
						
						if(map[temp_r][temp_c] == color)	//색이 같은 것 만나면 정상 종료
							break;
						
						if(map[temp_r][temp_c] == 0)	//중간에 0이 끼어있으면
						{
							change_list[i] = null;	//리스트를 없던걸로 만들고 탈출
							break;
						}						
						
						change_list[i].add(new Point(temp_r, temp_c)); 	//다음 지점 넣기. 이 때의 다음 지점은 다른 색깔의 점
					}
				}
				
				for(int i=0; i<8; i++)
				{
					if(change_list[i] == null)
						continue;
					
					for(Point p : change_list[i])
					{
						map[p.x][p.y] = color;
						count[color]++;
						
						if(color == 1)
							count[2]--;
						else
							count[1]--;
					}
				}

			}
			
			System.out.println("#"+ii+" "+count[1]+" "+count[2]);
		}
		
		br.close();
	}
}
import java.util.*;
import java.io.*;

public class Main {

	static final int dr[] = {-1, 1, 0, 0};	//상하좌우
	static final int dc[] = {0, 0, -1, 1};
	static int N;		//지도 크기
	static int map[][];	//지도
	static int shark_r, shark_c;	//현재 상어 좌표
	static int shark_size = 2;			//현재 상어 크기
	static int shark_count = 0;			//상어가 먹이 먹은 수
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		shark_r = 0;	shark_c = 0;	//아기상어의 위치
		map = new int[N][N];
		
		for(int i=0; i<N; i++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++)
			{
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 9)	//상어인 경우 좌표 저장
				{
					shark_r = i;	shark_c = j;
					map[i][j] = 0;
				}
			}
		}
		
		//입력 끝
		int count = 0;
		while(true)	//다 돌면 -1 반환. 그때까지 count 늘려간다
		{
			int temp = solution();
			if(temp == -1)
				break;
			else
				count += temp;
		}
		
		System.out.print(count);
	}

	static int solution()
	{
		int result = -1;
		Queue<Fish> q = new LinkedList<Fish>();
		boolean visit[][] = new boolean[N][N];		//BFS 방문 체크하기 위한 배열
		int dist[][] = new int[N][N];				//해당 좌표까지의 거리
		
		q.offer(new Fish(shark_r, shark_c, shark_size));	//현재 상어의 위치 제공
		visit[shark_r][shark_c] = true;			//현재 위치에 방문 체크
		
		//BFS로 완전탐색
		Fish min_fish = new Fish(999, 999, 999);
		int min_dist = Integer.MAX_VALUE;	//이동 횟수의 최소값
		
		while(!q.isEmpty())
		{
			Fish cur = q.poll();		//큐의 원소 꺼내기
			
			int nR, nC;					//다음 좌표
			
			for(int i=0; i<4; i++)
			{
				nR = cur.r + dr[i];		nC = cur.c + dc[i];		//상어가 다음에 갈 좌표
				
				if(nR < 0 || nR >= N || nC < 0 || nC >= N)		//좌표 범위를 벗어날 경우
					continue;
				if(visit[nR][nC] == true)			//이미 방문했을 경우
					continue;
				if(map[nR][nC] > cur.size)		//갈 위치에 나보다 큰 물고기가 존재한다면 가지 않음
					continue;
				
				//잡아먹든 안잡아먹든 이동은 해야함
				dist[nR][nC] = dist[cur.r][cur.c] + 1;
				visit[nR][nC] = true;
				
				if(map[nR][nC] < cur.size && map[nR][nC] != 0)		//잡아먹을 수 있다면
				{
					if(dist[nR][nC] < min_dist)		//이동값이 최소값보다 작으면
					{
						min_dist = dist[nR][nC];			//최소 거리값 업데이트
						min_fish = new Fish(nR, nC, map[nR][nC]);	//잡아먹을 물고기 정보 업데이트
						continue;
					}
					else if(dist[nR][nC] == min_dist)	//이동값이 최소값이랑 같으면 물고기 좌표 비교
					{
						Fish temp = new Fish(nR, nC, map[nR][nC]);	//물고기 정보 비교하기 위해 불러옴
						if(temp.compareTo(min_fish) < 0)		//기존의 물고기보다 조건에 가까우면
						{
							min_dist = dist[nR][nC];
							min_fish = temp;
							continue;
						}
					}	
				}
				
				//잡아먹을 수 없다면 다른 물고기 탐색		
				q.offer(new Fish(nR, nC, shark_size));		
			}
		}
		
		if(min_dist < 400)		//물고기를 먹은 경우
		{
			result = dist[min_fish.r][min_fish.c];	//먹은곳까지 도달하는 거리
			map[min_fish.r][min_fish.c] = 0;		//먹은곳 0으로 만들기
			shark_r = min_fish.r;	//상어의 새 좌표
			shark_c = min_fish.c;
			shark_count++;		//먹이 먹은 수 증가
			
			if(shark_count%shark_size == 0)		//상어 크기 증가
			{
				shark_count = 0;
				shark_size++;
			}
		}
		
		return result;
	}
}

class Fish implements Comparable<Fish>{
	int r, c;
	int size;
	Fish(){};
	Fish(int r, int c, int size)
	{
		this.r = r; this.c = c;	this.size = size;
	}
	
	@Override		
	public int compareTo(Fish f){
		if(this.r == f.r)
			return this.c-f.c;
		else
			return this.r-f.r;
	}

}
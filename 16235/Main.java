import java.util.*;
import java.io.*;

public class Main {

	static final int dr[] = {-1, -1, -1, 0, 0, 1, 1, 1};
	static final int dc[] = {-1, 0, 1, -1, 1, -1, 0, 1};
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());	//땅의 가로세로
		int M = Integer.parseInt(st.nextToken());	//초기 나무의 수
		int K = Integer.parseInt(st.nextToken());	//흘러간 세월
		
		int A[][] = new int[N+1][N+1];		//겨울에 추가하는 양분 배열 -> 변하면 안된다
		for(int i=1; i<=N; i++)
		{
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<=N; j++)
				A[i][j] = Integer.parseInt(st.nextToken());
		}
		
		//나무 위치를 나이 어린 순으로 입력받기
		PriorityQueue<Tree> pq = new PriorityQueue<Tree>();
		for(int i=0; i<M; i++)
		{
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int z = Integer.parseInt(st.nextToken());
			pq.add(new Tree(x, y, z));
		}
		
		Deque<Tree> dq = new LinkedList<Tree>();	//정렬한걸 덱에 넣기
		while(!pq.isEmpty())
		{
			Tree temp = pq.poll();
			dq.addLast(temp);
		}
		
		//처음 양분은 5로 초기화
		int map[][] = new int[N+1][N+1];
		for(int i=1; i<=N; i++)
			for(int j=1; j<=N; j++)	map[i][j] = 5;
	
		
		int years = 0;
	
		while(years < K)
		{
			Deque<Tree> temp_q = new LinkedList<Tree>();
			Queue<Tree> drop_list = new LinkedList<Tree>();	//나무 버릴 리스트
			Queue<Tree>	grow_list = new LinkedList<Tree>();	//키울 나무 리스트
			
			//봄
			while(!dq.isEmpty())
			{
				Tree cur = dq.pollFirst();
				int cur_r = cur.r;
				int cur_c = cur.c;
				
				if(map[cur_r][cur_c] >= cur.age)	//양분을 먹을 수 있다면 섭취
				{
					map[cur_r][cur_c] -= cur.age;
					cur.age++;
					temp_q.addLast(cur);
					
					if(cur.age%5 == 0)		grow_list.add(cur);	//5의 배수이면 성장
				}
				else
				{
					drop_list.add(cur);
				}
			}
			
			//여름
			while(!drop_list.isEmpty())
			{
				Tree cur = drop_list.poll();
				int cur_r = cur.r;
				int cur_c = cur.c;
				
				map[cur_r][cur_c] += cur.age/2;
			}
			
			//가을
			while(!grow_list.isEmpty())
			{
				Tree cur = grow_list.poll();
				
				for(int i=0; i<8; i++)
				{
					int nr = cur.r+dr[i];
					int nc = cur.c+dc[i];
					
					if(nr < 1 || nc < 1 || nr > N || nc > N)	continue;
					temp_q.addFirst(new Tree(nr, nc, 1)); 		//맨 앞에 새 나무 생성
				}
			}
			
			//겨울: 양분 뿌리기
			for(int i=1; i<=N; i++)
			{
				for(int j=1; j<=N; j++)
					map[i][j] += A[i][j];
			}
			
			dq = temp_q;
			years++;
		}
		
		System.out.println(dq.size());
		
		br.close();
	}

}

class Tree implements Comparable<Tree>{
	int r;	int c;	int age;
	Tree(int r, int c, int age)
	{
		this.r = r;	this.c = c;	this.age = age;
	}
	@Override
	public int compareTo(Tree o)
	{
		return this.age-o.age;
	}

}
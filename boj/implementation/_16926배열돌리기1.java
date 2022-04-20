package algorithm.boj.implementation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/**
 *
 * @author juyoung
 * 
 * 1. 입력 받은 배열에서 돌려야 하는 그룹의 개수를 구해야 한다
 * 3*3 배열 : 1개, 4*4 배열 : 2개, 4*6 배열 : 2 개 .. 이런 식으로
 * N, M 중 최솟값을 찾아 2로 나누면 그룹의 개수가 구해진다
 * 
 * 2. 배열의 범위를 구한다.
 * 첫 시작은 항상 (0,0), (1,1), (2,2)...로 그룹 level에 따라 시작점이 다르다.
 * 변수 i : 회전수 (R까지)
 * 변수 j : 그룹 갯수만큼 반복
 * 
 * 우하좌상의 방향으로 이동하며 현재 가리키고 있는 값을 현재 위치에 저장한다
 * [0,0] <- [0,1] : (0,1)의 값이 (0,0) 위치에 저장됨.
 * 이동할 위치가 허용된 범위를 벗어나면 idx++ 하여 방향을 다음 방향으로 바꾼다. 반시계방향을 전부 돌 때까지 돈다.
 * 이동할 x,y좌표는 j(현재 그룹 인덱스)보다 크거나 같아야 하고 x좌표는 N-j보다 작고, y좌표는 M-j보다 작아야 한다
 * 
 * 이 때 시작점은 미리 따로 값을 뺴놓고 우하좌상 탐색이 끝난 후 다음 레벨로 넘어가기 전에 항상 [j+1][j] 위치에 넣어준다.
 * 한 바퀴씩 이동하면서 반시계방향으로 한 칸씩 이동했으므로 시작점도 동일하게 이동시켜주는 것.
 * 
 * 
 */
public class _16926배열돌리기1 {

	static int M, N, R, group_value;
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {1, 0, -1, 0}; // 우하좌상
	static int[][] arr;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
	
		st = new StringTokenizer(br.readLine()," ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
		arr = new int[N][M];

		/* 배열 초기화 */
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < M; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		/* 돌려야 하는 그룹 개수 */
		group_value = (Math.min(N, M))/2;
		
		/* 회전 */
		rotateMap();
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				sb.append(arr[i][j]+" ");
			}
			sb.append("\n");
		}
		
		System.out.println(sb);
	}
	
	public static void rotateMap() {
		for(int i = 0; i < R; i++) {

			for(int j = 0; j < group_value; j++) {
				int x = j;
				int y = j; // 현재 위치
				
				int startValue = arr[x][y]; // 시작점 값
				
				int idx = 0; // 초기 방향
				
				/*
				 * 우하좌상 돌면서 값 변경 (반시계 방향으로 1바퀴 회전)
				 */
				while(idx < 4) {
					
					int nextX = x + dx[idx];
					int nextY = y + dy[idx]; // 그 다음 위치
					
					if(nextX >= j && nextY >= j && nextX < N-j && nextY < M-j) {
						arr[x][y] = arr[nextX][nextY]; // 범위 내에 있을 경우 그 값을 현재 위치에 저장
						
						// 그 다음 탐색할 위치를 현재 위치로
						x = nextX;
						y = nextY;
					}
					else idx++; // 범위를 벗어나면 다음 방향으로 탐색
					
				}
				
				// 한 바퀴 돌았으면 이동된 시작점 위치도 선정해줌
				arr[j+1][j] = startValue;

			}
		}
		
	}

}
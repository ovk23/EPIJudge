package epi.systemDesign;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.Set;

class SnakeGame {
    class Location {
        int x;
        int y;

        Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + "," + y;
        }
    }

    private Set<String> snakeLocationSet;
    private int[][] board;
    private Deque<Location> food;
    private int totalFood;

    private Deque<Location> snakePosition;

    public SnakeGame(int width, int height, int[][] food) {
        this.board = new int[height][width];
        this.food = new ArrayDeque<>();

        for (int[] row : food) {
            board[row[0]][row[1]] = 1;
            this.food.add(new Location(row[0], row[1]));
        }

        this.totalFood = this.food.isEmpty()
                ? 0
                : this.food.size();

        Location initSnakeLocation = new Location(0, 0);
        this.snakeLocationSet = new LinkedHashSet<>();
        this.snakeLocationSet.add(initSnakeLocation.toString());

        this.snakePosition = new ArrayDeque<>();
        this.snakePosition.add(initSnakeLocation);
    }

    public int move(Character direction) {
        int x = 0, y = 0;
        switch (direction) {
            case 'U':
                return moveSnake(new Location(-1, 0));
            case 'D':
                return moveSnake(new Location(1, 0));
            case 'R':
                return moveSnake(new Location(0, 1));
            case 'L':
                return moveSnake(new Location(0, -1));
            default:
                return -1;
        }
    }

    private boolean checkIfNewLocationValid(Location location) {
        return location.x >= 0 && location.x <= this.board.length - 1
                && location.y >= 0 && location.y <= this.board[0].length - 1
                && !this.snakeLocationSet.contains(location.toString());
    }

    private int moveSnake(Location move) {
        Location oldLocation = this.snakePosition.peek();
        Location newLocation = new Location(oldLocation.x + move.x, oldLocation.y + move.y);

        if (!checkIfNewLocationValid(newLocation)) {
            return -1;
        } else if (this.board[newLocation.x][newLocation.y] != 1) {
            Location lastLocation = this.snakePosition.pollLast();
            this.snakeLocationSet.remove(lastLocation.toString());
            this.snakePosition.addFirst(newLocation);
            return 0;
        } else {
            this.snakeLocationSet.add(newLocation.toString());
            this.snakePosition.addFirst(newLocation);
            this.food.poll();
            return this.totalFood - this.food.size();
        }

    }

    /**
     * ["SnakeGame","move","move","move","move","move","move"]
     * [[3,2,[[1,2],[0,1]]],["R"],["D"],["R"],["U"],["L"],["U"]]
     *
     * @param args
     */
    public static void main(String[] args) {
        int[][] food = {{1, 2}, {0, 1}};
        SnakeGame game = new SnakeGame(3, 2, food);

        System.out.println(game.move('R'));
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
package engineermypath;

import java.util.*;

/**
 *
 * @author João
 */
public class Grelha {

    private final double[][] map;
    private final boolean allowDiagonal;

    public class MapNode implements Nodo<MapNode> {

        private final int x, y;

        public MapNode(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public double getHeuristic(MapNode goal) {
            if (allowDiagonal) {
                return Math.max(Math.abs(x - goal.x), Math.abs(y - goal.y));
            } else {
                return Math.abs(x - goal.x) + Math.abs(y - goal.y);
            }
        }

        public double getTraversalCost(MapNode neighbour) {
            return 1 + map[neighbour.y][neighbour.x];
        }

        public Set<MapNode> getNeighbours() {
            Set<MapNode> neighbours = new HashSet<MapNode>();

            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if ((i == x && j == y) || i < 0 || j < 0 || j >= map.length
                            || i >= map[j].length) {
                        continue;
                    }

                    if (!allowDiagonal
                            && ((i < x && j < y) || (i > x && j > y))) {
                        continue;
                    }

                    if (map[j][i] < 0) {
                        continue;
                    }

                    // TODO: create cache instead of recreation
                    neighbours.add(new MapNode(i, j));
                }
            }

            return neighbours;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            MapNode other = (MapNode) obj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (x != other.x) {
                return false;
            }
            if (y != other.y) {
                return false;
            }
            return true;
        }

        private Grelha getOuterType() {
            return Grelha.this;
        }

    }

    public Grelha(double[][] map, boolean allowDiagonal) {
        this.map = map;
        this.allowDiagonal = allowDiagonal;
    }

    public List<MapNode> findPath(int xStart, int yStart, int xGoal, int yGoal) {
        return new PathFinding().doAStar(new MapNode(xStart, yStart), new MapNode(
                xGoal, yGoal));
    }
}
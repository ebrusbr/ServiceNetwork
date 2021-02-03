public class Tuple implements Comparable<Tuple>{
        public String node;
        public int distance;

        public Tuple(String node, int distance){
            this.node = node;
            this.distance = distance;
        }

        @Override
        public String toString(){
            return "[" + node + ", " + distance + "]";
        }

        @Override
        public int compareTo(Tuple otherTuple) {
            return this.distance - otherTuple.distance;
        }
}

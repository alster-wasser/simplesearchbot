import java.util.ArrayList;
    class Frontier {
        private ArrayList<SearchPath> pathsInFrontier;
        public int expansionOperations = 0;
        public int maxNodesInFrontier = 1;

        Frontier(SearchPath path) {
            pathsInFrontier = new ArrayList<SearchPath>();
            pathsInFrontier.add(path);
            expansionOperations++;
        }

        void addPathToFrontier(SearchPath path) {
            pathsInFrontier.add(path);
            expansionOperations++; 
            int possibleMax = 0;
            for(SearchPath p : pathsInFrontier){
                possibleMax = possibleMax + p.getPathLength();
            }
            if(possibleMax > maxNodesInFrontier){
                maxNodesInFrontier = possibleMax;
            }
        }

        void removePathFromFrontier(SearchPath path) {
            pathsInFrontier.remove(path);
        }

        void removeLastEntry() {
            pathsInFrontier.remove(pathsInFrontier.size() - 1);
        }

        void removeFirstEntry(){
            pathsInFrontier.remove(0);
        }

        public SearchPath getFirstEntry(){
            return pathsInFrontier.get(0);
        }

        //returns the number of paths in the frontier
        int getFrontierSize() {
            return pathsInFrontier.size();
        }

        //returns the path at the end of the frontier
        SearchPath getCurrentPath() {
            return pathsInFrontier.get(pathsInFrontier.size() - 1);
        }

        boolean isEmpty() {
            return pathsInFrontier.isEmpty();
        }
        
        ArrayList<SearchPath> getCurrentFrontier(){
            return pathsInFrontier;
            }

        public String toString() {
            String frontierToString = "[ ";
            for (SearchPath path : pathsInFrontier) {
                frontierToString = frontierToString.concat(path.toString() + " ");
            }
            frontierToString = frontierToString.concat("]");
            return frontierToString;
        }

    }
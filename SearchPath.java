import java.util.LinkedList;
    class SearchPath {
        protected LinkedList<Field> path;

        //constructor for private class SearchPath, creates
        //a new path with just one node in it
        SearchPath(Field field) {
            path = new LinkedList<>();
            path.add(field);
        }

        Field getHeadNode() {
            return path.getLast();
        }


        //@TODO: implement a way to check if this node is already in the frontier as head of another path
        void addNode(Field field) {
            path.add(field);
        }

        int getPathLength() {
            return path.size();
        }
        
        protected SearchPath clone(){
            SearchPath clone = new SearchPath(path.getFirst());
            clone.path.clear();
            for(Field f : path){
                clone.addNode(f);
            }
            return clone;
        }

        public String toString() {
            String searchPathToString = "<";
            for (Field node : path) {
                searchPathToString = searchPathToString.concat(node.toString());
            }
            searchPathToString = searchPathToString.concat(">");
            return searchPathToString;
        }
    }
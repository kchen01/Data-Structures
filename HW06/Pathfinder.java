import java.util.*;
import java.net.URLDecoder;
import java.io.*;

public class Pathfinder{
    private Map<String, Integer> nameMap = new HashMap<>(); // map to hold pairs of int keys and String name values.
    private Map<Integer, String> intMap = new HashMap<>();
    private Map<String, List<String>> linksMap = new HashMap<>(); //makes a new map for article that will keep the names of links as values
    private MysteryUnweightedGraphImplementation graph = new MysteryUnweightedGraphImplementation(); //graph to hold articles connected by links
   
    
    /*
    *This creates object Pathfinder with 2 given textfiles, and adding the content of those text files to the declared
    *variables/maps/graphs above.
    */
    
public Pathfinder(String nodeFile, String edgeFile){
        try{
            Scanner scan = new Scanner(new File(nodeFile));
            int articleIndex = 0; // number that is used to refer to each article
            while(scan.hasNext()){
                //the following two if statements will ignore blank lines and comments in the scanned file.
                String articleName = scan.nextLine();
                if(articleName.equals("")){
                     continue;
                }
                 String c = articleName.substring(0,1);
                 if(c.equals("#")){
                     continue;
                }
                graph.addVertex(); //adds an additional vertex for every iteration of while loop.
                articleName = java.net.URLDecoder.decode(articleName, "UTF-8"); //turns encoded article names into readable names.
                nameMap.put(articleName, articleIndex); // new entry in nameMap: puts index of article as value and article name as key, so we can refer to each article by name, and get its value as a vertex on the map as an index
                intMap.put(articleIndex, articleName);
                 articleIndex = articleIndex + 1; // increments next articleIndex by one each iteration of while loop.
            }
            scan = new Scanner(new File(edgeFile));
            while(scan.hasNext()){
                String linkFile = scan.nextLine();
                if(linkFile.equals("")){
                    continue;
                }
                String c = linkFile.substring(0,1);
                if(c.equals("#")){
                    continue;
                }
                linkFile = java.net.URLDecoder.decode(linkFile, "UTF-8");
                String[] check = linkFile.split("\t");
                String fatherArticle = check[0];
                String linkArticle = "";
                linkArticle = check[1];
                if(linksMap.containsKey(fatherArticle)){
                    linksMap.get(fatherArticle).add(linkArticle);
                }else{
                    List<String> links = new ArrayList<String>(); // makes a new list to store all links of the article
                    links.add(linkArticle);
                    linksMap.put(fatherArticle, links);
                }
            }
            for(int i = 0; i < articleIndex; i ++){
                String fatherString = intMap.get(i);
                int fatherVertex = nameMap.get(fatherString); // could be referred to as i, but is just clearer here
                List<String> links = linksMap.get(fatherString);
                if(links == null){
                    continue;
                }
                for(String link: links){
                    int linkVertex = nameMap.get(link);
                    graph.addEdge(fatherVertex, linkVertex);
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("This File Does Not Exist");
        }catch(UnsupportedEncodingException f){
            System.out.println("This string could not be changed from UTF-8 form");
        }
    }
/*
*Gets ShortestPath by calling getShortestPath() using node1 and node2, and returns the length of the list size -1.
* If the path does not exist, returns -1, and in the main, we check if i == -1, and if so, we say there is no path between the two. 
*/
public int getShortestPathLength(String node1, String node2){
    List<String> list = getShortestPath(node1, node2);
    int i = list.size();
    i --;
    return i;
}
/* 
* Gets the shortest path between two nodes, including an intermediate node, by calling getShortestPath() with 2 arguments two times, and combining those answers into one list. If either of the two getShortestPath() return a blank list, return a blank list as well (to signify a lack of path)
*/
public List<String> getShortestPath(String node1, String intermediateNode, String node2){
    if(node1.equals(intermediateNode) && node1.equals(node2)){
        List<String> list = new ArrayList<String>();
        list.add(node1);
        return list;
    }
    List<String> path1 = this.getShortestPath(node1, intermediateNode);
    List<String> path2 = this.getShortestPath(intermediateNode, node2);
    if(path1.size() == 0 || path2.size() == 0){
        List<String> blankList = new ArrayList<String>();
        return blankList;
    }
    path2.remove(0);
    path1.addAll(path2);
    return path1;
}
    
    
/*
*Returns a list containing the shortest path between two nodes, by using breadth first algorithm through a graph. If there is not a path formed by edges between the two nodes, returns a blank List. It does this by after the while loop completes, checking if the queue is empty.
* If a path between the two nodes is found, the while loop is broken with 'break;', meaning the queue will still have items inside of it. If not, the while loop will stop when the Queue runs out of items, and that means there are no more options to check, AKA, these two nodes have no path between them.
*/
public List<String> getShortestPath(String node1, String node2){
    List<String> shortestList = new ArrayList<String>();
    List<Integer> shortestInts = new ArrayList<Integer>();
    List<Integer> visitedList = new ArrayList<Integer>();
    Queue<Integer> vertexQueue = new LinkedList<Integer>();
    Map<Integer, Integer> predMap = new HashMap<Integer, Integer>();
    int startVertex = nameMap.get(node1);
    int endVertex = nameMap.get(node2);
    int currentVertex = startVertex;
    vertexQueue.add(currentVertex);
    visitedList.add(currentVertex);
    while(!vertexQueue.isEmpty()){
        currentVertex = vertexQueue.poll();
        for(int i: graph.getNeighbors(currentVertex)){
            if(!visitedList.contains(i)){
                vertexQueue.add(i);
                visitedList.add(i);
                predMap.put(i, currentVertex);  
            }
        }
        if(currentVertex == endVertex){
            break;
        }
    }
    if(vertexQueue.isEmpty()){
        shortestList = new ArrayList<String>();
        return shortestList;
    }
    shortestInts.add(currentVertex);
    while(currentVertex != startVertex){
        currentVertex = predMap.get(currentVertex);
        shortestInts.add(0, currentVertex);
    }
    for(int i = 0; i < shortestInts.size(); i ++){
        shortestList.add(intMap.get(shortestInts.get(i)));
    }
    return shortestList;
}

/*
*Uses a random number generator between 0 and the length of the map/dictionary holding all of the nodes. intMap uses integer as key, and a string as value, so we call a random int, and then use that returned string as the randomLink()
*/
public String getRandomNode(){
    int startNum = new Random().nextInt(intMap.size() - 1); 
    String s = intMap.get(startNum);
    return s;
    }

/*
*
*/
public static void main(String[] args){
    // When testing the TestLinks and articles, change articles.tsv and links.tsv to testArticles.tsv and testLinks.tsv. These are very small, and easy to check the path lengths and what they should be. It will still randomize everything for you.
    System.out.println();
    System.out.println(); //indentation to make it easier to read
    String article = args[0];
    String link = args[1];
    Pathfinder p = new Pathfinder(article, link);
    String start = p.getRandomNode(); // Calls getRandomNode to create a random node Name - these are what randomize the links chosen in the code, and are called a few times throughout the main
    String end = p.getRandomNode();
    System.out.println("Your Start Link is : " + start);
    System.out.println("Your End Link is : " + end);
    int i = p.getShortestPathLength(start, end);  // Calls getShortestPathLength to show it works
    if (i == -1){
        System.out.println("These two items have no path between them");
    }else{
        System.out.println("This Took " + i + " Links to get there");
        List<String> check =  p.getShortestPath(start,end); // Calls getShortestPath with 2 arguments to show it works
        System.out.print(check.get(0));
        for(int j = 1; j<check.size(); j ++){
            System.out.print(" --> ");
            System.out.print(check.get(j));
        }
        System.out.println("");
    }
    System.out.println(); 
    //Gives an example of 3 items used when calling getShortestPath
    System.out.println("**********Now with A random start page, a random middle page, and a random end page**********");
    System.out.println(); //spacing to make it easier to read
    System.out.println();
    start = p.getRandomNode();
    System.out.println("Start Page: " + start);
    String middle = p.getRandomNode();
    System.out.println("Middle Page: " + middle);
    end = p.getRandomNode();
    System.out.println("End Page: " + end);
    List<String> check = p.getShortestPath(start,middle,end);
    if(check.size() == 0){
        System.out.println("The start and end page did not have a path that went through the middle page");   
    }else{
        System.out.println("This took " + (check.size() - 1) + " links to get there through the middle page");
        System.out.print(check.get(0));
        for(int j = 1; j<check.size(); j ++){
            System.out.print(" --> ");
            System.out.print(check.get(j));
        }
        System.out.println("");
    }
    System.out.println();
    System.out.println(); // indentation to make it easier to read command line
}

}

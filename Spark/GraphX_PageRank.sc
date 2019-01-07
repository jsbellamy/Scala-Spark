//PageRank Algorithm with weighted Edges

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.graphx._

val conf = new SparkConf()
conf.setMaster("local")
conf.setAppName("First Application")

val sc = new SparkContext(conf)
val sp = SparkSession.builder().appName("DataFrameExercise").getOrCreate()

//Create our graph
val myVertices = sc.makeRDD(Array((1L, "A"), (2L, "B"), (3L, "C"),
  (4L, "D")))
val myEdges = sc.makeRDD(Array(Edge(1L, 2L, 7.0), Edge(1L, 3L, 5.0),
  Edge(2L, 4L, 9.0),
  Edge(3L, 1L, 5.0), Edge(3L, 2L, 15.0), Edge(3L, 4L, 6.0),
  Edge(4L, 3L, 8.0)))
val myGraph = Graph(myVertices, myEdges)

//Built in Static PageRank
//val rank = myGraph.staticPageRank(2).vertices
//rank.take(10)


//val graph = GraphLoader.edgeListFile(sc, "C:\\Users\\Jake\\Desktop\\Spark_Projects\\spark\\src\\main\\scala\\Test\\Data\\web-Google.txt")
//Get the out degrees of each Vertex
val tmp = myGraph.outerJoinVertices(myGraph.outDegrees) {
     (vid, vdata, deg) => deg.getOrElse(0)
}
tmp.vertices.take(10)

//Set the weight of each Edge (1/outDegree)*Initial Value
val edgetmp = tmp.mapTriplets( e => (1.0/e.srcAttr) * e.attr   )
edgetmp.triplets.take(5)

val initialGraph = edgetmp.mapVertices( (id, attr) => 1.00)
initialGraph.vertices.take(10)

//Another way to do the above code with less lines
//val initialGraph: Graph[Double, Double] = myGraph
//  .outerJoinVertices(myGraph.outDegrees) {
//    (vid, vdata, deg) => deg.getOrElse(0)
//  }
//  .mapTriplets(e => (1.0 / e.srcAttr) * e.attr)
//  .mapVertices((id, attr) => 1.0)

//Execute the PageRank Algorithm
val resetProb = 0.00
val initialMessage = 0.25
val numIter = 2

def vertexProgram(id: VertexId, attr: Double, msgSum: Double): Double =
  resetProb + (1.0 - resetProb) * msgSum
def sendMessage(edge: EdgeTriplet[Double, Double]): Iterator[(VertexId, Double)] =
  Iterator((edge.dstId, edge.srcAttr * edge.attr))
def messageCombiner(a: Double, b: Double): Double = a + b

val pagerankGraph = Pregel(initialGraph, initialMessage, numIter)(
  vertexProgram, sendMessage, messageCombiner)

pagerankGraph.vertices.take(10)

val rank = myGraph.staticPageRank(10).vertices.take(10)


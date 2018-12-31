import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.graphx._

val conf = new SparkConf()
conf.setMaster("local")
conf.setAppName("First Application")

val sc = new SparkContext(conf)
val sp = SparkSession.builder().appName("DataFrameExercise").getOrCreate()

//map and reduce function practice
val x = List("Rob", "Ammy")
x.map(name => "Hello " + name).foreach(println)

class fruitCount(val name:String, val num:Int)
val groceries = List(new fruitCount("banana", 5), new fruitCount("apple", 3))
groceries.map(f => f.num).reduce(_ + _)

val df_emps = sp.read.option("header", "true").csv("C:\\Users\\Jake\\Desktop\\Spark_Projects\\spark\\src\\main\\scala\\Test\\Data\\employee.txt")

df_emps.take(10)
df_emps.show(10)
df_emps.schema

//Create graph and find the VertixID with the most inDegrees
val graph = GraphLoader.edgeListFile(sc, "C:\\Users\\Jake\\Desktop\\Spark_Projects\\spark\\src\\main\\scala\\Test\\Data\\Cit-HepTh.txt")
graph.inDegrees.reduce((a,b) => if (a._2 > b._2) a else b)

//PageRank Example
graph.vertices.take(10)
val v = graph.pageRank(0.001).vertices
v.take(10)
v.reduce((a,b) => if (a._2 > b._2) a else b)

//Construct a graph
val myVertices = sc.makeRDD(Array((1L, "Ann"), (2L, "Bill"), (3L, "Charles"), (4L, "Diana"), (5L, "Went to the gym this morning")))
val myEdges = sc.makeRDD(Array(Edge(1L, 2L, "is-friends-with"), Edge(2L, 3L, "is-friends-with"), Edge(3L, 4L, "is-friends-with"),
Edge(4L, 5L, "Likes-status"), Edge(3L, 5L, "Wrote-status")))

val myGraph = Graph(myVertices, myEdges)

myGraph.vertices.collect
myGraph.edges.collect
myGraph.triplets.collect

myGraph.mapTriplets(t => (t.attr, t.attr == "is-friends-with" && t.srcAttr.toLowerCase.contains("a"))).triplets.collect


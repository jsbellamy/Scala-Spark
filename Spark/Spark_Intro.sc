import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.stat.Statistics

import scala._
import scala.util.Random

val conf = new SparkConf()
conf.setMaster("local")
conf.setAppName("First Application")

val sc = new SparkContext(conf)

//Create RDD
val rdd1 = sc.makeRDD(Array(1,2,3,4,5,6))
rdd1.collect().foreach(println)

//Create random array from 1 to 100000
val bigRng = util.Random.shuffle(1 to 100000)

//Create RDD and do some simple statistics
val bigPRng = sc.parallelize(bigRng)
println(bigPRng.mean)
bigPRng.min()
bigPRng.max()
bigPRng.take(25)

//New RDD that is the double of the first
val bigPRng2 = bigPRng.map(_ * 2)
bigPRng2.take(25)
bigPRng2.mean()

//Simple method to see if x is divisible by 3
def div(x:Int) = {
  val y = x%3
  y == 0

}

div(2)

val x = bigPRng2.takeSample(true, 1000, 1234)
bigPRng2.stats()

val series1 = Array.fill(100000)(Random.nextDouble)
val series2 = Array.fill(100000)(Random.nextDouble)

val pseries1 = sc.parallelize(series1)
val pseries2 = sc.parallelize(series2)

val myCorrelation:Double = Statistics.corr(pseries1, pseries2, "pearson")
val distTest = Statistics.kolmogorovSmirnovTest(pseries1, "norm", 0, 1)


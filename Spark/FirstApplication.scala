package Test.Spark

import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random

object FirstApplication {

  def main(args:Array[String]) = {
    //Create SparkConfig object and SparkContext object to initialize Spark
    val conf = new SparkConf()
    conf.setMaster("local")
    conf.setAppName("First Application")

    val sc = new SparkContext(conf)

    //Create RDD
    val rdd1 = sc.makeRDD(Array(1,2,3,4,5,6))
    rdd1.collect().foreach(println)

    val bigRng = scala.util.Random.shuffle(1 to 100000)

    val bigPRng = sc.parallelize(bigRng)
    println(bigPRng.mean)

    val series1 = Array.fill(100000)(Random.nextDouble)
    val series2 = Array.fill(100000)(Random.nextDouble)

    val pseries1 = sc.parallelize(series1)
    val pseries2 = sc.parallelize(series2)

    val myCorrelation:Double = Statistics.corr(pseries1, pseries2, "pearsom")

  }

}

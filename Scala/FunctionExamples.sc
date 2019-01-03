object functionExamples {
  def abs(x:Int) = if(x < 0) -x else x

  def fac(n:Int) = {
    var r = 1
    for(i <- 1 to n)
      r = r * i
      r
  }

  def factorial(n:Int):Int = if(n <= 0) 1 else n * factorial(n-1)

  def sum(args:Int*) = {
    var result = 0
    for(arg <- args) result += arg
    result
  }
}
functionExamples.abs(-5)
functionExamples.fac(5)
functionExamples.factorial(5)
functionExamples.sum(1,2,3,4,5)
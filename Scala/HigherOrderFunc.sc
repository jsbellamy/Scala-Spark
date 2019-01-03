object higherOrder {
  val double = (i:Int) => i * 2
  def highOrder(x:Int, y:Int=>Int) = y(x)

  val triple = (i:Int) => i * 3

  def sayHello = (name:String) => {
    "Hello" + " " + name
  }

  var y = 5
  val multiplier = (x:Int) => x * y
}
higherOrder.highOrder(5, higherOrder.triple)
higherOrder.sayHello("Jake")
higherOrder.multiplier(10)
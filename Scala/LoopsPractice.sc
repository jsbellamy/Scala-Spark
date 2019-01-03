object loopsPractice {
  var x = 10
  // While loop
//  while(x >= 0) {
//    println(x)
//    x -= 1
//  }

  // Do While Loop
//  do {
//    println(x)
//    x -= 1
//  } while(x >= 0)

  //For loop
//  for(x <- 10 to 0 by -1) {
//    println(x)
//  }
//  println("Blast off!")

  // For Comprehension Loop
//  var y = for(num <- 1 to 10) yield num + 1
  var word = "Monday"
  for(x <- 0 until word.length) {
    println(word(x))
  }
}
loopsPractice
package objects

data class Node(
    var id : String,
    var leftNode : Node?,
    var rigthNode : Node?,
    var parentNode : Node?,
    var level : Int,
    var ui : Any?,
    var size : Pair<Double, Double>
){
    constructor() : this("", null, null, null,0, null, Pair(0.00,0.00))
}
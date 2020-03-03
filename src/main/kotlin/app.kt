import objects.Node
import javax.swing.*

fun main(){
    Main.initTree()
}

object Main{
    lateinit var nodeA : Node

    var nodeList = arrayListOf<Node>()

    lateinit var view : View

    var path = ""

    lateinit var txtParent : JTextField

    lateinit var leftRadioButton : JRadioButton
    lateinit var rightRadioButton: JRadioButton

    val letters = arrayListOf("A","B","C","D","E","F","G","H","I","J","K","L","M","N","Ñ","O","P","Q","R","S","T","U","V","W","X","Y","Z")
    var inTurn = "A"

    fun initTree(){
        view = View()
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        view.setSize(1800, 1000)

        view.beginUpdates()
        nodeA =  Node("A",null,null, null, 1, view.createNode("A", 100.00, 0.00), Pair(100.00, 0.00));
        view.endUpdates()

        nodeList.add(nodeA)

        view.pack()

        view.isVisible = true
    }

    fun addNode(parentId : String, left : Boolean, right : Boolean){

        println("$parentId - $left - $right")

        nodeList.firstOrNull { it.id.equals(parentId) }?.let {

            if (left && it.leftNode != null){
                showMessage("El nodo izquierdo de \"$parentId\" ya se encuentra ocupado")
                return
            }else if (right && it.rigthNode != null){
                showMessage("El nodo derecho de \"$parentId\" ya se encuentra ocupado")
                return
            }

            var currentID = ""
            for (i in 0..letters.size - 1){
                if (letters[i].equals(inTurn)){
                    currentID = letters[(i + 1)]
                    inTurn = currentID
                    break
                }
            }

            var x = 0.0
            val y = it.size.second + 10
            if (left){
                x = it.size.first - 10
            }else if (right){
                x = it.size.first + 10
            }

            val nodeX =  Node(currentID,null,null, it,it.level + 1, view.createNode(currentID, x, y), Pair(x, y))

            if (left){
                it.leftNode = nodeX
            }else if (right){
                it.rigthNode = nodeX
            }

            view.joinNodes(it.ui, nodeX.ui)

            nodeList.add(nodeX)
        } ?: kotlin.run {
            showMessage("El nodo \"$parentId\" no existe")
        }
    }

    fun showMessage(text : String){
        JOptionPane.showMessageDialog(null, text)
    }

    fun searchNode(){
        path = "Recorrido de arbol (Izquierda-Centro-Derecha): \n"

        nodeList.firstOrNull()?.let {
            readNode(it, it.level)
        }

        showMessage(path)
    }

    fun readNode(node : Node, currentLevel : Int){
        node.leftNode?.let {
            readNode(it, currentLevel)
        }

        path += "• ${node.id}\n"

        node.rigthNode?.let {
            readNode(it, currentLevel)
        }
    }
}
import com.mxgraph.swing.mxGraphComponent
import com.mxgraph.util.mxRectangle
import com.mxgraph.view.mxGraph
import javafx.css.Size
import objects.Node
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.border.Border
import javax.swing.border.EmptyBorder

class View : JFrame(){

    init {
        JFrame.setDefaultLookAndFeelDecorated(true)
    }

    private val serialVersionUID = -2707712944901661771L
    private val graph: mxGraph = object : mxGraph() {
        override fun isPort(cell: Any): Boolean {
            val geo = getCellGeometry(cell)
            return geo?.isRelative ?: false
        }

        override fun getToolTipForCell(cell: Any): String {
            return if (model.isEdge(cell)) {
                ""
            } else super.getToolTipForCell(cell)
        }

        override fun isCellFoldable(cell: Any, collapse: Boolean): Boolean {
            return false
        }
    }

    private val parent = graph.defaultParent

    fun beginUpdates() {
        graph.model.beginUpdate()

        graph.minimumGraphSize = mxRectangle(0.00,0.00,2000.00,2000.00)
    }

    fun createNode(letra: String?, x: Double, y: Double): Any? {
        return graph.insertVertex(parent, null, letra, x * 10, y * 10, 50.0, 50.0)
    }

    fun joinNodes(nodo1: Any?, nodo2: Any?) {
        graph.insertEdge(parent, null, "", nodo1, nodo2)
    }

    fun endUpdates() {
        graph.model.endUpdate()
        val graphComponent = mxGraphComponent(graph)
        graphComponent.graphControl.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                val cell = graphComponent.getCellAt(e.x, e.y)
                if (cell != null) {
                    //Main.serachNode(graph.getLabel(cell))
                }
            }
        })
        val button = JButton("Agregar nodo")
        button.setBounds(15,15,5,5)
        button.addActionListener {
            Main.addNode(Main.txtParent.text, Main.leftRadioButton.isSelected, Main.rightRadioButton.isSelected)
        }

        val searchButton = JButton("Recorrer arbol (Izquierda-Centro-Derecha)")
        searchButton.setBounds(15,15,5,5)
        searchButton.addActionListener {
            Main.searchNode()
        }

        val label = JLabel("Nodo padre: ")

        Main.txtParent = JTextField()
        Main.txtParent.preferredSize = Dimension(100, 20)
        Main.txtParent.maximumSize = Dimension(100, 20)

        Main.leftRadioButton = JRadioButton("Izquierda")
        Main.rightRadioButton = JRadioButton("Derecha")
        val group = ButtonGroup()
        group.add(Main.leftRadioButton)
        group.add(Main.rightRadioButton)

        contentPane.layout = BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS)
        val panel = JPanel()
        panel.add(label)
        panel.add(Main.txtParent)
        panel.add(Main.leftRadioButton)
        panel.add(Main.rightRadioButton)
        panel.add(button)
        panel.add(searchButton)
        panel.alignmentX = Component.LEFT_ALIGNMENT
        panel.preferredSize = Dimension(500, 100)
        panel.maximumSize = Dimension(500, 100)
        panel.border = BorderFactory.createTitledBorder("Acciones")

        val graphPanel = JPanel()
        graphPanel.add(graphComponent)
        graphPanel.alignmentX = Component.CENTER_ALIGNMENT
        graphPanel.preferredSize = Dimension(2000, 1000)
        graphPanel.maximumSize = Dimension(2000, 1000)

        val mainPanel = JPanel()
        mainPanel.add(panel)
        mainPanel.add(graphPanel)

        contentPane.add(panel)
        contentPane.add(mainPanel)
    }
}
package thecodewarrior.paletteswap

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class Main : Application() {

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.getResource("sample.fxml"))
        primaryStage.title = "Palette Swapper"
        primaryStage.scene = Scene(root, 350.0, 420.0)
        primaryStage.isResizable = false
        primaryStage.show()

    }
}

fun main(args: Array<String>) {
    Application.launch(Main::class.java, *args)
}

package thecodewarrior.paletteswap

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.DragEvent
import javafx.scene.input.TransferMode
import java.io.File
import javafx.scene.input.Dragboard
import thecodewarrior.paletteswap.converter.Converter
import java.io.FileInputStream


class Controller {
    @FXML lateinit var convertButton: Button

    @FXML lateinit var sourcePalette: ImageView
    @FXML lateinit var destPalette: ImageView
    @FXML lateinit var sourceFiles: ImageView
    @FXML lateinit var destFolder: ImageView

    val converter = Converter()

    val imageIcon = Image(Main::class.java.getResourceAsStream("assets/imageIcon.png"))
    val folderIcon = Image(Main::class.java.getResourceAsStream("assets/folderIcon.png"))

    @FXML
    fun convertClicked(event: ActionEvent) {
        converter.convert()
    }

    @FXML
    fun sourcePaletteDragOver(event: DragEvent) {
        dragOver(event) {
            it.size == 1 && it.first().isFile && it.first().extension == "png"
        }
    }
    @FXML
    fun sourcePaletteDragDropped(event: DragEvent) {
        dragDropped(event, {
            it.size == 1 && it.first().isFile && it.first().extension == "png"
        }) {
            val file = it.first()
            converter.sourcePalette = file
            sourcePalette.image = Image(FileInputStream(file), 512.0, 512.0, true, false)
        }
    }

    @FXML
    fun destPaletteDragOver(event: DragEvent) {
        dragOver(event) {
            it.size == 1 && it.first().isFile && it.first().extension == "png"
        }
    }
    @FXML
    fun destPaletteDragDropped(event: DragEvent) {
        dragDropped(event, {
            it.size == 1 && it.first().isFile && it.first().extension == "png"
        }) {
            val file = it.first()
            converter.destPalette = file
            destPalette.image = Image(FileInputStream(file), 512.0, 512.0, true, false)
        }
    }

    @FXML
    fun sourceFilesDragOver(event: DragEvent) {
        dragOver(event) {
            (it.size == 1 && it.first().isDirectory) || (it.all { it.isFile && it.extension == "png" })
        }
    }
    @FXML
    fun sourceFilesDragDropped(event: DragEvent) {
        dragDropped(event, {
            (it.size == 1 && it.first().isDirectory) || (it.all { it.isFile && it.extension == "png" })
        }) {
            converter.sourceFiles = it
            if(it.any { it.isDirectory }) {
                sourceFiles.image = folderIcon
            } else {
                sourceFiles.image = imageIcon
            }
        }
    }


    @FXML
    fun destFolderDragOver(event: DragEvent) {
        dragOver(event) {
            it.size == 1 && it.first().isDirectory
        }
    }
    @FXML
    fun destFolderDragDropped(event: DragEvent) {
        dragDropped(event, {
            it.size == 1 && it.first().isDirectory
        }) {
            converter.destFolder = it.first()
            destFolder.image = folderIcon
        }
    }

    private fun dragOver(event: DragEvent, predicate: (List<File>) -> Boolean) {
        val db = event.dragboard
        if (db.hasFiles() && predicate(db.files)) {
            event.acceptTransferModes(TransferMode.COPY)
        } else {
            event.consume()
        }
    }
    private fun dragDropped(event: DragEvent, predicate: (List<File>) -> Boolean, callback: (List<File>) -> Unit) {
        val db = event.dragboard
        var success = false
        if (db.hasFiles() && predicate(db.files)) {
            success = true
            callback(db.files)
        }
        event.isDropCompleted = success
        event.consume()
    }
}

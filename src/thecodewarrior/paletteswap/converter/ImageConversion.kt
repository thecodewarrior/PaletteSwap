package thecodewarrior.paletteswap.converter

import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.image.WritableRaster
import java.awt.image.ColorModel

class ImageConversion(val file: File, val containing: File) {

    fun convert(swap: PaletteSwap, output: File) {
        val image = ImageIO.read(file)

        (0 until image.width).forEach { x ->
            (0 until image.height).forEach { y ->
                val color = image.getRGB(x, y)
                image.setRGB(x, y, swap.mappings[color] ?: color)
            }
        }

        val outputFile = File(output.path, file.relativeTo(containing).path)
        outputFile.parentFile.mkdirs()

        ImageIO.write(image, "png", outputFile)
    }

}

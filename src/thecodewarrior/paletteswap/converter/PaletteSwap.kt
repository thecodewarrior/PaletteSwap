package thecodewarrior.paletteswap.converter

import thecodewarrior.paletteswap.DialogException
import java.awt.Color
import java.io.File
import javax.imageio.ImageIO

class PaletteSwap(sourcePalette: File, destPalette: File) {
    val mappings = mutableMapOf<Int, Int>()

    init {
        val sourceImage = ImageIO.read(sourcePalette)
        val destImage = ImageIO.read(destPalette)

        if(sourceImage.width != destImage.width || sourceImage.height != destImage.height)
            throw DialogException("Palettes not the same size")

        (0 until sourceImage.width).forEach { x ->
            (0 until sourceImage.height).forEach { y ->
                mappings.put(sourceImage.getRGB(x, y), destImage.getRGB(x, y))
            }
        }
    }
}
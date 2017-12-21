package thecodewarrior.paletteswap.converter

import java.io.File
import java.io.IOException



class Converter {
    lateinit var sourcePalette: File
    lateinit var destPalette: File
    lateinit var sourceFiles: List<File>
    lateinit var destFolder: File

    fun convert() {
        try {
            sourcePalette.javaClass
            destPalette.javaClass
            sourceFiles.javaClass
            destFolder.javaClass
        } catch (e: UninitializedPropertyAccessException) {
            println("Not initialized")
            return
        }

        val conversions = sourceFiles.flatMap { source ->
            if(source.isDirectory) {
                recursiveList(source).map { ImageConversion(it, source) }
            } else {
                listOf(ImageConversion(source, source.parentFile))
            }
        }

        val swap = PaletteSwap(sourcePalette, destPalette)

        conversions.forEach { it.convert(swap, destFolder) }
    }

    fun recursiveList(dir: File, list: MutableList<File> = mutableListOf()): MutableList<File> {
        try {
            val files = dir.listFiles()
            for (file in files) {
                if (file.isDirectory) {
                    recursiveList(file, list)
                } else {
                    list.add(file)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return list
    }
}

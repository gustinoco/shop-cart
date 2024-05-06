import java.io.File

fun loadRepositories(filePath: String): List<String> {
    val file = File(filePath)
    return file.readLines().map { it.trim() }
}
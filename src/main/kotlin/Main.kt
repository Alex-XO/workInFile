import java.io.File
import com.beust.klaxon.Klaxon

fun getFile(filePath: String): File {
    val url = {}.javaClass.classLoader.getResource(filePath)
    if (url == null) {
        throw Error("File not found: $filePath")
    }
    return File(url.path)
}

data class Person(val name: String, val age: Int)

//Filter: Output the name if it contains the letter "a"
fun filteredName(namePerson: List<String>) {
    val filtered = namePerson.filter { it.contains("a", true) }
    println(filtered)
}

//Sorts the names in alphabetical order and outputs them with indexes
fun sortsNames(namePerson: List<String>) {
    namePerson.sorted().forEachIndexed { index, name
        -> println("${index + 1}. $name ") }
}

//Sorts objects by name and outputs them with indexes and number of years
fun sortsObjects(result: List<Person>) {
    result.sortedBy { it.name }.forEachIndexed{ index, person
        -> println("${index + 1}. ${person.name} (${person.age})") }
}

fun newFile(file: File, result: List<Person>) {
    val sortFile = file.parentFile.resolve("sortUsers.json")
    val sortedList = result
        .filter { it.name.contains("a", true) }
        .filter { it.age > 23 }
        .sortedBy { it.name }
    sortedList.forEachIndexed { index, person ->
        println("${index + 1}. ${person.name} (${person.age})")
    }
    sortFile.writeText(Klaxon().toJsonString(sortedList), Charsets.UTF_8)
}

fun main() {
    val file = getFile("./data/users.json")
    val result = Klaxon()
        .parseArray<Person>(file.readText())

    if (result == null) {
        throw Exception("Пользователь не найден.")
    }

    val namePerson = result.map { person -> person.name }

    filteredName(namePerson)
    sortsNames(namePerson)
    sortsObjects(result)
    newFile(file, result)
}
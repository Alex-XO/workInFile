package api.user

import java.io.File
import com.beust.klaxon.Klaxon

//class user (login, password)
//add edit get delete login

class User(val name: String, val login: String, val password: String)

/*fun getFile(filePath: String): File {
    val url = {}.javaClass.classLoader.getResource(filePath)
    if (url == null) {
        throw Error("File not found: $filePath")
    }
    return File(url.path)
} */

fun main() {
    val file = File("C:/Users/1/IdeaProjects/workInFile/src/main/resources/database/users.json")
    println(file.absolutePath)
    val result = Klaxon()
        .parseArray<User>(file.readText())

    if (result == null) {
        throw Exception("Пользователь не найден.")
    }


    userAdd(result, file)
}

fun userAdd(userList: List<User>, file: File) {
    print("Name: ")
    val name = readln()
    print("Login: ")
    val login = readln()
    print("Password: ")
    val password = readln()

    val user = User(name, login, password)
    val result = Klaxon().toJsonString(userList + user)
    file.writeText(result)
    println(result)
}

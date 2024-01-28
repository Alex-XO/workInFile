package api.user

import java.io.File
import com.beust.klaxon.Klaxon

class User(var name: String, val login: String, val password: String)

fun main() {
    val file = File("./src/main/resources/database/users.json")
    val result = Klaxon()
        .parseArray<User>(file.readText())

    if (result == null) {
        throw Exception("File not found.")
    }

    print(  "\n 1. Add a new user" +
            "\n 2. Edit the user" +
            "\n 3. Find a user" +
            "\n 4. Delete the user" +
            "\n Select an action: ")

    val response = readln()

    when (response) {
        "1" -> userAdd(result, file)
        "2" -> userEdit(result, file)
        "3" -> userGet(result)
        "4" -> userDelete(result, file)
    }
}

fun saveInFile(userList: List<User>, file: File) {
    val result = Klaxon().toJsonString(userList)
    file.writeText(result)
}

fun userAdd(userList: List<User>, file: File) {
    print("Name: ")
    val name = readln()
    print("Login: ")
    val login = readln()
    print("Password: ")
    val password = readln()

    val user = User(name, login, password)

    saveInFile(userList + user, file)
}

fun userEdit(userList: List<User>, file: File) {
    print("Enter the user name to edit: ")
    val searchName = readln()
    val user = userList.find { it.name == searchName }

    if (user == null)  {
        throw Exception("User not found.")
    }

    print("Enter new name: ")
    val newName = readln()
    user.name = newName

    saveInFile(userList, file)
}

fun userGet(userList: List<User>) {
    print("Enter the user name or login: ")
    val search = readln()
    userList
        .filter { it.name.contains(search, true) || it.login.contains(search, true) }
        .forEach { println("${it.name} ${it.login}") }

    if (userList.isEmpty())  {
        throw Exception("User not found.")
    }
}

fun userDelete(userList: List<User>, file: File) {
    print("Enter the user name or login to delete: ")
    val search = readln()

    val updateUserList = userList
        .filter { it.name != search && it.login != search }

    saveInFile(updateUserList, file)
}
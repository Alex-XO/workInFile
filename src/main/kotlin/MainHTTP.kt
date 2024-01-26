import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import com.beust.klaxon.Klaxon

data class ToDo(val userId: Int, val id: Int, val title: String, val completed: Boolean)
data class Users(val id: Int, val name: String, val username: String, val email: String)

fun getJsonWithHttp(url: String): HttpResponse<String> {
    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    return response
}

fun useToDoJson(userId: Int) {
    val urlToDo = "https://jsonplaceholder.typicode.com/todos"
    val response = getJsonWithHttp(urlToDo)
    val listToDo = Klaxon()
        .parseArray<ToDo>(response.body())

    if (listToDo == null) {
        throw Exception("Пользователь не найден.")
    }


    val usersList = listToDo
        .filter { it.userId == userId }
    val completedList = usersList
        .filter { it.completed }
    val notCompletedList = usersList
        .filter { !it.completed }
    println("completed: ${completedList.count()} not completed: ${notCompletedList.count()}")
    notCompletedList
        .forEach { println(" ${it.title}")}
}

fun useUsersJson(userId: Int) {
    val urlUsers = "https://jsonplaceholder.typicode.com/users"
    val response = getJsonWithHttp(urlUsers)
    val listUsers = Klaxon()
        .parseArray<Users>(response.body())

    if (listUsers == null) {
        throw Exception("Пользователь не найден.")
    }

    val user = listUsers
        .find { it.id == userId }
        println("User: ${user?.name} ")
    useToDoJson(userId)
}
fun main() {
    print("Введите userId для получения информации: ")
    val userId = readln().toInt()

    useUsersJson(userId)
}


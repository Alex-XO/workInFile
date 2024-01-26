import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import com.beust.klaxon.Klaxon

fun getJsonWithHttp(url: String): HttpResponse<String> {
    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    return response
}
fun allToDo(): List<ToDo> {
    val urlToDo = "https://jsonplaceholder.typicode.com/todos"
    val response = getJsonWithHttp(urlToDo)
    val listToDo = Klaxon()
        .parseArray<ToDo>(response.body())

    if (listToDo == null) {
        throw Exception("Пользователь не найден.")
    }
    return listToDo
}

fun allUsers(): List<User> {
    val urlUsers = "https://jsonplaceholder.typicode.com/users"
    val response = getJsonWithHttp(urlUsers)
    val listUsers = Klaxon()
        .parseArray<User>(response.body())

    if (listUsers == null) {
        throw Exception("Пользователь не найден.")
    }
    return listUsers
}
data class ToDo(val userId: Int, val id: Int, val title: String, val completed: Boolean)
data class User(val id: Int, val name: String, val username: String, val email: String)
class Statistic(val countCompleted: Int, val countNotCompleted: Int, val notCompletedTodos: List<ToDo>)
class UserStatistic(val user: User, val statistic: Statistic)
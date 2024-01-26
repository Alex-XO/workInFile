fun getTodoStatisticByUserId(userId: Int): Statistic {
    val listToDo = allToDo()
        .filter { it.userId == userId }
    val completedList = listToDo
        .filter { it.completed }
    val notCompletedTodos = listToDo
        .filter { !it.completed }

    return Statistic(completedList.count(), notCompletedTodos.count(), notCompletedTodos)
}

fun main() {
    print("Введите userId для получения информации: ")
    val userId = readln().toInt()
    val user = allUsers()
        .find { it.id == userId }
    println("User: ${user?.name} ")

    val userStatistic = getTodoStatisticByUserId(userId)
    println("completed: ${userStatistic.countCompleted} not completed: ${userStatistic.countNotCompleted}")
    userStatistic.notCompletedTodos
        .forEach { println(" ${it.title}")}
}


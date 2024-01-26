fun main() {
    allUsers().map { UserStatistic(it, getTodoStatisticByUserId(it.id)) }
        .sortedByDescending { it.statistic.countNotCompleted }
        .forEach {
            println("${it.user.name} completed: ${it.statistic.countCompleted} " +
                    "not completed: ${it.statistic.countNotCompleted}")
        }
}
package api.user

import java.io.File
import com.beust.klaxon.Klaxon

class Company(val titleCompany: String, var users: MutableList<User>)

class ConsoleManager {
    fun createUser(): User {
        print("Name: ")
        val name = readln()

        print("Login: ")
        val login = readln()


        print("Password: ")
        val password = readln()

        val user = User(name, login, password)

        return user
    }

}

class CompanyManager {
    private val fileCompany = File("./src/main/resources/database/company.json")
    private val companyList = initCompany()

    fun isUniqueCompany(titleCompany: String, companyList: List<Company>): Boolean {
        val company = companyList.find { titleCompany == it.titleCompany }

        if (company != null) {
            println("A company with this login already exists.")
            return false
        } else {
            return true
        }
    }

    fun addCompany(title: String): Company {
        val company = Company(title, mutableListOf())

        return company
    }

    fun getAllCompany(): List<Company> {
        return companyList
    }

    private fun initCompany(): List<Company> {
        val resultCompany = Klaxon()
            .parseArray<Company>(fileCompany.readText())

        return resultCompany.orEmpty()
    }

    fun saveCompany(company: Company) {
        if (isUniqueCompany(company.titleCompany, companyList)) {
            saveInFile(companyList + company, fileCompany)
        } else {
            saveInFile(companyList, fileCompany)
        }

    }

    fun getCompanyByTitle(title: String): Company {
        val company = companyList.find { it.titleCompany == title }

        if (company == null) {
            throw Exception("Company not found.")
        }

        return company
    }
}

/*class UserManager() {
    fun getAllUsers(): List<User> {
        val fileUser = File("./src/main/resources/database/users.json")
        val resultUser = Klaxon()
            .parseArray<User>(fileUser.readText())

        return resultUser.orEmpty()
    }
} */

fun main() {
    val companyManager = CompanyManager()
    val companies = companyManager.getAllCompany()

    print("Title company: ")
    val titleCompany = readln()
    val consoleManager = ConsoleManager()

    if (companyManager.isUniqueCompany(titleCompany, companies)) {
        val company = companyManager.addCompany(titleCompany)
        val user = consoleManager.createUser()
        company.users.add(user)
        companyManager.saveCompany(company)
    } else {
        val company = companyManager.getCompanyByTitle(titleCompany)
        val user = consoleManager.createUser()
        company.users.add(user)
        companyManager.saveCompany(company)
    }
}
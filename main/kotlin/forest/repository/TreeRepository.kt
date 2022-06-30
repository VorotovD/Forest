package forest.repository

import forest.dto.Cost
import forest.dto.Join
import forest.dto.Tree


interface TreeRepository {

    fun <T> getAllFromTable(tableName: String): List<T>

    fun getJoinFromDB(table1: String, table2: String, usingBy: String): List<Join>

    fun <T> getTreeById(id: Int, tableName: String): List<T>

    fun createTree(tree: Tree)

    fun createCost(cost: Cost)

    fun updateTree(tree: Tree): Int

    fun updateCost(cost: Cost): Int

    fun delete(id: Int, tableName: String): Int
}
package forest.repository

import forest.dto.Cost
import forest.dto.Join
import forest.dto.Tree
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet


@Suppress("IMPLICIT_CAST_TO_ANY")
@Repository
class TreeRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : TreeRepository {

    override fun <T> getAllFromTable(tableName: String): List<T> {

        return jdbcTemplate.query("select * from $tableName", tableMapper(tableName))
    }

    override fun <T> getTreeById(id: Int, tableName: String): List<T> {
        return jdbcTemplate.query("select * from $tableName where id =$id", tableMapper(tableName))
    }

    override fun getJoinFromDB(table1: String, table2: String, usingBy: String): List<Join> {
        return jdbcTemplate.query("select * from $table1 join $table2 using($usingBy)", joinMapper())
    }

    override fun createTree(tree: Tree) {
        jdbcTemplate.update("insert into tree values (${tree.id},'${tree.type}','${tree.name}')")
    }

    override fun createCost(cost: Cost) {
        jdbcTemplate.update("insert into cost values (${cost.id},'${cost.type}','${cost.cost}')")
    }

    override fun updateTree(tree: Tree): Int {
        queryTreeById(tree.id)
        return jdbcTemplate.update("update tree set type = '${tree.type}', name = '${tree.name}' where id = ${tree.id}")
    }

    override fun updateCost(cost: Cost): Int {
        queryCostById(cost.id)
        return jdbcTemplate.update("update cost set type = '${cost.type}', cost = '${cost.cost}' where id = ${cost.id}")
    }

    override fun delete(id: Int, tableName: String): Int {
        if (tableName == "tree") {
            queryTreeById(id)
        } else {
            queryCostById(id)
        }
        val sql: String = "delete from $tableName where id =$id"
        return jdbcTemplate.update(sql)
    }


    fun queryTreeById(id: Int) {
        val sqlGetById: String = "select * from tree where id =$id"
        val resultGetById = jdbcTemplate.query(sqlGetById, treeMapper()).first()
    }

    fun queryCostById(id: Int) {
        val sqlGetById: String = "select * from cost where id =$id"
        val resultGetById = jdbcTemplate.query(sqlGetById, costMapper()).first()
    }

    fun joinMapper(): RowMapper<Join> {
        return RowMapper<Join> { rs, rowNum ->
            Join(
                rs.getInt("id"),
                rs.getString("type"),
                rs.getString("name"),
                rs.getString("cost")
            )

        }
    }

    fun <T> tableMapper(table: String): RowMapper<T> {

        val result = when (table) {
            "tree" -> treeMapper()
            "cost" -> costMapper()
            else -> java.lang.IllegalStateException()
        }
        result as RowMapper<T>
        return result
    }


    fun treeMapper(): RowMapper<Tree> {
        val treeMapper = RowMapper<Tree> { rs: ResultSet, rowNum: Int ->
            Tree(
                rs.getInt("id"),
                rs.getString("type"),
                rs.getString("name")
            )
        }
        return treeMapper
    }

    fun costMapper(): RowMapper<Cost> {
        val costMapper = RowMapper<Cost> { rs: ResultSet, rowNum: Int ->
            Cost(
                rs.getInt("id"),
                rs.getString("type"),
                rs.getInt("cost")
            )
        }
        return costMapper
    }

}
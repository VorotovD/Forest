package forest.controller

import forest.dto.*
import forest.repository.TreeRepositoryImpl
import org.springframework.web.bind.annotation.*


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/forest")
class RestController(private val forestRepositoryImpl: TreeRepositoryImpl) {

    @GetMapping("/all/{tableName}")
    fun <T> getAll(@PathVariable tableName: String): List<T> {
        return forestRepositoryImpl.getAllFromTable(tableName)
    }

    @GetMapping("/join")
    fun getJoin(@RequestBody joinSearch: JoinSearch): List<Join> {
        return forestRepositoryImpl.getJoinFromDB(joinSearch.table1, joinSearch.table2, joinSearch.usingBy)
    }

    @GetMapping("/id")
    fun <T> getTreeById(@RequestBody idSearch: IdSearch): List<T> {
        return forestRepositoryImpl.getTreeById(idSearch.id, idSearch.tableName)
    }

    @PostMapping("/createTree")
    fun createTreeById(@RequestBody tree: Tree) {
        forestRepositoryImpl.createTree(tree)
    }

    @PostMapping("/createCost")
    fun createCostById(@RequestBody cost: Cost) {
        forestRepositoryImpl.createCost(cost)
    }

    @PutMapping("/updateTree")
    fun updateTreeById(@RequestBody tree: Tree) {
        forestRepositoryImpl.updateTree(tree)
    }

    @PutMapping("/updateCost")
    fun updateCostById(@RequestBody cost: Cost) {
        forestRepositoryImpl.updateCost(cost)
    }

    @DeleteMapping("/delete")
    fun deleteById(@RequestBody delete: Delete) {
        forestRepositoryImpl.delete(delete.id, delete.tableName)
    }
}
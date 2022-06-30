package forest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ForestApplication

fun main(args: Array<String>) {
	runApplication<ForestApplication>(*args)
}

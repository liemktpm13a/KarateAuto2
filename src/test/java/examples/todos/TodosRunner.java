package examples.todos;

import com.intuit.karate.junit5.Karate;

class TodosRunner {
    
    @Karate.Test
    Karate testTodos() {
        return Karate.run("todos").relativeTo(getClass());
    }    
}
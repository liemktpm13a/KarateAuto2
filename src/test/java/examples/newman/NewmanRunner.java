package examples.newman;

import com.intuit.karate.junit5.Karate;

class NewmanRunner {
    
    @Karate.Test
    Karate testTodos() {
        return Karate.run("callNewman").relativeTo(getClass());
    }    
}
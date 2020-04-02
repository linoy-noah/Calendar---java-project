package il.ac.shenkar.final_project.calender.model;


import static org.junit.jupiter.api.Assertions.*;
import java.util.Vector;

import il.ac.shenkar.final_project.calender.MVVMDemoException;
import il.ac.shenkar.final_project.calender.eventDef;
import org.junit.jupiter.api.Test;

class ModelTest {
    IModel model = new Model();
//"501,test,1999-01-01,1999-01-01,1:00,test test",model.getItemsByDate(1,1999)

    @Test
    void checkItems() {
        assertNotNull(model.getItemsByDate(1,1999));//success
    }

    @Test
    public void checkAddItem() {
        assertNotNull(model.addItem(new eventDef("test,1999-01-01,1999-01-01,1:00,test test",1))); //success
        assertNotNull(model.addItem(new eventDef("test,1999-2-2,1999-2-2,2:30,test test",1))); //success
        //assertNotNull(model.addItem(new eventDef("test,12,1999-1-1,1:00,test test",1))); //fail
    }
    @Test
    public void checkIfDelete() {
        assertTrue(model.deleteItem(1801));//success
    }
}

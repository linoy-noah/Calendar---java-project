package il.ac.shenkar.final_project.calender.model;

import il.ac.shenkar.final_project.calender.DerbyDB;
import il.ac.shenkar.final_project.calender.MVVMDemoException;
import il.ac.shenkar.final_project.calender.eventDef;
import il.ac.shenkar.final_project.calender.viewModel.ViewModel;

import java.util.Date;
import java.util.Vector;

import static il.ac.shenkar.final_project.calender.log._log;
/**
 * Model Class implements from IModel interface
 *@see IModel
 * in this class we make our calls to the database (DerbyDB database) for requests
 *@author  Linoy Noah and Ido Kilker
 *
 *
 */
public class Model implements IModel{
    DerbyDB db;
    public Model(){
        db=new DerbyDB();
    }

    /**
     * ask for all the rows in the database that their start date is in specific year and month
     * @param mon month that we want to check
     * @param year year that we want to check
     * @return vector of string whit the value of the rows we got from the database
     */
    @Override
    public Vector<String> getItemsByDate(int mon,int year)  {
        String text="select * from calendar where month(startDate) = "+mon+" and year(startDate) = "+year+"  ORDER BY startDate";
        synchronized(_log){
            _log.info("execute query:  "+text);
        }
        return db.execQuery(text);


    }

    /**
     * add item to the database
     * @param event we made a class that present our item
     * @see eventDef
     * @return true on success and false if fail
     */

    @Override
    public boolean addItem(eventDef event) {
        String text = "insert into calendar (eventName, startDate, endDate, time, description) values ("+ event.insert()+")";
        synchronized(_log){
            _log.info("execute query:  "+text);
        }
        return db.exec(text);
    }

    /**
     * delete item from the database by his primary key(id)
     * @param id primary key
     * @return true on success and false if fail
     */
    @Override
    public boolean deleteItem(int id)  {
        String text = "delete from calendar where id="+ id;
        return db.exec(text);
    }
}

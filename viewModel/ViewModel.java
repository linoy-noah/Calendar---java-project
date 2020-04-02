package il.ac.shenkar.final_project.calender.viewModel;

import il.ac.shenkar.final_project.calender.MVVMDemoException;
import il.ac.shenkar.final_project.calender.eventDef;
import il.ac.shenkar.final_project.calender.model.IModel;
import il.ac.shenkar.final_project.calender.model.Model;
import il.ac.shenkar.final_project.calender.view.IView;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import static il.ac.shenkar.final_project.calender.log._log;

/**
 * ViewModel Class implements from IViewModel interface
 *@see IViewModel
 * in this class we make connect between our View Class and Model Class
 *@author  Linoy Noah and Ido Kilker
 *
 *
 */
public class ViewModel implements IViewModel{
    private IView view;
    private IModel model;


    /**
     * set view
     * @param view class that create the GUI
     */

    public void setView(IView view) {
        this.view = view;
        synchronized(_log){
            _log.info("set IView");
        }
    }

    /**
     * set model
     * @param model class that call the database whit request
     */
    public void setModel(IModel model) {
        this.model = model;
        synchronized(_log){
            _log.info("set IModel");
        }
    }

    /**
     * call the getItemsByDate method in Model,
     * create array of string[] that present the result
     * and send it to the method showItems in View
     * @param mon month that we want to check
     * @param year year that we want to check
     */
    @Override
    public void getItems(int mon,int year) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Vector<String> text=model.getItemsByDate(mon,year);
                String[][]  eventsList = new String[text.size()][];
                int i=0;
                for (String t:text) {
                    synchronized(_log){
                        _log.info("read from db: "+t);
                    }
                    eventsList[i]=(new eventDef(t).toStringA());
                    i++;
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            view.showItems(eventsList);
                            synchronized(_log){
                                _log.info("call function from view: show_items");
                            }
                        } catch (MVVMDemoException m) {
                            m.printStackTrace();
                        }
                    }
                });

            }
        }).start();
    }

    /**
     * call the method addItem in Model to add an event to the database
     * and call it's method getItem so the changes will show in the View
     * @param e we made a class that present our item
     * @see eventDef
     */

    @Override
    public void addItem(eventDef e) throws MVVMDemoException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                model.addItem(e);
                synchronized(_log){
                    _log.info("insert:  "+e);
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        getItems(view.getCurrmon(),view.getCurryear());

                    }
                });

            }
        }).start();
    }

    /**
     * call the method deleteItem in Model to delete an event to the database
     * and call it's method getItem so the changes will show in the View
     * @param id primary key (present the eventID)
     */
    @Override
    public void deleteItem(int id) throws MVVMDemoException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                model.deleteItem(id);
                synchronized(_log){
                    _log.info("delete item:  "+id);
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        getItems(view.getCurrmon(),view.getCurryear());
                    }
                });

            }
        }).start();
    }
}



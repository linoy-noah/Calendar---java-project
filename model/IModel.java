package il.ac.shenkar.final_project.calender.model;
import il.ac.shenkar.final_project.calender.MVVMDemoException;
import il.ac.shenkar.final_project.calender.eventDef;
import il.ac.shenkar.final_project.calender.viewModel.ViewModel;

import java.util.Date;
import java.util.Vector;

public interface IModel {


    public Vector<String> getItemsByDate(int mon,int year) ;
    public boolean addItem(eventDef event) ;
    public boolean deleteItem(int id) ;
}

package il.ac.shenkar.final_project.calender.viewModel;
import il.ac.shenkar.final_project.calender.MVVMDemoException;
import il.ac.shenkar.final_project.calender.eventDef;
import il.ac.shenkar.final_project.calender.model.IModel;
import il.ac.shenkar.final_project.calender.view.IView;

import java.util.Date;

public interface IViewModel {
//    public void getItems(Date d1, Date d2);
    public void getItems(int mon,int year);
//    public void updateItem(eventDef ev,String ch);
    public void addItem(eventDef e) throws MVVMDemoException;
    public void deleteItem(int id) throws MVVMDemoException;
    public void setModel(IModel model);
    public void setView(IView view);
}

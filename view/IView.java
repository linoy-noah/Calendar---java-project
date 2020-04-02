package il.ac.shenkar.final_project.calender.view;


import il.ac.shenkar.final_project.calender.eventDef;
import il.ac.shenkar.final_project.calender.viewModel.IViewModel;
import il.ac.shenkar.final_project.calender.MVVMDemoException;
import il.ac.shenkar.final_project.calender.viewModel.ViewModel;

import java.util.Vector;

public interface IView {
    public void setMessage(String text) throws MVVMDemoException;
    public  void showItems(String[][] items) throws MVVMDemoException;
    public void setViewModel(IViewModel ob);
    public void start();
    public int getCurryear();
    public int getCurrmon();
}

package il.ac.shenkar.final_project.calender;



import il.ac.shenkar.final_project.calender.model.IModel;
import il.ac.shenkar.final_project.calender.model.Model;
import il.ac.shenkar.final_project.calender.view.IView;
import il.ac.shenkar.final_project.calender.view.View;
import il.ac.shenkar.final_project.calender.viewModel.IViewModel;
import il.ac.shenkar.final_project.calender.viewModel.ViewModel;

import javax.swing.*;

    public class Program {
        public static void main(String[] args) throws MVVMDemoException {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    IModel m = new Model();
                    IView v = new View();
                    IViewModel vm = new ViewModel();
                    v.setViewModel(vm);
                    vm.setModel(m);
                    vm.setView(v);
                    v.start();


                }
            });

        }
    }



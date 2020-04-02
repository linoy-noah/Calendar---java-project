package il.ac.shenkar.final_project.calender.view;

import il.ac.shenkar.final_project.calender.MVVMDemoException;
import il.ac.shenkar.final_project.calender.eventDef;
import il.ac.shenkar.final_project.calender.model.IModel;
import il.ac.shenkar.final_project.calender.viewModel.IViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.Vector;

import static il.ac.shenkar.final_project.calender.log._log;
/**
 * View Class implements from IView interface
 *@see IView
 * in this class we create the GUI
 *@author  Linoy Noah and Ido Kilker
 */
public class View implements IView{

    IViewModel viewmodel;
    private JFrame frame;
    private String[] monthsName= {"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST"
            ,"SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
    private JPanel monPanel;
    private JPanel addremovePanel;
    private JPanel removePanel;
    private JPanel yearPanel;
    private JPanel navPanel;
    private JPanel centerPanel;
    private JButton removeSubmit;
    private JLabel add;
    private JLabel remove;
    private JTextField  removeID;
    private JLabel removeActionLabel;
    private JPanel removeLabelPanel;
    private JPanel removeActionPanel;
    private JLabel yearLabel;
    private JLabel eventLabel;
    private JComboBox yearSelect;
    private JTable eventTable;
    public static String errorMsg="";
    public static int realYear, realMonth, realDay, currentYear, currentMonth;
    private JScrollPane sp;
    private String[][] tableData ;
    private String[] columnNames = { "ID", "Event Name", "Start Date", "End Date", "Hour" , "Description"};
    //Add panel
    private JPanel addPanel;
    private JPanel addLabelPanel;
    private JPanel addDetails;
    private JButton addSubmit;
    private JLabel nameLabel;
    private JTextField  nameText;
    private JLabel sDateLabel;          ///need to check the type of date
    private JTextField  sDateText;
    private JLabel eDateLabel;          ///need to check the type of date
    private JTextField  eDateText;
    private JLabel sHourLabel;          ///need to check the type of hour
    private JTextField  sHourText;
    private JLabel descLabel;
    private JTextField descText;
    //Actions Methods
    class removeSubmit_Action implements ActionListener {
        /**
         * actionPerformed (removeSubmit_Action)
         * @param e (ActionEvent)
         * this function sets what will happened when clicking the remove button
         * the function will search for the event by id and remove it if its realy exists. if the id doesnt exist or the textfield data is invalid or empty, the function will popup error message
         */
        public void actionPerformed (ActionEvent e){
            if(removeID.getText().isEmpty()){
                try {
                    synchronized(_log){
                        _log.info("remove item fail:  TextField is empty");
                    }
                    setMessage("TextField is empty");

                    return;
                } catch (MVVMDemoException ex) {
                    ex.printStackTrace();
                }
            }
            int id;
            try{
                id = Integer.parseInt(removeID.getText());
            }catch(NumberFormatException m){
                try {
                    setMessage("TextField is invalid");
                    synchronized(_log){
                        _log.info("remove item fail:  TextField is invalid");
                    }
                } catch (MVVMDemoException ex) {
                    ex.printStackTrace();
                }
                return;
            }


            String[] event=searchEv(id);
            if (event==null){ //check if textField is empty

                try {
                    setMessage("TextField is invalid");
                    synchronized(_log){
                        _log.info("remove item fail:  TextField is invalid");
                    }
                } catch (MVVMDemoException ex) {
                    ex.printStackTrace();
                }
                return;
            }

            else{

                try {
                    viewmodel.deleteItem(Integer.parseInt(removeID.getText()));
                } catch (MVVMDemoException ex) {
                    ex.printStackTrace();
                }


            }

        }
    }
    class addSubmit_Action implements ActionListener {
        /**
         * actionPerformed (addSubmit_Action)
         * @param e (ActionEvent)
         * this function sets what will happened when clicking the add button
         * the function will create a new event. if one of the textfield data is invalid or empty, the function will popup error message
         */
        public void actionPerformed (ActionEvent e){
            errorMsg="";
            String event="";
            if (nameText.getText().isEmpty()){ //check if textField is empty
                errorMsg="One of the TextFields is empty";
                synchronized(_log){
                    _log.info("add item fail: "+errorMsg);
                }
                try {
                    setMessage(errorMsg);
                } catch (MVVMDemoException ex) {
                    ex.printStackTrace();
                }

                return;
            }
            else if(sDateText.getText().isEmpty()){ //check if textField is empty
                errorMsg="One of the TextFields is empty";

                try {
                    setMessage(errorMsg);
                    synchronized(_log){
                        _log.info("add item fail: "+errorMsg);
                    }
                } catch (MVVMDemoException ex) {
                    ex.printStackTrace();
                }
                return;
            }
            else if(eDateText.getText().isEmpty()){ //check if textField is empty
                errorMsg="One of the TextFields is empty";

                try {
                    setMessage(errorMsg);
                    synchronized(_log){
                        _log.info("add item fail: "+errorMsg);
                    }
                } catch (MVVMDemoException ex) {
                    ex.printStackTrace();
                }
                return;
            }
            else if(sHourText.getText().isEmpty()){ //check if textField is empty
                errorMsg="One of the TextFields is empty";

                try {
                    setMessage(errorMsg);
                    synchronized(_log){
                        _log.info("add item fail: "+errorMsg);
                    }
                } catch (MVVMDemoException ex) {
                    ex.printStackTrace();
                }
                return;
            }
            else if(descText.getText().isEmpty()){ //check if textField is empty
                errorMsg="One of the TextFields is empty";

                try {
                    setMessage(errorMsg);
                    synchronized(_log){
                        _log.info("add item fail: "+errorMsg);
                    }
                } catch (MVVMDemoException ex) {
                    ex.printStackTrace();
                }
                return;
            }
            else{
                event=nameText.getText()+","+sDateText.getText()+","+eDateText.getText()+","+sHourText.getText()+","+descText.getText();

                try {
                    viewmodel.addItem(new eventDef(event,1));
                } catch (MVVMDemoException ex) {
                    ex.printStackTrace();
                }

            }



        }
    }

    class yearSelect_Action implements ActionListener{
        /**
         * actionPerformed (yearSelect_Action)
         * @param e (ActionEvent)
         * this function sets what will happened when clicking the select year combo box
         * the function will set the current year to the year selected by the user
         */
        public void actionPerformed (ActionEvent e){
            if (yearSelect.getSelectedItem() != null){
                String b = yearSelect.getSelectedItem().toString();
                currentYear = Integer.parseInt(b);
                viewmodel.getItems(currentMonth+1,currentYear);

            }
        }
    }
    /////////////////////////////////

    /**
     * View() constructor
     */
    public View () {

        addremovePanel=new JPanel();
        addLabelPanel=new JPanel();
        add=new JLabel("Add new event:");
        addSubmit=new JButton("Add");
        addPanel=new JPanel();
        addDetails=new JPanel();
        nameLabel=new JLabel("Event name:");
        nameText=new JTextField(10);
        sDateLabel =new JLabel("Start date:");
        sDateText=new JTextField(7);
        eDateLabel =new JLabel("End date:");
        eDateText=new JTextField(7);
        sHourLabel =new JLabel("Start hour:");
        sHourText=new JTextField(7);
        descLabel=new JLabel("Description:");
        descText=new JTextField(35);
        //remove Event
        remove=new JLabel("Remove event by ID:");
        removeActionLabel=new JLabel("Enter ID:");
        removeLabelPanel=new JPanel();
        removePanel=new JPanel();
        removeActionPanel=new JPanel();
        removeSubmit=new JButton("Remove");
        removeID=new JTextField(10);
        //top Nav
        navPanel=new JPanel();
        yearPanel=new JPanel();
        yearLabel=new JLabel("Select Year:");
        yearSelect= new JComboBox();
        monPanel=new JPanel();
        //Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Match month and year
        currentYear = realYear;
        //event list Table
        centerPanel=new JPanel();
        eventLabel=new JLabel("Events List:");


        //frame
        frame=new JFrame("My Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked
    }

    /**
     * drawTableCell()
     * this function Draws the Calendar data table on the screen
     */
    private void drawTableCal(){
        eventTable=new JTable(tableData,columnNames){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        sp = new JScrollPane(eventTable);
        centerPanel.add(sp,BorderLayout.CENTER);

    }

    /**
     * drawTableCell()
     * this function sets the GUI layout and its components
     */
    public void start(){
        frame.setLayout(new BorderLayout());

        monPanel.setLayout(new GridLayout(1,12));

        class ButtonTest implements ActionListener {
            public ButtonTest() {
                for (int i = 0; i < 12; i++) {
                    JButton btn = new JButton();
                    btn.setText(monthsName[i]);
                    btn.setActionCommand(String.valueOf(i));
                    btn.addActionListener(this);
                    monPanel.add(btn);
                }
            }

            /**
             * actionPerformed (aButtonTest)
             * @param e (ActionEvent)
             * this function sets what will happened when clicking the months buttons
             * the function will set the current month according to the month selected by the user
             */
            public void actionPerformed( ActionEvent ae ) {
                String command = ae.getActionCommand();
                String year = String.valueOf(yearSelect.getSelectedItem());
                currentMonth=Integer.parseInt(command);
                viewmodel.getItems(currentMonth+1, Integer.parseInt(year) );

            }
        }
        ButtonTest btns=new ButtonTest();

        for (int i=realYear-100; i<=realYear+100; i++){
            yearSelect.addItem(String.valueOf(i));
        }
        yearSelect.setSelectedItem(String.valueOf(currentYear));

        addremovePanel.setLayout(new GridLayout(2,1));
        removePanel.setLayout(new GridLayout(2,1));
        navPanel.setLayout(new GridLayout(2,1));
        centerPanel.setLayout(new BorderLayout());
        addPanel.setLayout(new GridLayout(2,1));
        removePanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        addSubmit.addActionListener(new addSubmit_Action());
        removeSubmit.addActionListener(new removeSubmit_Action());
        yearSelect.addActionListener(new yearSelect_Action());





        addLabelPanel.add(add);
        removeLabelPanel.add(remove);
        removeActionPanel.add(removeActionLabel);
        removeActionPanel.add(removeID);
        removeActionPanel.add(removeSubmit);
        removePanel.add(removeLabelPanel);
        removePanel.add(removeActionPanel);

        addDetails.add(nameLabel);
        addDetails.add(nameText);
        addDetails.add(sDateLabel);
        addDetails.add(sDateText);
        addDetails.add(eDateLabel);
        addDetails.add(eDateText);
        addDetails.add(sHourLabel);
        addDetails.add(sHourText);
        addDetails.add(descLabel);
        addDetails.add(descText);
        addDetails.add(addSubmit);

        addPanel.add(addLabelPanel);
        addPanel.add(addDetails);
        addremovePanel.add(addPanel);
        addremovePanel.add(removePanel);
        yearPanel.add(yearLabel);
        yearPanel.add(yearSelect);
        navPanel.add(yearPanel);
        navPanel.add(monPanel);




        //Frame
        frame.add(navPanel,BorderLayout.NORTH);
        frame.add(addremovePanel,BorderLayout.SOUTH);
        frame.add(centerPanel,BorderLayout.CENTER);

        frame.setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-50), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-150));
        frame.setMaximizedBounds(new Rectangle(0,0 , 500, 500));
        removeActionPanel.setVisible(true);
        removeLabelPanel.setVisible(true);
        addLabelPanel.setVisible(true);
        addDetails.setVisible(true);
        addPanel.setVisible(true);

        centerPanel.setVisible(true);
        navPanel.setVisible(true);
        yearPanel.setVisible(true);
        removePanel.setVisible(true);
        addremovePanel.setVisible(true);
        viewmodel.getItems(currentMonth+1, currentYear);
        frame.setVisible(true);

    }

    /**
     * getCurryear()
     * this function returns the current year
     * @return currentYear(int)
     */
    @Override
    public int getCurryear() {
        return currentYear;
    }

    /**
     * getCurrmon()
     * this function returns the current month
     * @return currentMonth(int)
     */
    @Override
    public int getCurrmon() {
        return currentMonth+1;
    }

    /**
     * clearTable()
     * this function clears the table from the screen
     */
    public void clearTable(){
        if(centerPanel.getComponentCount() == 1){
            centerPanel.remove(0);
            centerPanel.updateUI();
        }
    }

    /**
     * setMessage()
     * @param text (String)
     * this function creates a popup message with the text param
     */
    @Override
    public void setMessage(String text) throws MVVMDemoException {
        JOptionPane.showMessageDialog(frame, text);
        throw new MVVMDemoException();
    }

    /**
     * showItems()
     * @param items (String[][])
     * this function calls the clearTable() function to clear the table from the screen,
     * then it calls the drawTableCal() function to draw the table with the new changes on the screen
     */
    @Override
    public void showItems(String[][] items) throws MVVMDemoException {

        this.tableData= items;
        clearTable();
        drawTableCal();
        sp.revalidate();
        sp.repaint();

    }

    /**
     * setViewModel()
     * @param ob (IViewModel)
     * sets the view model
     */
    @Override
    public void setViewModel(IViewModel ob) {
        this.viewmodel = ob;
        synchronized(_log){
            _log.info("set IViewModel");
        }
    }

    /**
     * searchEv()
     * this function checks if an event is already exists on the table, by ID
     * @param id (int)
     * @return i (String[]) on success or "null" on fail
     */
    private String[] searchEv(int id) {
        for (String[] i : this.tableData){
            if (Integer.parseInt(i[0]) == id) {
                return i;
            }
        }
        return null;
    }
}

package print.simulation;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

/**
 * PrintSimulationGUI.java
 * A class 
 * <pre>
 *    Project: print.simulation
 *    Platform: jdk 1.6.0_14; NetBeans IDE 6.8; Mac OSx
 *    Course: CS 143
 *    Hours: 2 hours
 *    Created on May 25th, 2013, 10:00:00 PM
 * </pre>
 *
 * @author	brent.young@go.shoreline.edu
 * @version 	%1% %1%
 * @see Server
 * @see Client
 * @see Queue
 * @see Random
 */
public class PrintSimulationGUI extends javax.swing.JFrame {
    
    /**
     * Format for statistical representation of server efficiency.
     * @see #decimal
     * @see DecimalFormat
     */
    DecimalFormat percent = new DecimalFormat("##0.00");
    
    /**
     * Format to contain statistical data to three decimal places.
     * @see DecimalFormat
     * @see #percent
     */
    DecimalFormat decimal = new DecimalFormat("####0.000");
    
    /**
     * Array of servers representing all of the printers in the simulation.
     * @see #setServers() 
     */
    private Server[] myServers;
    
    /**
     * Queue that all print tasks will pass through and remain in if there are
     * no free servers.
     * @see Queue
     * @see #simOneSecond(int) 
     */
    private Queue printQueue = new Queue();
    
    /**
     * ArrayList containing Client objects that have completed service.
     * For statistics gathering.
     * @see #simOneSecond(int) 
     */
    static ArrayList<Client> finished = new ArrayList<>();
    
    /**
     * Tracker variable that keeps track of the size of the queue in a given
     * second.
     * @see Queue
     * @see #simOneSecond(int) 
     */
    protected static int queueSize = 0;
    
    /**
     * Client reference used for iteration.
     * @see Client
     */
    Client moreClients;
    
    /**
     * Average time between job arrivals, set by the user.
     * @see #lockJButtonActionPerformed(java.awt.event.ActionEvent) 
     */
    int meanInterarrivalTime;
    
    /**
     * Keeps track of the current time in seconds that the simulation is on.
     * @see #simToMaxJButtonActionPerformed(java.awt.event.ActionEvent) 
     */
    private int currentTime = 0;
    
    /**
     * Amount of times the statistics button has been pressed. Allows for
     * toggling of the statistics screen.
     * @see #statsJButtonActionPerformed(java.awt.event.ActionEvent) 
     * @see #showStats() 
     */
    private int statsCount = 0;
    
    /**
     * Cumulative String that all stats information will be added to and displayed
     * upon pressing of the statistics button.
     * @see #showStats() 
     */
    static String statsLine = "";
    
    /**
     * Cumulative String that all output information not pertaining to statistics.
     * @see Client
     * @see Server
     */
    static String line = "";
    
    /**
     * Random object that loads the desired mean for job inter arrival times
     * and creates an exponential distribution around that mean.
     * @see Random
     * @see #lockJButtonActionPerformed(java.awt.event.ActionEvent) 
     */
    private static Random randomMeanInterarrivalTime;
    
    /**
     * The actual interval that determines when the next print task will arrive.
     * @see #randomMeanInterarrivalTime
     * @see #createNewInterval(int) 
     */
    private static int arrivalInterval;
    
    /**
     * Boolean value representing whether or not there is a current interval
     * active, meaning a job is waiting to arrive.
     * @see #createNewInterval(int) 
     * @see #handleInterval(int) 
     */
    private boolean intervalActive = false;
    
    /**
     * The time in seconds that the new Job will enter the system.
     * @see #createNewInterval(int) 
     * @see #handleInterval(int) 
     * @see #currentTime
     */
    private int jobCreationTime;
    
    /**
     * The maximum time in seconds the simulation will be allowed to run, set
     * by the user.
     * @see #lockJButtonActionPerformed(java.awt.event.ActionEvent) 
     * @see #simToMaxJButtonActionPerformed(java.awt.event.ActionEvent) 
     */
    private int maxTime;
    
    /**
     * Handles the scrolling of the output text area. Used so that autoscrolling
     * does not force the scroll to the bottom of the data.
     */
    private DefaultCaret caret;
    
    /**
     * Icon to be used on creation of all JOptipnPane and JFrame screens and popups.
     * @see JOptionPane
     */
    private ImageIcon icon = new ImageIcon("./src/resources/printIcon.jpeg");
    
    /**
     * Class reference used for easy statistical operations for common occurrences.
     * @see PrintStats
     */
    private PrintStats stats;
    
    /**
     * Statistical variable used to compute the average queue size.
     * @see #queueSize
     */
    private double averageQueueSize;
    
    /**
     * Statistical variable used to compute the average number of jobs that
     * are in the entire system, queue or service, at once.
     * @see #simOneSecond(int) 
     */
    private double averageNumJobsInSystem;
    
    /**
     * Constructor: creates new GUI interface and sets all variables to the
     * "original," or unaltered states.
     * @see #resetValues() 
     * @see #setJButtons(boolean) 
     */
    public PrintSimulationGUI() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(lockJButton);
        editJButton.setEnabled(false);
        setJButtons(false);
        maxTimeJProgressBar.setStringPainted(true);
        maxTimeJProgressBar.setString("");
        outJTextArea.setText(" ");
        intervalJTextField.requestFocus();
        resetValues();
    }
    
    /**
     * If the current time matches the job creation time, creates a new job,
     * otherwise creates a new interval if one is not active.
     * @param time number of seconds the simulation has been running
     * @return 1 if create job time is the current time,  0 if this value 
     * has not been reached
     * @see #createNewInterval(int) 
     */
    private int handleInterval(int time) {
        if (!intervalActive) {
            createNewInterval(time);
            intervalActive = true;
        }
        if (time == jobCreationTime) {
            intervalActive = false;
            return 1;
        }
        return 0;
    }
    
    /**
     * Sets the interval of time in seconds until a new print task will be created.
     * @param time number of seconds the simulation has been running
     * @see #handleInterval(int) 
     * @see #jobCreationTime
     */
    private void createNewInterval(int time) {
        int interval = (int)randomMeanInterarrivalTime.nextExponential();
        double em = Math.exp(-interval);
        double x = Math.random();
        int n = 0;
        while (x > em) 
        {
            n++;
            x = x * Math.random();
        } // end while
        jobCreationTime = n + time;
        //return n;
        
    }
    
    /**
     * Runs one second of the simulation, if jobs are in the queue, any free
     * servers begin service on the first one in queue. If it is time for a
     * job to endService the server does so, and all cumulative
     * statistical variables are updated.
     * @param second the time in seconds that the method is simulating
     * @see #currentTime
     */
    private void simOneSecond(int second) {
        int numPrintJobs = handleInterval(second);
        
        
        //queue new print jobs after adding one client to the queue
        for (int i = 0; i < numPrintJobs; i++) {
            moreClients = new Client(second);
            printQueue.enqueue(moreClients);
            queueSize++;
            
            //print queue contents
            line += printQueue.toString();
            outJTextArea.setText(line);
        }

        
        //assign jobs to free printers
        for (int i = 0; i < myServers.length; i++) {
            
            if (!printQueue.isEmpty()) {
                if (myServers[i].isFree()) {
                    queueSize--;
                    myServers[i].beginServing((Client)printQueue.dequeue(), second);
                    
                    //print queue contents after dequeueing one client
                    line += "\n" + printQueue.toString();
                    outJTextArea.setText(line);
                } else {
                    averageNumJobsInSystem++;
                }
            }
            
            
            
            //Count number of jobs still in the queue for stats later
            averageQueueSize += queueSize;
            averageNumJobsInSystem += queueSize;
            
            //if printer has a job and end time matches current time then end service
            if (myServers[i].getTimeServiceEnds() == second) {
                myServers[i].endServing(second);
            }
            
        }
        
        for (int i = 0; i < myServers.length; i++) {
            if (myServers[i].isFree()) {
                myServers[i].isIdle++;
            }
        }
        
        //increment wait time for all clients still in queue
        for (ListNode x = printQueue.firstNode; x != null; x = x.next) {
            Client temp = (Client)x.data;
            temp.waitTime++;
        }
        maxTimeJProgressBar.setValue(second);
        maxTimeJProgressBar.setString(currentTime + " / " + maxTime);
    }
    
    /**
     * Resets the servers to the correct amount and into their unaltered states.
     * @see #resetValues() 
     */
    private void setServers() {
        int numServers = Integer.parseInt(serversJTextField.getText());
        myServers = new Server[numServers];
        Server.nextId = 'A';
        Client.nextId = 1;
        for (int i = 0; i < numServers; i++) {
            myServers[i] = new Server();
        }
    }
    
    /**
     * Enables/disables the specified buttons according to the boolean value passed.
     * @param boo boolean value representing the turning off or on of the buttons.
     * @see #PrintSimulationGUI() 
     * @see #setTextFields(boolean) 
     */
    private void setJButtons(boolean boo) {
        simOneSecondJButton.setEnabled(boo);
        simToMaxJButton.setEnabled(boo);
    }
    
    /**
     * Enables/disables the text fields based on the passed boolean value.
     * @param boo boolean value, true to set fields enabled
     * @see #setJButtons(boolean) 
     */
    private void setTextFields(boolean boo) {
        intervalJTextField.setEnabled(boo);
        meanJobSizeJTextField.setEnabled(boo);
        maxSecondsJTextField.setEnabled(boo);
        serversJTextField.setEnabled(boo);
    }
    
    /**
     * Resets all values and variables to their original unaltered states.
     * @see #PrintSimulationGUI() 
     * @see #setServers() 
     */
    private void resetValues() {
        currentTime = 0;
        maxTimeJProgressBar.setValue(0);
        finished = new ArrayList<Client>();
        statsJButton.setText("Statistics");
        resetJButton.setEnabled(false);
        resetJMenuItem.setEnabled(false);
        intervalActive = false;
        line = "";
        outJTextArea.setText(line);
        printQueue = new Queue();
        queueSize = 0;
        statsCount = 0;
        statsLine = "";
        maxTimeJProgressBar.setString(0 + " / " + maxTime);
    }
    
    /**
     * Prints all relevant statistics to the output text area by utilizing the
     * cumulative String statsLine.
     * @see PrintStats
     * @see #statsLine
     * @see #queueSize
     */
    private void showStats() {
        int totalTimeSpentInSystem = 0;
        int totalTimeSpentInQueue = 0;
        
        Client me;
        for (int i = 0; i < finished.size(); i++) {
            me = finished.get(i);
            totalTimeSpentInQueue += me.getWaitTime();
            totalTimeSpentInSystem += me.getWaitTime() + me.getServiceTime();
        }
        
        
        statsLine = "";
        if (statsCount % 2 == 1) {
            statsJButton.setText("Statistics: On");
            statsLine += stats.toString()
                    + "\nMean Interarrival Time: " + meanInterarrivalTime
                    + "\nMean Job Length: " + meanJobSizeJTextField.getText()
                    + "\nNumber of Servers: " + myServers.length
                    + "\nDuration: " + currentTime
                    + "\nTotal print tasks finished: " + finished.size()
                    + "\nAverage number of jobs in the system: " + decimal.format(averageNumJobsInSystem / currentTime)
                    + "\nAverage number of jobs in the queue: " + decimal.format(averageQueueSize / currentTime)
                    + "\nAverage Service Time: " + stats.findMeanServiceTime()
                    + "\nMaximum Service Time: " + stats.findMaxServiceTime()
                    + "\nAverage Time spent in Queue: " + stats.findMeanWaitTime()
                    + "\nMaximum time spent in Queue: " + stats.findMaxWaitTime()
                    + "\nAverage time spent by a job in the system: " + stats.findAverageTimeSpent()
                    + "\nTotal time spent by all jobs in the queue: " + totalTimeSpentInQueue
                    + "\nTotal time spent by all jobs in the system: " + totalTimeSpentInSystem
                    + "\nNumber of jobs left in the queue after simulation: " + queueSize
                    + "\nAverage service time for server:\n";
            
            for (int i = 0; i < myServers.length; i++) {
                if (myServers[i].numFinished != 0) {
                    statsLine += "   " + myServers[i].toString() + ": " + myServers[i].totalServiceTime / myServers[i].numFinished + "\n";
                } else {
                    statsLine += "   " + myServers[i].toString() + ": NaN\n";
                }
                
            }
            statsLine += "\nPercent idle time for server:\n";
            for (int i = 0; i < myServers.length; i++) {
                if (finished.isEmpty())
                    statsLine += "   " + myServers[i].toString() + ": NaN\n";
                else
                statsLine += "   " + myServers[i].toString() + ": " + percent.format((myServers[i].isIdle / currentTime) * 100) + "%\n";
            }
            
            
            if (detailedJMenuItem.isSelected()) {
                statsLine += "\n\nIndividual print task statistics (in order finished):";
                Iterator<Client> iter = finished.iterator();
                Client c;
                while (iter.hasNext()) {
                    c = iter.next();
                    statsLine += "\n\nJob  #" + c.id
                            + "\nServer used: " + c.myServer.id
                            + "\nTime Arrived: " + c.timeArrived
                            + "\nTime Began: " + c.timeBegan
                            + "\nTime Ended: " + c.timeEnded
                            + "\nTotal Service Time: " + c.getServiceTime()
                            + "\nTotal Wait Time in Queue: " + c.getWaitTime();
                }
            }
            outJTextArea.setText(statsLine);
        } else if (statsCount  % 2 == 0) {
            statsJButton.setText("Statistics: Off");
            outJTextArea.setText(line);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleJPanel7 = new javax.swing.JPanel();
        titleJLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tabPane = new javax.swing.JTabbedPane();
        prefJTabPane = new javax.swing.JPanel();
        prefButtonJPanel = new javax.swing.JPanel();
        lockJButton = new javax.swing.JButton();
        editJButton = new javax.swing.JButton();
        quitJButton = new javax.swing.JButton();
        valueJPanel = new javax.swing.JPanel();
        intervalJLabel = new javax.swing.JLabel();
        serversJLabel = new javax.swing.JLabel();
        durationJLabel = new javax.swing.JLabel();
        secondsJLabel = new javax.swing.JLabel();
        pagesJLabel = new javax.swing.JLabel();
        defaultValuesButton = new javax.swing.JButton();
        maxSecondsLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        intervalJTextField = new javax.swing.JFormattedTextField();
        serversJTextField = new javax.swing.JFormattedTextField();
        meanJobSizeJTextField = new javax.swing.JFormattedTextField();
        maxSecondsJTextField = new javax.swing.JFormattedTextField();
        serverJLabel = new javax.swing.JLabel();
        outputJTabPane = new javax.swing.JPanel();
        controlJPanel = new javax.swing.JPanel();
        simOneSecondJButton = new javax.swing.JButton();
        statsJButton = new javax.swing.JButton();
        simToMaxJButton = new javax.swing.JButton();
        resetJButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        outJTextArea = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        maxTimeJProgressBar = new javax.swing.JProgressBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileJMenu = new javax.swing.JMenu();
        resetJMenuItem = new javax.swing.JMenuItem();
        clearJMenuItem = new javax.swing.JMenuItem();
        printJMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitJMenuItem = new javax.swing.JMenuItem();
        statsJMenu = new javax.swing.JMenu();
        detailedJMenuItem = new javax.swing.JCheckBoxMenuItem();
        helpJMenu = new javax.swing.JMenu();
        aboutJMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        titleJPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        titleJLabel.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        titleJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleJLabel.setText("Print Simulation");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/printIcon.jpeg"))); // NOI18N

        org.jdesktop.layout.GroupLayout titleJPanel7Layout = new org.jdesktop.layout.GroupLayout(titleJPanel7);
        titleJPanel7.setLayout(titleJPanel7Layout);
        titleJPanel7Layout.setHorizontalGroup(
            titleJPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, titleJPanel7Layout.createSequentialGroup()
                .add(47, 47, 47)
                .add(jLabel3)
                .add(54, 54, 54)
                .add(titleJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 302, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(147, Short.MAX_VALUE))
        );
        titleJPanel7Layout.setVerticalGroup(
            titleJPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(titleJPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .add(titleJPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(titleJLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(titleJPanel7Layout.createSequentialGroup()
                        .add(jLabel3)
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        getContentPane().add(titleJPanel7, java.awt.BorderLayout.PAGE_START);

        tabPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        prefJTabPane.setToolTipText("Set Preferences for Simulation");
        prefJTabPane.setLayout(new java.awt.BorderLayout());

        prefButtonJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lockJButton.setMnemonic('L');
        lockJButton.setText("Lock");
        lockJButton.setToolTipText("Lock in the current values for the next simulation and reset the simulation");
        lockJButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lockJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lockJButtonActionPerformed(evt);
            }
        });

        editJButton.setMnemonic('E');
        editJButton.setText("Edit");
        editJButton.setToolTipText("Edit the values to be used for the next simulation");
        editJButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        editJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editJButtonActionPerformed(evt);
            }
        });

        quitJButton.setMnemonic('Q');
        quitJButton.setText("Quit");
        quitJButton.setToolTipText("Exit the application");
        quitJButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        quitJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitJButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout prefButtonJPanelLayout = new org.jdesktop.layout.GroupLayout(prefButtonJPanel);
        prefButtonJPanel.setLayout(prefButtonJPanelLayout);
        prefButtonJPanelLayout.setHorizontalGroup(
            prefButtonJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(prefButtonJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(prefButtonJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(editJButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(quitJButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(lockJButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        prefButtonJPanelLayout.setVerticalGroup(
            prefButtonJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(prefButtonJPanelLayout.createSequentialGroup()
                .add(15, 15, 15)
                .add(lockJButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(editJButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(quitJButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        prefJTabPane.add(prefButtonJPanel, java.awt.BorderLayout.LINE_START);

        valueJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        intervalJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        intervalJLabel.setText("Number of Servers:");

        serversJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        serversJLabel.setText("Average Arrival Interval:");

        durationJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        durationJLabel.setText("Average Job Size:");

        secondsJLabel.setText("seconds (ex: 035)");

        pagesJLabel.setText("pages (ex: 050)");

        defaultValuesButton.setMnemonic('D');
        defaultValuesButton.setText("Default Values");
        defaultValuesButton.setToolTipText("Adds preset values into fields for easy simulation");
        defaultValuesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultValuesButtonActionPerformed(evt);
            }
        });

        maxSecondsLabel.setText("seconds (ex: 0400)");

        jLabel1.setText("Maximum SimulationTime:");

        try {
            intervalJTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        intervalJTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intervalJTextFieldActionPerformed(evt);
            }
        });
        intervalJTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                intervalJTextFieldFocusGained(evt);
            }
        });

        try {
            serversJTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        serversJTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serversJTextFieldActionPerformed(evt);
            }
        });
        serversJTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                serversJTextFieldFocusGained(evt);
            }
        });

        try {
            meanJobSizeJTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        meanJobSizeJTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                meanJobSizeJTextFieldActionPerformed(evt);
            }
        });
        meanJobSizeJTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                meanJobSizeJTextFieldFocusGained(evt);
            }
        });

        try {
            maxSecondsJTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        maxSecondsJTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxSecondsJTextFieldActionPerformed(evt);
            }
        });
        maxSecondsJTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                maxSecondsJTextFieldFocusGained(evt);
            }
        });

        serverJLabel.setText("(ex: 05)");

        org.jdesktop.layout.GroupLayout valueJPanelLayout = new org.jdesktop.layout.GroupLayout(valueJPanel);
        valueJPanel.setLayout(valueJPanelLayout);
        valueJPanelLayout.setHorizontalGroup(
            valueJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(valueJPanelLayout.createSequentialGroup()
                .add(37, 37, 37)
                .add(valueJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(durationJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(serversJLabel)
                    .add(intervalJLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(valueJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(valueJPanelLayout.createSequentialGroup()
                        .add(intervalJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 84, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(secondsJLabel))
                    .add(valueJPanelLayout.createSequentialGroup()
                        .add(serversJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 84, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(serverJLabel))
                    .add(valueJPanelLayout.createSequentialGroup()
                        .add(meanJobSizeJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 84, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(pagesJLabel))
                    .add(valueJPanelLayout.createSequentialGroup()
                        .add(maxSecondsJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 84, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(maxSecondsLabel)))
                .addContainerGap(42, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, valueJPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(defaultValuesButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 230, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(112, 112, 112))
        );
        valueJPanelLayout.setVerticalGroup(
            valueJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(valueJPanelLayout.createSequentialGroup()
                .add(20, 20, 20)
                .add(valueJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(intervalJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(serversJLabel)
                    .add(secondsJLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(valueJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(intervalJLabel)
                    .add(serversJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(serverJLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(valueJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(pagesJLabel)
                    .add(durationJLabel)
                    .add(meanJobSizeJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(valueJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(valueJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(maxSecondsJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(maxSecondsLabel))
                    .add(jLabel1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(defaultValuesButton)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        prefJTabPane.add(valueJPanel, java.awt.BorderLayout.CENTER);

        tabPane.addTab("Preferences", prefJTabPane);

        outputJTabPane.setToolTipText("Run simulatiion and review statistics");
        outputJTabPane.setLayout(new java.awt.BorderLayout());

        simOneSecondJButton.setMnemonic('o');
        simOneSecondJButton.setToolTipText("Run one second of the simulation");
        simOneSecondJButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        simOneSecondJButton.setLabel("Sim one\nsecond");
        simOneSecondJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simOneSecondJButtonActionPerformed(evt);
            }
        });

        statsJButton.setMnemonic('S');
        statsJButton.setText("Statistics");
        statsJButton.setToolTipText("Toggel statistics representing the last run simulation.");
        statsJButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        statsJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statsJButtonActionPerformed(evt);
            }
        });

        simToMaxJButton.setMnemonic('M');
        simToMaxJButton.setText("Sim to Max");
        simToMaxJButton.setToolTipText("Run the simulation from the current second to the maximum alotted time");
        simToMaxJButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        simToMaxJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simToMaxJButtonActionPerformed(evt);
            }
        });

        resetJButton.setMnemonic('R');
        resetJButton.setText("Reset");
        resetJButton.setToolTipText("Reset the simulation to time zero and erase any current data");
        resetJButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        resetJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetJButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout controlJPanelLayout = new org.jdesktop.layout.GroupLayout(controlJPanel);
        controlJPanel.setLayout(controlJPanelLayout);
        controlJPanelLayout.setHorizontalGroup(
            controlJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(controlJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(controlJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(simOneSecondJButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                    .add(simToMaxJButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(statsJButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(resetJButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        controlJPanelLayout.setVerticalGroup(
            controlJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(controlJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(simToMaxJButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 14, Short.MAX_VALUE)
                .add(simOneSecondJButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(resetJButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(statsJButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(15, 15, 15))
        );

        outputJTabPane.add(controlJPanel, java.awt.BorderLayout.LINE_START);

        outJTextArea.setEditable(false);
        outJTextArea.setColumns(20);
        outJTextArea.setRows(5);
        jScrollPane2.setViewportView(outJTextArea);

        outputJTabPane.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(maxTimeJProgressBar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(maxTimeJProgressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        outputJTabPane.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        tabPane.addTab("Output", outputJTabPane);

        getContentPane().add(tabPane, java.awt.BorderLayout.CENTER);

        fileJMenu.setMnemonic('F');
        fileJMenu.setText("File");
        fileJMenu.setToolTipText("File Menu");

        resetJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        resetJMenuItem.setText("Reset");
        resetJMenuItem.setToolTipText("Reset the simulation to time zero and erase any current data");
        resetJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(resetJMenuItem);

        clearJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        clearJMenuItem.setText("Clear");
        clearJMenuItem.setToolTipText("Clears the text area of all text, but retains the information. To view hidden text, toggle the Statistics button.");
        clearJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(clearJMenuItem);

        printJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printJMenuItem.setText("Print");
        printJMenuItem.setToolTipText("Print the current contents of the text area");
        printJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(printJMenuItem);
        fileJMenu.add(jSeparator1);

        exitJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        exitJMenuItem.setText("Exit");
        exitJMenuItem.setToolTipText("Exit the application");
        exitJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(exitJMenuItem);

        jMenuBar1.add(fileJMenu);

        statsJMenu.setMnemonic('t');
        statsJMenu.setText("Stats");
        statsJMenu.setToolTipText("Statistics Menu");

        detailedJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        detailedJMenuItem.setText("Detailed Statistics");
        detailedJMenuItem.setToolTipText("Display detailed stats for each completed print task when checked");
        detailedJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailedJMenuItemActionPerformed(evt);
            }
        });
        statsJMenu.add(detailedJMenuItem);

        jMenuBar1.add(statsJMenu);

        helpJMenu.setMnemonic('H');
        helpJMenu.setText("Help");
        helpJMenu.setToolTipText("Help Menu");

        aboutJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        aboutJMenuItem.setText("About");
        aboutJMenuItem.setToolTipText("Diaply an About form for the application");
        aboutJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutJMenuItemActionPerformed(evt);
            }
        });
        helpJMenu.add(aboutJMenuItem);

        jMenuBar1.add(helpJMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Button that simulates through just one second of the current simulation.
     * Used for slow viewing the simulation to see events play out.
     * @param evt button press
     * @see #simOneSecond(int) 
     * @see #simToMaxJButtonActionPerformed(java.awt.event.ActionEvent) 
     */
    private void simOneSecondJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simOneSecondJButtonActionPerformed
        resetJButton.setEnabled(true);
        resetJMenuItem.setEnabled(true);
        if (currentTime < maxTime) { 
            currentTime++;
            simOneSecond(currentTime);
            if (currentTime == maxTime) {
                line += "\nMax Time  of " + maxTime + " has been reached.";
                outJTextArea.setText(line);
                simOneSecondJButton.setEnabled(false);
                simToMaxJButton.setEnabled(false);
                
            }
            caret = (DefaultCaret)outJTextArea.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        }
    }//GEN-LAST:event_simOneSecondJButtonActionPerformed

    /**
     * Exits the application.
     * @param evt menu item press
     * @see #quitJButtonActionPerformed(java.awt.event.ActionEvent) 
     */
    private void exitJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJMenuItemActionPerformed
        quitJButtonActionPerformed(evt);
    }//GEN-LAST:event_exitJMenuItemActionPerformed

    /**
     * creates a fresh PrintStats object and sets the caret position.
     * @param evt button press
     * @see #showStats() 
     * @see #caret
     */
    private void statsJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statsJButtonActionPerformed
        caret = (DefaultCaret)outJTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        statsCount++;
        stats = new PrintStats(finished);
        showStats();
            
    }//GEN-LAST:event_statsJButtonActionPerformed

    /**
     * Exits the application.
     * @param evt button press
     * @see #exitJMenuItemActionPerformed(java.awt.event.ActionEvent) 
     */
    private void quitJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitJButtonActionPerformed
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to Exit?",
                "Exit Application", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, icon);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_quitJButtonActionPerformed

    /**
     * Locks in the values the user wishes to use after Validating that all values
     * are acceptable. Requires button press if simulation is to occur. In place
     * to prevent accidental overwrites of simulation data.
     * @param evt button press
     * @see #simOneSecond(int) 
     */
    private void lockJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lockJButtonActionPerformed
        resetValues();
        boolean isValid = true;
        String error = "Make sure to enter the correct amount of integers for each field.";
        String meanSize = meanJobSizeJTextField.getText().replaceAll(" ", "");
        String server = serversJTextField.getText().replaceAll(" ", "");
        String interval = intervalJTextField.getText().replaceAll(" ", "");
        String max = maxSecondsJTextField.getText().replaceAll(" ", "");
        if (!meanSize.equals("")) {
            Client.meanJobSize = Integer.parseInt(meanJobSizeJTextField.getText());
        } else {
            error += "\nThe 'Average Job Size' field requires 3 integers (ex: 0100).";
            isValid = false;
        }
        if (!interval.equals("")) {
            meanInterarrivalTime = Integer.parseInt(intervalJTextField.getText());
            randomMeanInterarrivalTime = new Random(meanInterarrivalTime);
        } else {
            error += "\nThe 'Average Arrival Interval' field requires 3 integers (ex: 050).";
            isValid = false;
        }
        if (!max.equals("")) {
            maxTime = Integer.parseInt(maxSecondsJTextField.getText());
            maxTimeJProgressBar.setString(0 + " / " + maxTime);
        } else {
            error += "\nThe 'Maximum Simulation Time' field requires 4 integers (ex: 0150).";
            isValid = false;
        }
        if (!server.equals("")) {
            setServers();
        } else {
            error += "\nThe 'Number of Servers' field requires 2 integers (ex: 04).";
            isValid = false;
        }
        if (isValid) {
            outputJTabPane.getRootPane().setDefaultButton(simToMaxJButton);
            editJButton.setEnabled(true);
            lockJButton.setEnabled(false);
            defaultValuesButton.setEnabled(false);
            statsJButton.setEnabled(true);
            setJButtons(true);
            setTextFields(false);
            maxTimeJProgressBar.setMaximum(maxTime);
            if (currentTime >= 1) {
                resetJButton.setEnabled(true);
                setJButtons(false);
            } else {
                setJButtons(true);
                resetJButton.setEnabled(false);
            }
        } else {
            JOptionPane.showMessageDialog(null, error, "Input Error",
                    JOptionPane.ERROR_MESSAGE, icon);
        }
    }//GEN-LAST:event_lockJButtonActionPerformed

    /**
     * Sets all value text fields to predetermined values in order to provide a
     * quick and easy way to see an output that functions at a moderately
     * efficient rate.
     * @param evt button press
     * @see #lockJButtonActionPerformed(java.awt.event.ActionEvent) 
     */
    private void defaultValuesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defaultValuesButtonActionPerformed
        intervalJTextField.setText("0" + 35);
        serversJTextField.setText("0" + 4);
        meanJobSizeJTextField.setText("" + 100);
        maxSecondsJTextField.setText("" + 1000);
        lockJButton.requestFocus();
    }//GEN-LAST:event_defaultValuesButtonActionPerformed

    /**
     * Enables the value text fields for editing. User is unable to edit the values
     * or hit Lock until this is pressed. In place to prevent accidental simulation
     * data overwrite.
     * @param evt button press
     * @see #lockJButtonActionPerformed(java.awt.event.ActionEvent) 
     * @see #simOneSecond(int) 
     */
    private void editJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editJButtonActionPerformed
        intervalJTextField.requestFocus();
        lockJButton.setEnabled(true);
        editJButton.setEnabled(false);
        defaultValuesButton.setEnabled(true);
        setJButtons(false);
        setTextFields(true);
    }//GEN-LAST:event_editJButtonActionPerformed

    /**
     * Clears the output text area of all text. Text can be retrieved by toggling
     * the statistics button.
     * @param evt button press
     * @see #resetValues()
     */
    private void clearJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearJMenuItemActionPerformed
        outJTextArea.setText(null);
    }//GEN-LAST:event_clearJMenuItemActionPerformed

    /**
     * Prints the content of the output text area.
     * @param evt menu item press
     * @see PrintUtilities
     */
    private void printJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printJMenuItemActionPerformed
        try {
            PrintUtilities.printComponent(outJTextArea);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Unable to print.",
                    "Print Error", JOptionPane.ERROR_MESSAGE, icon);
        }
    }//GEN-LAST:event_printJMenuItemActionPerformed

    /**
     * Displays an about form for the application.
     * @param evt menu item press
     * @see UtilPane
     * @see UtilPane.#printFileToPane(java.lang.String) 
     */
    private void aboutJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutJMenuItemActionPerformed
        UtilPane pane = new UtilPane();
        try {
            pane.printFileToPane("./src/resources/About.txt");
            pane.setVisible(true);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "File could not be found.", 
                    "File Error", JOptionPane.ERROR_MESSAGE, icon);
        }
    }//GEN-LAST:event_aboutJMenuItemActionPerformed

    /**
     * runs the simulation to the maximum time allowed in seconds.
     * @param evt button press
     * @see #simOneSecond(int) 
     * @see #simOneSecondJButtonActionPerformed(java.awt.event.ActionEvent) 
     */
    private void simToMaxJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simToMaxJButtonActionPerformed
        caret = (DefaultCaret)outJTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);        
        resetValues();
        setServers();
        while (currentTime < maxTime) {
            //increment
            currentTime++;
            //sim one second of printing
            simOneSecond(currentTime);
        }
        line += "Max Time  of " + maxTime + " has been reached.";
        outJTextArea.setText(line);
        simOneSecondJButton.setEnabled(false);
        resetJButton.setEnabled(true);
        resetJMenuItem.setEnabled(true);
        simToMaxJButton.setEnabled(false);
        statsJButton.setEnabled(true);
    }//GEN-LAST:event_simToMaxJButtonActionPerformed

    /**
     * Resets all variables to their original states.
     * @param evt menu item press
     * @see #resetValues() 
     * @see #setServers() 
     */
    private void resetJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetJMenuItemActionPerformed
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset the simulation?",
                "Reset Simulation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, icon);
        if (result == JOptionPane.YES_OPTION) {
        resetValues();
        setServers();
        resetJButton.setEnabled(false);
        resetJMenuItem.setEnabled(false);
        simToMaxJButton.setEnabled(true);
        simOneSecondJButton.setEnabled(true);
        }
    }//GEN-LAST:event_resetJMenuItemActionPerformed

    /**
     * Resets all variables to their original states.
     * @param evt button press
     * @see #resetValues() 
     * @see #setServers() 
     * @see #resetJMenuItemActionPerformed(java.awt.event.ActionEvent) 
     */
    private void resetJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetJButtonActionPerformed
        resetJMenuItemActionPerformed(evt);
    }//GEN-LAST:event_resetJButtonActionPerformed

    /**
     * Check menu item that, when activated, adds detailed statistical information
     * to statsLine for display in the output Text area.
     * @param evt menu item press
     * @see #statsJButtonActionPerformed(java.awt.event.ActionEvent) 
     * @see #showStats() 
     */
    private void detailedJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailedJMenuItemActionPerformed
        stats = new PrintStats(finished);
        showStats();
    }//GEN-LAST:event_detailedJMenuItemActionPerformed

    /**
     * Pressing enter locks the current values in for the next simulation.
     * @param evt Enter pressed in text field
     * @see #lockJButtonActionPerformed(java.awt.event.ActionEvent) 
     */
    private void intervalJTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intervalJTextFieldActionPerformed
        lockJButtonActionPerformed(evt);
    }//GEN-LAST:event_intervalJTextFieldActionPerformed

    /**
     * Selects all text in the given text field for quick editing.
     * @param evt focus shifted to this text field.
     * @see JTextField
     */
    private void intervalJTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_intervalJTextFieldFocusGained
        intervalJTextField.selectAll();
    }//GEN-LAST:event_intervalJTextFieldFocusGained

    /**
     * Pressing enter locks the current values in for the next simulation.
     * @param evt Enter pressed in text field
     * @see #lockJButtonActionPerformed(java.awt.event.ActionEvent) 
     */
    private void serversJTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serversJTextFieldActionPerformed
        lockJButtonActionPerformed(evt);
    }//GEN-LAST:event_serversJTextFieldActionPerformed

    /**
     * Selects all text in the given text field for quick editing.
     * @param evt focus shifted to this text field.
     * @see JTextField
     */
    private void serversJTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serversJTextFieldFocusGained
        serversJTextField.selectAll();
    }//GEN-LAST:event_serversJTextFieldFocusGained

    /**
     * Pressing enter locks the current values in for the next simulation.
     * @param evt Enter pressed in text field
     * @see #lockJButtonActionPerformed(java.awt.event.ActionEvent) 
     */
    private void meanJobSizeJTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_meanJobSizeJTextFieldActionPerformed
        lockJButtonActionPerformed(evt);
    }//GEN-LAST:event_meanJobSizeJTextFieldActionPerformed

    /**
     * Selects all text in the given text field for quick editing.
     * @param evt focus shifted to this text field.
     * @see JTextField
     */
    private void meanJobSizeJTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_meanJobSizeJTextFieldFocusGained
        meanJobSizeJTextField.selectAll();
    }//GEN-LAST:event_meanJobSizeJTextFieldFocusGained

    /**
     * Pressing enter locks the current values in for the next simulation.
     * @param evt Enter pressed in text field
     * @see #lockJButtonActionPerformed(java.awt.event.ActionEvent) 
     */
    private void maxSecondsJTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxSecondsJTextFieldActionPerformed
        lockJButtonActionPerformed(evt);
    }//GEN-LAST:event_maxSecondsJTextFieldActionPerformed

    /**
     * Selects all text in the given text field for quick editing.
     * @param evt focus shifted to this text field.
     * @see JTextField
     */
    private void maxSecondsJTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_maxSecondsJTextFieldFocusGained
        maxSecondsJTextField.selectAll();
    }//GEN-LAST:event_maxSecondsJTextFieldFocusGained

    
    /**
     * Creates a new PrintSimulationGUI.
     * @param args the command line arguments
     * @see PrintSimulationGUI
     * @see PrintSimulationGUI.#PrintSimulationGUI() 
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrintSimulationGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrintSimulationGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrintSimulationGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrintSimulationGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrintSimulationGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutJMenuItem;
    private javax.swing.JMenuItem clearJMenuItem;
    private javax.swing.JPanel controlJPanel;
    private javax.swing.JButton defaultValuesButton;
    private javax.swing.JCheckBoxMenuItem detailedJMenuItem;
    private javax.swing.JLabel durationJLabel;
    private javax.swing.JButton editJButton;
    private javax.swing.JMenuItem exitJMenuItem;
    private javax.swing.JMenu fileJMenu;
    private javax.swing.JMenu helpJMenu;
    private javax.swing.JLabel intervalJLabel;
    private javax.swing.JFormattedTextField intervalJTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    public static javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JButton lockJButton;
    private javax.swing.JFormattedTextField maxSecondsJTextField;
    private javax.swing.JLabel maxSecondsLabel;
    private javax.swing.JProgressBar maxTimeJProgressBar;
    private javax.swing.JFormattedTextField meanJobSizeJTextField;
    public static javax.swing.JTextArea outJTextArea;
    private javax.swing.JPanel outputJTabPane;
    private javax.swing.JLabel pagesJLabel;
    private javax.swing.JPanel prefButtonJPanel;
    private javax.swing.JPanel prefJTabPane;
    private javax.swing.JMenuItem printJMenuItem;
    private javax.swing.JButton quitJButton;
    private javax.swing.JButton resetJButton;
    private javax.swing.JMenuItem resetJMenuItem;
    private javax.swing.JLabel secondsJLabel;
    private javax.swing.JLabel serverJLabel;
    private javax.swing.JLabel serversJLabel;
    private javax.swing.JFormattedTextField serversJTextField;
    private javax.swing.JButton simOneSecondJButton;
    private javax.swing.JButton simToMaxJButton;
    private javax.swing.JButton statsJButton;
    private javax.swing.JMenu statsJMenu;
    private javax.swing.JTabbedPane tabPane;
    private javax.swing.JLabel titleJLabel;
    private javax.swing.JPanel titleJPanel7;
    private javax.swing.JPanel valueJPanel;
    // End of variables declaration//GEN-END:variables
}

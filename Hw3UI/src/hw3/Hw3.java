/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ming
 */
public class Hw3 extends javax.swing.JFrame {

    /**
     * Creates new form Hw3
     */
    ArrayList list = new ArrayList();
    ArrayList subCtgRet = new ArrayList();
    ArrayList subList = new ArrayList();
    ArrayList attrArrayList = new ArrayList();
    ArrayList attrSelect = new ArrayList();
    ArrayList stateList = new ArrayList();
    ArrayList cityList = new ArrayList();
    int rowNum = 0;
    ResultSet searchRet = null;
    ArrayList bidList = new ArrayList();
    public Hw3() {
        initComponents();
    }
    
public void search() {
    Connection con = null;
    try {
        con = openConnection();
        System.out.println("Connect database success!");
    } catch (SQLException e) {
        System.err.println("Errors occurs when communicating with the database server: " + e.getMessage());
    } catch (ClassNotFoundException e) {
        System.err.println("Cannot find the database driver");
    } finally {
        closeConnection(con);
    }
}
private void closeConnection(Connection con) {
try {
con.close();
} catch (SQLException e) {
System.err.println("Cannot close connection: " + e.getMessage());
}
}
private void clear(){
    list.clear();
    subCtgRet.clear();
    subList.clear();
    model1.clear();
    model.clear();
    model2.clear();
    attrArrayList.clear();
    attrSelModel.clear();
    tModel.setRowCount(0);
    for (int i = 0; i < 28; i++){
        javax.swing.JCheckBox b = (javax.swing.JCheckBox)jPanel1.getComponent(i);
        b.setSelected(false);
    }
    Food.setSelected(false);
}
DefaultTableModel tModel1 = new DefaultTableModel();  
private void showRet() throws SQLException{
    tModel1 = (DefaultTableModel) jTable1.getModel();
    System.out.println(jTable.getSelectedRow());
    System.out.println("continue");
    String bid = bidList.get(jTable.getSelectedRow()).toString();
    Connection con = null;
    try {
        con = openConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT R.DATES, R.STARS, R.CONTENT, U.NAME FROM USERS U, REVIEW R WHERE U.USER_ID = R.USER_ID AND R.BUSINESS_ID = ?");
        stmt.setString(1, bid);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            System.out.println("Review result is:" + rs.getString("name"));
            tModel1.addRow(new Object[]{rs.getString("dates"), rs.getString("stars"), rs.getString(3),rs.getString("name")});
        }
        System.out.println("Connect database success!");
    } catch (SQLException e) {
        System.err.println("Errors occurs when communicating with the database server: " + e.getMessage());
    } catch (ClassNotFoundException e) {
        System.err.println("Cannot find the database driver");
    } finally {
        closeConnection(con);
    }
}

private void selMain(){
     getMainCtg();
        model.clear();
        Connection con = null;
        try{
            con = openConnection();
            StringBuffer sb = new StringBuffer();
            sb.append("select Distinct CATEGORIE from CATEGORIES where BUSINESS_ID in (select BUSINESS_ID from CATEGORIES where CATEGORIE = ?)");
            int numCate;
            for (numCate = 1; numCate < list.size(); numCate++){
                sb.append("union ");
                sb.append("select Distinct CATEGORIE from CATEGORIES where BUSINESS_ID in (select BUSINESS_ID from CATEGORIES where CATEGORIE = ?)");
            }
//            System.out.println(sb.toString());
//            System.out.println(list.toString());
            PreparedStatement stmt = con.prepareStatement(sb.toString());
            for (int i = 1; i <= numCate; i++){
                stmt.setString(i, list.get(i - 1).toString());
            }
            System.out.println(stmt.toString());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                subCtgRet.add(rs.getString("categorie"));
            }
            System.out.println(subCtgRet.toString());
//            int numOfChk = Math.min(31, subCtgRet.size());
//            for (int i = 0; i < numOfChk; i++){
//                javax.swing.JCheckBox b1 = (javax.swing.JCheckBox)jPanel4.getComponent(i);
//                b1.setText(subCtgRet.get(i) + "");
//            }
            for (int i = 0; i < subCtgRet.size(); i++){
                model.addElement(subCtgRet.get(i));
            }
            jList1.setModel(model);
        }catch(Exception e){
            System.out.println("SQL error: " + e.getMessage());
        }finally {
            closeConnection(con);
        }
}
private void getMainCtg(){
    try{        
        if (activeLife.isSelected()){
            list.add("Active Life");
        }
        if (artsEntertainment.isSelected()){
            list.add("Arts & Entertainment");
        }
        if (automotive.isSelected()){
            list.add("Automotive");
        }
        if (carRental.isSelected()){
            list.add("Car Rental");
        }
        if (cafes.isSelected()){
            list.add("Cafes");
        }
        if (beautySpas.isSelected()){
            list.add("Beauty & Spas");
        }
        if (convenienceStores.isSelected()){
            list.add("Convenience Stores");
        }
        if (dentists.isSelected()){
            list.add("Dentists");
        }
        if (doctors.isSelected()){
            list.add("Doctors");
        }
        if (Drugstores.isSelected()){
            list.add("Drugstores");
        }
        if (DepartmentStores.isSelected()){
            list.add("Department Stores");
        }
        if (Education.isSelected()){
            list.add("Education");
        }
        if (EventPlanningServices.isSelected()){
            list.add("Event Planning & Services");
        }
        if (FlowersGifts.isSelected()){
            list.add("Flowers & Gifts");
        }
        if (Food.isSelected()){
            list.add("Food");
        }
        if (HealthMedical.isSelected()){
            list.add("Health & Medical");
        }
        if (HomeServices.isSelected()){
             list.add("Home Services");
        }
        if (HomeGarden.isSelected()){
             list.add("Home & Garden");
        }
        if (Hospitals.isSelected()){
             list.add("Hospitals");
        }
        if (HotelsTravel.isSelected()){
             list.add("Hotels & Travel");
        }
        if (HardwareStores.isSelected()){
             list.add("Hardware Stores");
        }
        if (Grocery.isSelected()){
             list.add("Grocery");
        }
        if (MedicalCenters.isSelected()){
             list.add("Medical Centers");
        }
        if (NurseriesGardening.isSelected()){
             list.add("Nurseries & Gardening");
        }
        if (Nightlife.isSelected()){
             list.add("Nightlife");
        }
        if (Restaurants.isSelected()){
             list.add("Restaurants");
        }
        if (Shopping.isSelected()){
             list.add("Shopping");
        }
        if (Transportation.isSelected()){
             list.add("Transportation");
        }
    }catch(Exception e){
        System.out.println("error:" + e.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        artsEntertainment = new javax.swing.JCheckBox();
        activeLife = new javax.swing.JCheckBox();
        carRental = new javax.swing.JCheckBox();
        automotive = new javax.swing.JCheckBox();
        cafes = new javax.swing.JCheckBox();
        beautySpas = new javax.swing.JCheckBox();
        convenienceStores = new javax.swing.JCheckBox();
        dentists = new javax.swing.JCheckBox();
        HealthMedical = new javax.swing.JCheckBox();
        Food = new javax.swing.JCheckBox();
        FlowersGifts = new javax.swing.JCheckBox();
        EventPlanningServices = new javax.swing.JCheckBox();
        Education = new javax.swing.JCheckBox();
        DepartmentStores = new javax.swing.JCheckBox();
        Drugstores = new javax.swing.JCheckBox();
        doctors = new javax.swing.JCheckBox();
        HomeServices = new javax.swing.JCheckBox();
        HomeGarden = new javax.swing.JCheckBox();
        HardwareStores = new javax.swing.JCheckBox();
        Grocery = new javax.swing.JCheckBox();
        Hospitals = new javax.swing.JCheckBox();
        HotelsTravel = new javax.swing.JCheckBox();
        MedicalCenters = new javax.swing.JCheckBox();
        NurseriesGardening = new javax.swing.JCheckBox();
        Nightlife = new javax.swing.JCheckBox();
        Restaurants = new javax.swing.JCheckBox();
        Shopping = new javax.swing.JCheckBox();
        Transportation = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        cbOpen = new javax.swing.JComboBox();
        cbDay = new javax.swing.JComboBox();
        cbClose = new javax.swing.JComboBox();
        cbAttr = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        selectSub = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        search = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        selectAttr = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        attrList = new javax.swing.JList();
        search1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        searchButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        selectMain = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        statecb = new javax.swing.JComboBox();
        citycb = new javax.swing.JComboBox();
        filterbystate = new javax.swing.JButton();
        filterbycity = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        artsEntertainment.setText("Arts & Entertainment");

        activeLife.setText("Active Life");

        carRental.setText("Car Rental");

        automotive.setText("Automotive");
        automotive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                automotiveActionPerformed(evt);
            }
        });

        cafes.setText("Cafes");

        beautySpas.setText("Beauty & Spas");

        convenienceStores.setText("Convenience Stores");

        dentists.setText("Dentists");

        HealthMedical.setText("Health & Medical");

        Food.setText("Food");

        FlowersGifts.setText("Flowers & Gifts");
        FlowersGifts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FlowersGiftsActionPerformed(evt);
            }
        });

        EventPlanningServices.setText("Event Planning & Services");

        Education.setText("Education");

        DepartmentStores.setText("Department Stores");

        Drugstores.setText("Drugstores");
        Drugstores.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Drugstores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DrugstoresActionPerformed(evt);
            }
        });

        doctors.setText("Doctors");
        doctors.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        HomeServices.setText("Home Services");

        HomeGarden.setText("Home & Garden");

        HardwareStores.setText("Hardware Stores");

        Grocery.setText("Grocery");
        Grocery.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GroceryMouseClicked(evt);
            }
        });

        Hospitals.setText("Hospitals");

        HotelsTravel.setText("Hotels & Travel");

        MedicalCenters.setText("Medical Centers");

        NurseriesGardening.setText("Nurseries & Gardening");

        Nightlife.setText("Nightlife");

        Restaurants.setText("Restaurants");

        Shopping.setText("Shopping");

        Transportation.setText("Transportation");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Transportation)
                    .addComponent(activeLife)
                    .addComponent(carRental)
                    .addComponent(cafes)
                    .addComponent(automotive)
                    .addComponent(beautySpas)
                    .addComponent(artsEntertainment)
                    .addComponent(EventPlanningServices)
                    .addComponent(FlowersGifts)
                    .addComponent(Food)
                    .addComponent(HealthMedical)
                    .addComponent(HomeServices)
                    .addComponent(HomeGarden)
                    .addComponent(Hospitals)
                    .addComponent(convenienceStores)
                    .addComponent(dentists)
                    .addComponent(doctors)
                    .addComponent(Drugstores)
                    .addComponent(DepartmentStores)
                    .addComponent(Education)
                    .addComponent(HotelsTravel)
                    .addComponent(HardwareStores)
                    .addComponent(Grocery)
                    .addComponent(MedicalCenters)
                    .addComponent(NurseriesGardening)
                    .addComponent(Nightlife)
                    .addComponent(Restaurants)
                    .addComponent(Shopping))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(activeLife)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(artsEntertainment)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(automotive)
                .addGap(6, 6, 6)
                .addComponent(carRental, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cafes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(beautySpas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(convenienceStores)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dentists)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(doctors)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Drugstores)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DepartmentStores)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Education)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EventPlanningServices)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FlowersGifts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Food)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HealthMedical)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HomeServices)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HomeGarden)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Hospitals)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HotelsTravel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HardwareStores)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Grocery)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MedicalCenters)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NurseriesGardening)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Nightlife)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Restaurants)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Shopping)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Transportation)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cbOpen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00" }));

        cbDay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" }));

        cbClose.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00" }));

        cbAttr.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All Attributes", "Any Attributes" }));

        jLabel1.setText("Day of the Week");

        jLabel2.setText("From:");

        jLabel3.setText("To:");

        jLabel4.setText("Search for:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbDay, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbClose, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(cbAttr, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbOpen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbAttr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        selectSub.setText("Select SubCategory");
        selectSub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectSubActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(jList2);

        jScrollPane2.setViewportView(jList1);

        search.setText("Show Attributes");
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Selected SubCategories:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("SubCategories:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(selectSub))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel9))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(search)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selectSub)
                .addGap(21, 21, 21)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(search)
                .addGap(44, 44, 44))
        );

        selectAttr.setText("Select Attributes");
        selectAttr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAttrActionPerformed(evt);
            }
        });

        jScrollPane4.setViewportView(jList3);

        jScrollPane5.setViewportView(attrList);

        search1.setText("Search");
        search1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search1ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Attributes:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Selected Attributes:");

        searchButton1.setText("Clear");
        searchButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(selectAttr))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(search1)
                        .addGap(27, 27, 27)
                        .addComponent(searchButton1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel10)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selectAttr)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search1)
                    .addComponent(searchButton1))
                .addGap(48, 48, 48))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Main Categories:");

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Business", "City", "State", "Stars"
            }
        ));
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTable);

        jButton1.setText("Show Review");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Review Date", "Stars", "Reveiw Text", "User Name"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Search result:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Review of the business:");

        selectMain.setText("Select");
        selectMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectMainActionPerformed(evt);
            }
        });

        searchButton.setText("Clear");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        filterbystate.setText("Filter by state");
        filterbystate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterbystateActionPerformed(evt);
            }
        });

        filterbycity.setText("Filter by city");
        filterbycity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterbycityActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(selectMain)
                        .addGap(29, 29, 29)
                        .addComponent(searchButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(statecb, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filterbystate)
                                .addGap(18, 18, 18)
                                .addComponent(citycb, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(filterbycity))
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addGap(33, 33, 33)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(138, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(231, 231, 231))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(28, 28, 28)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(selectMain)
                                .addComponent(searchButton))
                            .addGap(38, 38, 38))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(statecb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(citycb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(filterbystate)
                                .addComponent(filterbycity))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jButton1)
                                    .addGap(16, 16, 16))
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12))
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void automotiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_automotiveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_automotiveActionPerformed

    private void DrugstoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DrugstoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DrugstoresActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        clear();        // TODO add your handling code here:
    }//GEN-LAST:event_searchButtonActionPerformed

    private void FlowersGiftsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FlowersGiftsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FlowersGiftsActionPerformed
    DefaultListModel model = new DefaultListModel();
    private void selectMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectMainActionPerformed
//        jCheckBox25.setText("test");
        getMainCtg();
        model.clear();
        subCtgRet.clear();
        Connection con = null;
        try{
            con = openConnection();
            StringBuffer sb = new StringBuffer();
            sb.append("select Distinct CATEGORIE from CATEGORIES where BUSINESS_ID in (select BUSINESS_ID from CATEGORIES where CATEGORIE = ?)");
            int numCate;
            for (numCate = 1; numCate < list.size(); numCate++){
                sb.append("union ");
                sb.append("select Distinct CATEGORIE from CATEGORIES where BUSINESS_ID in (select BUSINESS_ID from CATEGORIES where CATEGORIE = ?)");
            }
            sb.append(" ORDER BY CATEGORIE");
//            System.out.println(sb.toString());
//            System.out.println(list.toString());
            PreparedStatement stmt = con.prepareStatement(sb.toString());
            for (int i = 1; i <= numCate; i++){
                stmt.setString(i, list.get(i - 1).toString());
            }
            System.out.println(stmt.toString());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                subCtgRet.add(rs.getString("categorie"));
            }
            System.out.println(subCtgRet.toString());
            for (int i = 0; i < subCtgRet.size(); i++){
                model.addElement(subCtgRet.get(i));
            }
            jList1.setModel(model);
        }catch(Exception e){
            System.out.println("SQL error: " + e.getMessage());
        }finally {
            closeConnection(con);
        }
        
          
    }//GEN-LAST:event_selectMainActionPerformed
    DefaultListModel model2 = new DefaultListModel();
    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        list.addAll(subList);
        model2.clear();
        StringBuffer attrQuerry = new StringBuffer();
        attrQuerry.append("SELECT DISTINCT ATTRIBUTES FROM ATTRIBUTES WHERE BUSINESS_ID IN (" +
"SELECT BUSINESS_ID FROM CATEGORIES WHERE CATEGORIE = '");
        attrQuerry.append(list.get(0));
        attrQuerry.append("' ");
        for (int i = 1; i < list.size(); i++){
            attrQuerry.append(" UNION " +
            "SELECT BUSINESS_ID FROM CATEGORIES WHERE CATEGORIE = '");
            attrQuerry.append(list.get(i));
            attrQuerry.append("'");
        }
        attrQuerry.append(") ORDER BY ATTRIBUTES");
//        System.out.println(attrQuerry);
        Connection con = null;
    try {
        con = openConnection(); 
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(attrQuerry.toString());
        while(rs.next()){
            model2.addElement(rs.getString("attributes"));
            attrArrayList.add(rs.getString("attributes"));
        }
        attrList.setModel(model2);
    } catch (SQLException e) {
        System.err.println("Errors occurs when communicating with the database server: " + e.getMessage());
    } catch (ClassNotFoundException e) {
        System.err.println("Cannot find the database driver");
    } finally {
        closeConnection(con);
    }
    }//GEN-LAST:event_searchActionPerformed
    DefaultListModel model1 = new DefaultListModel();
    private void selectSubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectSubActionPerformed
//        System.out.println(jList1.getSelectedValue().toString());
        if (jList1.getSelectedValue() != null){
            subList.add(jList1.getSelectedValue().toString());
            model1.addElement(jList1.getSelectedValue().toString());
            jList2.setModel(model1);
        }else{
            model1.addElement("Please select subcategory");
            jList2.setModel(model1);
        }
    }//GEN-LAST:event_selectSubActionPerformed
    DefaultListModel attrSelModel = new DefaultListModel();
    private void selectAttrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAttrActionPerformed
//        System.out.println(jList1.getSelectedValue().toString());
        if (attrList.getSelectedValue() != null){
            attrSelect.add(attrList.getSelectedValue().toString());
            attrSelModel.addElement(attrList.getSelectedValue().toString());
            jList3.setModel(attrSelModel);
        }else{
            attrSelModel.addElement("Please select one or more attributes");
            jList3.setModel(attrSelModel);
        }
        
    }//GEN-LAST:event_selectAttrActionPerformed
    DefaultTableModel tModel = new DefaultTableModel();
    private void filter(){
        for (int i = 0; i < stateList.size(); i++){
            statecb.addItem(stateList.get(i));
        }
        for (int i = 0; i < cityList.size(); i++){
            citycb.addItem(cityList.get(i));
        }
        
    }
    private void search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search1ActionPerformed
        tModel = (DefaultTableModel) jTable.getModel();
        tModel.setRowCount(0);
        rowNum = 0;
        String day = (cbDay.getSelectedItem().toString());
        String open = cbOpen.getSelectedItem().toString();
        String close = cbClose.getSelectedItem().toString();
        boolean all = false;
        if (cbAttr.getSelectedItem().toString().equals("All Attributes")){
            all = true;
        }
        Connection con = null;
        try {
        con = openConnection(); 
        StringBuffer cate = new StringBuffer();
        StringBuffer attr = new StringBuffer();
        StringBuffer hours = new StringBuffer();
        hours.append("(SELECT DISTINCT BUSINESS_ID FROM HOURS WHERE OPEN <= TO_TIMESTAMP(?, 'HH24:MI') AND CLOSE >= TO_TIMESTAMP(?, 'HH24:MI') AND DAY = ?)");
        cate.append("(SELECT DISTINCT BUSINESS_ID FROM CATEGORIES WHERE CATEGORIE = ?");
        for (int i = 1; i < list.size(); i++){
            cate.append(" UNION SELECT DISTINCT BUSINESS_ID FROM CATEGORIES WHERE CATEGORIE = ?");
        }
        cate.append(")");
        attr.append("(SELECT DISTINCT BUSINESS_ID FROM ATTRIBUTES WHERE ATTRIBUTES = ?");
        if (all){
            for (int i = 1; i < attrSelect.size(); i++){
            attr.append(" INTERSECT SELECT DISTINCT BUSINESS_ID FROM ATTRIBUTES WHERE ATTRIBUTES = ?");
            }
        }else{
            for (int i = 1; i < attrSelect.size(); i++){
            attr.append(" UNION SELECT DISTINCT BUSINESS_ID FROM ATTRIBUTES WHERE ATTRIBUTES = ?");
            }
        }
        attr.append(")");
        StringBuffer total = new StringBuffer();
        total.append("SELECT NAME, CITY, STATE, STARS, BUSINESS_ID FROM BUSINESS WHERE BUSINESS_ID IN (");
        total.append(hours);
        total.append(" intersect ");
        total.append(cate);
        total.append(" intersect ");
        total.append(attr);
        total.append(")");
        System.out.println(total); 
        PreparedStatement stmt = con.prepareStatement(total.toString());  
        stmt.setString(1, open);
        System.out.println("1" + open);
        stmt.setString(2, close);
        System.out.println("2" + close);
        stmt.setString(3, day);
        System.out.println("3" + day);
        for (int i = 0; i < list.size(); i++){
            stmt.setString(i + 4, list.get(i).toString());
            System.out.println(i + 4 + " " + list.get(i).toString());
        }
        for (int i = 0; i < attrSelect.size(); i++){
            stmt.setString(i + 4 +list.size(), attrSelect.get(i).toString());
            System.out.println(i + 4 +list.size() + " "+ attrSelect.get(i).toString());
        }
        ResultSet rs = stmt.executeQuery();
        searchRet = rs;
        System.out.println("finished");
        while(rs.next()){
            rowNum++;
            System.out.println("result is:" + rs.getString("name"));
            tModel.addRow(new Object[]{rs.getString("name"), rs.getString("city"), rs.getString("state"),rs.getString("stars")});
            bidList.add(rs.getString("business_id").toString());
            stateList.add(rs.getString("state"));
            cityList.add(rs.getString("city"));
        }
        if (rowNum == 0){
            tModel.addRow(new Object[]{"No result found"});
        }
        filter();
//        jTable.setModel(tModel);
        
//        ResultSet rs = stmt.executeQuery(stmt);
//        while(rs.next()){
//            model2.addElement(rs.getString("attributes"));
//            attrArrayList.add(rs.getString("attributes"));
//        }
//        attrList.setModel(model2);
    } catch (SQLException e) {
        System.err.println("Errors occurs when communicating with the database server: " + e.getMessage());
    } catch (ClassNotFoundException e) {
        System.err.println("Cannot find the database driver");
    } finally {
        closeConnection(con);
    }
    }//GEN-LAST:event_search1ActionPerformed

    private void GroceryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GroceryMouseClicked
        
    }//GEN-LAST:event_GroceryMouseClicked

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked

    }//GEN-LAST:event_jTableMouseClicked
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
            showRet();
        }catch(SQLException e){
            System.out.println("error: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void searchButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButton1ActionPerformed
        clear();
    }//GEN-LAST:event_searchButton1ActionPerformed

    private void filterbystateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterbystateActionPerformed
        tModel = (DefaultTableModel) jTable.getModel();
        tModel.setRowCount(0);
        String day = (cbDay.getSelectedItem().toString());
        String open = cbOpen.getSelectedItem().toString();
        String close = cbClose.getSelectedItem().toString();
        boolean all = false;
        if (cbAttr.getSelectedItem().toString().equals("All Attributes")){
            all = true;
        }
        Connection con = null;
        try {
        con = openConnection(); 
        StringBuffer cate = new StringBuffer();
        StringBuffer attr = new StringBuffer();
        StringBuffer hours = new StringBuffer();
        hours.append("(SELECT DISTINCT BUSINESS_ID FROM HOURS WHERE OPEN <= TO_TIMESTAMP(?, 'HH24:MI') AND CLOSE >= TO_TIMESTAMP(?, 'HH24:MI') AND DAY = ?)");
        cate.append("(SELECT DISTINCT BUSINESS_ID FROM CATEGORIES WHERE CATEGORIE = ?");
        for (int i = 1; i < list.size(); i++){
            cate.append(" UNION SELECT DISTINCT BUSINESS_ID FROM CATEGORIES WHERE CATEGORIE = ?");
        }
        cate.append(")");
        attr.append("(SELECT DISTINCT BUSINESS_ID FROM ATTRIBUTES WHERE ATTRIBUTES = ?");
        if (all){
            for (int i = 1; i < attrSelect.size(); i++){
            attr.append(" INTERSECT SELECT DISTINCT BUSINESS_ID FROM ATTRIBUTES WHERE ATTRIBUTES = ?");
            }
        }else{
            for (int i = 1; i < attrSelect.size(); i++){
            attr.append(" UNION SELECT DISTINCT BUSINESS_ID FROM ATTRIBUTES WHERE ATTRIBUTES = ?");
            }
        }
        attr.append(")");
        StringBuffer total = new StringBuffer();
        total.append("SELECT NAME, CITY, STATE, STARS, BUSINESS_ID FROM BUSINESS WHERE BUSINESS_ID IN (");
        total.append(hours);
        total.append(" intersect ");
        total.append(cate);
        total.append(" intersect ");
        total.append(attr);
        total.append(")");
        total.append("AND STATE = ?");
        System.out.println(total); 
        PreparedStatement stmt = con.prepareStatement(total.toString());  
        stmt.setString(1, open);
        System.out.println("1" + open);
        stmt.setString(2, close);
        System.out.println("2" + close);
        stmt.setString(3, day);
        System.out.println("3" + day);
        for (int i = 0; i < list.size(); i++){
            stmt.setString(i + 4, list.get(i).toString());
            System.out.println(i + 4 + " " + list.get(i).toString());
        }
        for (int i = 0; i < attrSelect.size(); i++){
            stmt.setString(i + 4 +list.size(), attrSelect.get(i).toString());
            System.out.println(i + 4 +list.size() + " "+ attrSelect.get(i).toString());
        }
        stmt.setString(4 +list.size()+attrSelect.size(), statecb.getSelectedItem().toString());
        ResultSet rs = stmt.executeQuery();
        searchRet = rs;
        System.out.println("finished");
        while(rs.next()){
            rowNum++;
            System.out.println("result is:" + rs.getString("name"));
            tModel.addRow(new Object[]{rs.getString("name"), rs.getString("city"), rs.getString("state"),rs.getString("stars")});
            bidList.add(rs.getString("business_id").toString());
            stateList.add(rs.getString("state"));
            cityList.add(rs.getString("city"));
        }
        if (rowNum == 0){
            tModel.addRow(new Object[]{"No result found"});
        }
        filter();
    } catch (SQLException e) {
        System.err.println("Errors occurs when communicating with the database server: " + e.getMessage());
    } catch (ClassNotFoundException e) {
        System.err.println("Cannot find the database driver");
    } finally {
        closeConnection(con);
    }
    }//GEN-LAST:event_filterbystateActionPerformed

    private void filterbycityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterbycityActionPerformed
        tModel = (DefaultTableModel) jTable.getModel();
        tModel.setRowCount(0);
        String day = (cbDay.getSelectedItem().toString());
        String open = cbOpen.getSelectedItem().toString();
        String close = cbClose.getSelectedItem().toString();
        boolean all = false;
        if (cbAttr.getSelectedItem().toString().equals("All Attributes")){
            all = true;
        }
        Connection con = null;
        try {
        con = openConnection(); 
        StringBuffer cate = new StringBuffer();
        StringBuffer attr = new StringBuffer();
        StringBuffer hours = new StringBuffer();
        hours.append("(SELECT DISTINCT BUSINESS_ID FROM HOURS WHERE OPEN <= TO_TIMESTAMP(?, 'HH24:MI') AND CLOSE >= TO_TIMESTAMP(?, 'HH24:MI') AND DAY = ?)");
        cate.append("(SELECT DISTINCT BUSINESS_ID FROM CATEGORIES WHERE CATEGORIE = ?");
        for (int i = 1; i < list.size(); i++){
            cate.append(" UNION SELECT DISTINCT BUSINESS_ID FROM CATEGORIES WHERE CATEGORIE = ?");
        }
        cate.append(")");
        attr.append("(SELECT DISTINCT BUSINESS_ID FROM ATTRIBUTES WHERE ATTRIBUTES = ?");
        if (all){
            for (int i = 1; i < attrSelect.size(); i++){
            attr.append(" INTERSECT SELECT DISTINCT BUSINESS_ID FROM ATTRIBUTES WHERE ATTRIBUTES = ?");
            }
        }else{
            for (int i = 1; i < attrSelect.size(); i++){
            attr.append(" UNION SELECT DISTINCT BUSINESS_ID FROM ATTRIBUTES WHERE ATTRIBUTES = ?");
            }
        }
        attr.append(")");
        StringBuffer total = new StringBuffer();
        total.append("SELECT NAME, CITY, STATE, STARS, BUSINESS_ID FROM BUSINESS WHERE BUSINESS_ID IN (");
        total.append(hours);
        total.append(" intersect ");
        total.append(cate);
        total.append(" intersect ");
        total.append(attr);
        total.append(")");
        total.append("AND CITY = ?");
        System.out.println(total); 
        PreparedStatement stmt = con.prepareStatement(total.toString());  
        stmt.setString(1, open);
        System.out.println("1" + open);
        stmt.setString(2, close);
        System.out.println("2" + close);
        stmt.setString(3, day);
        System.out.println("3" + day);
        for (int i = 0; i < list.size(); i++){
            stmt.setString(i + 4, list.get(i).toString());
            System.out.println(i + 4 + " " + list.get(i).toString());
        }
        for (int i = 0; i < attrSelect.size(); i++){
            stmt.setString(i + 4 +list.size(), attrSelect.get(i).toString());
            System.out.println(i + 4 +list.size() + " "+ attrSelect.get(i).toString());
        }
        stmt.setString(4 +list.size()+attrSelect.size(), citycb.getSelectedItem().toString());
        System.out.println("selected: " + citycb.getSelectedItem().toString());
        ResultSet rs = stmt.executeQuery();
        searchRet = rs;
        System.out.println("finished");
        while(rs.next()){
            rowNum++;
            System.out.println("result is:" + rs.getString("name"));
            tModel.addRow(new Object[]{rs.getString("name"), rs.getString("city"), rs.getString("state"),rs.getString("stars")});
            bidList.add(rs.getString("business_id").toString());
            stateList.add(rs.getString("state"));
            cityList.add(rs.getString("city"));
        }
        if (rowNum == 0){
            tModel.addRow(new Object[]{"No result found"});
        }
        filter();
    } catch (SQLException e) {
        System.err.println("Errors occurs when communicating with the database server: " + e.getMessage());
    } catch (ClassNotFoundException e) {
        System.err.println("Cannot find the database driver");
    } finally {
        closeConnection(con);
    }
    }//GEN-LAST:event_filterbycityActionPerformed

    /**
     * @param args the command line arguments
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
            java.util.logging.Logger.getLogger(Hw3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Hw3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Hw3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Hw3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Hw3().setVisible(true);
            }
        });
    }
    
    private Connection openConnection() throws SQLException, ClassNotFoundException {
// Load the Oracle database driver
DriverManager.registerDriver(new oracle.jdbc.OracleDriver());


String host = "localhost";
String port = "1521";
String dbName = "orcl";    
String userName = "system";  
String password = "123456";

// Construct the JDBC URL
String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;
return DriverManager.getConnection(dbURL, userName, password);
}

/**
* Close the database connection
* @param con
*/


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox DepartmentStores;
    private javax.swing.JCheckBox Drugstores;
    private javax.swing.JCheckBox Education;
    private javax.swing.JCheckBox EventPlanningServices;
    private javax.swing.JCheckBox FlowersGifts;
    private javax.swing.JCheckBox Food;
    private javax.swing.JCheckBox Grocery;
    private javax.swing.JCheckBox HardwareStores;
    private javax.swing.JCheckBox HealthMedical;
    private javax.swing.JCheckBox HomeGarden;
    private javax.swing.JCheckBox HomeServices;
    private javax.swing.JCheckBox Hospitals;
    private javax.swing.JCheckBox HotelsTravel;
    private javax.swing.JCheckBox MedicalCenters;
    private javax.swing.JCheckBox Nightlife;
    private javax.swing.JCheckBox NurseriesGardening;
    private javax.swing.JCheckBox Restaurants;
    private javax.swing.JCheckBox Shopping;
    private javax.swing.JCheckBox Transportation;
    private javax.swing.JCheckBox activeLife;
    private javax.swing.JCheckBox artsEntertainment;
    private javax.swing.JList attrList;
    private javax.swing.JCheckBox automotive;
    private javax.swing.JCheckBox beautySpas;
    private javax.swing.JCheckBox cafes;
    private javax.swing.JCheckBox carRental;
    private javax.swing.JComboBox cbAttr;
    private javax.swing.JComboBox cbClose;
    private javax.swing.JComboBox cbDay;
    private javax.swing.JComboBox cbOpen;
    private javax.swing.JComboBox citycb;
    private javax.swing.JCheckBox convenienceStores;
    private javax.swing.JCheckBox dentists;
    private javax.swing.JCheckBox doctors;
    private javax.swing.JButton filterbycity;
    private javax.swing.JButton filterbystate;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton search;
    private javax.swing.JButton search1;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton searchButton1;
    private javax.swing.JButton selectAttr;
    private javax.swing.JButton selectMain;
    private javax.swing.JButton selectSub;
    private javax.swing.JComboBox statecb;
    // End of variables declaration//GEN-END:variables
}

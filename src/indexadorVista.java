//ProyectoFinal
import com.sun.awt.AWTUtilities;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Andres&Valeria
 */
public class indexadorVista extends javax.swing.JFrame {
    int x,y;
    DefaultListModel model; //Cargar Archivos Lista
    DefaultListModel model2; // Resultado Lista
    DefaultListModel model3; // cargarCompleta Lista
    DefaultTableModel model4; // Estadistica Tabla
    DefaultListModel model5; //Excepciones Lista
    List<File> files;
    List<File> cargar;
    
    public indexadorVista() {
        this.model = new DefaultListModel();
        this.model2 = new DefaultListModel();
        this.model3 = new DefaultListModel();
        this.model4 = new DefaultTableModel();
        this.model5 = new DefaultListModel();
        
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch(Exception e){
            System.out.println(e);
        }
        
        initComponents();
        cargarModelos();
        AWTUtilities.setWindowOpaque(this,false);
        modifyLabel();
        modifyTextArea();
    }
    
    public void cargarModelos(){
        cargarArchivosList.setModel(model);
        resultadoLista.setModel(model2);
        cargaCompletaList.setModel(model3);
        model4 = (DefaultTableModel)estadistica.getModel();
        exList.setModel(model5);
    }
    
    public void setLista(List<File> lista){
        this.cargar = lista;
    }
    
    public void modifyLabel(){
        
        TransferHandler th;
        th = new TransferHandler(){
            @Override
            public boolean canImport(JComponent jc, DataFlavor[] dfs) {
                return true; 
            }
            
            @Override
            public boolean importData(JComponent jc, Transferable t) {
                try{
                    files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
                    for(File file: files){                          
                        model.addElement(file);                            
                    }
                }
                catch(UnsupportedFlavorException | IOException ex){
                    Logger.getLogger(indexadorVista.class.getName()).log(Level.SEVERE,null,ex);
                }
                return true;
            }
        };
        contenedor.setTransferHandler(th);
    }

    public void modifyTextArea(){        
        
        resultadoLista.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent event) {
                    if (!event.getValueIsAdjusting()){
                        JList source = (JList)event.getSource();

                        if(source.getSelectedValue() != null){
                            for(File file: cargar){                          
                               if(file.getName().equals(source.getSelectedValue().toString())){
                                   try {
                                       visualizarArchivos(file);
                                   } catch (IOException ex) {
                                       Logger.getLogger(indexadorVista.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                               }
                            }   
                        }
                            
                    }
                }
         });               
    }
    
    public void visualizarArchivos(File file) throws IOException{
        String cadena = "";
        String texto = "";
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
            texto += cadena + "\n";
        }
        b.close();
        mostrarArchivo.setText(texto);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        minimizar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        exList = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        cargaCompletaList = new javax.swing.JList<>();
        eliminarCargaLista = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        cargarLista = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        cargarArchivosList = new javax.swing.JList<>();
        contenedor = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        cargarArchivosList1 = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultadoLista = new javax.swing.JList<>();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        btnHacerConsulta = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        palabraBuscar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnEliminarConsulta = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        estadistica = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        mostrarArchivo = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jLabel4.setText("jLabel4");
        jLabel4.setPreferredSize(new java.awt.Dimension(50, 54));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Indexador");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1079, 696));
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1080, 700));
        jPanel1.setPreferredSize(new java.awt.Dimension(1080, 696));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cerrar.png"))); // NOI18N
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1031, 5, 30, 30));

        minimizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/minimizar.png"))); // NOI18N
        minimizar.setBorder(null);
        minimizar.setBorderPainted(false);
        minimizar.setContentAreaFilled(false);
        minimizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        minimizar.setFocusPainted(false);
        minimizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minimizarActionPerformed(evt);
            }
        });
        jPanel1.add(minimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 5, 30, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/title.png"))); // NOI18N
        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel1MouseDragged(evt);
            }
        });
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -10, 1110, 70));

        jTabbedPane1.setForeground(new java.awt.Color(43, 45, 66));
        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(1080, 660));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1080, 660));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1080, 610));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane7.setBorder(null);

        exList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        exList.setForeground(new java.awt.Color(204, 51, 0));
        exList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(exList);

        jPanel2.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 610, 80));

        jScrollPane3.setBorder(null);

        cargaCompletaList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cargaCompletaList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(cargaCompletaList);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 440, 610, 120));

        eliminarCargaLista.setBorder(null);
        eliminarCargaLista.setBorderPainted(false);
        eliminarCargaLista.setContentAreaFilled(false);
        eliminarCargaLista.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        eliminarCargaLista.setFocusPainted(false);
        eliminarCargaLista.setFocusable(false);
        eliminarCargaLista.setPreferredSize(new java.awt.Dimension(159, 40));
        eliminarCargaLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarCargaListaActionPerformed(evt);
            }
        });
        jPanel2.add(eliminarCargaLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(864, 59, -1, -1));

        jButton6.setBorder(null);
        jButton6.setBorderPainted(false);
        jButton6.setContentAreaFilled(false);
        jButton6.setFocusPainted(false);
        jButton6.setFocusable(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(855, 50, -1, -1));

        cargarLista.setBorder(null);
        cargarLista.setBorderPainted(false);
        cargarLista.setContentAreaFilled(false);
        cargarLista.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cargarLista.setFocusPainted(false);
        cargarLista.setFocusable(false);
        cargarLista.setPreferredSize(new java.awt.Dimension(159, 40));
        jPanel2.add(cargarLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(864, 138, -1, -1));

        jButton8.setBorder(null);
        jButton8.setBorderPainted(false);
        jButton8.setContentAreaFilled(false);
        jButton8.setFocusPainted(false);
        jButton8.setFocusable(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(855, 130, -1, -1));

        jScrollPane4.setBorder(null);
        jScrollPane4.setAlignmentX(0.2F);
        jScrollPane4.setAlignmentY(0.2F);
        jScrollPane4.setFocusTraversalPolicyProvider(true);
        jScrollPane4.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N

        cargarArchivosList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cargarArchivosList.setForeground(new java.awt.Color(43, 45, 66));
        cargarArchivosList.setSelectionBackground(new java.awt.Color(53, 160, 232));
        cargarArchivosList.setSelectionForeground(new java.awt.Color(254, 254, 254));
        jScrollPane4.setViewportView(cargarArchivosList);

        jPanel2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 540, 120));

        contenedor.setAutoscrolls(true);
        contenedor.setFocusable(false);
        contenedor.setRequestFocusEnabled(false);
        jPanel2.add(contenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 180, 180));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JAVAGUI1v2.png"))); // NOI18N
        jLabel9.setFocusable(false);
        jLabel9.setRequestFocusEnabled(false);
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, -1));

        jScrollPane6.setBorder(null);
        jScrollPane6.setAlignmentX(0.2F);
        jScrollPane6.setAlignmentY(0.2F);
        jScrollPane6.setFocusTraversalPolicyProvider(true);
        jScrollPane6.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N

        cargarArchivosList1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cargarArchivosList1.setForeground(new java.awt.Color(43, 45, 66));
        cargarArchivosList1.setSelectionBackground(new java.awt.Color(53, 160, 232));
        cargarArchivosList1.setSelectionForeground(new java.awt.Color(254, 254, 254));
        jScrollPane6.setViewportView(cargarArchivosList1);

        jPanel2.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 540, 120));

        jTabbedPane1.addTab("CARGAR ARCHIVOS", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMinimumSize(new java.awt.Dimension(1080, 610));
        jPanel3.setPreferredSize(new java.awt.Dimension(1080, 610));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setAlignmentX(0.2F);
        jScrollPane1.setAlignmentY(0.2F);
        jScrollPane1.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N

        resultadoLista.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        resultadoLista.setForeground(new java.awt.Color(43, 45, 66));
        resultadoLista.setSelectionBackground(new java.awt.Color(53, 160, 232));
        resultadoLista.setSelectionForeground(new java.awt.Color(254, 254, 254));
        jScrollPane1.setViewportView(resultadoLista);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, 430, 170));
        jScrollPane1.getAccessibleContext().setAccessibleParent(jScrollPane1);

        jRadioButton3.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jRadioButton3.setText("Buscar Palabra");
        jRadioButton3.setBorder(null);
        jRadioButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(jRadioButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        jRadioButton2.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jRadioButton2.setText("NOT");
        jRadioButton2.setBorder(null);
        jRadioButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, -1, -1));

        jRadioButton1.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jRadioButton1.setText("OR");
        jRadioButton1.setBorder(null);
        jRadioButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, -1, -1));

        jRadioButton4.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jRadioButton4.setText("AND");
        jRadioButton4.setBorder(null);
        jRadioButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(jRadioButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        btnHacerConsulta.setBorderPainted(false);
        btnHacerConsulta.setContentAreaFilled(false);
        btnHacerConsulta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHacerConsulta.setFocusPainted(false);
        btnHacerConsulta.setPreferredSize(new java.awt.Dimension(84, 40));
        btnHacerConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHacerConsultaActionPerformed(evt);
            }
        });
        jPanel3.add(btnHacerConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 194, -1, -1));

        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setFocusPainted(false);
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, -1));

        palabraBuscar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        palabraBuscar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 153, 255)));
        jPanel3.add(palabraBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 200, 20));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 8)); // NOI18N
        jLabel8.setText("INTRODUCE LAS PALABRAS A CONSULTAR");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, 20));

        btnEliminarConsulta.setBorder(null);
        btnEliminarConsulta.setBorderPainted(false);
        btnEliminarConsulta.setContentAreaFilled(false);
        btnEliminarConsulta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarConsulta.setFocusPainted(false);
        btnEliminarConsulta.setPreferredSize(new java.awt.Dimension(84, 40));
        btnEliminarConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarConsultaActionPerformed(evt);
            }
        });
        jPanel3.add(btnEliminarConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 194, -1, -1));

        jButton9.setBorder(null);
        jButton9.setBorderPainted(false);
        jButton9.setContentAreaFilled(false);
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton9.setFocusPainted(false);
        jPanel3.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, -1, -1));

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane5.setBorder(null);
        jScrollPane5.setFocusable(false);
        jScrollPane5.setRequestFocusEnabled(false);

        estadistica.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        estadistica.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Palabra", "Frecuencia", "Documento", "Apariciones", "Linea/s"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(estadistica);

        jPanel3.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, 440, 230));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);

        mostrarArchivo.setColumns(20);
        mostrarArchivo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        mostrarArchivo.setRows(5);
        mostrarArchivo.setBorder(null);
        mostrarArchivo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        mostrarArchivo.setEnabled(false);
        jScrollPane2.setViewportView(mostrarArchivo);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 320, 440, 240));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JAVAUGUI2v2.png"))); // NOI18N
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 1080, 630));

        jTabbedPane1.addTab("CONSULTAS", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setMinimumSize(new java.awt.Dimension(1080, 610));
        jPanel4.setPreferredSize(new java.awt.Dimension(1080, 610));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JAVAGUI3v2.png"))); // NOI18N
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 610));

        jTabbedPane1.addTab("CREDITOS", jPanel4);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 60, 1110, 640));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jLabel5.setFocusable(false);
        jLabel5.setRequestFocusEnabled(false);
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHacerConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHacerConsultaActionPerformed
    //Aqui hacer el metodo de busqueda 
    }//GEN-LAST:event_btnHacerConsultaActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseDragged
        // TODO add your handling code here:
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y -y);
    }//GEN-LAST:event_jLabel1MouseDragged

    private void minimizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minimizarActionPerformed
        // TODO add your handling code here:
        setExtendedState(ICONIFIED);
    }//GEN-LAST:event_minimizarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnEliminarConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarConsultaActionPerformed
        
        
        
    }//GEN-LAST:event_btnEliminarConsultaActionPerformed

    private void eliminarCargaListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarCargaListaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eliminarCargaListaActionPerformed
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnEliminarConsulta;
    public javax.swing.JButton btnHacerConsulta;
    private javax.swing.ButtonGroup buttonGroup1;
    public javax.swing.JList<String> cargaCompletaList;
    public javax.swing.JList<String> cargarArchivosList;
    public javax.swing.JList<String> cargarArchivosList1;
    public javax.swing.JButton cargarLista;
    public javax.swing.JLabel contenedor;
    public javax.swing.JButton eliminarCargaLista;
    public javax.swing.JTable estadistica;
    public javax.swing.JList<String> exList;
    private javax.swing.JButton jButton1;
    public javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    public javax.swing.JRadioButton jRadioButton1;
    public javax.swing.JRadioButton jRadioButton2;
    public javax.swing.JRadioButton jRadioButton3;
    public javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JButton minimizar;
    public javax.swing.JTextArea mostrarArchivo;
    public javax.swing.JTextField palabraBuscar;
    public javax.swing.JList<String> resultadoLista;
    // End of variables declaration//GEN-END:variables

}

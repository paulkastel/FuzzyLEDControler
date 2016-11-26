
import com.fuzzylite.Engine;
import com.fuzzylite.defuzzifier.Bisector;
import com.fuzzylite.defuzzifier.WeightedAverage;
import com.fuzzylite.norm.s.AlgebraicSum;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.AlgebraicProduct;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Ramp;
import com.fuzzylite.term.Trapezoid;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;
import java.awt.Color;
import java.text.DecimalFormat;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kastel
 */
public class LEDWindow extends javax.swing.JFrame {

    Engine engine;
    InputVariable inRed;
    InputVariable inGreen;
    InputVariable inBlue;
    OutputVariable outlum;
    OutputVariable outColor;
    public double redCol, greenCol, blueCol, lux;

    /**
     * Creates new form NewJFrame
     */
    public LEDWindow() {
        initComponents();
        lux = redCol = greenCol = blueCol = 0;
        initFuzzy();
    }

    private void initFuzzy() {
        engine = new Engine();
        engine.setName("DiodaLED_RGB");

        inRed = new InputVariable();
        inRed.setEnabled(true);
        inRed.setName("red");
        inRed.setRange(0.000, 5.000);
        inRed.addTerm(new Ramp("high", 0.000, 5.000));
        inRed.addTerm(new Triangle("mid", 0.000, 2.500, 5.000));
        inRed.addTerm(new Ramp("low", 5.000, 0.000));
        engine.addInputVariable(inRed);

        inGreen = new InputVariable();
        inGreen.setEnabled(true);
        inGreen.setName("green");
        inGreen.setRange(0.000, 5.000);
        inGreen.addTerm(new Ramp("high", 0.000, 5.000));
        inGreen.addTerm(new Triangle("mid", 0.000, 2.500, 5.000));
        inGreen.addTerm(new Ramp("low", 5.000, 0.000));
        engine.addInputVariable(inGreen);

        inBlue = new InputVariable();
        inBlue.setEnabled(true);
        inBlue.setName("blue");
        inBlue.setRange(0.000, 5.000);
        inBlue.addTerm(new Ramp("high", 0.000, 5.000));
        inBlue.addTerm(new Triangle("mid", 0.000, 2.500, 5.000));
        inBlue.addTerm(new Ramp("low", 5.000, 0.000));
        engine.addInputVariable(inBlue);

        outlum = new OutputVariable();
        outlum.setEnabled(true);
        outlum.setName("lum");
        outlum.setRange(0.000, 600.000);
        outlum.fuzzyOutput().setAccumulation(new Maximum());
        outlum.setDefuzzifier(new WeightedAverage());
        outlum.setDefaultValue(0.000);
        outlum.setLockPreviousOutputValue(false);
        outlum.setLockOutputValueInRange(true);
        outlum.addTerm(new Ramp("max", 0.000, 600.000));
        engine.addOutputVariable(outlum);

        outColor = new OutputVariable();
        outColor.setEnabled(true);
        outColor.setName("ledcolor");
        outColor.setRange(-30.000, 440.000);
        outColor.fuzzyOutput().setAccumulation(new AlgebraicSum());
        outColor.setDefuzzifier(new Bisector(250000));
        outColor.setDefaultValue(0);
        outColor.setLockPreviousOutputValue(false);
        outColor.setLockOutputValueInRange(false);
        outColor.addTerm(new Triangle("red", -30.000, 0.000, 30.000));
        outColor.addTerm(new Triangle("orange", 0.000, 30.000, 60.000));
        outColor.addTerm(new Triangle("yellow", 30.000, 60.000, 90.000));
        outColor.addTerm(new Triangle("lime", 60.000, 90.000, 120.000));
        outColor.addTerm(new Triangle("green", 90.000, 120.000, 150.000));
        outColor.addTerm(new Triangle("turq", 120.000, 150.000, 180.000));
        outColor.addTerm(new Triangle("cyan", 150.000, 180.000, 210.000));
        outColor.addTerm(new Triangle("ocean", 180.000, 210.000, 240.000));
        outColor.addTerm(new Triangle("blue", 210.000, 240.000, 270.000));
        outColor.addTerm(new Triangle("purple", 240.000, 270.000, 300.000));
        outColor.addTerm(new Triangle("magenta", 270.000, 300.000, 330.000));
        outColor.addTerm(new Triangle("rasp", 300.000, 330.000, 360.000));
        outColor.addTerm(new Trapezoid("white", 360.000, 370.000, 440.000, 440.000));
        engine.addOutputVariable(outColor);

        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.setEnabled(true);
        ruleBlock.setName("");
        ruleBlock.setConjunction(new AlgebraicProduct());
        ruleBlock.setDisjunction(new AlgebraicSum());
        ruleBlock.setActivation(new AlgebraicProduct());
        ruleBlock.addRule(Rule.parse("if red is high and green is low and blue is low then ledcolor is red", engine));
        ruleBlock.addRule(Rule.parse("if red is high and green is mid and blue is low then ledcolor is orange", engine));
        ruleBlock.addRule(Rule.parse("if red is high and green is high and blue is low then ledcolor is yellow", engine));
        ruleBlock.addRule(Rule.parse("if red is mid and green is high and blue is low then ledcolor is lime", engine));
        ruleBlock.addRule(Rule.parse("if red is low and green is high and blue is low then ledcolor is green", engine));
        ruleBlock.addRule(Rule.parse("if red is low and green is high and blue is mid then ledcolor is turq", engine));
        ruleBlock.addRule(Rule.parse("if red is low and green is high and blue is high then ledcolor is cyan", engine));
        ruleBlock.addRule(Rule.parse("if red is low and green is mid and blue is high then ledcolor is ocean", engine));
        ruleBlock.addRule(Rule.parse("if red is low and green is low and blue is high then ledcolor is blue", engine));
        ruleBlock.addRule(Rule.parse("if red is mid and green is low and blue is high then ledcolor is purple", engine));
        ruleBlock.addRule(Rule.parse("if red is high and green is low and blue is high then ledcolor is magenta", engine));
        ruleBlock.addRule(Rule.parse("if red is high and green is low and blue is mid then ledcolor is rasp", engine));
        ruleBlock.addRule(Rule.parse("if red is high and green is high and blue is high then ledcolor is white and lum is max", engine));
        ruleBlock.addRule(Rule.parse("if red is mid and green is mid and blue is mid then ledcolor is white", engine));
        ruleBlock.addRule(Rule.parse("if red is high then lum is max", engine));
        ruleBlock.addRule(Rule.parse("if green is high then lum is max", engine));
        ruleBlock.addRule(Rule.parse("if blue is high then lum is max", engine));
        engine.addRuleBlock(ruleBlock);

    }

    private void setAppColor() {
        int r = (int) (redCol * 51);
        int g = (int) (greenCol * 51);
        int b = (int) (blueCol * 51);
        int a = (int) (lux * 0.425);
        Color RGB = new Color(r, g, b, a);
        this.getContentPane().setBackground(RGB);
        this.repaint();
    }

    private double valueChange(JSlider sld, JLabel lbl) {
        double value = (double) sld.getValue() / 100;
        lbl.setText(String.valueOf(value) + " V");
        return value;
    }

    private void fuzzyDoIt() {
        StringBuilder status = new StringBuilder();
        if (!engine.isReady(status)) {
            throw new RuntimeException("Engine not ready. "
                    + "The following errors were encountered:\n" + status.toString());
        }
        inBlue.setInputValue(blueCol);
        inGreen.setInputValue(greenCol);
        inRed.setInputValue(redCol);

        DecimalFormat df1 = new DecimalFormat("#0.0");
        DecimalFormat df2 = new DecimalFormat("#0.00");
        engine.process();
        lblLux.setText(df1.format(outlum.getOutputValue()));
        lux = outlum.getOutputValue();
        lblDegree.setText(df2.format(outColor.getOutputValue()));
        //lblColor.setText(df2.format(outColor.fuzzyOutput().activationDegree(outColor.getTerm("red"))));

        checkName(outColor.getOutputValue(), -1, 30, "red", "orange");
        checkName(outColor.getOutputValue(), 30, 60, "orange", "yellow");
        checkName(outColor.getOutputValue(), 60, 90, "yellow", "lime");
        checkName(outColor.getOutputValue(), 90, 120, "lime", "green");
        checkName(outColor.getOutputValue(), 120, 150, "green", "turq");
        checkName(outColor.getOutputValue(), 150, 180, "turq", "cyan");
        checkName(outColor.getOutputValue(), 180, 210, "cyan", "ocean");
        checkName(outColor.getOutputValue(), 210, 240, "ocean", "blue");
        checkName(outColor.getOutputValue(), 240, 270, "blue", "purple");
        checkName(outColor.getOutputValue(), 270, 300, "purple", "rasp");
        checkName(outColor.getOutputValue(), 300, 330, "rasp", "magenta");
        checkName(outColor.getOutputValue(), 360, 420, "magenta", "white");
    }

    private void checkName(double deg, double l_val, double r_val, String l_name, String r_name) {

        DecimalFormat df = new DecimalFormat("#0.000");

        if (deg > l_val && deg < r_val) {
            if (outColor.fuzzyOutput().activationDegree(outColor.getTerm(r_name)) > outColor.fuzzyOutput().activationDegree(outColor.getTerm(l_name))) {
                lblColor.setText(r_name + " " + df.format(outColor.fuzzyOutput().activationDegree(outColor.getTerm(r_name))));
            } else {
                lblColor.setText(l_name + " " + df.format(outColor.fuzzyOutput().activationDegree(outColor.getTerm(l_name))));
            }
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblRed = new javax.swing.JLabel();
        lblGreen = new javax.swing.JLabel();
        lblBlue = new javax.swing.JLabel();
        slider_Red = new javax.swing.JSlider();
        slider_Green = new javax.swing.JSlider();
        slider_Blue = new javax.swing.JSlider();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblColor = new javax.swing.JLabel();
        lblLux = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblDegree = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LED Fuzzy Controller");
        setMaximumSize(new java.awt.Dimension(470, 250));
        setMinimumSize(new java.awt.Dimension(470, 250));
        setPreferredSize(new java.awt.Dimension(470, 250));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("RED:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 11, 50, 40);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("GREEN:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 62, 50, 40);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("BLUE:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 110, 50, 40);

        lblRed.setText("0.0 V");
        getContentPane().add(lblRed);
        lblRed.setBounds(410, 10, 34, 40);

        lblGreen.setText("0.0 V");
        getContentPane().add(lblGreen);
        lblGreen.setBounds(410, 60, 34, 40);

        lblBlue.setText("0.0 V");
        getContentPane().add(lblBlue);
        lblBlue.setBounds(410, 110, 34, 40);

        slider_Red.setMajorTickSpacing(100);
        slider_Red.setMaximum(500);
        slider_Red.setMinorTickSpacing(10);
        slider_Red.setPaintTicks(true);
        slider_Red.setValue(0);
        slider_Red.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slider_RedStateChanged(evt);
            }
        });
        getContentPane().add(slider_Red);
        slider_Red.setBounds(70, 10, 326, 40);

        slider_Green.setMajorTickSpacing(100);
        slider_Green.setMaximum(500);
        slider_Green.setMinorTickSpacing(10);
        slider_Green.setPaintTicks(true);
        slider_Green.setValue(0);
        slider_Green.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slider_GreenStateChanged(evt);
            }
        });
        getContentPane().add(slider_Green);
        slider_Green.setBounds(70, 60, 326, 40);

        slider_Blue.setMajorTickSpacing(100);
        slider_Blue.setMaximum(500);
        slider_Blue.setMinorTickSpacing(10);
        slider_Blue.setPaintTicks(true);
        slider_Blue.setValue(0);
        slider_Blue.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slider_BlueStateChanged(evt);
            }
        });
        getContentPane().add(slider_Blue);
        slider_Blue.setBounds(70, 110, 326, 40);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("COLOR:");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(40, 170, 60, 20);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("LUX:");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(330, 170, 40, 20);

        lblColor.setText("0");
        getContentPane().add(lblColor);
        lblColor.setBounds(100, 170, 100, 20);

        lblLux.setText("0");
        getContentPane().add(lblLux);
        lblLux.setBounds(370, 170, 40, 20);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("DEGREE:");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(200, 170, 60, 20);

        lblDegree.setText("0");
        getContentPane().add(lblDegree);
        lblDegree.setBounds(270, 170, 40, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void slider_BlueStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider_BlueStateChanged
        blueCol = valueChange(slider_Blue, lblBlue);
        fuzzyDoIt();
        setAppColor();
    }//GEN-LAST:event_slider_BlueStateChanged

    private void slider_GreenStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider_GreenStateChanged
        greenCol = valueChange(slider_Green, lblGreen);
        fuzzyDoIt();
        setAppColor();
    }//GEN-LAST:event_slider_GreenStateChanged

    private void slider_RedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider_RedStateChanged
        redCol = valueChange(slider_Red, lblRed);
        fuzzyDoIt();
        setAppColor();
    }//GEN-LAST:event_slider_RedStateChanged

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
            java.util.logging.Logger.getLogger(LEDWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LEDWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LEDWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LEDWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LEDWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblBlue;
    private javax.swing.JLabel lblColor;
    private javax.swing.JLabel lblDegree;
    private javax.swing.JLabel lblGreen;
    private javax.swing.JLabel lblLux;
    private javax.swing.JLabel lblRed;
    private javax.swing.JSlider slider_Blue;
    private javax.swing.JSlider slider_Green;
    private javax.swing.JSlider slider_Red;
    // End of variables declaration//GEN-END:variables
}

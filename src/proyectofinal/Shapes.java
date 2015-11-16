/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pv_expominipaint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 *
 * @author Karlos
 */
public class Shapes {
    
    GraphicsContext[] gcs;
    int fig;
    
    Shapes(GraphicsContext[] gcs){
        this.gcs = gcs;
    }
   
    public void drawRectangle(double x,double y,double w,double h, boolean stroke){
        if(stroke){
            gcs[fig].strokeRect(x, x, w, h);
        }else
            gcs[fig].fillRect(x, x, w, h);
    }
    
    
    public void setColor(Color cl){
        gcs[fig].setFill(cl);
        gcs[fig].setStroke(cl);
    }
    
    public void setFig(int fig){
        this.fig = fig;
    }
}

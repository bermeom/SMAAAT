/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BESAFile.Data;

/**
 *
 * @author berme_000
 */
public class Vector3D {
    
    private double x;
    private double y;
    private double z;

    public Vector3D() {
        this.x=this.y=this.y=0;
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return  "x= " + x + ", y= " + y + ", z= " + z;
    }
    
    
    
    
    
}

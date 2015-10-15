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
    
    public Vector3D normalize(){
           double h=Math.sqrt(x*x+y*y+z*z);
           return new Vector3D(x/h, y/h, z/h);
    }
    
    
    
    private double floor(double a){
        
        double f=Math.abs(a);
        double b=0;
        if(f>0.41){
             b=1;
        }
        if(a<0){
            f=-b;
        }else{
            f=b;
        }
        return f;
        
    }
    
    public Vector3D normalize1(){
           double h=Math.sqrt(x*x+z*z);
           return new Vector3D((floor(x/h)), (y), (floor(z/h)));
    }
    
    private boolean compareDistance(double a, double b,double delta){
        return Math.abs(a-b)<=delta;
    } 
    
    public boolean isEquals(Vector3D v,double delta){
            return  compareDistance(this.x,v.getX(), delta)&&compareDistance(this.y,v.getY(), delta)&&compareDistance(this.z,v.getZ(), delta);
    
    }
    
    
}

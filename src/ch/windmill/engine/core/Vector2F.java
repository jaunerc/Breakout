/*
 * The MIT License
 *
 * Copyright 2016 Cyrill Jauner.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ch.windmill.engine.core;

/**
 * This class represents a 2 dimensional vector with float coordinates.
 * @author Cyrill Jauner
 */
public class Vector2F {
    
    public float x, y;
    
    /**
     * Creates a new Vector2F object.
     */
    public Vector2F() {
        this(0,0);
    }
    
    /**
     * Creates a new Vector2F object.
     * @param x coordinate.
     * @param y coordinate.
     */
    public Vector2F(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Returns a new vector that is v multiplied with d.
     * @param v vector.
     * @param d scalar.
     * @return new vector object.
     */
    public static Vector2F multiply(Vector2F v, float d) {
        return new Vector2F(v.x * d, v.y * d);
    }
    
    /**
     * Returns a new vector that is v added with d.
     * @param v vector
     * @param d scalar.
     * @return new vector object.
     */
    public static Vector2F add(Vector2F v, float d) {
        return new Vector2F(v.x + d, v.y + d);
    }
    
    /**
     * Returns a new vector that is v1 subtracted with v2.
     * @param v1 vector 1.
     * @param v2 vector 2.
     * @return new vector object.
     */
    public static Vector2F sub(Vector2F v1, Vector2F v2) {
        return new Vector2F(v1.x - v2.x, v1.y - v2.y);
    }
    
    /**
     * Returns a new vector that is the dot product of v1 and v2.
     * @param v1
     * @param v2
     * @return 
     */
    public static double dot(Vector2F v1, Vector2F v2) {
        return (v1.x * v2.x + v1.y * v2.y);
    }
    
    public static Vector2F copyOf(Vector2F v) {
        return new Vector2F(v.x, v.y);
    }
    
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    /**
     * Adds v to this.
     * @param v 
     */
    public void add(Vector2F v) {
        x += v.x;
        y += v.y;
    }
    
    /**
     * Subtracts v to this.
     * @param v 
     */
    public void sub(Vector2F v) {
        x -= v.x;
        y -= v.y;
    }
    
    /**
     * Multiplies d to this.
     * @param d 
     */
    public void mul(float d) {
        x *= d;
        y *= d;
    }
    
    /**
     * Multiplies d to this x coordinate.
     * @param d 
     */
    public void mulX(float d) {
        x *= d;
    }
    
    /**
     * Multiplies d to this x coordinate.
     * @param d 
     */
    public void mulY(float d) {
        y *= d;
    }
    
    /**
     * Gets the length of this.
     * @return scalar value that is the length of this.
     */
    public float length() {
        return (float)Math.sqrt((x*x + y*y));
    }
    
    /**
     * Gets the squared length of this.
     * @return scalar value that is the squared length of this.
     */
    public float lengthSquared() {
        return (x*x + y*y);
    }
    
    /**
     * Normalizes this vector.
     */
    public void normalize() {
        float len = length();
        if(len != 0.0f) {
            x = x / len;
            y = y / len;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vector2F other = (Vector2F) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        return true;
    }
}

package application;

import javafx.scene.canvas.GraphicsContext;

public class Orbit {
	double radius;
	double posX;
	double posY;
	double angleSpeed;
	double angle;
	Orbit parentOrbit;
	Orbit childOrbit;
	int k = -4;
	int n;
	
	public Orbit(double _radius, double _posX, double _posY, int _n) {
		this(_radius, _posX, _posY, _n, null);
	}
	
	public Orbit(double _radius, double _posX, double _posY, int _n, Orbit _parent) {
		radius = _radius;
		posX = _posX;
		posY = _posY;
		n = _n;
		parentOrbit = _parent;
		childOrbit = null;
		angleSpeed = Math.toRadians(Math.pow(k, n - 1)) / Constants.resolution;
		angle = Math.PI / 2;
	}
	
	public void show(GraphicsContext gc) {
		gc.strokeOval(posX - radius, posY - radius, radius * 2, radius * 2);
	}
	
	public Orbit addChild() {
		double newRadius = radius / 3;
		double newX = posX + radius + newRadius;
		double newY = posY;
		childOrbit = new Orbit(newRadius, newX, newY, n + 1, this);
		return childOrbit;
	}
	
	public void update() {
		Orbit parent = this.parentOrbit;
	    if (parent != null) {
	      this.angle += this.angleSpeed;
	      var rsum = this.radius + parent.radius;
	      this.posX = parent.posX + rsum * Math.cos(this.angle);
	      this.posY = parent.posY + rsum * Math.sin(this.angle);
	    }
	}
}

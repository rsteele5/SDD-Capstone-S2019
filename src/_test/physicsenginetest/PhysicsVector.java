class PhysicsVector{
    double x, y;
    private double mass;

    public PhysicsVector(double x, double y) {
        this.x = x;
        this.y = y;
        this.mass = 1;
    }

    private double magnitude(double nX, double nY) {
        return Math.sqrt(nX * nX + nY * nY);
    }

    private double direction(double nX, double nY) {
        return Math.atan(nY/nX); //NOTE: The direction will be in radians
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void normalize() {
        double nMag = magnitude(x, y);
        if (nMag == 0) {
            x = 0;
            y = 0;
        } else {
            x /= nMag;
            y /= nMag;
        }
    }

    public void add(PhysicsVector vector) {
        x += vector.x;
        y += vector.y;
    }

    public void minus(PhysicsVector vector) {
        x -= vector.x;
        y -= vector.y;
    }

    public void mult(double scalar) {
        x *= scalar;
        y *= scalar;
    }

    public void div(double scalar) {
        x /= scalar;
        y /= scalar;
    }

    //Newton's 2nd Law of Motion: Acceleration = Force / Mass
    public void applyForce(PhysicsVector vector) {
        vector.div(this.mass);
        this.add(vector);
    }

    public PhysicsVector calculateFriction(PhysicsVector velocity) {
        double coefficientOfFriction = 0.01; //Note: This value depends on the surface
        double normalForce = 0.1;  //Hardcoded for now, should ideally be calculated
        double frictionMag = coefficientOfFriction * normalForce;
        PhysicsVector friction = new PhysicsVector(velocity.x * -1, velocity.y * -1);
        friction.normalize();
        friction.mult(frictionMag);
        return friction;
    }
}

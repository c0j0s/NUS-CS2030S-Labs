class Point {
    private final double x;
    private final double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Return a new point with coord of midpoint
     */
    Point midPoint(Point q) {
        // midpoint = [(x1 + x2)/2, (y1 + y2)/2] 
        return new Point((this.x + q.x) / 2, (this.y + q.y) / 2);
    }

    /**
     * return the angle of line form by this.point and given point w.r.t x-axis
     * angle = arctan(y2 - y1/x2 - x1)
     */
    double angleTo(Point q) {
        // angle = arctan(y2 - y1/x2 - x1)
        return Math.atan2(q.y - this.y, q.x - this.x);
    }

    /**
     * Return a new point given angle and distance
     * d = sqrt(x^2 + y^2)
     * newX = x + d cos(theta) -> the adjacent length
     * newY = y + d sin(theta) -> the opposite length
     */
    Point moveTo(double theta, double d) {
        return new Point(this.x + d * Math.cos(theta), this.y + d * Math.sin(theta));
    }

    /**
    * Return the distance between 2 points
    */
    double distanceTo(Point q) {
        return Math.sqrt(Math.pow(q.x - this.x, 2) + Math.pow(q.y - this.y, 2));
    }

    @Override
    public String toString() {
        return String.format("point (%.3f, %.3f)", this.x, this.y);
    }     
}

class Circle {
    private final Point origin;
    private final double radius;

    Circle(Point p, double r) {
        this.origin = p;
        this.radius = r;
    }
    
    boolean isPointWithin(Point p) {
        return origin.distanceTo(p) <= radius;
    }

    @Override
    public String toString() {
        return String.format("circle of radius %.1f centered at %s",this.radius, this.origin);
    }
}

